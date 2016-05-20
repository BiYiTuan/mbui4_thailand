package com.moon.android.launcher.thailand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.moon.android.launcher.thai.R;
import com.moon.android.launcher.thailand.util.AppUtils;
import com.moon.android.launcher.thailand.util.CustomAppInfo;

public class Constant {

	public static final String NETWORK_TEST = "com.videocesu.android";
	public static final String CLEARN = "kantv.clean";

	public static final String SYS_DOWNLOAD = "com.android.providers.downloads.ui";
	public static final String SYS_DOWNLOAD_AC = "com.android.providers.downloads.ui.DownloadList";

	public static final String SYS_BROWSER = "com.android.browser";
	public static final String SYS_BROWSER_AC = "com.android.browser.BrowserActivity";
	public static final String SYS_SETTING = "com.android.settings";
	public static final String SYS_SETTING_AC = "com.android.settings.Settings";
	public static final String SYS_FILE_BROWSER = "com.fb.FileBrower";
	public static final String SYS_FILE_BROWSER_AC = "com.fb.FileBrower.FileBrower";
	public static final String SYS_VIDEO = "com.farcore.videoplayer";
	public static final String SYS_VIDEO_AC = "com.farcore.videoplayer.FileList";
	public static final String SYS_PIC = "com.amlogic.PicturePlayer";
	public static final String SYS_PIC_AC = "org.geometerplus.android.fbreader.FBReader";
	public static final String SYS_MUSIC = "org.geometerplus.zlibrary.ui.android";
	public static final String SYS_MUSIC_AC = "org.geometerplus.android.fbreader.FBReader";
	public static final String DLNA = "com.amlogic.mediacenter";
	public static final String DLNA_AC = "com.amlogic.mediacenter.AmlMediaCenter";

	public static final String APP_STORE = "com.android.vending";
	public static final String APP_STORE_AC = "com.android.vending.AssetBrowserActivity";

	public static final String MOON_APP_STORE = "com.moon.appstore";
	public static final String MOON_APP_STORE_AC = "com.moon.appstore.WelcomeActivity";
	// MX
	public static final String SYS_MUSIC_MX = "com.android.music";
	public static final String SYS_MUSIC_MX_AC = "com.android.music.MusicBrowserActivity";
	public static final String UPGRADE = "com.example.Upgrade";
	public static final String UPGRADE_AC = "com.example.Upgrade.UpgradeActivity";
	public static final String BOX_SETTING = "com.aml.settings";
	public static final String BOX_SETTING_AC = "com.aml.settings.PreferenceWithHeaders";

	public static List<CustomAppInfo> getDefaultApp(Context context) {
		List<CustomAppInfo> list = new ArrayList<CustomAppInfo>();
		// list.add(new CustomAppInfo(getString(context, R.string.setting),
		// SYS_SETTING, SYS_SETTING_AC, getDrawable(context,
		// R.drawable.app_setting), true));
		list.add(new CustomAppInfo(getString(context, R.string.menu_1),
				"com.moonlive.android.iptv",
				"com.moonlive.android.Vitnam.MainActivity", getDrawable(
						context, R.drawable.menu_1), true));
		list.add(new CustomAppInfo(getString(context, R.string.menu_2),
				"com.forcetech.android.live",
				"com.forcetech.android.live.LivePlayerActivity", getDrawable(
						context, R.drawable.menu_2), true));
		list.add(new CustomAppInfo(getString(context, R.string.menu_3),
				"com.mooncloud.android.iptv",
				"com.moon.android.activity.IndexActivity", getDrawable(context,
						R.drawable.menu_3), true));
		list.add(new CustomAppInfo(getString(context, R.string.menu_4),
				"com.moonlive.android.sports",
				"com.moonlive.android.sports.MainActivity", getDrawable(
						context, R.drawable.menu_4), true));
		list.add(new CustomAppInfo(getString(context, R.string.menu_5),
				"com.moonlivehk.android.iptv",
				"com.moonlivehk.android.iptv.MainActivity", getDrawable(
						context, R.drawable.menu_5), true));
		list.add(new CustomAppInfo(getString(context, R.string.menu_6),
				"com.mooncloud.android.looktvb",
				"com.moon.android.activity.IndexActivity", getDrawable(context,
						R.drawable.menu_6), true));
		list.add(new CustomAppInfo(getString(context, R.string.menu_8),
				"com.moonlive.android.Vitnam",
				"com.moonlive.android.Vitnam.MainActivity", getDrawable(
						context, R.drawable.menu_8), true));
		list.add(new CustomAppInfo(getString(context, R.string.menu_7),
				"com.moon.hk.hktv", "com.moon.hk.hktv.MainActivity",
				getDrawable(context, R.drawable.menu_7), true));
		
		list.add(new CustomAppInfo(getString(context, R.string.menu_9),
				"com.mooncloud.android.namvod",
				"com.moon.android.activity.IndexActivity", getDrawable(
						context, R.drawable.menu_9), true));
		list.add(new CustomAppInfo(getString(context, R.string.menu_10),
				"com.moonlive.android.iptvback",
				"com.moonlive.android.iptvback.MainActivity", getDrawable(
						context, R.drawable.menu_10), true));
		return list;
	}

