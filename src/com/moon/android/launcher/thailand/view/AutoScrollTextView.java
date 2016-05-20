package com.moon.android.launcher.thailand.view;

import java.util.ArrayList;
import java.util.List;

import com.moon.android.launcher.thailand.model.LauncherMsg;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

public class AutoScrollTextView extends TextView {

	private float mTextLength = 0f;
	private float mViewWidth = 0f;
	private float mStep = 0f;
	private float mYPos = 0f;
	private float mXPos = 0f;
	private float mViewLength = 0.0f;
	private float mViewTwoLength = 0.0f;
	private boolean isStarting = false;
	private Paint mPaint = null;
	private String mCurrentShowText = "";
	private boolean isFirst = true;
	private boolean isSingleLineScrool = true;
	private int position = 0;
	private List<LauncherMsg> mListMsg = new ArrayList<LauncherMsg>();
	
	public static final float SPEED_lEVEL_1=1.0f;
	public static final float SPEED_lEVEL_1_PLUS=1.5f;
	public static final float SPEED_lEVEL_2=2.0f;
	public static final float SPEED_lEVEL_2_PLUS=2.5f;
	public static final float SPEED_lEVEL_3=3.0f;
	public static final float SPEED_lEVEL_3_PLUS=3.5f;
	public static final float SPEED_lEVEL_4=4.0f;
	public static final float SPEED_lEVEL_4_PLUS=4.5f;
	public static final float SPEED_lEVEL_5=5.0f;
	public static final float SPEED_lEVEL_5_PLUS=5.5f;
	
	private float mSpeed = SPEED_lEVEL_2_PLUS;
	
	
	public AutoScrollTextView(Context context) {
		super(context);
		init();
	}

	public AutoScrollTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public AutoScrollTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
//		this.setFocusable(true);
		init();
	}

	public void setSpeed(float speed){
		mSpeed=speed;
	}
	
	private void init() {
		mPaint = getPaint();
		mPaint.setARGB(255, 250, 250, 250);
	}

	public Parcelable onSaveInstanceState() {
		Parcelable superState = super.onSaveInstanceState();
		SavedState ss = new SavedState(superState);
		ss.step = mStep;
		ss.isStarting = isStarting;
		return ss;

	}

	public void onRestoreInstanceState(Parcelable state) {
		if (!(state instanceof SavedState)) {
			super.onRestoreInstanceState(state);
			return;
		}
		SavedState ss = (SavedState) state;
		super.onRestoreInstanceState(ss.getSuperState());

		mStep = ss.step;
		isStarting = ss.isStarting;
	}

	public static class SavedState extends BaseSavedState {
		public boolean isStarting = false;
		public float step = 0.0f;

		SavedState(Parcelable superState) {
			super(superState);
		}

		public void writeToParcel(Parcel out, int flags) {
			super.writeToParcel(out, flags);
			out.writeBooleanArray(new boolean[] { isStarting });
			out.writeFloat(step);
		}

		public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {

			public SavedState[] newArray(int size) {
				return new SavedState[size];
			}

			public SavedState createFromParcel(Parcel in) {
				return new SavedState(in);
			}
		};

		private SavedState(Parcel in) {
			super(in);
			boolean[] b = null;
			in.readBooleanArray(b);
			if (b != null && b.length > 0)
				isStarting = b[0];
			step = in.readFloat();
		}
	}


	public void startScroll() {
		isStarting = true;
		invalidate();
	}


	public void stopScroll() {
		isStarting = false;
		invalidate();
	}
	@Override
	public void onDraw(Canvas canvas) {
		if (isFirst) {
			reCaculate();
			if (mTextLength < mViewWidth && !isSingleLineScrool)
				isStarting = false;
			isFirst = false;
		}
		if (!isStarting) {
			canvas.drawText(mCurrentShowText, mXPos, mYPos, mPaint);
			return;
		}
		canvas.drawText(mCurrentShowText, mViewLength - mStep, mYPos, mPaint);
		mStep += mSpeed;
		if (mStep > mViewTwoLength){
			position ++;
			reCaculate();
			mStep = mTextLength;
		}
		invalidate();
	}

	private void reCaculate() {
		try{
			String bodys = mListMsg.get(position % mListMsg.size()).getBody();
			String showBody = null == bodys ? "" : bodys;
			mCurrentShowText = showBody;
			mViewWidth = getWidth();
			mTextLength = mPaint.measureText(mCurrentShowText);
			mStep = mTextLength;
			mViewLength = mViewWidth + mTextLength;
			mViewTwoLength = mViewWidth + mTextLength * 2;
			mYPos = getTextSize() + getPaddingTop();
			mXPos = getPaddingLeft();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	
	public void setText(List<LauncherMsg> list){
		mListMsg = list;
		if(null != list  &&  list.size() > 0)
			startScroll();
	}
	
	
	public void setText(String text){
		LauncherMsg msg = new LauncherMsg();
		msg.setBody(text);
		mListMsg.add(msg);
	}
	
	public void setSingleLineScrool(boolean isSingleLineScrool) {
		this.isSingleLineScrool = isSingleLineScrool;
	}
	
	
}
