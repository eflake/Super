package com.lavaspark.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lavaspark.adapter.Move_attr;
import com.lavaspark.ssf4.R;
import com.lavaspark.util.GlobalVariables;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

public class DBManager {
	private final int BUFFER_SIZE = 400000;
	public static final String DB_NAME = "framedatabase.db"; // 保存的数据库文件名
	public static final String PACKAGE_NAME = "com.lavaspark.ssf4";
	public static final String DB_PATH = "/data"
			+ Environment.getDataDirectory().getAbsolutePath() + "/"
			+ PACKAGE_NAME; // 在手机里存放数据库的位置
	public static SQLiteDatabase database;
	private Context context;
	private static  DBManager mDbManager = null;
	private DBManager(Context context) {
		this.context = context;
		//ImportDatabase(DB_PATH + "/" + DB_NAME);
		//Log.d("DB_PATH + DB_NAME", DB_PATH + "/" + DB_NAME);
	}
	public static DBManager getdbManger(Context context){
		if( null == mDbManager){
			mDbManager = new DBManager(context);
			return mDbManager;
		}else
			return mDbManager;
	}

	public void openDatabase() {

	}

	

	//**************************************************   lz 0327
	public Object querydata(String name ,String methed) throws Exception {
		if(database == null ){
			Log.i("lei", "database is null");
			return null;
		}
		Cursor cursor_1 = database.rawQuery("select * from framedata where name = ?"
				,new String[]{name});
		while(cursor_1.moveToNext()){
			String cursorString = cursor_1.getString(cursor_1.getColumnIndex("frameinfo"));
			EncryptionDecryption encryptionDecryption = new EncryptionDecryption("lavaspark");
			String decryptString= encryptionDecryption.decrypt(cursorString);
			
			
			if("jsonPhaserName".equals(methed)){
				return jsonPhaserName(decryptString);
			}else if("jsonPhaserframeKeyAndallFrame".equals(methed)){
				jsonPhaserframeKeyAndallFrame(decryptString);
			}
		}
		cursor_1.close();
		return null;
	}

	//**************************************************   lz 0327
	public Object jsonPhaserName(String jsonString){
		ArrayList<String> nameList ;
		JSONArray array;
		ArrayList<Move_attr> arraymoveList = new ArrayList<Move_attr>();
		try {
			array = new JSONArray(jsonString);
			nameList = new ArrayList<String>();
			for (int i = 0; i < array.length(); i++) {
				JSONObject object = array.getJSONObject(i);
				for (Iterator<String> iterator = object.keys(); iterator.hasNext();) {
					Move_attr move_attr = new Move_attr();
					String key = iterator.next();
					nameList.add(key);
				}
			}
			return nameList;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{

		}
		return null;
	}

	//**************************************************   lz 0327
	public void jsonPhaserframeKeyAndallFrame(String jsonString){
		List<String> nameList = new ArrayList<String>();
		List<String> frameKeyList = new ArrayList<String>();
		List<HashMap<String, String>> allFrameList = new ArrayList<HashMap<String,String>>();
		HashMap<String,String> frameMap;
		JSONArray array = null;
		ArrayList<Move_attr> arraymoveList = new ArrayList<Move_attr>();
		try {
			array = new JSONArray(jsonString);
			nameList = new ArrayList<String>();
			allFrameList = new ArrayList<HashMap<String,String>>();

			for (int i = 0; i < array.length(); i++) {
				JSONObject object = array.getJSONObject(i);
				for (Iterator<String> iterator = object.keys(); iterator.hasNext();) {
					Move_attr move_attr = new Move_attr();
					String key = iterator.next();
					nameList.add(key);
					JSONArray frameaArray = object.getJSONArray(key);
					frameKeyList = new ArrayList<String>();
					frameMap = new HashMap<String, String>();
					for (int j = 0; j < frameaArray.length(); j++) {
						JSONObject lastdata = frameaArray.getJSONObject(j);
						for (Iterator<String> iteratorNext = lastdata.keys(); iteratorNext.hasNext();) {
							String frameKey = iteratorNext.next();
							frameKeyList.add(frameKey);
							frameMap.put(frameKey, lastdata.getString(frameKey));
							if("Moves".equals(frameKey)){
								move_attr.setmMove_Name(lastdata.getString(frameKey));
							}else if("Startup".equals(frameKey)){
								move_attr.setmStartup(lastdata.getString(frameKey));
							}else if("FrameAdvHit".equals(frameKey)){
								move_attr.setmOnHit(lastdata.getString(frameKey));
							}else if("FrameAdvBlock".equals(frameKey)){
								move_attr.setmOnGruard(lastdata.getString(frameKey));
							}
						}	
					}
					arraymoveList.add(move_attr);
					allFrameList.add(frameMap);
					
				}				
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
//		Log.d("lavaspark", nameList.toString());
//		Log.d("lavaspark", frameKeyList.toString());
//		Log.d("lavaspark", allFrameList.toString());
		GlobalVariables globalVariable = ((GlobalVariables)context.getApplicationContext());
		globalVariable.setArraymoveList(arraymoveList);
		globalVariable.setAllFrameList(allFrameList);
		globalVariable.setJsonarray(array);
	}
	
	/*****
	 * 
	 * @param path     
	 * @author lei 
	 * @serialData  130328
	 * @	public static final String DB_NAME = "framedatabase.db"; //保存的数据库文件名
    	public static final String PACKAGE_NAME = "com.lz.dbcopy";
    	public static final String DB_PATH = "/data"
            + Environment.getDataDirectory().getAbsolutePath() + "/"
            + PACKAGE_NAME;  //在手机里存放数据库的位置
        String databasePath = DB_PATH + "/" + DB_NAME;    ---->   /data/data/com.lz.dbcopy/framedatabase.db
		String paht = "/data/data/"+PACKAGE_NAME+"/"+DB_NAME;   ------>   /data/data/com.lz.dbcopy/framedatabase.db   this is good;
	 */
	public void importdatabase(String path , InputStream inputStream){
		
		if(inputStream == null ||  path == null){
			Log.e("lei", "path or inputstream is not null!");
			return;
		}
		//Open your local db as the input stream
		try {
			OutputStream myOutput = new FileOutputStream(path);
	    	byte[] buffer = new byte[1024];
	    	int length;
	    	while ((length = inputStream.read(buffer))>0){
	    		myOutput.write(buffer, 0, length);
	    	}
	    	myOutput.flush();
	    	myOutput.close();
	    	inputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}