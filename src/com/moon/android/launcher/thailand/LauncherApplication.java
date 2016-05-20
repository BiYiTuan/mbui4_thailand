package com.moon.android.launcher.thailand;

import android.app.Application;

import com.moon.android.launcher.thailand.model.UpdateData;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class LauncherApplication extends Application{
	public static final String TAG = "LauncherApplication";
	private static LauncherApplication application;
	public static UpdateData updateData;

	public static LauncherApplication getApplication() {
		return application;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		application = this;
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
		.threadPriority(Thread.NORM_PRIORITY - 2)
		.denyCacheImageMultipleSizesInMemory()
		.diskCacheFileNameGenerator(new Md5FileNameGenerator())
		.diskCacheSize(50 * 1024 * 1024) // 50 Mb
		.tasksProcessingOrder(QueueProcessingType.LIFO)
		.writeDebugLogs() // Remove for release app
		.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
	
	
	
}
