package com.lavaspark.adapter;

import java.util.zip.Inflater;

import com.lavaspark.ssf4.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BarDropListAdapter extends BaseAdapter {
	public String[] array;
	public Context context;
	public BarDropListAdapter(String[] array,Context context) {
		this.array = array;
		this.context = context;
	}

	@Override
	public int getCount() {
		return array.length;
	}

	@Override
	public String getItem(int position) {
		return array[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		mViewHolder holder = new mViewHolder();
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.bar_droplist_layout, null);
			holder.icon = (ImageView) convertView.findViewById(R.id.imageView1);
		    holder.name = (TextView) convertView.findViewById(R.id.textView1);
		    convertView.setTag(holder);
		}else {
			holder = (mViewHolder)convertView.getTag();
		}
		switch (position) {
		case 0:
			holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.menu_wallpaper));
			break;
		case 1:
			holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.menu_video));
			break;
		case 2:
			holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.menu_forum));
			break;
		case 3:
			holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.menu_frame));
			break;
		default:
			break;
		}
		holder.name.setText(getItem(position));
		
		return convertView;
	}
	static class mViewHolder{
		TextView name;
		ImageView icon;
	}
}



