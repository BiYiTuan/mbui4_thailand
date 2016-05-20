package com.moon.android.launcher.thailand.view;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.moon.android.launcher.thai.R;
import com.moon.android.launcher.thailand.Configs;
import com.moon.android.launcher.thailand.database.AppDAO;
import com.moon.android.launcher.thailand.util.AppUtils;
import com.moon.android.launcher.thailand.util.CustomAppInfo;
import com.moon.android.launcher.thailand.util.Logger;

@SuppressLint("NewApi")
public class AppManagerGrid extends LinearLayout implements OnKeyListener{
	
	private Logger logger = Logger.getInstance();
	private LinearLayout mArrowLeft;
	private LinearLayout mArrowRight;
	private GridView mGridApps;
	private Context mContext;
	private int mColumnNum = 3;
	private int mRows = 4;
	private int mCurrentPage = 1;
	private int mAppNumPerPage;
	private int mTotalPage = 1;
	private AppDAO mAppDAOI;
	private List<CustomAppInfo> mListAppInfo;
	private PageIndicatorView mPageView;
	private boolean isDoOperationInCurrentPage = false;
	public static final String PACKAGE_REMOVED = "android.intent.action.PACKAGE_REMOVED";
	public static final String  PACKAGE_ADDED = "android.intent.action.PACKAGE_ADDED";
	public static final int ACTION_UPDATE_DESKTOP = 0x1000002;
	public AppManagerGrid(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		View view = LayoutInflater.from(context).inflate(R.layout.apps_manage_view, this);
		mArrowLeft = (LinearLayout) view.findViewById(R.id.arrow_left);
		mArrowRight = (LinearLayout) view.findViewById(R.id.arrow_right);
		mGridApps = (GridView) view.findViewById(R.id.page_app_gridview);
		mPageView = (PageIndicatorView) view.findViewById(R.id.ad_page_num);
		mArrowLeft.setOnClickListener(mArrowClickListener);
		mArrowRight.setOnClickListener(mArrowClickListener);
		mGridApps.setOnKeyListener(this);
		mAppDAOI = new AppDAO(context);
		
		setAdapter(AppUtils.loadApplications(context,false));
		
		regApkOp();
	}
	
	private void regApkOp() {
	    IntentFilter filter = new IntentFilter();
	    filter.addAction(PACKAGE_ADDED); 
	    filter.addAction(PACKAGE_REMOVED);  
	    filter.addDataScheme("package");  
	    mContext.registerReceiver(mAppOperateReceiver, filter);
	}
	
	public void unRegApkOp(){
		if(null != mAppOperateReceiver){
			mContext.unregisterReceiver(mAppOperateReceiver);
		}
	}
	
	private BroadcastReceiver mAppOperateReceiver = new BroadcastReceiver(){
		@Override
		public void onReceive(Context context, Intent intent) {
			List<CustomAppInfo> list = AppUtils.loadApplications(mContext,Configs.isShowSystemApp());
			String packageName = intent.getData().getSchemeSpecificPart();
			CustomAppInfo appInfo = new CustomAppInfo();
			if(PACKAGE_ADDED.equals(intent.getAction()) ){
				appInfo.pkgName = packageName;
				logger.i("获取到安装应用的广播   pkg = " + appInfo.pkgName);
				AppDAO appDAO = new AppDAO(mContext);
				if(appDAO.canInsert(appInfo)){
					logger.i("桌面未满，可以添加到桌面");
					appDAO.insert(mContext, appInfo);
					Intent intentToDeskTop = new Intent(Configs.BroadCastConstant.ACTION_TO_DESKTOP);
					mContext.sendBroadcast(intentToDeskTop);
				}
			}
			setAdapter(list);
			if(PACKAGE_REMOVED.equals(intent.getAction())){
				appInfo.pkgName = packageName;
				AppDAO appDAO = new AppDAO(mContext);
				appDAO.delete(appInfo);
				Intent intentDesk = new Intent(Configs.BroadCastConstant.ACTION_UPDATE_DESKTOP);
				mContext.sendBroadcast(intentDesk);
			}
		}
	};
	
