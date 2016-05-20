package com.moon.android.launcher.thailand.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.moon.android.launcher.thai.R;
import com.moon.android.launcher.thailand.Configs;
import com.moon.android.launcher.thailand.HelpActivity;
import com.moon.android.launcher.thailand.HomeActivity;

public class SettingBtn extends Button implements OnClickListener{
	
	private Context mContext;
	public SettingBtn(Context context) {
		super(context);
		mContext = context;
		setOnClickListener(this);
	}

	public SettingBtn(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		setOnClickListener(this);
	}

	public SettingBtn(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		
		Intent intent = new Intent();
		intent.setClass(mContext.getApplicationContext(), HomeActivity.class);
		//intent.putExtra(Configs.PARAM_1,null);
		intent.putExtra(Configs.PARAM_2, R.string.app_and_set);
		mContext.startActivity(intent);
		/*try{
//			String pkgname = Constant.CLEARN;
//			ActivityUtils.startActivity(mContext, pkgname);
			Intent intent = new Intent(mContext,HelpActivity.class);
			mContext.startActivity(intent);
		}catch(Exception e){
			new CustomToast(mContext, mContext.getString(R.string.app_not_found), 24).show();
		}*/
	}
	
	
}
