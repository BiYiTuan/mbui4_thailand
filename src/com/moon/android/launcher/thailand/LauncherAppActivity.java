package com.moon.android.launcher.thailand;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.moon.android.launcher.thai.R;
import com.moon.android.launcher.thailand.Constant.NavItem;
import com.moon.android.launcher.thailand.adapter.NavAppAdapter;
import com.moon.android.launcher.thailand.database.CountryDAO;
import com.moon.android.launcher.thailand.util.ActivityUtils;
import com.moon.android.launcher.thailand.util.AppUtils;
import com.moon.android.launcher.thailand.util.Logger;
import com.moon.android.launcher.thailand.util.AppUtils.AppInfo;
import com.moon.android.launcher.thailand.view.AdScrollView;
import com.moon.android.launcher.thailand.view.AutoScrollTextView;
import com.moon.android.launcher.thailand.view.CustomToast;
import com.moon.android.launcher.thailand.view.StatusBar;

public class LauncherAppActivity extends Activity implements OnKeyListener{
	
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

	private List<String> mListInitApp;
	private List<AppInfo> mListAppInfo;
	private AutoScrollTextView mScrollText;

	private int mSubTag;
	private AdScrollView mAdScrollView;
	private int mTitle;
	private TextView mTextTitle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sub_app);
		Intent intent2 = getIntent();
		mSubTag = intent2.getIntExtra(Configs.PARAM_1, 0);
		mTitle = intent2.getIntExtra(Configs.PARAM_2,0);
		log.i("sub tag " + mSubTag);
		initWidget();
		
		Intent intent = new Intent(this,MsgService.class);
		startService(intent);
		
	}


	private void initWidget() {
		mGridNavigation = (GridView) findViewById(R.id.welcome_country_grid);
		mStatusBar = (StatusBar) findViewById(R.id.statusbar);
		mScrollText = (AutoScrollTextView) findViewById(R.id.auto_scroll_text);
		
		mAdScrollView = (AdScrollView) findViewById(R.id.ad_scroll_view);
		mAdScrollView.restart();
		
		mTextTitle = (TextView) findViewById(R.id.title);
//		if(0 != mTitle)
//			mTextTitle.setText(">> "+ getText(mTitle));
//		if(null != Declare.listLauncherMsg && Declare.listLauncherMsg.size() > 0){
//			String body = Declare.listLauncherMsg.get(0).getBody();
//			if(null != body)
//				mScrollText.setText(body);
//		}
		if(null != Declare.listLauncherMsg && Declare.listLauncherMsg.size() > 0){
			mScrollText.setText(Declare.listLauncherMsg);
		} 
//		else mScrollText.setText(getString(R.string.index_ad));
		
		
		mListNavItem = Constant.getNavItemByTag(mSubTag);
		mListAppInfo = AppUtils.getAppsByNavItem(getApplicationContext(), mListNavItem);
		
		mGridNavigation.setNumColumns(8);
		mGridNavigation.setOnKeyListener(this);
		mGridNavigation.requestFocus();
		mGridNavigation.requestFocusFromTouch();
		NavAppAdapter countryAdapter = new NavAppAdapter(this,
				mListAppInfo);
		mGridNavigation.setAdapter(countryAdapter);
		mGridNavigation.setOnItemClickListener(mGridClitener);
		mGridNavigation.requestFocus();
		mGridNavigation.requestFocusFromTouch();
	}

	private OnItemClickListener mGridClitener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View view,
				int position, long arg3) {
			
			NavItem item = mListNavItem.get(position);
			boolean isLocal = item.isLocalActivity;
			if(isLocal && null != item.clazz){
				if(item.clazz == LauncherActivity.class){
					finish();
				} else {
					Intent intent = new Intent();
					intent.setClass(getApplicationContext(), item.clazz);
					intent.putExtra(Configs.PARAM_1, item.subTag);
					intent.putExtra(Configs.PARAM_2, item.nameRes);
					startActivity(intent);
				}
			} else {
				try{
					AppInfo item2 = mListAppInfo.get(position);
					String pkg = item2.getPackageName();
					ActivityUtils.startActivity(LauncherAppActivity.this, pkg);
				}catch(Exception e){
					new CustomToast(getApplicationContext(), getString(R.string.app_not_found), 24).show();
				}
			}
			
			
//			try{
//				AppInfo info = mListAppInfo.get(position);
//				String pkg = info.getPackageName();
//				ActivityUtils.startActivity(LauncherAppActivity.this, pkg);
//			}catch(Exception e){
//				new CustomToast(getApplicationContext(), getString(R.string.app_not_found), 24).show();
//			}
		}
	};
	
	public static final int DIRECT_MAINACTIVITY = 1;
	public static final int SHOW_DIALOG = 2;
	public Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case DIRECT_MAINACTIVITY:
				ActivityUtils.startActivity(LauncherAppActivity.this,
						MainActivity.class);
				LauncherAppActivity.this.finish();
				break;
			case SHOW_DIALOG:
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
}
