package com.moon.android.launcher.thailand.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.moon.android.launcher.thai.R;
import com.moon.android.launcher.thailand.HelpActivity;

public class HelpBtn extends Button implements OnClickListener{
	
	private Context mContext;
	public HelpBtn(Context context) {
		super(context);
		mContext = context;
		setOnClickListener(this);
	}

	public HelpBtn(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		setOnClickListener(this);
	}

	public HelpBtn(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		try{
//			String pkgname = Constant.CLEARN;
//			ActivityUtils.startActivity(mContext, pkgname);
			Intent intent = new Intent(mContext,HelpActivity.class);
			mContext.startActivity(intent);
		}catch(Exception e){
			new CustomToast(mContext, mContext.getString(R.string.app_not_found), 24).show();
		}
	}
	
	
}