	public static List<CustomAppInfo> getSysApp(Context context) {
		List<CustomAppInfo> list = new ArrayList<CustomAppInfo>();

		// list.add(new
		// CustomAppInfo(getDrawable(context,R.drawable.app_setting),getString(context,R.string.setting),SYS_SETTING));
		list.add(new CustomAppInfo(getString(context, R.string.setting),
				SYS_SETTING, SYS_SETTING_AC, getDrawable(context,
						R.drawable.app_setting), true));

		// list.add(new
		// AppItem(getDrawable(context,R.drawable.app_file),getString(context,R.string.file),SYS_FILE_BROWSER));
		list.add(new CustomAppInfo(getString(context, R.string.file),
				SYS_FILE_BROWSER, SYS_FILE_BROWSER_AC, getDrawable(context,
						R.drawable.app_file), true));
		// list.add(new
		// AppItem(getDrawable(context,R.drawable.app_video),getString(context,R.string.video),SYS_VIDEO));
		list.add(new CustomAppInfo(getString(context, R.string.video),
				SYS_VIDEO, SYS_VIDEO_AC, getDrawable(context,
						R.drawable.app_video), true));

		// String picPkg = Configs.getType() == Configs.BOX_TYPE_M2S ? SYS_PIC :
		// SYS_PIC_MX;
		String musicPkg = null;
		String musicPkgAc = null;
		// String musicPkgac = Configs.getType() == Configs.BOX_TYPE_M2S ?
		// SYS_MUSIC_AC : SYS_MUSIC_MX_AC;
		// list.add(new
		// AppItem(getDrawable(context,R.drawable.app_pic),getString(context,R.string.gallery),SYS_PIC));
		list.add(new CustomAppInfo(getString(context, R.string.gallery),
				SYS_PIC, SYS_PIC_AC, getDrawable(context, R.drawable.app_pic),
				true));

		// list.add(new
		// AppItem(getDrawable(context,R.drawable.app_music),getString(context,R.string.music),musicPkg));
		if (AppUtils.isAppInstalled(context, SYS_MUSIC)) {
			musicPkg = SYS_MUSIC;
			musicPkgAc = SYS_MUSIC_AC;
		} else if (AppUtils.isAppInstalled(context, SYS_MUSIC_MX)) {
			musicPkg = SYS_MUSIC_MX;
			musicPkgAc = SYS_MUSIC_MX_AC;
		}

		if (null != musicPkg) {
			list.add(new CustomAppInfo(getString(context, R.string.music),
					musicPkg, musicPkgAc, getDrawable(context,
							R.drawable.app_music), true));
		}

		// list.add(new
		// AppItem(getDrawable(context,R.drawable.app_browser),getString(context,R.string.broswer),SYS_BROWSER));
		list.add(new CustomAppInfo(getString(context, R.string.broswer),
				SYS_BROWSER, SYS_BROWSER_AC, getDrawable(context,
						R.drawable.app_browser), true));

		// list.add(new
		// AppItem(getDrawable(context,R.drawable.app_download),getString(context,R.string.download),SYS_DOWNLOAD));
		list.add(new CustomAppInfo(getString(context, R.string.download),
				SYS_DOWNLOAD, SYS_DOWNLOAD_AC, getDrawable(context,
						R.drawable.app_download), true));

		// andy 定制版本 需要加入GooglePlay
		// list.add(new
		// AppItem(getDrawable(context,R.drawable.googleplay),getString(context,R.string.google_play),APP_STORE));
		list.add(new CustomAppInfo(getString(context, R.string.google_play),
				APP_STORE, APP_STORE_AC, getDrawable(context,
						R.drawable.googleplay), true));

		// list.add(new
		// AppItem(getDrawable(context,R.drawable.icon_dlna),getString(context,R.string.dlna),DLNA));
		// list.add(new
		// AppItem(getDrawable(context,R.drawable.icon_dlna),getString(context,R.string.setting),SYS_SUB_SETTING));
		// list.add(new
		// AppItem(getDrawable(context,R.drawable.app_download),"媒体中心",MEDIA_CENTER));

		list.add(new CustomAppInfo(getString(context, R.string.upgrade),
				UPGRADE, UPGRADE_AC, getDrawable(context,
						R.drawable.icon_upgrade), true));
		// if(Configs.getType() == Configs.BOX_TYPE_M3){
		// list.add(new
		// AppItem(getDrawable(context,R.drawable.icon_upgrade),getString(context,R.string.upgrade),UPGRADE));
		// list.add(new
		// AppItem(getDrawable(context,R.drawable.setting_icon),getString(context,R.string.box_setting),BOX_SETTING));

		// }
		String box_setting = null;
		if (AppUtils.isAppInstalled(context, BOX_SETTING)) {
			box_setting = BOX_SETTING;
		}
		if (null != box_setting)
			list.add(new CustomAppInfo(
					getString(context, R.string.box_setting), BOX_SETTING,
					BOX_SETTING_AC, getDrawable(context,
							R.drawable.setting_icon), true));

		list.add(new CustomAppInfo(getString(context, R.string.appstore2),
				MOON_APP_STORE, MOON_APP_STORE_AC, getDrawable(context,
						R.drawable.icon_appstore), true));
		return list;
	}

