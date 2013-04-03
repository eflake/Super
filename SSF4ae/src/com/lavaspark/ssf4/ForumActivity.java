package com.lavaspark.ssf4;

import com.lavaspark.adapter.ForumAdapter;
import com.lavaspark.adapter.Forum_Custom_Adapter;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class  ForumActivity  extends Activity implements OnItemClickListener,android.view.View.OnClickListener{
	public ListView listView;
	public ForumAdapter adapter; 
	public TextView textView;
	public  String [] characters;
	public static int PositionImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.forum_layout);
		
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.actionbar_bg);
		Drawable drawable = new BitmapDrawable(bitmap);
		ActionBar bar = getActionBar();
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setBackgroundDrawable(drawable);
		getActionBar().setIcon(R.drawable.icon_ryu);
		listView = (ListView) findViewById(R.id.listView1);
		textView=(TextView) findViewById(R.id.textView1);
		Bundle bundle = getIntent().getExtras();
		characters = getResources().getStringArray(R.array.character_name);
		String bundleString = bundle.getString("character");
		textView.setText(bundleString);
		int positionimage = bundle.getInt("position");
		System.out.println("    aa"+positionimage);
	    PositionImage = positionimage;
		
		textView.setOnClickListener(this);
		for (int i = 0; i < characters.length; i++) {
			String s = bundleString;
			s=s.concat("_"+characters[i]);
			characters[i]=s;
		}
		Forum_Custom_Adapter  forum_Custom_Adapter = new Forum_Custom_Adapter(this, 0);
		listView.setAdapter(forum_Custom_Adapter);
		listView.setOnItemClickListener(this);
	}
		

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.textView1:
			for (int i = 0; i < characters.length; i++) {
				Log.d("test", characters[i]);				
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
			String vsInfoString = characters[position]; 
			Log.d("test", vsInfoString);
			Intent intentanother = new Intent(ForumActivity.this, ForumDetailActivity.class);
			intentanother.putExtra("info", vsInfoString);
			startActivity(intentanother);
		}
	

}
