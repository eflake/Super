package com.lavaspark.adapter;

import com.lavaspark.ssf4.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SelectCharacterListAdapter extends BaseAdapter {
	public Context context;
	public String[] array;

	public SelectCharacterListAdapter(Context context) {
		super();
		this.context = context;
		this.array  = context.getResources().getStringArray(R.array.character_name);
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
		Viewholder holder = null;
		if (convertView == null) {
		convertView  = LayoutInflater.from(context).inflate(R.layout.main_list_layout, null);
		holder = new Viewholder();
		holder.image = (ImageView) convertView.findViewById(R.id.imageView1);
		holder.text = (TextView)convertView.findViewById(R.id.textView1);
		convertView.setTag(holder);
	}else {
		holder = (Viewholder)convertView.getTag();
	}
		holder.text.setText(getItem(position));
		holder.image.getDrawable().setLevel(position+1);
		return convertView;
	}

	public static class Viewholder{
		private ImageView image;
		private TextView text;
	}

}
