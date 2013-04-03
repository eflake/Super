package com.lavaspark.asynctask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.lavaspark.adapter.VideoAdapter;
import com.lavaspark.customview.RefreshableListView;
import com.lavaspark.ssf4.VideoActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class GetVideoInfoAsyncTask extends AsyncTask<String, Integer, String> {
	public Context context;
	public VideoAdapter adapter;
	public RefreshableListView listView;
	public HashMap<String, String> videoMap;
	public ArrayList<HashMap<String, String>> itemlist;
	public String result;
	public ProgressDialog dialog;
	public GetVideoInfoAsyncTask(Context context,VideoAdapter adapter,RefreshableListView listView,ProgressDialog dialog) {
		this.context = context;
		this.listView = listView;
		this.adapter =adapter;
		this.dialog = dialog;
		itemlist =new ArrayList<HashMap<String,String>>();
	}

	@Override
	protected String doInBackground(String... params) {
		AndroidHttpClient client = null;
		HttpGet httpGet = null;
		HttpResponse httpResponse = null;
		String httpUrl = "http://dev.lavaspark.com/jb/videolist";
		client = AndroidHttpClient.newInstance("Android");
		String paramString = "last_id=0"+"&"+"show_amount=10";
		httpGet = new HttpGet(httpUrl);
		httpGet.setHeader("last_id", "0");
		httpGet.setHeader("show_amount", "8");
		try {
			httpResponse = client.execute(httpGet);
			Log.d("lavaspark", "Send ok");
		    if(httpResponse.getStatusLine().getStatusCode()==200){
					Log.d("lavaspark", "Responce ok");
				    result = EntityUtils.toString(httpResponse.getEntity());
					Log.d("lavaspark", result);
			 }else {
				Log.d("lavaspark", "Responce fail");
			    result = "";
			}	
		} 
		catch (ParseException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (client != null) {
				client.close();
			}
		}
	    VideoActivity.adapter = new VideoAdapter(context,jsonPhaser(result));
		return result;
	}

	@Override
	protected void onPostExecute(String resultjsonString) {
		Toast.makeText(context,"Refresh Complete", Toast.LENGTH_SHORT).show();
		listView.setAdapter(VideoActivity.adapter);
		dialog.dismiss();
	}
	
	public ArrayList<HashMap<String, String>> jsonPhaser(String result){
		try {
			JSONArray array = new JSONArray(result);
			for (int i = 0; i < array.length(); i++) {
				JSONObject object = array.getJSONObject(i);
				videoMap = new HashMap<String, String>();
				for (Iterator<String> iterator = object.keys(); iterator.hasNext();) 
				{
					String keyString = iterator.next();
					String data = object.getString(keyString);
					videoMap.put(keyString, data);
				}
				itemlist.add(videoMap);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Log.d("lavaspark", itemlist.toString());
		return itemlist;
	}
}
