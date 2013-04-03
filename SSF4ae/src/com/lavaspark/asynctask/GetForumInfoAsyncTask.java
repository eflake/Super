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
import com.lavaspark.adapter.ForumDetailAdapter;
import com.lavaspark.customview.RefreshableListView;
import com.lavaspark.ssf4.ForumDetailActivity;
import com.lavaspark.ssf4.Forumpersonalveiwpoint;
import com.lavaspark.ssf4.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class GetForumInfoAsyncTask extends AsyncTask<String, Integer, String>  implements OnItemClickListener{
	public Context context;
	public ForumDetailAdapter adapter;
	public RefreshableListView listView;
	public HashMap<String, String> forumMap;
	public ArrayList<HashMap<String, String>> forumItemList;
	public String result;
	public ProgressDialog dialog;
	public GetForumInfoAsyncTask(Context context,ForumDetailAdapter adapter,RefreshableListView listView,ProgressDialog dialog) {
		this.context = context;
		this.listView = listView;
		this.adapter =adapter;
		this.dialog = dialog;
		forumItemList = new ArrayList<HashMap<String,String>>();
	}

	@Override
	protected String doInBackground(String... params) {
		AndroidHttpClient client = null;
		HttpGet httpGet = null;		
		HttpResponse httpResponse = null;
		String httpUrl = "http://dev.lavaspark.com/jb/topiclist";
		client = AndroidHttpClient.newInstance("Android");
		String characterString = params[0];
		httpGet = new HttpGet(httpUrl);
		httpGet.setHeader("forum_id",characterString);
		httpGet.setHeader("show_amount", "10");
		try {
			httpResponse = client.execute(httpGet);
			Log.d("lavaspark", "Send ok");
		    if(httpResponse.getStatusLine().getStatusCode()==200){
					Log.d("lavaspark", "Responce ok");
				    result = EntityUtils.toString(httpResponse.getEntity());
					Log.d("lavaspark", result);
			 }else {
					Log.d("lavaspark", Integer.valueOf(httpResponse.getStatusLine().getStatusCode()).toString());
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
		ForumDetailActivity.adapter = new ForumDetailAdapter(context,jsonPhaser(result),characterString);
		return result;
	}

	@Override
	protected void onPostExecute(String resultjsonString) {
		Toast.makeText(context,"Refresh Complete", Toast.LENGTH_SHORT).show();
		listView.setAdapter(ForumDetailActivity.adapter);
		listView.setOnItemClickListener(this);
		dialog.dismiss();
	}
	
	public ArrayList<HashMap<String, String>> jsonPhaser(String result){
		try {
			JSONArray array = new JSONArray(result);
			for (int i = 0; i < array.length(); i++) {
				JSONObject object = array.getJSONObject(i);
				forumMap = new HashMap<String, String>();
				for (Iterator<String> iterator = object.keys(); iterator.hasNext();) 
				{
					String keyString = iterator.next();
					String data = object.getString(keyString);
					forumMap.put(keyString, data);
				}
				forumItemList.add(forumMap);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return forumItemList;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Toast.makeText(context,"this "+arg2+"pressed", 0).show();
		TextView assess_info = (TextView) view.findViewById(R.id.assess_info);
		TextView assess_content = (TextView) view.findViewById(R.id.assess_content);
		TextView assess_author = (TextView) view.findViewById(R.id.assess_author);
		TextView assess_date = (TextView) view.findViewById(R.id.assess_date);
		TextView assesslikedegree = (TextView) view.findViewById(R.id.assesslikedegree);
		Intent intent = new Intent(context, Forumpersonalveiwpoint.class);
		intent.putExtra("assess_info", assess_info.getText().toString());
		intent.putExtra("assess_content", assess_content.getText().toString());
		intent.putExtra("assess_author", assess_author.getText().toString());
		intent.putExtra("assess_date", assess_date.getText().toString());
		intent.putExtra("assesslikedegree", assesslikedegree.getText().toString());
		context.startActivity(intent);
	}
}
