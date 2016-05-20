package com.moon.android.launcher.thailand.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.moon.android.launcher.thailand.Configs;
import com.moon.android.launcher.thailand.Constant;
import com.moon.android.launcher.thailand.Constant.AppItem;
import com.moon.android.launcher.thailand.Constant.NavItem;

public class AppUtils {
	
	public static final Logger log = Logger.getInstance();
	public static boolean isAppInstalled(Context context,String uri) {
		PackageManager pm = context.getPackageManager();
		boolean installed =false;
		try {
			pm.getPackageInfo(uri,0);
			log.i("Is installed ");
			installed = true;
		} catch(PackageManager.NameNotFoundException e) {
			log.e(e.toString());
			installed =false;
		}
		return installed;
	}
	
	
	public static List<AppInfo> getAppsByPackage(Context context,List<AppInfo> list){
		List<AppInfo> listResult = new ArrayList<AppInfo>();
		List<AppInfo> listInstalled = getUserInstalledApps(context,false);
		for(AppInfo infoLocal : list){
			for(AppInfo info : listInstalled){
				if(info.packageName.equals(infoLocal.packageName)){
					listResult.add(info);
					break;
				}
			}
		}
//		AppTimerComparator comparator = new AppTimerComparator();
//	  	Collections.sort(listResult, comparator);
		return listResult;
	}
	
	
	public static List<AppInfo> getAppsByNavItem(Context context,List<NavItem> list){
		List<AppInfo> listResult = new ArrayList<AppInfo>();
//		List<AppInfo> listInstalled = getUserInstalledApps(context,false);
//		if(null != list)
//			for(NavItem infoLocal : list){
//				for(AppInfo info : listInstalled){
//					if(info.packageName.equals(infoLocal.pkgName)){
//						listResult.add(info);
//						break;
//					}
//				}
//			}
		PackageManager pm = context.getPackageManager();
		if(null != list)
			for(NavItem item : list){
				if(!item.isLocalActivity){
					String pkgName = item.pkgName;
					try {
						AppInfo appInfo = new AppInfo();
						ApplicationInfo  info = pm.getApplicationInfo(pkgName, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
						appInfo.setAppIcon(pm.getApplicationIcon(info));
						appInfo.setPackageName(pkgName);
						appInfo.setAppName(pm.getApplicationLabel(info).toString());
						listResult.add(appInfo);
					} catch (Exception e) {
						log.e(e.toString());
					}
				} else {
					try {
						Resources res = context.getResources();
						AppInfo appInfo = new AppInfo();
						appInfo.setAppIcon(res.getDrawable(item.imageRes));
						appInfo.setPackageName(item.pkgName);
						appInfo.setAppName(res.getString(item.nameRes));
						listResult.add(appInfo);
					} catch (Exception e) {
						log.e(e.toString());
					}
				}
			}
		return listResult;
	}
	
	public static List<AppInfo> getApps(Context context) {
		List<AppInfo> list = new ArrayList<AppInfo>();

			if(Configs.isShowSystemApp()){
				for(AppItem item : Constant.getSysApps(context)){
					AppInfo appInfo = new AppInfo();
					appInfo.appName = item.getAppName();
					appInfo.appIcon = item.getIcon();
					appInfo.packageName = item.getPkgName();
					list.add(appInfo);
				}
			}
			list.addAll(getUserInstalledApps(context,true));
		return list;
	}
	
	public static List<AppInfo> getUserInstalledApps(Context context,boolean isSortByTime) {
		ArrayList<AppInfo> listReturn = new ArrayList<AppInfo>();
		PackageManager packageManager = context.getPackageManager();
		
		
		List<PackageInfo> packages = packageManager.getInstalledPackages(0);
		
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        final List<ResolveInfo> apps = packageManager.queryIntentActivities(mainIntent, 0);
        Collections.sort(apps, new ResolveInfo.DisplayNameComparator(packageManager));
		
		for (int i = 0; i < packages.size(); i++) {
			PackageInfo packageInfo = packages.get(i);
//			if(packageInfo.packageName.equals(Configs.THIS_APP_PACKAGE_NAME))
//				continue;
			//判断是否显示此应用 start
			boolean go = true;
			for(String pkg : Configs.getHiddenAppPkg()){
				if(packageInfo.packageName.equals(pkg)){
					go = false;
					break;
				}
			} 
			if(!go) continue; 
			//判断是否显示此应用 end
			
			AppInfo appInfo = new AppInfo();
			try{
				appInfo.appName = packageInfo.applicationInfo.loadLabel(packageManager).toString();
				appInfo.packageName = packageInfo.packageName;
				appInfo.versionName = packageInfo.versionName;
				appInfo.versionCode = packageInfo.versionCode;
				appInfo.appIcon = packageInfo.applicationInfo
						.loadIcon(packageManager);
				appInfo.firstInstallTime = packageInfo.firstInstallTime;
	//			Logger.getLogger().i(appInfo.appName + "  " + appInfo.firstInstallTime);
			}catch(Exception e){
				e.printStackTrace();
			}
			
				//系统应用
			if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0 
					&& !Configs.isShowSystemApp()) {
				listReturn.add(appInfo);
			} else 
				//非系统应用
				listReturn.add(appInfo);
		}
		if(isSortByTime){
			AppTimerComparator comparator = new AppTimerComparator();
		  	Collections.sort(listReturn, comparator);
		}
		return listReturn;
	}
	
	
	/**
     * Loads the list of installed applications in mApplications.
     */
    public static List<CustomAppInfo> loadAppFilter(Context context,boolean containSysApp) {
    	
        PackageManager manager = context.getPackageManager();

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        final List<ResolveInfo> apps = manager.queryIntentActivities(mainIntent, 0);
        Collections.sort(apps, new ResolveInfo.DisplayNameComparator(manager));
        List<CustomAppInfo> list = new ArrayList<CustomAppInfo>();
        if (apps != null) {
            final int count = apps.size();
            
         
            for (int i = 0; i < count; i++) {
            	CustomAppInfo application = new CustomAppInfo();
                ResolveInfo info = apps.get(i);

                application.title = info.loadLabel(manager);
                application.setActivity(new ComponentName(
                        info.activityInfo.applicationInfo.packageName,
                        info.activityInfo.name),
                        Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
                       );
                
//                application.icon = info.loadIcon(manager);
//                packageInfo.applicationInfo
//				.loadIcon(packageManager);
                String pkgName = info.activityInfo.packageName;
                String activityInfoName = info.activityInfo.name;
               
                application.pkgName = pkgName;
                application.activityInfoName = activityInfoName;
                
                boolean go = true;
    			for(String pkg : Configs.IndexHiddenAppPkg()){
    				if(application.pkgName.equals(pkg)){
    					go = false;
    					break;
    				}
    			} 
    			if(!go) continue; 
    			
                if(application.pkgName.equals(Configs.THIS_APP_PACKAGE_NAME))
                	continue;
                
                PackageInfo packageInfo;
				try {
					packageInfo = manager.getPackageInfo(pkgName, 0);
					application.firstInstallTime = packageInfo.firstInstallTime;
//					log.i(application.title  + "  installed time " + application.firstInstallTime);
					application.lastUpdateTime = packageInfo.lastUpdateTime;
					boolean isSystemApp = false;
				
					if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {  
						log.i(application.title  + "  installed time " + application.firstInstallTime.toString().length());
					    if( application.firstInstallTime.toString().length()!=13){
					    	//continue;
					    }
                        //第三方应用  
                    } else{ //系统应用  {
                    	isSystemApp = true;
                    	continue;
                    //	if(!containSysApp)continue;
                    }
					application.isSystemApp = isSystemApp;
					application.versionName = packageInfo.versionName;
					application.versionCode = packageInfo.versionCode;
					application.icon = packageInfo.applicationInfo
							.loadIcon(manager);
					application.title = packageInfo.applicationInfo.loadLabel(manager).toString();
				} catch (NameNotFoundException e) {
					e.printStackTrace();
				}
                list.add(application);
            }
            
            AppTimerComparator2 comparator = new AppTimerComparator2();
		  	Collections.sort(list, comparator);
        }
        return list;
    }
	/**
     * Loads the list of installed applications in mApplications.
     */
    public static List<CustomAppInfo> loadApplications(Context context,boolean containSysApp) {
    	
        PackageManager manager = context.getPackageManager();

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        final List<ResolveInfo> apps = manager.queryIntentActivities(mainIntent, 0);
        Collections.sort(apps, new ResolveInfo.DisplayNameComparator(manager));
        
        List<CustomAppInfo> list = new ArrayList<CustomAppInfo>();
        if (apps != null) {
            final int count = apps.size();

            for (int i = 0; i < count; i++) {
            	CustomAppInfo application = new CustomAppInfo();
                ResolveInfo info = apps.get(i);

                application.title = info.loadLabel(manager);
                application.setActivity(new ComponentName(
                        info.activityInfo.applicationInfo.packageName,
                        info.activityInfo.name),
                        Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
                       );
                
//                application.icon = info.loadIcon(manager);
//                packageInfo.applicationInfo
//				.loadIcon(packageManager);
                String pkgName = info.activityInfo.packageName;
                String activityInfoName = info.activityInfo.name;
                
                application.pkgName = pkgName;
                application.activityInfoName = activityInfoName;
                
                boolean go = true;
    			for(String pkg : Configs.getHiddenAppPkg()){
    				if(application.pkgName.equals(pkg)){
    					go = false;
    					break;
    				}
    			} 
    			if(!go) continue; 
    			
                if(application.pkgName.equals(Configs.THIS_APP_PACKAGE_NAME))
                	continue;
                
                PackageInfo packageInfo;
				try {
					packageInfo = manager.getPackageInfo(pkgName, 0);
					application.firstInstallTime = packageInfo.firstInstallTime;
//					log.i(application.title  + "  installed time " + application.firstInstallTime);
					application.lastUpdateTime = packageInfo.lastUpdateTime;
					boolean isSystemApp = false;
					if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {  
                        //第三方应用  
                    } else{ //系统应用  {
                    	isSystemApp = true;
                    	if(!containSysApp)continue;
                    }
					application.isSystemApp = isSystemApp;
					application.versionName = packageInfo.versionName;
					application.versionCode = packageInfo.versionCode;
					application.icon = packageInfo.applicationInfo
							.loadIcon(manager);
					application.title = packageInfo.applicationInfo.loadLabel(manager).toString();
				} catch (NameNotFoundException e) {
					e.printStackTrace();
				}
                list.add(application);
            }
            
            AppTimerComparator2 comparator = new AppTimerComparator2();
		  	Collections.sort(list, comparator);
        }
        return list;
    }
	
	
	
