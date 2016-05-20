package com.moon.android.launcher.thailand.view;

import com.moon.android.launcher.thailand.Configs;
import com.moon.android.launcher.thailand.Declare;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.AttributeSet;
import android.widget.TextView;

public class ValidityView extends TextView{
	
	private Context mContext;
	public ValidityView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		initWiget();
	}

	public ValidityView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initWiget();
	}

	public ValidityView(Context context) {
		super(context);
		mContext = context;
		initWiget();
	}

	private void initWiget() {
		regRegionLimitBroad();
		if(null == Declare.VALIDITY_TIME || "".equals(Declare.VALIDITY_TIME))
			Declare.VALIDITY_TIME = "0000-00-00";
		setText(Declare.VALIDITY_TIME);
	}
	
	private void regRegionLimitBroad() {
		IntentFilter regionLimitFilter = new IntentFilter();
		regionLimitFilter.addAction(Configs.RegionLimit.ACTION_REGION_LIMIT);
		mContext.registerReceiver(mReceiverRegionLimit, regionLimitFilter);
	}
	
	private BroadcastReceiver mReceiverRegionLimit = new BroadcastReceiver(){
		@Override
		public void onReceive(Context context, Intent intent) {
			setText(Declare.VALIDITY_TIME);
		}
	};
	

}