	public static List<AppItem> getSysApps(Context context) {
		List<AppItem> list = new ArrayList<AppItem>();
		list.add(new AppItem(getDrawable(context, R.drawable.app_setting),
				getString(context, R.string.setting), SYS_SETTING));
		list.add(new AppItem(getDrawable(context, R.drawable.app_file),
				getString(context, R.string.file), SYS_FILE_BROWSER));
		list.add(new AppItem(getDrawable(context, R.drawable.app_video),
				getString(context, R.string.video), SYS_VIDEO));

		// String picPkg = Configs.getType() == Configs.BOX_TYPE_M2S ? SYS_PIC :
		// SYS_PIC_MX;
		String musicPkg = Configs.getType() == Configs.BOX_TYPE_M2S ? SYS_MUSIC
				: SYS_MUSIC_MX;
		list.add(new AppItem(getDrawable(context, R.drawable.app_pic),
				getString(context, R.string.gallery), SYS_PIC));
		list.add(new AppItem(getDrawable(context, R.drawable.app_music),
				getString(context, R.string.music), musicPkg));

		list.add(new AppItem(getDrawable(context, R.drawable.app_browser),
				getString(context, R.string.broswer), SYS_BROWSER));
		list.add(new AppItem(getDrawable(context, R.drawable.app_download),
				getString(context, R.string.download), SYS_DOWNLOAD));
		// andy 定制版本 需要加入GooglePlay
		list.add(new AppItem(getDrawable(context, R.drawable.googleplay),
				getString(context, R.string.google_play), APP_STORE));
		// list.add(new
		// AppItem(getDrawable(context,R.drawable.icon_dlna),getString(context,R.string.dlna),DLNA));
		// list.add(new
		// AppItem(getDrawable(context,R.drawable.icon_dlna),getString(context,R.string.setting),SYS_SUB_SETTING));
		// list.add(new
		// AppItem(getDrawable(context,R.drawable.app_download),"媒体中心",MEDIA_CENTER));

		if (Configs.getType() == Configs.BOX_TYPE_M3) {
			list.add(new AppItem(getDrawable(context, R.drawable.icon_upgrade),
					getString(context, R.string.upgrade), UPGRADE));
			list.add(new AppItem(getDrawable(context, R.drawable.setting_icon),
					getString(context, R.string.box_setting), BOX_SETTING));
		}
		return list;
	}