	/*
	 * 对app进行排序 
	 * 时间  / 应用名称 / 包名
	 */
	public static class AppTimerComparator implements Comparator<AppInfo>{
		@Override
		public int compare(AppInfo lhs, AppInfo rhs) {
			if(lhs.getFirstInstallTime() == 0){
				return 1;
			}
			int flag = lhs.getFirstInstallTime().compareTo(rhs.getFirstInstallTime());
			if(flag == 0){
				flag = lhs.getAppName().compareTo(rhs.getAppName());
				if(flag == 0){  
	                flag = lhs.getPackageName().compareTo(rhs.getPackageName()); 
	            }
			}
			return flag;
		}
	}
	
	
	/*
	 * 对app进行排序 
	 * 时间  / 应用名称 / 包名
	 */
	public static class AppTimerComparator2 implements Comparator<CustomAppInfo>{
		@Override
		public int compare(CustomAppInfo lhs, CustomAppInfo rhs) {
			if(lhs.firstInstallTime == 0){
				return 1;
			}
			int flag = lhs.firstInstallTime.compareTo(rhs.firstInstallTime);
			if(flag == 0){
				flag = lhs.title.toString().compareTo(rhs.title.toString());
				if(flag == 0){  
	                flag = lhs.pkgName.compareTo(rhs.pkgName); 
	            }
			}
			return flag;
		}
	}
	
