package com.lavaspark.ssf4;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

public class ZhaoShiActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zhaoshi_layout);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.abbg));
	}
}