	public static List<NavItem> getLauncherNavItems() {
		List<NavItem> list = new ArrayList<NavItem>();
		// 35124
		// list.add(new
		// NavItem(R.drawable.icon_taiwan,R.string.live_taiwan,false,LIVE_3,LauncherAppActivity.class,APP_TAG_LIVE));
		// list.add(new
		// NavItem(R.drawable.icon_sports,R.string.live_tiyu,false,LIVE_5,LauncherAppActivity.class,APP_TAG_LIVE_BACK));
		// list.add(new
		// NavItem(R.drawable.icon_dalu,R.string.live_dalu,false,LIVE_1,LauncherAppActivity.class,APP_TAG_FILM));
		// list.add(new
		// NavItem(R.drawable.icon_hk,R.string.live_hk,false,LIVE_2,LauncherAppActivity.class,APP_TAG_DRAMA));
		// list.add(new
		// NavItem(R.drawable.icon_guoji,R.string.live_universal,false,LIVE_4,LauncherAppActivity.class,APP_TAG_LEARN));
		// list.add(new
		// NavItem(R.drawable.icon_58ive,R.string.live_yuenan,false,LIVE_6,LauncherAppActivity.class,APP_TAG_LEARN));

		// list.add(new
		// NavItem(R.drawable.icon_t_ktv,R.string.n_ktv,true,"",LauncherAppActivity.class,APP_TAG_KTV));
		// list.add(new
		// NavItem(R.drawable.icon_t_18plus,R.string.live_other,true,"",LauncherAppActivity.class,APP_TAG_OTHER_APP));
		list.add(new NavItem(R.drawable.icon_t_setting, R.string.app_and_set,
				true, "", HomeActivity.class));
		list.add(new NavItem(R.drawable.icon_t_18plus, R.string.live_other,
				true, "", LauncherAppActivity.class, APP_TAG_OTHER_APP));
		list.add(new NavItem(R.drawable.icon_t_18plus, R.string.live_other,
				true, "", LauncherAppActivity.class, APP_TAG_OTHER_APP));
		list.add(new NavItem(R.drawable.icon_t_18plus, R.string.live_other,
				true, "", LauncherAppActivity.class, APP_TAG_OTHER_APP));
		list.add(new NavItem(R.drawable.icon_t_18plus, R.string.live_other,
				true, "", LauncherAppActivity.class, APP_TAG_OTHER_APP));
		list.add(new NavItem(R.drawable.icon_t_18plus, R.string.live_other,
				true, "", LauncherAppActivity.class, APP_TAG_OTHER_APP));
		list.add(new NavItem(R.drawable.icon_t_18plus, R.string.live_other,
				true, "", LauncherAppActivity.class, APP_TAG_OTHER_APP));
		list.add(new NavItem(R.drawable.icon_t_18plus, R.string.live_other,
				true, "", LauncherAppActivity.class, APP_TAG_OTHER_APP));
		list.add(new NavItem(R.drawable.icon_t_18plus, R.string.live_other,
				true, "", LauncherAppActivity.class, APP_TAG_OTHER_APP));
		list.add(new NavItem(R.drawable.icon_t_18plus, R.string.live_other,
				true, "", LauncherAppActivity.class, APP_TAG_OTHER_APP));
		list.add(new NavItem(R.drawable.icon_t_18plus, R.string.live_other,
				true, "", LauncherAppActivity.class, APP_TAG_OTHER_APP));
		return list;
	}

	public static final String APPSTORE = "com.moon.appstore";
	public static final String AML_SETTING = "com.mbx.settingsmbox";

	public static List<NavItem> getHomeNavItems() {
		List<NavItem> list = new ArrayList<NavItem>();
		list.add(new NavItem(R.drawable.icon_h_home, R.string.h_home, true, "",
				LauncherActivity.class));
		list.add(new NavItem(R.drawable.icon_h_myapp, R.string.h_myapp, true,
				"", AppActivity.class));
		list.add(new NavItem(R.drawable.icon_home_appmanager,
				R.string.h_app_mana, true, "", AppManageActivity.class));
		list.add(new NavItem(R.drawable.icon_home_appgarden,
				R.string.h_app_grden, false, APPSTORE, null));
		list.add(new NavItem(R.drawable.icon_h_broswer,
				R.string.h_app_internet, false, SYS_BROWSER, null));
		list.add(new NavItem(R.drawable.icon_h_file, R.string.h_app_file,
				false, SYS_FILE_BROWSER, null));
		list.add(new NavItem(R.drawable.icon_h_setting, R.string.h_app_setting,
				false, AML_SETTING, null));
		return list;
	}

