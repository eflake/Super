package com.lavaspark.ssf4;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class Forumpersonalveiwpoint extends Activity{

	private TextView reviewtitle;
	private TextView reviewcontent;
	private TextView likedegree;
	private TextView authordata;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forum_personal_assess);
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.actionbar_bg);
		Drawable drawable = new BitmapDrawable(bitmap);
		ActionBar bar = getActionBar();
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setBackgroundDrawable(drawable);
		getActionBar().setIcon(R.drawable.icon_ryu);
		
		String assess_info = getIntent().getExtras().getString("assess_info");
		String assess_content = getIntent().getExtras().getString("assess_content");
		String assess_author = getIntent().getExtras().getString("assess_author");
		String assess_date = getIntent().getExtras().getString("assess_date");
		String assesslikedegree = getIntent().getExtras().getString("assesslikedegree");
		String authoranddate = assess_author+"     "+assess_date;
		getUIWidget();
		reviewtitle.setText(assess_info);
		reviewcontent.setText(assess_content);
		likedegree.setText(assesslikedegree);
		authordata.setText(authoranddate);
	}
	
	private void getUIWidget(){
		reviewtitle = (TextView) this.findViewById(R.id.reviewtitle);
		reviewcontent = (TextView) this.findViewById(R.id.reviewcontent);
		likedegree = (TextView) this.findViewById(R.id.likedegree);
		authordata = (TextView) this.findViewById(R.id.authordata);
	}

	
}
