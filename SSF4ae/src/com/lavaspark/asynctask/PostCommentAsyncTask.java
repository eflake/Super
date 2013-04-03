package com.lavaspark.asynctask;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.lavaspark.ssf4.ForumDetailActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

public class PostCommentAsyncTask extends AsyncTask<String, Integer, String> {
	public String title;
	public String content;
	public Context context;
	public String infoString;
	public PostCommentAsyncTask(String title,String content,String infosString, Context context) {
		this.title = title;
		this.content = content;
		this.infoString =infosString;
		this.context = context;
	}

	@Override
	protected String doInBackground(String... params) {
		AndroidHttpClient client = null;
		HttpPost httpPost = null;
		HttpResponse httpResponse = null;
		HttpEntity httpEntity = null;
		String postUrl = "http://dev.lavaspark.com/jb/topic";
		client = AndroidHttpClient.newInstance("Android");
		httpPost = new HttpPost(postUrl);
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("Content",content));
		param.add(new BasicNameValuePair("Sender","eflake"));
		try {
			 httpEntity = new UrlEncodedFormEntity(param, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		httpPost.setEntity(httpEntity);
		try {
			httpResponse = client.execute(httpPost);
			Log.i("lavaspark", "Send");
		} catch (IOException e) {
			e.printStackTrace();
		}
		String result = null;
		if (httpResponse.getStatusLine().getStatusCode()==200) {
			try {
				result = EntityUtils.toString(httpResponse.getEntity());
				Log.i("lavaspark", "result:" +result );
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			Log.i("lavaspark", Integer.valueOf(httpResponse.getStatusLine().getStatusCode()).toString());
			Log.i("lavaspark", "fail" );
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		Log.i("lavaspark", "postExcute");
		Activity activity = (Activity)context;
		Intent intent = new Intent();
		activity.setResult(111,intent);
		activity.finish();
	}

}
