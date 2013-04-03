package com.lavaspark.adapter;

import com.lavaspark.ssf4.R;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ForumAdapter extends BaseAdapter {
	public Context context;
	public String [] characters;
	public ForumAdapter(Context context,String[] characters) {
		this.context = context;
		this.characters = characters;
	}

	@Override
	public int getCount() {
		return characters.length;
	}

	@Override
	public String getItem(int position) {
		int divider = characters[position].indexOf("_");
		String main  = characters[position].substring(0, divider);
		Log.d("lavaspark", Integer.valueOf(divider).toString());
		Log.d("lavaspark", Integer.valueOf(characters[position].length()).toString());
		String other = characters[position].substring(divider+1, characters[position].length());
		String all = main + " " + "vs"+ " "+ other;
		return all;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Viewholder holder = null;
		if (convertView == null) {
		convertView  = LayoutInflater.from(context).inflate(R.layout.forum_list_layout, null);
		holder = new Viewholder();
		holder.image = (ImageView) convertView.findViewById(R.id.imageView1);
		holder.text = (TextView)convertView.findViewById(R.id.textView1);
		convertView.setTag(holder);
	}else {
		holder = (Viewholder)convertView.getTag();
	}
		holder.text.setText(getItem(position));
		holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.forum));
		return convertView;
	}

	public static class Viewholder{
		private ImageView image;
		private TextView text;
	}

}
