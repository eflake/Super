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

public class EveryMoveAttraListAdapter extends BaseAdapter {
	
	private ArrayList<EveryMoveAttrademo> moveattrList;
	private LayoutInflater inflater;
	
	public EveryMoveAttraListAdapter(Context context,ArrayList<EveryMoveAttrademo> moveattrList) {
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
		EveryMoveAttrademo move_attr = moveattrList.get(position);
		ViewHolder holder = null;
		
		if(convertView == null){
			convertView = inflater.inflate(R.layout.everymoveattrvalue, null);
					
			holder = new ViewHolder();
			holder.every_move_attra = (TextView) convertView.findViewById(R.id.every_move_attra);
			holder.every_move_attravlaue = (TextView) convertView.findViewById(R.id.every_move_attravlaue);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.every_move_attra.setText(move_attr.getStr_attra()+"");
		holder.every_move_attravlaue.setText(move_attr.getStr_attra_value()+"");
		
		return convertView;
	}
	
	
	static class ViewHolder{
		TextView every_move_attra;
		TextView every_move_attravlaue;
	}
}
