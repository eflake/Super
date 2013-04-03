package com.lavaspark.ssf4;

import com.lavaspark.adapter.ForumDetailAdapter;
import com.lavaspark.asynctask.ForumRefreshAsyncTask;
import com.lavaspark.asynctask.GetForumInfoAsyncTask;
import com.lavaspark.customview.RefreshableListView;
import com.lavaspark.customview.RefreshableListView.OnRefreshListener;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class ForumDetailActivity extends Activity implements OnItemClickListener,OnRefreshListener {
	public static ForumDetailAdapter adapter;
	public RefreshableListView listView;
	private ProgressDialog progressDialog = null;
	public TextView textView;
	
	public String infoString;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.forum_detail_layout);		
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.actionbar_bg);
		Drawable drawable = new BitmapDrawable(bitmap);
		ActionBar bar = getActionBar();
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setBackgroundDrawable(drawable);
		getActionBar().setIcon(R.drawable.icon_ryu);
		textView=(TextView) findViewById(R.id.textView1);
		Bundle bundle = getIntent().getExtras();
	    infoString =bundle.getString("info");
		Log.d("test",infoString );
		ConnectivityManager cwjManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE); 
		NetworkInfo info = cwjManager.getActiveNetworkInfo(); 
		  if (info != null && info.isAvailable()){ 
			//能联网 
			    listView = (RefreshableListView) findViewById(R.id.listView1);
				progressDialog=ProgressDialog.show(ForumDetailActivity.this, "Message", "Loading", false, true);
				GetForumInfoAsyncTask task = new GetForumInfoAsyncTask(this, adapter,listView,progressDialog);
				task.execute(infoString);	  
				listView.setonRefreshListener(this);
				listView.setOnItemClickListener(this);		       
		  }else{ 
			  //不能联网 
			  new AlertDialog.Builder(ForumDetailActivity.this)
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
	    public boolean onCreateOptionsMenu(Menu menu) {
	        getMenuInflater().inflate(R.menu.forum_detail_menu, menu);
	        return true;
	    }
	 
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		   switch (item.getItemId()) {
		case R.id.evaluate_add:
			Intent intent = new Intent(ForumDetailActivity.this, ForumEvaluateActivity.class);
			intent.putExtra("info", infoString);
			startActivityForResult(intent, 111);
			break;

		default:
			break;
		}
		   return super.onOptionsItemSelected(item);
	}
	@Override
	public void onRefresh() {
		ForumRefreshAsyncTask task = new ForumRefreshAsyncTask(ForumDetailActivity.this, adapter, listView);
		task.execute(infoString);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Toast.makeText(this, "you press screen", 0).show();
	}
}