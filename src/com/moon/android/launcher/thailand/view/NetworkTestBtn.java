package com.moon.android.launcher.thailand.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.moon.android.launcher.thai.R;
import com.moon.android.launcher.thailand.Constant;
import com.moon.android.launcher.thailand.util.ActivityUtils;

public class NetworkTestBtn extends Button implements OnClickListener{
	
	private Context mContext;
	public NetworkTestBtn(Context context) {
		super(context);
		mContext = context;
		setOnClickListener(this);
	}

	public NetworkTestBtn(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		setOnClickListener(this);
		
	}

	public NetworkTestBtn(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		try{
			String pkgname = Constant.NETWORK_TEST;
			ActivityUtils.startActivity(mContext, pkgname);
		}catch(Exception e){
			new CustomToast(mContext, mContext.getString(R.string.app_not_found), 24).show();
		}
	}
	
	
}
