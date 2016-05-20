package com.moon.android.launcher.thailand.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.moon.android.launcher.thailand.Configs;
import com.moon.android.launcher.thailand.StartAppActivity;

public class ActivityUtils {
	
	public static List<Activity> list = new ArrayList<Activity>();
	
	public static void finishAllActivity(){
		for(Activity activity : list){
			if(null != activity){
				try{
					activity.finish();
				} catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void startActivity(Context context,String pkgName){
			//增加跳转界面的目的是为了解决在启动应用时，如果应用中报错，会导致MBUI中的
			//ViewPager中的选项卡中的数据都消失的问题
			Intent intent = new Intent();
			intent.putExtra(Configs.PARAM_1, pkgName);
			intent.setClass(context, StartAppActivity.class);
			context.startActivity(intent);
	}
	
	public static void startActivity(Activity context, Class<?> classs){
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	intent.setClass(context, classs);
    	list.add(context);
    	context.startActivity(intent);
	}
	
	public static void startActivityForResult(Activity context, Class<?> classs, String strName, String result){
		list.add(context);
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setClass(context, classs);
		Bundle  budle = new Bundle();
		budle.putString(strName, result);
		intent.putExtras(budle);
    	context.startActivityForResult(intent, 0);
	}
	
	public static void startActivityForResult(Activity context, Class<?> classs, int resultFlag){
		list.add(context);
		Intent intent = new Intent(context, classs); 
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivityForResult(intent, resultFlag);  
	}
	
	public static void startActivity(Activity context, Class<?> classs, String key, List<?> result){
		list.add(context);
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setClass(context, classs);
		intent.putExtra(key, result.toArray());
    	context.startActivity(intent);
	}
	
	public static void startActivity(Activity context, Class<?> classs, String key, Serializable  result){
		list.add(context);
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setClass(context, classs);
		intent.putExtra(key, result);
    	context.startActivity(intent);
	}

}
