package com.moon.android.launcher.thailand;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Html;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.moon.android.launcher.thai.R;
import com.moon.android.launcher.thailand.Constant.NavItem;
import com.moon.android.launcher.thailand.adapter.NavAdapter;
import com.moon.android.launcher.thailand.database.CountryDAO;
import com.moon.android.launcher.thailand.model.LauncherMsg;
import com.moon.android.launcher.thailand.model.Regionlimit;
import com.moon.android.launcher.thailand.model.UpdateData;
import com.moon.android.launcher.thailand.util.ActivityUtils;
import com.moon.android.launcher.thailand.util.Logcat;
import com.moon.android.launcher.thailand.util.Logger;
import com.moon.android.launcher.thailand.util.AppUtils.AppInfo;
import com.moon.android.launcher.thailand.view.AdScrollView;
import com.moon.android.launcher.thailand.view.AutoScrollTextView;
import com.moon.android.launcher.thailand.view.CustomToast;
import com.moon.android.launcher.thailand.view.RegionLimitPrompt;
import com.moon.android.launcher.thailand.view.StatusBar;

public class LauncherActivity extends Activity implements OnKeyListener{
	
	public static final Logger log = Logger.getInstance();
	public static final String APPSTORE_PKG = "com.moon.appstore";
	public static final String NAMVOD = "com.mooncloud.android.namvod";
	public static final String NAMLIVE = "com.moonlive.android.Vitnam";
	public static final String NAM_ENTERTAINMENT = "com.vn.entertainment.vod";
	public static final String NAM_MUSIC = "com.vn.music.vod";
	public static final String NAM_VIET_KIDS = "com.vn.viet.kids";
	public static final String VOD_CLOUND = "com.mooncloud.android.iptv";
	public static final String ILIVE_HK = "com.moonlivehk.android.iptv";
	public static final String ILIVE = "com.moonlive.android.iptv";
	public static final String HK_DRAMAS = "com.mooncloud.android.looktvb";
	public static final String FPYPLAY = "com.fptplay.activity";
	public static final String YOUTUBE = "com.google.android.youtube";
	public static final String CN_DRAMA = "com.moon.serviesvod2.shenzhou";
	
	public static final String SETTING_M4 = "com.mbx.settingsmbox";

	public static final String XLS_NAME = "mac_auto_get_20140818.xls";
	public static final String SAVE_MAC_FILE_PATH = Environment
			.getExternalStorageDirectory().getPath() + XLS_NAME;

	private GridView mGridNavigation;
	public static final int COUNTRY_CHOOSE_COLUMN = 3;
	public static final int COUNTRY_CHOOSE_ROW = 2;
	private CountryDAO mCountryDAO;
	private List<NavItem> mListNavItem;
//	private TextView mTextBanner;
	private StatusBar mStatusBar;
	private AdScrollView mAdScrollView;
	
	private List<String> mListInitApp;
	private AutoScrollTextView mScrollText;
	public static final int START_DOWNLOAD = 101;

