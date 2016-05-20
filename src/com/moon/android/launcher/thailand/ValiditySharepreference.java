package com.moon.android.launcher.thailand;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ValiditySharepreference {
		
	public static final String VALIDITY_SHARE = "VALDITY_SHARE";
	public static final String VALIDITY = "VALIDITY";
	public static final String TIME = "0000-00-00";
	
	public static void setHasRead(Context context,String time){
		SharedPreferences sharedPreferences = context.getSharedPreferences(VALIDITY_SHARE, Context.MODE_PRIVATE);
		Editor delEditor = sharedPreferences.edit();
		delEditor.remove(VALIDITY);
		delEditor.commit();
		Editor addEditor = sharedPreferences.edit();
		addEditor.putString(VALIDITY, time);
		addEditor.commit();
	}
	
	public static String getHasRead(Context context){
		SharedPreferences sharedPreferences = context.getSharedPreferences(VALIDITY_SHARE, Context.MODE_PRIVATE);
		String value = sharedPreferences.getString(VALIDITY, TIME);
		return value;
	}

}