	public static final String LIVE_1 = "com.moonlive.android.thai01"; // dalu
	public static final String LIVE_2 = "com.moonlive.android.thai02"; // gangao
	public static final String LIVE_3 = "com.moonlive.android.thai03"; // taiwan
	public static final String LIVE_4 = "com.moonlive.android.thai04"; // guoji
	public static final String LIVE_5 = "com.moonlive.android.thai05"; // tiyu
	public static final String LIVE_6 = "com.moonlive.android.Vitnam";

	public static List<NavItem> getLiveApps() {
		List<NavItem> list = new ArrayList<NavItem>() {
			private static final long serialVersionUID = 8510808086032620521L;
			{
				add(new NavItem(false, LIVE_3));
				add(new NavItem(false, LIVE_5));
				add(new NavItem(false, LIVE_1));
				add(new NavItem(false, LIVE_2));
				add(new NavItem(false, LIVE_4));
				add(new NavItem(false, LIVE_6));
			}
		};
		return list;
	}

	public static final String LIVE_BACK = "com.moonlive.android.iptvback";

	public static List<NavItem> getLiveBackApps() {
		List<NavItem> list = new ArrayList<NavItem>() {
			private static final long serialVersionUID = -925167459599173582L;
			{
				add(new NavItem(false, LIVE_BACK));
			}
		};
		return list;
	}

	// com.bestbaan.vod.seriesvod.hayao
	public static final String FILM_GQJ = "com.bestbaan.vod.seriesvod.hayao";
	public static final String FILM_CARTOON = "com.moon.vod.seriesvod5.cartoon";
	public static final String FILM_VNMOVIE = "com.moon.serviesvod2.vnmovie";
	public static final String FILM_JIZZ = "com.youjizz.android";

	public static List<NavItem> getFilmApps() {
		List<NavItem> list = new ArrayList<NavItem>() {
			private static final long serialVersionUID = -925167459599173582L;
			{
				add(new NavItem(false, MOVIE_CLOUD));
				add(new NavItem(false, FILM_CARTOON));
				add(new NavItem(false, FILM_GQJ));
				add(new NavItem(false, FILM_VNMOVIE));
				add(new NavItem(false, VN_entertainment));
				add(new NavItem(false, VN_kids));
				add(new NavItem(false, FILM_JIZZ));
			}
		};
		return list;
	}

	public static final String MOVIE_HKCLASSIC = "com.moon.serviesvod2.hkClassic";
	public static final String MOVIE_KOREA = "com.moon.serviesvod2.korea";
	public static final String MOVIE_SHENZHOU = "com.moon.serviesvod2.shenzhou";
	public static final String MOVIE_US = "com.moon.serviesvod2.usdrama";
	public static final String MOVIE_TAIWAN = "com.moon.vod.seriesvod5.taiwan";
	public static final String MOVIE_YGSJ = "com.mooncloud.android.looktvb";
	public static final String MOVIE_CLOUD = "com.mooncloud.android.iptv";
	public static final String MOVIE_NAMVOD = "com.mooncloud.android.namvod";

	public static List<NavItem> getMovieApps() {
		List<NavItem> list = new ArrayList<NavItem>() {
			private static final long serialVersionUID = -925167459599173582L;
			{
				add(new NavItem(false, MOVIE_TAIWAN));
				add(new NavItem(false, MOVIE_US));
				add(new NavItem(false, MOVIE_KOREA));
				add(new NavItem(false, MOVIE_YGSJ));
				add(new NavItem(false, MOVIE_HKCLASSIC));
				add(new NavItem(false, MOVIE_SHENZHOU));
				add(new NavItem(false, MOVIE_NAMVOD));
			}
		};
		return list;
	}

	// com.kgeking.client K歌之王
	// com.kandian.mv4tv 快手MV
	// kantv.radio 阿狸电台
	// com.xtshine.epgletv.activity 彩虹音乐
	// cn.aikanmv.mv 爱看MV
	public static final String KTV_01 = "cn.aikanmv.mv";
	// public static final String KTV_02 = "com.xtshine.epgletv.activity";
	public static final String KTV_03 = "kantv.radio";
	public static final String KTV_04 = "com.kandian.mv4tv";
	public static final String KTV_05 = "com.kgeking.client";

