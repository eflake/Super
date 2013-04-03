package com.lavaspark.adapter;

import java.util.HashMap;
import java.util.List;
import com.lavaspark.ssf4.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FrameListAdapter extends BaseAdapter {
	public Context context;
	public Viewholder holder;
	public List<String> nameList ;
	public List<String> frameKeyList;
	public HashMap<String,String> frameMap;
	public List<HashMap<String, String>> allFrameList;
	public FrameListAdapter(Context context,List<String> namelist,List<String>frameKeyList,List<HashMap<String, String>>allFrameList) {
		this.context = context;
		this.nameList= namelist;
		this.frameKeyList = frameKeyList;
		this.allFrameList =allFrameList;
	}

	@Override
	public int getCount() {
		return nameList.size();
	}

	@Override
	public HashMap<String, String> getItem(int position) {
		return allFrameList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.frame_list_layout, null);
			holder= new Viewholder();
			holder.text = (TextView) convertView.findViewById(R.id.textView1);
			holder.text1 = (TextView) convertView.findViewById(R.id.textView2);
			holder.text2 = (TextView) convertView.findViewById(R.id.textView3);
			convertView.setTag(holder);
		} else {
			holder = (Viewholder)convertView.getTag();
		}
		holder.text.setText(nameList.get(position));
		holder.text1.setText("2");
		holder.text2.setText("3");

		return convertView;
	}
	public static class Viewholder{
		private TextView text;
		private TextView text1;
		private TextView text2;

	}
}