	private OnClickListener mArrowClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (v == mArrowLeft) {
				if(1 == mCurrentPage) new CustomToast(mContext, getResources().getString(R.string.has_to_first), Configs.TOAST_TEXT_SIZE).show();
				else turnPage(true);
			} else if (v == mArrowRight) {
				if(mTotalPage == mCurrentPage)new CustomToast(mContext, getResources().getString(R.string.has_to_last), Configs.TOAST_TEXT_SIZE).show();
				else turnPage(false);
			}
		}
	};
	
	public AppManagerGrid(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public AppManagerGrid(Context context) {
		this(context,null);
	}
	
	public void setAdapter(List<CustomAppInfo> list){
		List<CustomAppInfo> appInfoInDesktop = new ArrayList<CustomAppInfo>();
		List<CustomAppInfo> appInfoNoInDesktop = new ArrayList<CustomAppInfo>();
		for(CustomAppInfo appInfo : list){
			if(mAppDAOI.isExist(appInfo)) {
				appInfo.isDesktop = true;
				appInfoInDesktop.add(appInfo); 
			}
			else appInfoNoInDesktop.add(appInfo);
		}
		appInfoInDesktop.addAll(appInfoNoInDesktop);
		mGridApps.setNumColumns(mColumnNum);
		mListAppInfo = appInfoInDesktop;
		mTotalPage = caculaterPages(appInfoInDesktop);
		mAppNumPerPage = mColumnNum * mRows;
		if(mCurrentPage == 0) 
			mCurrentPage = 1;
		if(mCurrentPage >= mTotalPage){
			mCurrentPage = mTotalPage;
		}
		if(appInfoInDesktop.size() >= 0){
			fillGridView(mCurrentPage);
		}
	}
	
	private void fillGridView(int currentPage) {
		List<CustomAppInfo> listAppInfo = new ArrayList<CustomAppInfo>();
		for (int i = (currentPage - 1) * mAppNumPerPage; i < currentPage
				* mAppNumPerPage; i++) {
			try {
				listAppInfo.add(mListAppInfo.get(i));
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}
		if (0 <= listAppInfo.size()) {
			AppAdminAdapter appsAdapter = new AppAdminAdapter(mContext, listAppInfo ,this);
			mGridApps.setAdapter(appsAdapter);
			mPageView.setTotalPage(mTotalPage);
			mPageView.setCurrentPage(mCurrentPage-1); 
			mPageView.invalidate();
		}
	}
	
	private int caculaterPages(List<CustomAppInfo> list){
		int appSize = list.size();
		int mAppsNum = mColumnNum * mRows;
		if (0 == appSize % mAppsNum)
			return appSize / mAppsNum;
		else
			return appSize / mAppsNum + 1;
	}
	
	public void setNumColumns(int columns){
		mColumnNum = columns;
		mGridApps.setNumColumns(columns);
	}
	
	public void setNumRows(int rows){
		mRows = rows;
	}
	
	public void setArrowVisibility(int visible){
		mArrowLeft.setVisibility(visible);
		mArrowRight.setVisibility(visible);
	}
	
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if (v == mGridApps && event.getAction() == KeyEvent.ACTION_DOWN) {
			int gridSelection = mGridApps.getSelectedItemPosition();
			switch (keyCode) {
			case KeyEvent.KEYCODE_DPAD_RIGHT: 
				if (gridSelection + (mAppNumPerPage * (mCurrentPage-1)) + 1 == mListAppInfo.size()){
					return true;}
				
				if ((gridSelection + 1) % mColumnNum == 0 ) {
					if(mCurrentPage == mTotalPage) return true;
					turnPage(false);
					int nextSelection = gridSelection - (mColumnNum - 1);
					mGridApps.setSelection(nextSelection);
					return true;
				}
				break;
			case KeyEvent.KEYCODE_DPAD_LEFT:
				if (gridSelection %  mColumnNum == 0) {
					if (mCurrentPage == 0 || mCurrentPage == 1){
						return true;
					}
					else{
						turnPage(true);
						int nextSelection = gridSelection + (mColumnNum - 1);
						mGridApps.setSelection(nextSelection);
						return true;
					}
				}
				break;
			case KeyEvent.KEYCODE_ENTER:
			case KeyEvent.KEYCODE_DPAD_CENTER:
				try{
					View view = mGridApps.getSelectedView();
					Button toTop = (Button) view.findViewById(AppAdminAdapter.TO_DESKTOP_ID);
					Button uninstall = (Button) view.findViewById(AppAdminAdapter.UNSTALL_ID);
					toTop.setFocusable(true);
					uninstall.setFocusable(true);
					view.requestFocus();
					return true;
				}catch(Exception e){
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		}
		return false;
	}

	private void turnPage(boolean direction){
		if(direction){
			mCurrentPage--;
		} else {
			mCurrentPage++;
		}
		if(isDoOperationInCurrentPage){
			setAdapter(AppUtils.loadApplications(mContext,Configs.isShowSystemApp()));
		}
		fillGridView(mCurrentPage);
		isDoOperationInCurrentPage = false;
	}
	
	public class AppAdminAdapter extends BaseAdapter{
		private List<CustomAppInfo> mListApps;
		private LayoutInflater mInflater;
		private AppDAO mAppDAO;
		private Context mContext;
		private AppManagerGrid mAppManagerGrid;
		public AppAdminAdapter(Context context,List<CustomAppInfo> list, AppManagerGrid appManagerGrid){
			mListApps = list;
			mInflater = LayoutInflater.from(context);
			mAppDAO = new AppDAO(context);
			mContext = context;
			mAppManagerGrid = appManagerGrid;
		}
		@Override
		public int getCount() {
			return mListApps.size();
		}

		@Override
		public Object getItem(int position) {
			return mListApps.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			Holder holder = null;
			if(null == convertView){
				holder = new Holder();
				convertView = mInflater.inflate(R.layout.app_manager_item, null);
				holder.appIcon = (ImageView) convertView.findViewById(R.id.app_item_imageview);
				holder.appName = (TextView) convertView.findViewById(R.id.app_item_name);
				holder.toDeskTop = (Button) convertView.findViewById(R.id.app_mamager_item_btn_toindex);
				holder.unstall = (Button) convertView.findViewById(R.id.app_mamager_item_btn_unstall);
				holder.version = (TextView) convertView.findViewById(R.id.app_item_version);
				if(0 == position){
					holder.toDeskTop.setTag(0x11000011);
				}
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			
			CustomAppInfo appInfo = mListApps.get(position);
			int deskTopRes = appInfo.isDesktop ? R.string.cancel_to_desktp : R.string.to_desktp;
			int deskTopBackRes = appInfo.isDesktop ? R.drawable.btn_app_op_blue : R.drawable.btn_app_op;
//			int textColorRes = appInfo.isInDesktop() ? R.color.app_admin_text_color_todesktop : R.color.app_admin_text_color;
			holder.toDeskTop.setText(deskTopRes);
			holder.toDeskTop.setBackgroundResource(deskTopBackRes);
			holder.unstall.setBackgroundResource(R.drawable.btn_app_op);
//			holder.toDeskTop.setTextColor(mContext.getResources().getColorStateList(textColorRes));
			holder.appIcon.setImageDrawable(appInfo.icon);
			holder.appName.setText(appInfo.title);
			holder.toDeskTop.setOnClickListener(mToDeskTopListener);
			holder.unstall.setOnClickListener(mUnstallListener);
			holder.toDeskTop.setTag(appInfo);
			holder.unstall.setTag(appInfo);
			holder.toDeskTop.setId(TO_DESKTOP_ID);
			holder.unstall.setId(UNSTALL_ID);
			holder.unstall.setOnKeyListener(mKeyListener);
			holder.toDeskTop.setOnKeyListener(mKeyListener);
			String vesrion = appInfo.versionName;
			holder.version.setText(vesrion);
			
			final Button btn1 = holder.toDeskTop;
			final Button btn2 = holder.unstall;
			
			if(appInfo.isSystemApp)
				btn2.setVisibility(View.INVISIBLE);
			
			holder.toDeskTop.setOnFocusChangeListener(new OnFocusChangeListener() {
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					btnToDeskTop = btn1;
					btnUnstall = btn2;
				}
			});
			
			holder.unstall.setOnFocusChangeListener(new OnFocusChangeListener() {
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					btnToDeskTop = btn1;
					btnUnstall = btn2;
				}
			});
			return convertView;
		}
		
		Button btnToDeskTop;
		Button btnUnstall;
		
		public static final int TO_DESKTOP_ID = 0x112;
		public static final int UNSTALL_ID = 0x113;
		private OnKeyListener mKeyListener = new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(event.getAction() == KeyEvent.ACTION_DOWN){
					if(TO_DESKTOP_ID == v.getId()){
						if(keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
							CustomAppInfo appInfo = (CustomAppInfo) v.getTag();
							boolean returnBoolean = appInfo.isSystemApp ? true : false;
							return returnBoolean;
						}
						if(keyCode == KeyEvent.KEYCODE_DPAD_CENTER ||
								keyCode == KeyEvent.KEYCODE_ENTER) {
							toDeskTop(v);
							return true;
						}
						if(keyCode == KeyEvent.KEYCODE_BACK) {
							mAppManagerGrid.mGridApps.requestFocus();
							if(null != btnToDeskTop && null != btnUnstall){
								btnToDeskTop.setFocusable(false);
								btnUnstall.setFocusable(false);
							}
						}
					} else if(UNSTALL_ID == v.getId()){
						if(keyCode == KeyEvent.KEYCODE_DPAD_LEFT) return false;
						if(keyCode == KeyEvent.KEYCODE_DPAD_CENTER
								|| keyCode == KeyEvent.KEYCODE_ENTER) uninstallApk(v);
						if(keyCode == KeyEvent.KEYCODE_BACK) {
							mAppManagerGrid.mGridApps.requestFocus();
							if(null != btnToDeskTop && null != btnUnstall){
								btnToDeskTop.setFocusable(false);
								btnUnstall.setFocusable(false);
							}
						}
					} 
				}
				return true;
			}
		};
		
		
		private void toDeskTop(View v){
			CustomAppInfo appInfo = (CustomAppInfo) v.getTag();
			if(mAppDAO.isExist(appInfo)){
				mAppDAO.delete(appInfo);
				((Button)v).setText(R.string.to_desktp);
//				int deskTopBackRes = appInfo.isInDesktop() ? R.drawable.btn_app_op_blue : R.drawable.btn_app_op;
//				int textColorRes = appInfo.isInDesktop() ? R.color.app_admin_text_color_todesktop : R.color.app_admin_text_color;
				v.setBackgroundResource(R.drawable.btn_app_op);
//				((Button)v).setTextColor(mContext.getResources().getColorStateList(R.color.app_admin_text_color));
			} else if(mAppDAO.canInsert(appInfo)){
				boolean isSuccess = mAppDAO.insert(mContext, appInfo);
				if(isSuccess) {
					((Button)v).setText(R.string.cancel_to_desktp);
					v.setBackgroundResource(R.drawable.btn_app_op_blue);
//					((Button)v).setTextColor(mContext.getResources().getColorStateList(R.color.app_admin_text_color_todesktop));
				}
			} else {
//				showAlertDialog();
				showExitWindow();
			}
			isDoOperationInCurrentPage = true;
			Intent intent = new Intent(Configs.BroadCastConstant.ACTION_TO_DESKTOP);
			mContext.sendBroadcast(intent);
		} 
		
		private OnClickListener mToDeskTopListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				toDeskTop(v);  
			}
		};
		