	public static List<NavItem> getKTVApps() {
		List<NavItem> list = new ArrayList<NavItem>() {
			private static final long serialVersionUID = -925167459599173582L;
			{
				add(new NavItem(false, VN_music));
				add(new NavItem(false, KTV_05));
				add(new NavItem(false, KTV_04));
				add(new NavItem(false, KTV_03));
				// add(new NavItem(false,KTV_02));
				add(new NavItem(false, KTV_01));
			}
		};
		return list;
	}

	// 学习
	// com.lovesport.shaoer 宝宝跟我学
	// com.lekan.tv.kids.activity 乐看儿童动画
	// om.qqbb 贝比乐园
	// air.poem.tv.android.A123qibu.com 熊猫乐园
	// com.babaxiong.yueyuergetongyao 粤语儿童歌
	// public static final String Learn_01 = "com.lovesport.shaoer";
	// public static final String Learn_02 = "com.lekan.tv.kids.activity";
	// public static final String Learn_03 = "com.qqbb";
	// public static final String Learn_04 = "air.poem.tv.android.A123qibu.com";
	// public static final String Learn_05 = "com.babaxiong.yueyuergetongyao";
	public static final String Learn_06 = "com.netease.vopen.tablet";

	public static List<NavItem> getLearnApps() {
		List<NavItem> list = new ArrayList<NavItem>() {
			private static final long serialVersionUID = -925167459599173582L;
			{
				add(new NavItem(false, Learn_06));
				// add(new NavItem(false,Learn_01));
				// add(new NavItem(false,Learn_02));
				// add(new NavItem(false,Learn_03));
				// add(new NavItem(false,Learn_04));
				// add(new NavItem(false,Learn_05));
			}
		};
		return list;
	}

	public static final String GOOGLE_PLAY = "com.android.vending";

	public static List<NavItem> getOtherApps() {
		List<NavItem> list = new ArrayList<NavItem>() {
			private static final long serialVersionUID = -925167459599173582L;
			{
				add(new NavItem(R.drawable.icon_t_film, R.string.n_film, true,
						"", LauncherAppActivity.class, APP_TAG_FILM));
				add(new NavItem(R.drawable.icon_t_drama, R.string.n_drama,
						true, "", LauncherAppActivity.class, APP_TAG_DRAMA));
				add(new NavItem(R.drawable.icon_t_learn, R.string.n_learn,
						true, "", LauncherAppActivity.class, APP_TAG_LEARN));
				add(new NavItem(R.drawable.icon_t_ktv, R.string.n_ktv, true,
						"", LauncherAppActivity.class, APP_TAG_KTV));
				add(new NavItem(R.drawable.icon_look_back,
						R.string.live_lookback, false, LIVE_BACK, null));
				add(new NavItem(R.drawable.googleplay, R.string.google_play,
						false, GOOGLE_PLAY, null));
				// add(new
				// NavItem(R.drawable.icon_t_18plus,R.string.n_other,true,"",AppActivity.class));
			}
		};
		return list;
	}

	public static final int APP_TAG_LIVE = 1;
	public static final int APP_TAG_LIVE_BACK = 2;
	public static final int APP_TAG_FILM = 3;
	public static final int APP_TAG_DRAMA = 4;
	public static final int APP_TAG_LEARN = 5;
	public static final int APP_TAG_OTHER = 6;
	public static final int APP_TAG_KTV = 7;
	public static final int APP_TAG_BACK = 8;
	public static final int APP_TAG_OTHER_APP = 9;

