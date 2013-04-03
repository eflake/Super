package com.lavaspark.util;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class Utils {
	// 以最省内存的方式读取本地资源的图片,返回bitmap
	public static Bitmap readBitMap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	// 以最省内存的方式读取本地资源的图片,返回bitmap
	public static Drawable readDrawable(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		InputStream is = context.getResources().openRawResource(resId);
		BitmapDrawable bd = new BitmapDrawable(BitmapFactory.decodeStream(is,
				null, opt));
		Drawable drawable = (Drawable) bd;
		return drawable;
	}

	private void publ() {
		// TODO Auto-generated method stub
			Log.i("lei", "eflake");
	}
}
