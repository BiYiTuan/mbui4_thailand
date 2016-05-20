package com.moon.android.launcher.thailand.view;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.moon.android.launcher.thai.R;
import com.moon.android.launcher.thailand.Configs;
import com.moon.android.launcher.thailand.Declare;
import com.moon.android.launcher.thailand.model.AdMsg;
import com.moon.android.launcher.thailand.util.Logger;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class AdScrollView extends LinearLayout{
	
	private Logger log = Logger.getInstance();
	private Context mContext;
	private int mAdImageSelect;
	private ImageView mGalleryAd;
	private PageIndicatorView mPageView;
	private DisplayImageOptions mOptions;
	private Timer mTimerAdChange = new Timer(true);
	protected static final int 	CHANGE_AD_PICTURE = 0X00012;
	
	public AdScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		initView();
	}

	public AdScrollView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public AdScrollView(Context context) {
		this(context,null);
	}


	private void initView() {
		View view = LayoutInflater.from(getContext()).inflate(
				R.layout.scroll_ad, this);
		mGalleryAd = (ImageView) view.findViewById(R.id.ad_image);
		mPageView = (PageIndicatorView) view.findViewById(R.id.ad_page_num);
		
		mOptions = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.thai_default_ad)
		.showImageForEmptyUri(R.drawable.thai_default_ad)
		.showImageOnFail(R.drawable.thai_default_ad)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.displayer(new RoundedBitmapDisplayer(1))
		.build();
		regBroadCast();
		startScrollAd();
	}
	
	
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){
		@SuppressWarnings("unchecked")
		@Override
		public void onReceive(Context context, Intent intent) {
			log.i("接收到广告图片的广播");
			Declare.listAdMsg = (List<AdMsg>) intent.getSerializableExtra(Configs.PARAM_1);
			mAdImageSelect = 0;
			fillGallery(false);
		}
	};
	
	private void regBroadCast() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction(Configs.BroadCastConstant.GET_AD_PICTURE);
		mContext.registerReceiver(mBroadcastReceiver, myIntentFilter);
	}
	
	private void fillGallery(boolean isBack){
		if(Declare.listAdMsg.size() > 0){
			int totalCount = Declare.listAdMsg.size();
			if(isBack){
				if(mAdImageSelect - 1 < 0)
					mAdImageSelect = Declare.listAdMsg.size() - 1;
				else mAdImageSelect--;
			} else {
				if(mAdImageSelect + 1 > Declare.listAdMsg.size() -1 )
					mAdImageSelect = 0;
				else mAdImageSelect++;
			}
			String imageUrl = Declare.listAdMsg.get(mAdImageSelect).getPicurl();
			try{
				ImageLoader.getInstance().displayImage(imageUrl, mGalleryAd, mOptions);
				log.e("ad count = " + totalCount);
				log.i("the display picture url = " + imageUrl);
				mPageView.setTotalPage(totalCount);
				mPageView.setCurrentPage(mAdImageSelect);
			}catch(Exception e){
				log.e(e.toString());
			}
		}
	}
	
	private void startScrollAd() {
		mTimerAdChange.schedule(new TimerTask() {
			public void run() {
				try{
					mHandler.sendEmptyMessage(CHANGE_AD_PICTURE);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}, 30000L, 30000L);		
	}
	
	public void restart(){
		fillGallery(false);
	}
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case CHANGE_AD_PICTURE:
				fillGallery(false);
				break;
			default:
				break;
			}
		};
	};
}
