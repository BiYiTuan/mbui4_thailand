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

import com.moon.android.launcher.thai.R;
import com.moon.android.launcher.thailand.Constant.NavItem;
import com.moon.android.launcher.thailand.adapter.NavAdapter;
import com.moon.android.launcher.thailand.util.ActivityUtils;
import com.moon.android.launcher.thailand.util.Logger;
import com.moon.android.launcher.thailand.view.AdScrollView;
import com.moon.android.launcher.thailand.view.AutoScrollTextView;
import com.moon.android.launcher.thailand.view.CustomToast;
import com.moon.android.launcher.thailand.view.StatusBar;

public class HomeActivity extends Activity implements OnKeyListener{
	
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
	private List<NavItem> mListCountryItem;
	private StatusBar mStatusBar;

	private AutoScrollTextView mScrollText;
	private AdScrollView mAdScrollView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		initWidget();
	}


	private void initWidget() {
		mGridNavigation = (GridView) findViewById(R.id.welcome_country_grid);
		mStatusBar = (StatusBar) findViewById(R.id.statusbar);
		mScrollText = (AutoScrollTextView) findViewById(R.id.auto_scroll_text);
		mAdScrollView = (AdScrollView) findViewById(R.id.ad_scroll_view);
		mAdScrollView.restart();
		
		if(null != Declare.listLauncherMsg && Declare.listLauncherMsg.size() > 0){
			mScrollText.setText(Declare.listLauncherMsg);
		} 
//		else mScrollText.setText(getString(R.string.index_ad));
		
		mListCountryItem = Constant.getHomeNavItems();
		mGridNavigation.setNumColumns(mListCountryItem.size());
		mGridNavigation.setOnKeyListener(this);
		
		mGridNavigation.requestFocusFromTouch();
		NavAdapter countryAdapter = new NavAdapter(this,
				mListCountryItem);
		mGridNavigation.setAdapter(countryAdapter);
		mGridNavigation.setOnItemClickListener(mGridClitener);
		mGridNavigation.requestFocus();
	}

	private OnItemClickListener mGridClitener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View view,
				final int position, long arg3) {
			NavItem item = mListCountryItem.get(position);
			boolean isLocal = item.isLocalActivity;
			if(isLocal && null != item.clazz){
				if(item.clazz == LauncherActivity.class){
					finish();
				} else {
					Intent intent = new Intent();
					intent.setClass(getApplicationContext(), item.clazz);
					startActivity(intent);
				}
			} else {
				try{
					String pkgname = item.pkgName;
					ActivityUtils.startActivity(HomeActivity.this, pkgname);
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
				ActivityUtils.startActivity(HomeActivity.this,
						MainActivity.class);
				HomeActivity.this.finish();
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
