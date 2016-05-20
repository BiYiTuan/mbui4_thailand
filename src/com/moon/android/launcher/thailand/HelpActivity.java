package com.moon.android.launcher.thailand;

import com.moon.android.launcher.thai.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class HelpActivity extends Activity {

	private WebView mWebView;
	private WebSettings mWebSetting;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		mWebView = (WebView) findViewById(R.id.webview);
		mWebSetting = mWebView.getSettings();     
		mWebSetting.setJavaScriptEnabled(true);    
		mWebSetting.setLoadWithOverviewMode(true);
		mWebSetting.setUseWideViewPort(true);               
		mWebView.loadUrl("file:///android_asset/default.html");
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
        	mWebView.goBack(); 
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }
}