	public static final String UPGRADE_PATH = "/data"
			+ Environment.getDataDirectory().getAbsolutePath() + "/"
			+ Configs.THIS_APP_PACKAGE_NAME+ "/";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome2);
		initWidget();
		
		new Thread(new Runnable() {
			public void run() {
				Logcat.printLogcat();
			}
		}).start();
		
		regBroadCast();
		regRegionLimitBroad();
		Declare.VALIDITY_TIME = ValiditySharepreference.getHasRead(this);
		
		//region limit
		mWindowManager = (WindowManager) getApplicationContext()
				.getSystemService(Context.WINDOW_SERVICE);
		mLayoutParam = new LayoutParams();
		mRegionPrompt = new RegionLimitPrompt(this);
				
		Intent intent = new Intent(this,MsgService.class);
		startService(intent);
		int isReaded = DisclaimerSharepreference.getHasRead(this);
		if(isReaded == DisclaimerSharepreference.NOT_READ){
		
			ActivityUtils.startActivity(this, LanguageActivity.class);
		}
		
	}


	private void initWidget() {
		mGridNavigation = (GridView) findViewById(R.id.welcome_country_grid);
		mStatusBar = (StatusBar) findViewById(R.id.statusbar);
		mScrollText = (AutoScrollTextView) findViewById(R.id.auto_scroll_text);
//		mScrollText.setText(getString(R.string.index_ad));
		if(null != Declare.listLauncherMsg && Declare.listLauncherMsg.size() > 0){
			mScrollText.setText(Declare.listLauncherMsg);
		} 
//		else mScrollText.setText(getString(R.string.index_ad));
		mAdScrollView = (AdScrollView) findViewById(R.id.ad_scroll_view);
		mAdScrollView.restart();
		mListNavItem = Constant.getLauncherNavItems();
		mGridNavigation.setNumColumns(mListNavItem.size());
		mGridNavigation.setOnKeyListener(this);
		mGridNavigation.requestFocus();
		mGridNavigation.requestFocusFromTouch();
		NavAdapter countryAdapter = new NavAdapter(this,mListNavItem);
		mGridNavigation.setAdapter(countryAdapter);
		mGridNavigation.setOnItemClickListener(mGridClitener);
	//	mGridNavigation.requestFocus();
	//	mGridNavigation.requestFocusFromTouch();
	}

	
	private OnItemClickListener mGridClitener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View view,
				int position, long arg3) {
			NavItem item = mListNavItem.get(position);
			boolean isLocal = item.isLocalActivity;
			int appTag = item.subTag;
			if(isLocal){
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), item.clazz);
				intent.putExtra(Configs.PARAM_1, appTag);
				intent.putExtra(Configs.PARAM_2, item.nameRes);
				startActivity(intent);
			} else {
				try{
					NavItem item2 = mListNavItem.get(position);
					String pkg = item2.pkgName;
					ActivityUtils.startActivity(LauncherActivity.this, pkg);
				}catch(Exception e){
					new CustomToast(getApplicationContext(), getString(R.string.app_not_found), 24).show();
				}
			}
		}
	};
	
	public static final int DIRECT_MAINACTIVITY = 1;
	public static final int SHOW_DIALOG = 2;
	public Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case DIRECT_MAINACTIVITY:
				ActivityUtils.startActivity(LauncherActivity.this,
						MainActivity.class);
				LauncherActivity.this.finish();
