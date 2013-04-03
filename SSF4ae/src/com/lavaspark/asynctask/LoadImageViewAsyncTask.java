package com.lavaspark.asynctask;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class LoadImageViewAsyncTask extends AsyncTask<String, Integer, Bitmap> {
	public Integer[] _resid;
	public int _resourceid;
	public ImageView _image;
	public Context _context;
	public LoadImageViewAsyncTask (Context context,int resourceid,ImageView image){
		_context = context;
		_resourceid = resourceid;
		_image = image;
	}
	
	@Override
	protected Bitmap doInBackground(String... params) {
		 BitmapFactory.Options opt = new BitmapFactory.Options();  
	      opt.inPreferredConfig = Bitmap.Config.RGB_565;   
	      opt.inPurgeable = true;  
	      opt.inInputShareable = true;  
	//获取资源图片  
	      InputStream is = _context.getResources().openRawResource(_resourceid);  
	      BitmapFactory.decodeStream(is,null,opt); 
	      
		return null;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
		_image.setImageBitmap(result);
	}

	
}
