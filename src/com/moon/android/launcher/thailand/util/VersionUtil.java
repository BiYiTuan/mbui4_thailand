package com.moon.android.launcher.thailand.util;

import com.moon.android.launcher.thailand.Configs;

import android.content.Context;
import android.content.pm.PackageManager;

public class VersionUtil {

	public static int getVersionCode(Context paramContext) {
		try {
			int i = paramContext.getPackageManager().getPackageInfo(
					Configs.THIS_APP_PACKAGE_NAME, 0).versionCode;
			return i;
		} catch (PackageManager.NameNotFoundException localNameNotFoundException) {
			localNameNotFoundException.printStackTrace();
		}
		return -1;
	}
	
	public static String getVersionName(Context paramContext) {
		try {
			String versionName = paramContext.getPackageManager().getPackageInfo(
					Configs.THIS_APP_PACKAGE_NAME, 0).versionName;
			return versionName;
		} catch (PackageManager.NameNotFoundException localNameNotFoundException) {
			localNameNotFoundException.printStackTrace();
		}
		return "";
	}
}
