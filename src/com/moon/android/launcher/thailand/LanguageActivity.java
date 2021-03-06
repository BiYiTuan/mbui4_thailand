package com.moon.android.launcher.thailand;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.moon.android.launcher.thai.R;
import com.moon.android.launcher.thailand.Constant.CountryItem;
import com.moon.android.launcher.thailand.adapter.CountryAdapter;
import com.moon.android.launcher.thailand.database.CountryDAO;
import com.moon.android.launcher.thailand.util.ActivityUtils;
import com.moon.android.launcher.thailand.util.Logger;
import com.moon.android.launcher.thailand.util.MACUtils;
import com.moon.android.launcher.thailand.view.CustomToast;
import com.moon.android.launcher.thailand.view.Disclaimer2Dialog;
import com.moon.android.launcher.thailand.view.StatusBar;
import com.moonX.util.MacEncryption;
import com.moonX.util.POIOuput;

public class LanguageActivity extends Activity {
	
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

	private GridView mGridCountry;
	public static final int COUNTRY_CHOOSE_COLUMN = 3;
	public static final int COUNTRY_CHOOSE_ROW = 2;
	private CountryDAO mCountryDAO;
	private List<CountryItem> mListCountryItem;
	private TextView mTextBanner;
	private StatusBar mStatusBar;

	private List<String> mListInitApp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		checkIsFirstLoad();
		setContentView(R.layout.activity_welcome);
		initWidget();

		mListInitApp = new ArrayList<String>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 2116051300624006433L;
			{

			}
		};
		
//		if(Configs.getType() == Configs.BOX_TYPE_M4){
//			mListInitApp.add(SETTING_M4);
//		}
		
//		if(Configs.getType() == Configs.BOX_TYPE_M3 
//				|| Configs.getType() == Configs.BOX_TYPE_M2S){
//			mListInitApp.add(Constant.BOX_SETTING);
//		}
			
		
		// mListInitApp.add(APPSTORE_PKG);
		// mListInitApp.add(NAMLIVE);
		// mListInitApp.add(NAMVOD);
		// mListInitApp.add(NAM_ENTERTAINMENT);
		// mListInitApp.add(NAM_MUSIC);
		// mListInitApp.add(NAM_VIET_KIDS);
		// mListInitApp.add(FPYPLAY);
		// mListInitApp.add(YOUTUBE);
		// mListInitApp.add(VOD_CLOUND);
		// mListInitApp.add(ILIVE_HK);
		// mListInitApp.add(HK_DRAMAS);
		// mListInitApp.add(ILIVE);
		// mListInitApp.add(CN_DRAMA);
	}

	private void checkIsFirstLoad() {
		mCountryDAO = new CountryDAO(this);
		if (!mCountryDAO.isFirstLoad()) {
			super.finish();
			ActivityUtils.startActivity(this, LauncherActivity.class);
		} else {
//			log.i("Is first load and not choose the language");
			// disclaimer dialog

//			saveMac();
		}
	}

	private void saveMac() {
		if (!new File(SAVE_MAC_FILE_PATH).exists())
			return;
		String ethMac = MACUtils.getMac();
		String wifiMac = MACUtils.getWifiMac(this);
		String chipMode = MACUtils.getChipMode();
		String convertMac = MacEncryption.encryption(ethMac, wifiMac, chipMode);
		int resID = R.string.save_mac_fail;
		if (null != convertMac) {
			boolean status = POIOuput.saveMac(ethMac, convertMac);
			resID = status ? R.string.save_mac_success : R.string.save_mac_fail;
		}
		new CustomToast(getApplicationContext(), getText(resID).toString(), 24)
				.show();
	}

	private void initWidget() {
		mGridCountry = (GridView) findViewById(R.id.welcome_country_grid);
		mTextBanner = (TextView) findViewById(R.id.welcome_text);
		mStatusBar = (StatusBar) findViewById(R.id.statusbar);
		mGridCountry.setNumColumns(COUNTRY_CHOOSE_COLUMN);
		mListCountryItem = Constant.getCountrys();
		CountryAdapter countryAdapter = new CountryAdapter(this,
				mListCountryItem);
		mGridCountry.setAdapter(countryAdapter);
		mGridCountry.setOnItemClickListener(mGridClitener);
		mTextBanner.setText(R.string.welcome_banner);
	}

	private OnItemClickListener mGridClitener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View view,
				final int position, long arg3) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					setLocalLanguage(mListCountryItem.get(position).locale);
					// mCountryDAO.changeLoadStatus();
					/*
					 * if appstore is install then to the desktop
					 */
//					for (String pkgName : mListInitApp) {
//						if (AppUtils.isAppInstalled(getApplicationContext(),
//								pkgName)) {
//							try {
//								CustomAppInfo appInfo = new CustomAppInfo();
//								PackageInfo pkgInfo = getApplicationContext()
//										.getPackageManager().getPackageInfo(
//												pkgName, 0);
//								appInfo.pkgName = pkgName;
//								appInfo.title = pkgInfo.applicationInfo.name;
//								try {
//									appInfo.icon = getResources().getDrawable(
//											pkgInfo.applicationInfo.icon);
//								} catch (Exception e) {
//									e.printStackTrace();
//								}
//								AppDAO appDAO = new AppDAO(
//										getApplicationContext());
//								if (appDAO.canInsert(appInfo)) {
//									appDAO.insert(getApplicationContext(),
//											appInfo);
//								}
//							} catch (NameNotFoundException e) {
//								e.printStackTrace();
//							}
//						}
//					}
					mHandler.sendEmptyMessage(SHOW_DIALOG);
				}
			}).start();
		}
	};
	Disclaimer2Dialog dialog;
	private void showDisclmaierDialog() {
		dialog = new Disclaimer2Dialog(LanguageActivity.this);
		dialog.setCancelable(false);
		dialog.setCommitClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mCountryDAO.changeLoadStatus();
				DisclaimerSharepreference.setHasRead(LanguageActivity.this);
				mHandler.sendEmptyMessage(DIRECT_MAINACTIVITY);
			}
		});
		dialog.show();
		dialog.setContentView(R.layout.disclaimer_dialog);
		dialog.startTiming();
	}

	public static final int DIRECT_MAINACTIVITY = 1;
	public static final int SHOW_DIALOG = 2;
	public Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case DIRECT_MAINACTIVITY:
				ActivityUtils.startActivity(LanguageActivity.this,
						LauncherActivity.class);
				LanguageActivity.this.finish();
//				if(null != dialog && dialog.isShowing())
//					dialog.dismiss();
				break;
			case SHOW_DIALOG:
				showDisclmaierDialog();
				break;
			default:
				;
			}
		};
	};

	public void setLocalLanguage(Locale locale) {
		try {
			Class<?> activityManagerNative = Class
					.forName("android.app.ActivityManagerNative");
			log.i("amnType " + activityManagerNative.toString());

			Object am = activityManagerNative.getMethod("getDefault").invoke(
					activityManagerNative);
			log.i("amType "+ am.getClass().toString());

			Object config = am.getClass().getMethod("getConfiguration")
					.invoke(am);
			log.i("configType "+ config.getClass().toString());
			config.getClass().getDeclaredField("locale").set(config, locale);

			config.getClass().getDeclaredField("userSetLocale")
					.setBoolean(config, true);

			am.getClass()
					.getMethod("updateConfiguration",
							android.content.res.Configuration.class)
					.invoke(am, config);

		} catch (Exception e) {
			e.printStackTrace();
			log.e(e.toString());
		}
	}

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
}
