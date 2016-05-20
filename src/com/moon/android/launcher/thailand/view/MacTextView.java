package com.moon.android.launcher.thailand.view;

import com.moon.android.launcher.thailand.util.MACUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class MacTextView extends TextView{

	public MacTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initWiget();
	}

	public MacTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initWiget();
	}

	public MacTextView(Context context) {
		super(context);
		initWiget();
	}

	private void initWiget() {
		setText(MACUtils.getMac());
	}
	
	

	
}
