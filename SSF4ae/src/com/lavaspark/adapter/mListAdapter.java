package com.lavaspark.adapter;

import java.util.ArrayList;

import com.lavaspark.ssf4.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class mListAdapter extends BaseAdapter {
	
	private ArrayList<Move_attr> moveattrList;
	private LayoutInflater inflater;
	
	public mListAdapter(Context context,ArrayList<Move_attr> moveattrList) {
		this.moveattrList = moveattrList;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		if(moveattrList == null){
			return 0;
		}
		return moveattrList.size();
	}

	@Override
	public Object getItem(int position) {
		if(moveattrList == null){
			return null;
		}
		return moveattrList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Move_attr move_attr = moveattrList.get(position);
		mViewHolder holder = null;
		
		if(convertView == null){
			convertView = inflater.inflate(R.layout.move_item, null);
					
			holder = new mViewHolder();
			holder.Move_name_item = (TextView) convertView.findViewById(R.id.move_name_item);
			holder.Startup_item = (TextView) convertView.findViewById(R.id.startup_item);
			holder.On_hit_item = (TextView) convertView.findViewById(R.id.on_hit_item);
			holder.On_guard_item = (TextView) convertView.findViewById(R.id.on_guard_item);
			
			//tab是标签的意思,相当于id
			convertView.setTag(holder);
		}else{
			holder = (mViewHolder) convertView.getTag();
		}
		
		holder.Move_name_item.setText(move_attr.getmMove_Name()+"");
		holder.Startup_item.setText(move_attr.getmStartup()+"");
		holder.On_hit_item.setText(move_attr.getmOnHit()+"");
		holder.On_guard_item.setText(move_attr.getmOnGruard()+"");
		
		return convertView;
	}
	
	
	static class mViewHolder{
		TextView Move_name_item;
		TextView Startup_item;
		TextView On_hit_item;
		TextView On_guard_item;
	}
}
