package com.moon.android.launcher.thailand.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.moon.android.launcher.thai.R;
import com.moon.android.launcher.thailand.Constant.CountryItem;

public class CountryAdapter extends BaseAdapter {
	private List<CountryItem> mListCountrys;
	private LayoutInflater mInflater;

	public CountryAdapter(Context context, List<CountryItem> list) {
		mListCountrys = list;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return mListCountrys.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mListCountrys.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		Holder holder = null;
		if (null == convertView) {
			holder = new Holder();
			convertView = mInflater.inflate(R.layout.country_item, null);
			holder.image = (ImageView) convertView
					.findViewById(R.id.country_image);
			holder.text = (TextView) convertView
					.findViewById(R.id.country_name);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		CountryItem item = mListCountrys.get(position);
		holder.image.setBackgroundResource(item.imageRes);
		holder.text.setText(item.nameRes);
		return convertView;
	}

	class Holder {
		ImageView image;
		TextView text;
	}
}