	@SuppressLint("UseSparseArrays")
	public static Map<Integer, List<NavItem>> mMapApps = new HashMap<Integer, List<NavItem>>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		{
			put(APP_TAG_LIVE, getLiveApps());
			put(APP_TAG_LIVE_BACK, getLiveBackApps());
			put(APP_TAG_KTV, getKTVApps());
			put(APP_TAG_LEARN, getLearnApps());
			put(APP_TAG_FILM, getFilmApps());
			put(APP_TAG_DRAMA, getMovieApps());
			put(APP_TAG_OTHER_APP, getOtherApps());
		}
	};

	public static List<NavItem> getNavItemByTag(int tag) {
		return mMapApps.get(tag);
	}

	public static List<NavigationItem> getNavigationItems() {
		List<NavigationItem> list = new ArrayList<Constant.NavigationItem>();
		return list;
	}

	public static List<CountryItem> getCountrys() {
		List<CountryItem> list = new ArrayList<CountryItem>();
		list.add(new CountryItem(R.drawable.icon_hongkong,
				R.string.Chinese_traditional, Locale.TRADITIONAL_CHINESE));
		list.add(new CountryItem(R.drawable.icon_china,
				R.string.chinese_simple, Locale.SIMPLIFIED_CHINESE));
		list.add(new CountryItem(R.drawable.icon_english, R.string.english,
				Locale.ENGLISH));
		list.add(new CountryItem(R.drawable.icon_tibet, R.string.Vietnamese,
				new Locale("vi", "VN")));
		list.add(new CountryItem(R.drawable.icon_france, R.string.france,
				Locale.FRANCE));
		return list;
	}

	public static final String VN_entertainment = "com.vn.entertainment.vod";
	public static final String VN_music = "com.vn.music.vod";
	public static final String VN_kids = "com.vn.viet.kids";

	private static Drawable getDrawable(Context context, int resId) {
		return context.getResources().getDrawable(resId);
	}

	private static String getString(Context context, int resId) {
		return context.getResources().getString(resId);
	}

	public static class NavItem {
		public int imageRes;
		public int nameRes;
		public boolean isLocalActivity;
		public Class<?> clazz;
		public String pkgName;
		public int subTag;

		public NavItem(int imageRes, int nameRes, boolean isLocalActivity,
				String name, Class<?> clazz, int subTag) {
			this.imageRes = imageRes;
			this.nameRes = nameRes;
			this.isLocalActivity = isLocalActivity;
			this.pkgName = name;
			this.clazz = clazz;
			this.subTag = subTag;
		}

		public NavItem(int imageRes, int nameRes, boolean isLocalActivity,
				String name, Class<?> clazz) {
			this.imageRes = imageRes;
			this.nameRes = nameRes;
			this.isLocalActivity = isLocalActivity;
			this.pkgName = name;
			this.clazz = clazz;
		}

		public NavItem(boolean isLocalActivity, String name) {
			this.isLocalActivity = isLocalActivity;
			this.pkgName = name;
		}
	}

	public static class CountryItem {
		public int imageRes;
		public int nameRes;
		public Locale locale;

		public CountryItem(int imageRes, int nameRes, Locale locale) {
			this.imageRes = imageRes;
			this.nameRes = nameRes;
			this.locale = locale;
		}
	}

	public static class AppItem {
		Drawable icon;
		String appName;
		String pkgName;

		public AppItem(Drawable iconRes, String appName, String pkgName) {
			super();
			this.icon = iconRes;
			this.appName = appName;
			this.pkgName = pkgName;
		}

		public Drawable getIcon() {
			return icon;
		}

		public void setIcon(Drawable icon) {
			this.icon = icon;
		}

		public String getAppName() {
			return appName;
		}

		public void setAppName(String appName) {
			this.appName = appName;
		}

		public String getPkgName() {
			return pkgName;
		}

		public void setPkgName(String pkgName) {
			this.pkgName = pkgName;
		}
	}

	public static class NavigationItem {
		private int id;
		private int nameRes;
		private Fragment fragment;

		public NavigationItem(int id, int nameRes, Fragment fragment) {
			this.id = id;
			this.nameRes = nameRes;
			this.fragment = fragment;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getNameRes() {
			return nameRes;
		}

		public void setNameRes(int nameRes) {
			this.nameRes = nameRes;
		}

		public Fragment getFragment() {
			return fragment;
		}

		public void setFragment(Fragment fragment) {
			this.fragment = fragment;
		}
	}

	public static class NavigationItem2 {
		private int id;
		private int nameRes;
		private View view;

		public NavigationItem2(int id, int nameRes, View view) {
			this.id = id;
			this.nameRes = nameRes;
			this.view = view;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getNameRes() {
			return nameRes;
		}

		public void setNameRes(int nameRes) {
			this.nameRes = nameRes;
		}

		public View getFragment() {
			return view;
		}

		public void setFragment(View fragment) {
			this.view = fragment;
		}

	}
}
