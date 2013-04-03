package com.lavaspark.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.lavaspark.asynctask.PutEvaluateAsyncTask;
import com.lavaspark.ssf4.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ForumDetailAdapter extends BaseAdapter implements OnClickListener{
	public Context context;
	public HashMap<String, String> videoMap;
	public ArrayList<HashMap<String, String>> itemlist;
	public String infoString;
	public Context mcContext;
	public ForumDetailAdapter(Context context,ArrayList<HashMap<String, String>> list,String infoString) {
		this.context = context;
		this.itemlist = list;
		this.infoString = infoString;
		mcContext = context;
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
		convertView  = LayoutInflater.from(context).inflate(R.layout.forum_detail_list_layout, null);
		holder = new Viewholder();
		holder.assess_info = (TextView)convertView.findViewById(R.id.assess_info);
		holder.assess_author = (TextView)convertView.findViewById(R.id.assess_author);
		holder.assess_content = (TextView)convertView.findViewById(R.id.assess_content);
		holder.assess_date = (TextView)convertView.findViewById(R.id.assess_date);
		holder.assess_like_degree = (TextView)convertView.findViewById(R.id.assesslikedegree);
		convertView.setTag(holder);
	}else {
		holder = (Viewholder)convertView.getTag();
	}
		holder.assess_info.setText(getItem(position).get("title"));
		holder.assess_content.setText(getItem(position).get("content"));
		holder.assess_author.setText(getItem(position).get("poster"));
		holder.assess_date.setText(getItem(position).get("datetime"));
		holder.assess_like_degree.setText(getItem(position).get("good"));
		holder.assess_like_degree.setOnClickListener(this);
		return convertView;
	}

	public static class Viewholder{
		private TextView assess_info;
		private TextView assess_author;
		private TextView assess_date;
		private TextView assess_like_degree;
		private TextView assess_content;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.assesslikedegree:
			Toast.makeText(context, "good", Toast.LENGTH_SHORT).show();
			PutEvaluateAsyncTask task = new PutEvaluateAsyncTask();
			task.execute(infoString,"1");
			break;

		default:
			break;
		}
	}

	
}