	public static class AppInfo{
		 private Drawable appIcon;
		 private String appName;
		 private String packageName;
		 private int versionCode;
		 private String versionName;
		 private boolean isInDesktop;
		 private Long firstInstallTime;
		
		public Long getFirstInstallTime() {
			return firstInstallTime;
		}
		public void setFirstInstallTime(Long firstInstallTime) {
			this.firstInstallTime = firstInstallTime;
		}
		public boolean isInDesktop() {
			return isInDesktop;
		}
		public void setInDesktop(boolean isInDesktop) {
			this.isInDesktop = isInDesktop;
		}
		public Drawable getAppIcon() {
			return appIcon;
		}
		public void setAppIcon(Drawable appIcon) {
			this.appIcon = appIcon;
		}
		public String getAppName() {
			return appName;
		}
		public void setAppName(String appName) {
			this.appName = appName;
		}
		public String getPackageName() {
			return packageName;
		}
		public void setPackageName(String packageName) {
			this.packageName = packageName;
		}
		public int getVersionCode() {
			return versionCode;
		}
		public void setVersionCode(int versionCode) {
			this.versionCode = versionCode;
		}
		public String getVersionName() {
			return versionName;
		}
		public void setVersionName(String versionName) {
			this.versionName = versionName;
		}
	}
}
