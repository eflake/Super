package com.lavaspark.ssf4;

import com.lavaspark.adapter.VideoAdapter;
import com.lavaspark.asynctask.GetVideoInfoAsyncTask;
import com.lavaspark.asynctask.VideoUpdateAsyncTask;
import com.lavaspark.customview.RefreshableListView;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class VideoActivity extends Activity implements OnItemClickListener,com.lavaspark.customview.RefreshableListView.OnRefreshListener{
	public static VideoAdapter adapter;
	public RefreshableListView listView;
	private ProgressDialog progressDialog = null;
	public TextView textView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_layout);
		ActionBar bar = getActionBar();
		bar.setDisplayHomeAsUpEnabled(true);
		textView=(TextView) findViewById(R.id.textView1);
		ConnectivityManager cwjManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE); 
		NetworkInfo info = cwjManager.getActiveNetworkInfo(); 
		  if (info != null && info.isAvailable()){ 
			//能联网 
			  listView = (RefreshableListView) findViewById(R.id.listView1);
				progressDialog=ProgressDialog.show(VideoActivity.this, "Message", "Loading", false, true);
				GetVideoInfoAsyncTask task = new GetVideoInfoAsyncTask(getApplicationContext(), adapter,listView,progressDialog);
				task.execute();	  
				listView.setonRefreshListener(this);
				listView.setOnItemClickListener(this);		       
		  }else{ 
			  //不能联网 
			  new AlertDialog.Builder(VideoActivity.this)
	        	.setIcon(R.drawable.ic_launcher)
	        	.setTitle("NetWork Error")
	        	.setMessage("You Can Not Access Without NetWork")
	            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
	                  @Override
	                  public void onClick(DialogInterface dialog, int which) {
	              		textView.setVisibility(0);
	                  }
	            }).show();  		      
		  }
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		String url = "http://carey-blog-image.googlecode.com/files/vid_20120510_090204.mp4";
		Intent intent = new Intent();
		intent.setClass(this, VideoPlayActivity.class);
		intent.putExtra("url", url);
		intent.putExtra("cache",
				Environment.getExternalStorageDirectory().getAbsolutePath()
						+ "/VideoCache/" + System.currentTimeMillis() + ".mp4");
		startActivity(intent);
	}

	@Override
	public void onRefresh() {	
		VideoUpdateAsyncTask task = new VideoUpdateAsyncTask(getApplicationContext(), adapter,listView);
		task.execute();	   
	}
		
}
