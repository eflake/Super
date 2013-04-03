package com.lavaspark.ssf4;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.style.DrawableMarginSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

public class Image_Switch extends Activity implements OnClickListener{
	int[] WrapperResource = new int[]{R.drawable.all,R.drawable.wallpaper_able,R.drawable.wallpaper_adan,R.drawable.wallpaper_akuma,R.drawable.wallpaper_balrog,R.drawable.wallpaper_blanka
			,R.drawable.wallpaper_cammy,R.drawable.wallpaper_chunli,R.drawable.wallpaper_cody,R.drawable.wallpaper_dan,R.drawable.wallpaper_deejay,R.drawable.wallpaper_dhalsim,R.drawable.wallpaper_dudly
			,R.drawable.wallpaper_elfuerte,R.drawable.wallpaper_feilong,R.drawable.wallpaper_gen,R.drawable.wallpaper_gouken,R.drawable.wallpaper_guile,R.drawable.wallpaper_guy,R.drawable.wallpaper_hakan,R.drawable.wallpaper_honda
			,R.drawable.wallpaper_ibuki,R.drawable.wallpaper_juri,R.drawable.wallpaper_ken,R.drawable.wallpaper_m,R.drawable.wallpaper_makoto,R.drawable.wallpaper_rose,R.drawable.wallpaper_rufus,R.drawable.wallpaper_ryu,R.drawable.wallpaper_sagat,R.drawable.wallpaper_sakura
			,R.drawable.wallpaper_seth,R.drawable.wallpaper_t_hwak,R.drawable.wallpaper_vega,R.drawable.wallpaper_viper,R.drawable.wallpaper_zangief};
	//	int[] WrapperResource = new int[]{R.drawable.ic_launcher,R.drawable.all,R.drawable.wallpaper_able,R.drawable.wallpaper_adan,R.drawable.wallpaper_akuma,R.drawable.wallpaper_balrog,R.drawable.wallpaper_blanka,R.drawable.wallpaper_cammy
	//	};
	private static final int  IMAGE_CUT=2;
	private int from_deliveruri;
	private Uri imageUri;
	private int mheight;
	private int mwidth;
	private File tempFile;
	private File saveFile;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_switch);
		from_deliveruri = getIntent().getIntExtra("image_uri", 0);

		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		mwidth = metric.widthPixels;  
		mheight = metric.heightPixels;  

		String sdpath = Environment.getExternalStorageDirectory().toString();
		tempFile = new File(sdpath+"/"+"temppaper.png");
		saveFile = new File(sdpath+"/"+"Savewallpaper.png");

		Button button  = (Button) findViewById(R.id.button1);
		button.setOnClickListener(this);
		ImageView myImageView = (ImageView) this.findViewById(R.id.myimageview);
		//		Bitmap imageview = BitmapFactory.decodeFile(saveFile.toString());
		//		BitmapDrawable bd= new BitmapDrawable(getResources(), imageview);  
		myImageView.setBackgroundResource(WrapperResource[from_deliveruri]);

	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		//		Intent intent = new Intent("com.android.camera.action.CROP");
		//		intent.setDataAndType(Uri.fromFile(saveFile), "image/*");
		//		intent.putExtra("crop", "true");
		//		intent.putExtra("output", Uri.fromFile(tempFile));
		//		intent.putExtra("outputX", mheight);
		//		intent.putExtra("outputY", mwidth);
		//		intent.putExtra("return-data", false);
		Intent minten  = getImageClipIntent(Uri.fromFile(saveFile));
		startActivityForResult(minten, 1);

	}

	public static Bitmap sBitmap(Bitmap b, int w, int h) {
		int width = b.getWidth();
		int height = b.getHeight();
		float scaleWidth = ((float) w) / width;
		float scaleHeight = ((float) h) / height;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);//缩放
		return Bitmap.createBitmap(b, 0, 0, width, height, matrix, true);
	}

	@Override   
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) { 
		// TODO Auto-generated method stub 
		if(resultCode==RESULT_OK){ 
			Bitmap mbitmap = BitmapFactory.decodeFile(tempFile.toString());
			
			//			try {
			//				WallpaperManager instance = WallpaperManager.getInstance(Image_Switch.this);
			//				int desiredMinimumWidth = getWindowManager().getDefaultDisplay().getWidth()+300; 
			//				int desiredMinimumHeight = getWindowManager().getDefaultDisplay().getHeight();
			//				instance.suggestDesiredDimensions(desiredMinimumWidth, desiredMinimumHeight);
			//				instance.setBitmap(mbitmap);
			//				//				instance.setBitmap(sBitmap(mbitmap, desiredMinimumWidth, desiredMinimumHeight ));
			//			} catch (IOException e) {
			//				e.printStackTrace();
			//			}

			WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);  
			try {
				wallpaperManager.setBitmap(mbitmap);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  

			Toast.makeText(this, "设置成功", Toast.LENGTH_SHORT).show(); 
			this.finish();

		} 

	} 

	public  Intent getImageClipIntent(Uri uri) { 

		Intent intent = new Intent("com.android.camera.action.CROP");
		//		File tempFile = new File("/mnt/sdcard/camera.jpg");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("outputX", mheight);
		intent.putExtra("outputY", mwidth);
		intent.putExtra("scale", true); 
		intent.putExtra("output", Uri.fromFile(tempFile));
		intent.putExtra("return-data", false);
		return intent; 

	} 



}