//				if(null != dialog && dialog.isShowing())
//					dialog.dismiss();
				break;
			case SHOW_DIALOG:
				break;
			case START_DOWNLOAD:
				update();
				break;
			default:
				;
			}
		};
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		mStatusBar.unRegReceiver();
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if(v == mGridNavigation && event.getAction() == KeyEvent.ACTION_DOWN){
			switch(keyCode){
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				if(mGridNavigation.getSelectedItemPosition() == mGridNavigation.getCount() - 1){
					mGridNavigation.setSelection(0);
					return true;
				} 
				break;
			case KeyEvent.KEYCODE_DPAD_LEFT:
				if(mGridNavigation.getSelectedItemPosition() == 0){
					mGridNavigation.setSelection(mGridNavigation.getCount() - 1);
					return true;
				} 
				break;
			}
		}
		return false;
	}
	
	
	private void regBroadCast() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction(Configs.BroadCastConstant.GET_LAUNCHER_MSG);
		myIntentFilter.addAction(Configs.BroadCastConstant.ACTION_UPGRADE);
		registerReceiver(mBroadcastReceiver, myIntentFilter);
	}
	
	private List<LauncherMsg> mListLauncherMsg;
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){
		@Override
		public void onReceive(Context arg0, Intent intent) {
			if(intent.getAction().equals(Configs.BroadCastConstant.GET_LAUNCHER_MSG)){
//				mListLauncherMsg = (List<LauncherMsg>) intent.getSerializableExtra(Configs.PARAM_1);
				mListLauncherMsg = Declare.listLauncherMsg;
				if(null != mListLauncherMsg && mListLauncherMsg.size() > 0)
					mScrollText.setText(Declare.listLauncherMsg);
			} else if(intent.getAction().equals(Configs.BroadCastConstant.ACTION_UPGRADE)){
				UpdateData updata = LauncherApplication.updateData;
				String appUrl = updata.getUrl();
				String msg = updata.getMsg();
				initPopWindow(appUrl, msg);
			} 
		}
	};
	
	private String mUpdateUrl;
	private String mUpdateMsg;
	private PopupWindow mUpdatePopupWindow;
	private Button mBtnSubmit;
	private Button mBtnCancel;
	private void initPopWindow(final String appUrl,
			String msg) {
		mUpdateUrl = appUrl;
		mUpdateMsg = msg;
		View view = LayoutInflater.from(this).inflate(R.layout.update_dialog, null);
		mBtnSubmit = (Button) view.findViewById(R.id.dialog_submit);
		mBtnCancel = (Button) view.findViewById(R.id.dialog_cancel);
		mBtnSubmit.setOnClickListener(mDialogClickListener);
		mBtnCancel.setOnClickListener(mDialogClickListener);
		TextView textContent = (TextView) view.findViewById(R.id.text_content);
		Spanned text = Html.fromHtml(mUpdateMsg); 
		textContent.setText(text);
		
		int width = getWindowManager().getDefaultDisplay().getWidth();
		int height = getWindowManager().getDefaultDisplay().getHeight();
		mUpdatePopupWindow = new PopupWindow(view,width,height,true);
		mUpdatePopupWindow.showAsDropDown(view,0,0);
		mUpdatePopupWindow.setOutsideTouchable(false);
	}
	
	private void regRegionLimitBroad() {
		IntentFilter regionLimitFilter = new IntentFilter();
		regionLimitFilter.addAction(Configs.RegionLimit.ACTION_REGION_LIMIT);
		registerReceiver(mReceiverRegionLimit, regionLimitFilter);
	}
	
	private void dismissRegionLimitPrompt(){
		if(null != mRegionPrompt)
			try{
				mWindowManager.removeView(mRegionPrompt);
			}catch(Exception e){
				e.printStackTrace();
			}
	}
	
	private BroadcastReceiver mReceiverRegionLimit = new BroadcastReceiver(){
		@Override
		public void onReceive(Context context, Intent intent) {
			Regionlimit regionLimit =  (Regionlimit) intent.getSerializableExtra(Configs.PARAM_1);
			String code = regionLimit.getCode();
			String msg = regionLimit.getMsg();
			log.i("接到区域受限的广播  code = " + code + " msg = " + msg);
			if(Configs.RegionLimit.STATUS_AUTH_SUCCESS.equals(code)){
				dismissRegionLimitPrompt();
			} else if(Configs.RegionLimit.STATUS_AUTH_FAIL.equals(code)){
				showRegionLimitPrompt(msg);
			} else if(Configs.RegionLimit.STATUS_AUTH_VALIDITY_OUT.equals(code)){
				showRegionLimitPrompt(msg);
			} else if(Configs.RegionLimit.STATUS_AUTH_REGION_LIMIT.equals(code)){
				showRegionLimitPrompt(msg);
			}
		}
	};
	
	private WindowManager mWindowManager;
	private LayoutParams mLayoutParam;
	private RegionLimitPrompt mRegionPrompt;
	private void showRegionLimitPrompt(String text) {
		mLayoutParam.type = LayoutParams.TYPE_PHONE;
		mLayoutParam.alpha = PixelFormat.RGB_888;
		mLayoutParam.alpha = 0.9f;
		mLayoutParam.x = 0;
		mLayoutParam.y = 0;
		
		mLayoutParam.width = WindowManager.LayoutParams.MATCH_PARENT;
		mLayoutParam.height = WindowManager.LayoutParams.MATCH_PARENT;
		mRegionPrompt.setPromptText(text);
		try{
			mWindowManager.addView(mRegionPrompt, mLayoutParam);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private OnClickListener mDialogClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			mUpdatePopupWindow.dismiss();
			if(v == mBtnSubmit){
				new CustomToast(LauncherActivity.this, getString(R.string.startdownload), 28).show();
				downUpgradeApk(mUpdateUrl);
			}
		}
	};
	
	private void downUpgradeApk(final String paramString) {
		new Thread() {
			public void run() {
				try {
					DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
					HttpGet localHttpGet = new HttpGet(paramString.trim());
					HttpEntity localHttpEntity = localDefaultHttpClient
							.execute(localHttpGet).getEntity();
					localHttpEntity.getContentLength();
					InputStream localInputStream = localHttpEntity.getContent();
					FileOutputStream localFileOutputStream = null;
					byte[] arrayOfByte;
					if (localInputStream != null) {
						localFileOutputStream = openFileOutput(Configs.APK_NAME,1);
						arrayOfByte = new byte[1024];
						int j = 0;
						while ((j = localInputStream.read(arrayOfByte)) != -1) {
							localFileOutputStream.write(arrayOfByte, 0, j);
						}
						localFileOutputStream.flush();
					}
					if (localFileOutputStream != null)
						localFileOutputStream.close();
					mHandler.sendEmptyMessage(START_DOWNLOAD);
					return;
				} catch (ClientProtocolException localClientProtocolException) {
					localClientProtocolException.printStackTrace();
					return;
				} catch (IOException localIOException) {
					localIOException.printStackTrace();
				} catch(Exception e){
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	private void update() {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(UPGRADE_PATH + "/files/",
				Configs.APK_NAME)),
                        "application/vnd.android.package-archive");
        startActivity(intent);
	}
}
