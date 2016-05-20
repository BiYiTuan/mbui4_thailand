package com.moon.android.launcher.thailand.database;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.moon.android.launcher.thailand.Configs;
import com.moon.android.launcher.thailand.util.CustomAppInfo;
import com.moon.android.launcher.thailand.util.FormatUtil;
import com.moon.android.launcher.thailand.util.Logger;
import com.moon.android.launcher.thailand.util.AppUtils.AppInfo;

public class AppDAO {
	
	public static Logger logger = Logger.getInstance();
	private DBHelper mDBHelper;
	
	public AppDAO(Context context){
		mDBHelper = new DBHelper(context);
	}
	
	public boolean insert(Context context,CustomAppInfo appInfo){
		SQLiteDatabase db = null;
		try{
			db = mDBHelper.getReadableDatabase();
			ContentValues values = new ContentValues();
			try{
//				values.put("icon", FormatUtil.getInstance().Drawable2Bytes(appInfo.icon));
			}catch(Exception e){
				e.printStackTrace();
			}
			values.put("pkgname", appInfo.pkgName);
			logger.i("insert app pkgname = " + appInfo.pkgName);
//			if(null != appInfo.title.toString())
//				values.put("appname", appInfo.title.toString());
			long insertId = db.insert(DBHelper.TABLE_APP_IN_DESKTOP, null, values);
			logger.i("Add " + appInfo.title +" to " + DBHelper.TABLE_APP_IN_DESKTOP);
			if(-1 == insertId)  {
				logger.i("Add " + appInfo.pkgName + " to " + DBHelper.TABLE_APP_IN_DESKTOP + " fail");
				return false;
			} else {
				logger.i("Add " + appInfo.pkgName +" to " + DBHelper.TABLE_APP_IN_DESKTOP + " true");
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			if(null != db)db.close();
			if(null != mDBHelper)mDBHelper.close();
		}
		return false;
	}
	
	public void delete(CustomAppInfo appInfo){
		SQLiteDatabase db = mDBHelper.getReadableDatabase();
		String sql = "delete from " + DBHelper.TABLE_APP_IN_DESKTOP +" where pkgname = '" + appInfo.pkgName+"'";
		db.execSQL(sql);
		db.close();
		mDBHelper.close();
	}
	
	public boolean canInsert(CustomAppInfo appInfo){
		/**
		 * 一些需要隐藏的应用不让显示在桌面
		 */
		for(String pkg : Configs.getHiddenAppPkg())
			if(appInfo.pkgName.equals(pkg))
				return false;
		
		Cursor c = null;
		Cursor cCount = null;
		SQLiteDatabase db = null;
		try {
			db = mDBHelper.getReadableDatabase();
			c = db.query(DBHelper.TABLE_APP_IN_DESKTOP, null, " pkgname = '" + appInfo.pkgName+"'", null, null,
					null, null, null);
			cCount = db.query(DBHelper.TABLE_APP_IN_DESKTOP, null, null, null, null,
					null, null, null);
			if(c.getCount() <= 0 && cCount.getCount() < 12) {
				logger.i(appInfo.title + " can be send to desktop");
				return true;
			} else logger.i(appInfo.title + " cannt be send to desktop");
		} catch (Exception e) {
		}finally{
			if(null != c) c.close();
			if(null != db) db.close();
		}
		return false;
	}
	
	public boolean isExist(CustomAppInfo appInfo){
		Cursor c = null;
		SQLiteDatabase db = null;
		try {
			db = mDBHelper.getReadableDatabase();
			c = db.query(DBHelper.TABLE_APP_IN_DESKTOP, null, " pkgname = '" + appInfo.pkgName +"'", null, null,
					null, null, null);
//			cCount = db.query(DBHelper.TABLE_APP_IN_DESKTOP, null, null, null, null,
//					null, null, null);
			if(c.getCount() > 0) {
				logger.i("c.getCount = " + c.getCount());
				logger.i(appInfo.title + " is Exist");
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null != c) c.close();
			if(null != db) db.close();
		}
//		logger.i(appInfo.title + " is not Exist");
		return false;
	}
	
	public List<AppInfo> getAppInfo(List<AppInfo> appInfo){
		appInfo.clear();
//		List<AppInfo> list = new ArrayList<AppInfo>();
		Cursor c = null;
		SQLiteDatabase db = null;
		try {
			db = mDBHelper.getReadableDatabase();
			c = db.query(DBHelper.TABLE_APP_IN_DESKTOP, null, null, null, null,
					null, null, null);
			while (c != null && c.moveToNext()) {
				AppInfo item = new AppInfo();
				String pkg = c.getString(c.getColumnIndex("pkgname"));
				item.setPackageName(pkg);
				logger.i("桌面的PKG = " + c.getString(c.getColumnIndex("pkgname")));
				appInfo.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null != c) c.close();
			if(null != db) db.close();
		}
		return appInfo;
	}
	
}
