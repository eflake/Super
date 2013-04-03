package com.lavaspark.ssf4;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import com.lavaspark.adapter.EveryMoveAttraListAdapter;
import com.lavaspark.adapter.EveryMoveAttrademo;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ListView;

public class EveryMoveattr extends Activity {


	private ArrayList<EveryMoveAttrademo> moveattrslist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.everymoveattr);
		int numb = getIntent().getExtras().getInt("number");
		String str = getIntent().getStringExtra("content");
		ListView everymoveattra = (ListView) this.findViewById(R.id.everymoveattra);
		moveattrslist = new ArrayList<EveryMoveAttrademo>();
		
		try {
			getdata(str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		EveryMoveAttraListAdapter moveAttraListAdapter = new EveryMoveAttraListAdapter(this, moveattrslist);
		everymoveattra.setAdapter(moveAttraListAdapter);
	}

	public void getdata(String str) throws Exception{
		JSONObject mJsonObject = new JSONObject(str);
		System.out.println("mJsonObject = "+mJsonObject);
		Iterator<String> iterator = mJsonObject.keys();
		String item_name = iterator.next();
		System.out.println("item_name ="+item_name);
		JSONArray smJsonaArray  = mJsonObject.getJSONArray(item_name);
		for(int i = 0;i<smJsonaArray.length();i++){
			JSONObject object = smJsonaArray.getJSONObject(i);
			for (Iterator<String> iteratorNext = object.keys(); iteratorNext.hasNext();) {
				EveryMoveAttrademo attrademo = new EveryMoveAttrademo();
				String objectKey = iteratorNext.next();
				String objectValue = object.getString(objectKey);
				attrademo.setStr_attra(objectKey);
				attrademo.setStr_attra_value(objectValue);
				System.out.println(objectKey);
				System.out.println(objectValue);
				moveattrslist.add(attrademo);
			}
		}

	}


}
