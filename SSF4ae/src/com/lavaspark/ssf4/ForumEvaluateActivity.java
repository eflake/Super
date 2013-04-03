package com.lavaspark.ssf4;

import com.lavaspark.asynctask.PostCommentAsyncTask;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class ForumEvaluateActivity extends Activity {
	public String infoString;
	public EditText title;
	public EditText content;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.forum_evaluate_layout);
		infoString=getIntent().getStringExtra("info");
		Toast.makeText(ForumEvaluateActivity.this, infoString, Toast.LENGTH_SHORT).show();
		title = (EditText) findViewById(R.id.editText1);
		content = (EditText) findViewById(R.id.editText2);

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.forum_evaluate_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.evaluate_complete:
			String titleString  = title.getText().toString();
			String contentString = content.getText().toString();
			PostCommentAsyncTask task = new PostCommentAsyncTask(titleString,contentString,infoString,ForumEvaluateActivity.this);
			task.execute(infoString);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
