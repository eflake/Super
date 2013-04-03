package com.lavaspark.ssf4;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lavaspark.adapter.FrameListAdapter;
import com.lavaspark.adapter.Move_attr;
import com.lavaspark.adapter.mListAdapter;
import com.lavaspark.db.DBManager;
import com.lavaspark.db.EncryptionDecryption;
import com.lavaspark.util.GlobalVariables;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class FrameDataActivity extends Activity {
	private SQLiteDatabase database;
	private ArrayList<String> playerone_move_name_list = new ArrayList<String>();
	private TextView jsonText;
	private String tablename;
	public HashMap<String,String> frameMap;

	public ListView listView;
	private EncryptionDecryption encryptionDecryption;
	
	private String Deliver_str = "";
	
	private String character_name;
	private ArrayList<Move_attr> arraymoveList;
	private AlertDialog alert;
	private mListAdapter adapter;
	private GlobalVariables globalVariable;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.framedata_list_layout);
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.actionbar_bg);
		Drawable drawable = new BitmapDrawable(bitmap);
		ActionBar bar = getActionBar();
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setBackgroundDrawable(drawable);
		getActionBar().setIcon(R.drawable.icon_ryu);
		Bundle bundle = getIntent().getExtras();
		character_name = bundle.getString("character");
		Log.i("lei", "framedat character_name = "+character_name);
		globalVariable = ((GlobalVariables)this.getApplicationContext());
		AsynGetDataFromDatabase asynGetDataFromDatabase = new AsynGetDataFromDatabase();
		asynGetDataFromDatabase.execute("");
		LayoutInflater flater = LayoutInflater.from(FrameDataActivity.this);
		View view = flater.inflate(R.layout.customdialog, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(FrameDataActivity.this);
		builder.setCancelable(false)
		.setView(view);
		alert = builder.create();
		alert.show();
		listView = (ListView) findViewById(R.id.move_listview);
		arraymoveList = new ArrayList<Move_attr>();
	}
	
	private void setlistonlick(){
		adapter = new mListAdapter(FrameDataActivity.this, arraymoveList);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
					JSONArray jsonarray = globalVariable.getJsonarray();
					try {
						JSONObject jsonobject = jsonarray.getJSONObject(arg2);
						Intent intent = new Intent(FrameDataActivity.this, EveryMoveattr.class);
						intent.putExtra("number", arg2);
						intent.putExtra("content", jsonobject.toString());
						startActivity(intent);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});
	}


	class AsynGetDataFromDatabase  extends AsyncTask<String, Integer, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				DBManager.getdbManger(getApplicationContext()).querydata(character_name, "jsonPhaserframeKeyAndallFrame");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			arraymoveList  = globalVariable.getArraymoveList();
			Iterator a = arraymoveList.iterator();
			while(a.hasNext()){
				Log.i("sql", " a = "+a.next());
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			alert.cancel();
			setlistonlick();
			
		}

	}
	

	
}