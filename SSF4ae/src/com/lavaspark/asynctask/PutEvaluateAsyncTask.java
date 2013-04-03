package com.lavaspark.asynctask;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.util.EntityUtils;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

public class PutEvaluateAsyncTask extends AsyncTask<String, Integer, String> {

	public PutEvaluateAsyncTask() {

	}

	@Override
	protected String doInBackground(String... params) {
		AndroidHttpClient client = null;
		HttpPut httpPut = null;
		HttpResponse httpResponse = null;
		String postUrl = "http://dev.lavaspark.com/jb/topic";
		String infoString = params[0];
		String reviewString = params[1];
		client = AndroidHttpClient.newInstance("Android");
		httpPut = new HttpPut(postUrl);
		httpPut.setHeader("Topic_id", infoString);
		httpPut.setHeader("Review", reviewString);
		try {
			httpResponse = client.execute(httpPut);
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
	}

}
