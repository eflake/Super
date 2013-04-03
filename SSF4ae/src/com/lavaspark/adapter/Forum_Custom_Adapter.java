package com.lavaspark.adapter;

import com.lavaspark.ssf4.ForumActivity;
import com.lavaspark.ssf4.R;

import android.R.drawable;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Forum_Custom_Adapter extends BaseAdapter{

	public Context context;
	public int left_num;
	public int person_all = 39;
	private LayoutInflater inflater;
	public String[] character_name;
	public int[] character_id = new int[]{R.drawable.ryu,R.drawable.ryu};
	private View view;  
	public Forum_Custom_Adapter(Context context , int left_num) {

		super();
		this.context = context;
		ApplicationInfo appInfo = context.getApplicationInfo();
		
		this.left_num = left_num;
		character_name = context.getResources().getStringArray(R.array.character_name);
//		character_name = new String[]{"ryu","lion"};
//		getImageId(appInfo);
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void getImageId(ApplicationInfo appInfo){
		for(int i = 0;i<character_name.length;i++){
			int  resId = context.getResources().getIdentifier(character_name[i], "drawable", appInfo.packageName);
			character_id[i] = resId;
		}
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return person_all;
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if(character_name == null){
			return null;
		}
		return character_name[position];
	}
	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ForumViewHolder holder = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.forum_custonm_content_layout, null);
			holder = new ForumViewHolder();
			holder.left_iamgeview = (ImageView) convertView.findViewById(R.id.left_person);
			holder.left_name = (TextView) convertView.findViewById(R.id.left_name);
			holder.right_iImageView = (ImageView) convertView.findViewById(R.id.right_person);
			holder.right_name = (TextView) convertView.findViewById(R.id.right_name);
			convertView.setTag(holder);
		}else{
			holder = (ForumViewHolder) convertView.getTag();
		}
		System.out.println("aa   "+ForumActivity.PositionImage);
		holder.left_iamgeview.getDrawable().setLevel(ForumActivity.PositionImage+1); 
		holder.left_name.setText(character_name[ForumActivity.PositionImage]);
		holder.right_iImageView.getDrawable().setLevel(position+1);
		holder.right_name.setText(character_name[position]);
		return convertView;
	}

	static class ForumViewHolder{
		ImageView left_iamgeview;
		TextView left_name;
		ImageView right_iImageView;
		TextView right_name;
	}


}
