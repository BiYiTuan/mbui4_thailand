

package com.moon.android.launcher.thailand;

import java.util.ArrayList;
import java.util.List;

public class Configs {
	
	public static boolean DEBUG_MODE = true;
	
	public static final int BOX_TYPE_M2S = 0;
	public static final int BOX_TYPE_M3 = 1; 
	public static final int BOX_TYPE_M4 = 2;
	public static final int getType(){
		return BOX_TYPE_M4;
	}
	
	/**
	 * 是否验证区域限制，其余的盒子不需要
	 */
	public static final boolean isVerifyRegionRestrictions = true;
	
	
	public static String THIS_APP_PACKAGE_NAME = "com.moon.android.launcher.thai";
	public static final String APK_NAME = "MBUI3.0.apk";
	public static final String PARAM_1 = "intent_extra_1_param";
	public static final String PARAM_2 = "intent_extra_2_param";
	
	//PLAYER VERSION  file in /ASSETS/MoonPlayer_xx.apk
	public static final String PLAYER_VERSION = "1.0";
	public static final String PLAYER_PKG = "com.moon.android.moonplayer";
	
	public static class UserMsgVar{
		public static final String MSG_NOT_READ = "1";
		public static final String MSG_HAS_READ = "0";
		
	}
	
	public static class BroadCastConstant{
		public static final String GET_AD_PICTURE= "com.moon.android.launcher.thailand.getAdPicture";
		public static final String GET_USER_MSG= "com.moon.android.launcher.thailand.getUserMsg";
		public static final String GET_LAUNCHER_MSG= "com.moon.android.launcher.thailand.getLauncherMsg";
		public static final String NETWORK_CONNECTION_CHANGE = "com.moon.android.launcher.thailand.networkConnectChange";
		public static final String ACTION_NEW_USER_MSG = "com.moon.android.launcher.thailand.view.StatusBar.hasnewmsg";
		public static final String ACTION_NEW_USER_NO_MSG = "com.moon.android.launcher.thailand.view.StatusBar.hasnonewmsg";
		public static final String ACTION_TO_DESKTOP = "com.bestbann.launcher.moonboxlauncher2.todesktop";
		public static final String ACTION_UN_TO_DESKTOP = "com.bestbann.launcher.moonboxlauncher2.untodesktop";
		public static final String ACTION_UPGRADE = "com.moon.android.launcher.thailand.upgrade";
		public static final String ACTION_UPDATE_DESKTOP = "com.moon.android.launcher.thailand.updatedesktop";
		public static final String ACTION_GET_HARDWARE = "com.moon.android.launcher.thailand.gethardware";
	}
	
	public static class RegionLimit{
		public static final String ACTION_REGION_LIMIT = "com.moon.android.launcher.thailand.limitRegionBroad";
		public static final String STATUS_AUTH_SUCCESS = "0";
		public static final String STATUS_AUTH_FAIL = "1";
		public static final String STATUS_AUTH_REGION_LIMIT = "2";
		public static final String STATUS_AUTH_VALIDITY_OUT = "4";
	}
	
	public class ServerInterface{
		public  final String SERVER_ADDRESS = getServer();
		public  final String AD_PICTURE = SERVER_ADDRESS + "index.php/Tai/Ad/Json?";
		public  final String MSG_LAUNCHER = SERVER_ADDRESS +"index.php/Tai/Msg/Json?";
		public  final String UPGRADE_LAUNCHER = SERVER_ADDRESS +"index.php/Api/Tai/firmwareup?";
		public  final String REGION_LIMIT = SERVER_ADDRESS + "index.php/Api/Tai/stbauth?";
		
//		public  final String SOFTWARE_MSG = SERVER_ADDRESS + "stbinfo.php?";
//		public  final String MSG_CENTER = SERVER_ADDRESS + "msgcenter.php?";
//		public  final String MSG_STATUS_CHANGE = SERVER_ADDRESS + "upumsg.php?";
	}
	
	/**
	 * 获取系统所有应用还是获取安装的应用
	 */
//	public static final boolean isShowSystemApp = false;
	public static boolean isShowSystemApp(){
		if(getType() == BOX_TYPE_M2S || 
				getType() == BOX_TYPE_M3)  return false;
		return true;
	}
	
	public static int TOAST_TEXT_SIZE = 24;
	/**
	 * 预留的可以隐藏的几个应用的包名
	 * @return
	 */
	public static List<String> getHiddenAppPkg(){
		List<String> list = new ArrayList<String>();
		list.add("com.moon.android.moonplayer");
		list.add("com.moon.android.jodoone");
		list.add("com.moon.android.jodotwo");
		list.add("com.moon.android.jodothree");
		return list;
	}
	public static List<String> IndexHiddenAppPkg(){
		List<String> list = new ArrayList<String>();
		list.add("com.moon.android.moonplayer");
		list.add("com.moon.android.jodoone");
		list.add("com.moon.android.jodotwo");
		list.add("com.moon.android.jodothree");
		list.add("com.videocesu.android");
		list.add("kantv.clean");
		list.add("com.amlogic.PPPoE");
		list.add("com.adobe.flashplayer");
		list.add("com.google.android.gm");
		list.add("com.amlogic.miracast");
		list.add("com.android.browser");//sys browser
		list.add("com.meraki.sm");
		list.add("com.android.vending");//google play
		list.add("com.teamviewer.quicksupport.market");
		list.add("com.android.service.remotecontrol");
		list.add("com.waynaktv.android");
		list.add("com.zaaptv.android");
		list.add("com.android.providers.downloads.ui");//sys download
		list.add("com.google.android.gms");
		list.add("com.android.email");
		list.add("com.android.deskclock");
		list.add("com.android.contacts");
		list.add("com.android.calculator2");
		list.add("com.gsoft.appinstall");//sys app install
		list.add("com.moon.appstore");
		list.add("com.android.settings");//sys setting
		list.add("com.android.music");//sys music
		list.add("com.android.gallery3d");//sys picture player
		list.add("com.fb.FileBrower");//sys file browser
		list.add("com.amlogic.PicturePlayer");
		list.add("com.amlogic.netfilebrowser");
		list.add("com.moon.android.moonplayer");
		list.add("com.farcore.videoplayer");
		list.add("com.example.Upgrade");
		
		list.add("com.moonlive.android.iptv");
		list.add("com.forcetech.android.live");
		list.add("com.mooncloud.android.iptv");
		list.add("com.moonlive.android.sports");
		list.add("com.moonlivehk.android.iptv");
		list.add("com.mooncloud.android.looktvb");
		list.add("com.moon.hk.hktv");
		list.add("com.moonlive.android.Vitnam");
		list.add("com.mooncloud.android.namvod");
		list.add("com.moonlive.android.iptvback");

		return list;
	}
	private String getServer(){
		return "http://23.89.145.178:9011/";
//		return "http://192.168.100.221:9016/";
	}
	/*private native String getServer();
	
	static{
		System.loadLibrary("MBUI");
	}*/
}
