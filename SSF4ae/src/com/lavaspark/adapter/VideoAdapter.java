package com.lavaspark.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import com.lavaspark.ssf4.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class VideoAdapter extends BaseAdapter {
	public Context context;
	public HashMap<String, String> videoMap;
	public ArrayList<HashMap<String, String>> itemlist;
	public VideoAdapter(Context context,ArrayList<HashMap<String, String>> list) {
		this.context = context;
		this.itemlist = list;
	}

	@Override
	public int getCount() {
		return itemlist.size();
	}

	@Override
	public HashMap<String, String> getItem(int position) {
		return itemlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Viewholder holder = null;
		if (convertView == null) {
		convertView  = LayoutInflater.from(context).inflate(R.layout.video_list_layout, null);
		holder = new Viewholder();
		holder.image = (ImageView) convertView.findViewById(R.id.imageView1);
		holder.text = (TextView)convertView.findViewById(R.id.textView1);
		holder.text1 = (TextView) convertView.findViewById(R.id.textView2);
		convertView.setTag(holder);
	}else {
		holder = (Viewholder)convertView.getTag();
	}
		holder.text.setText(getItem(position).get("name"));
		holder.text1.setText(getItem(position).get("url"));
		holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.yu));
		return convertView;
	}

	public static class Viewholder{
		private ImageView image;
		private TextView text;
		private TextView text1;
	}

}