//		public void showAlertDialog() {
//			CustomDialog.Builder builder = new CustomDialog.Builder(mContext);
//			builder.setMessage(R.string.jiong);
//			builder.setTitle(R.string.prompt);
//			builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//				public void onClick(DialogInterface dialog, int which) {
//					dialog.dismiss();
//				}
//			});
//			builder.create().show();
//		}
		
		 private PopupWindow mExitPopupWindow;
		 private void showExitWindow(){
		    	LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    	WindowManager mWm = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
		    	View view = mInflater.inflate(R.layout.p_exit_pop, null);
		    	int width =  mWm.getDefaultDisplay().getWidth();
				int height = mWm.getDefaultDisplay().getHeight();
				mExitPopupWindow = new PopupWindow(view,width,height,true);
				mExitPopupWindow.showAsDropDown(view,0,0);
				mExitPopupWindow.setOutsideTouchable(false);
				Button sure = (Button) view.findViewById(R.id.p_eixt_sure);
				sure.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						exitPopDismiss();
					}
				});
		    }
		    
		    private void exitPopDismiss(){
		    	if(null != mExitPopupWindow && mExitPopupWindow.isShowing())
		    		mExitPopupWindow.dismiss();
		    }
		   
		private void uninstallApk(View v){
			CustomAppInfo appInfo = (CustomAppInfo) v.getTag();
			uninstallAPK(appInfo.pkgName);
		}
		
		private OnClickListener mUnstallListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				uninstallApk(v);
			}
		};
		
		public void uninstallAPK(final String packageName){  
			new Thread(){
				public void run() {
					Uri uri=Uri.parse("package:"+packageName);  
					Intent intent=new Intent(Intent.ACTION_DELETE,uri);  
					mContext.startActivity(intent);  
				};
			}.start();
	    }  
		
		class Holder {
			ImageView appIcon;
			TextView appName;
			Button toDeskTop;
			Button unstall;
			TextView version;
		}
	}
	
}
