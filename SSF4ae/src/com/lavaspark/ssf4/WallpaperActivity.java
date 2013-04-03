package com.lavaspark.ssf4;

/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.lavaspark.util.Utils;



//Need the following import to get access to the app resources, since this
//class is in a sub-package.


public class WallpaperActivity extends Activity implements OnItemClickListener {

	GridView mGrid;
	public static Context context;
	
		int[] WrapperResource = new int[]{R.drawable.all,R.drawable.wallpaper_able,R.drawable.wallpaper_adan,R.drawable.wallpaper_akuma,R.drawable.wallpaper_balrog,R.drawable.wallpaper_blanka
				,R.drawable.wallpaper_cammy,R.drawable.wallpaper_chunli,R.drawable.wallpaper_cody,R.drawable.wallpaper_dan,R.drawable.wallpaper_deejay,R.drawable.wallpaper_dhalsim,R.drawable.wallpaper_dudly
				,R.drawable.wallpaper_elfuerte,R.drawable.wallpaper_feilong,R.drawable.wallpaper_gen,R.drawable.wallpaper_gouken,R.drawable.wallpaper_guile,R.drawable.wallpaper_guy,R.drawable.wallpaper_hakan,R.drawable.wallpaper_honda
				,R.drawable.wallpaper_ibuki,R.drawable.wallpaper_juri,R.drawable.wallpaper_ken,R.drawable.wallpaper_m,R.drawable.wallpaper_makoto,R.drawable.wallpaper_rose,R.drawable.wallpaper_rufus,R.drawable.wallpaper_ryu,R.drawable.wallpaper_sagat,R.drawable.wallpaper_sakura
				,R.drawable.wallpaper_seth,R.drawable.wallpaper_t_hwak,R.drawable.wallpaper_vega,R.drawable.wallpaper_viper,R.drawable.wallpaper_zangief
		};
//	int[] WrapperResource = new int[]{R.drawable.ic_launcher,R.drawable.all,R.drawable.wallpaper_able,R.drawable.wallpaper_adan,R.drawable.wallpaper_akuma,R.drawable.wallpaper_balrog,R.drawable.wallpaper_blanka,R.drawable.wallpaper_cammy
//	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		loadApps(); 
		context = this;

		setContentView(R.layout.activity_wrapper_gridview);
		mGrid = (GridView) findViewById(R.id.myGrid);
		mGrid.setAdapter(new AppsAdapter());
		mGrid.setOnItemClickListener(this);
	}

	private List<Integer> mApps = new ArrayList<Integer>();

	private Bitmap bitmap;

	private void loadApps() {
		for(int i=0;i<WrapperResource.length;i++){
			mApps.add(WrapperResource[i]);
		}
	}

	public class AppsAdapter extends BaseAdapter {
		public AppsAdapter() {
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView i;

			if (convertView == null) {
				i = new ImageView(WallpaperActivity.this);
				i.setScaleType(ImageView.ScaleType.FIT_CENTER);
				i.setLayoutParams(new GridView.LayoutParams(150, 150));
			} else {
				i = (ImageView) convertView;
			}

			int info = mApps.get(position);
			i.setImageResource(info);
			return i;
		}


		public final int getCount() {
			return mApps.size();
		}

		public final Object getItem(int position) {
			return mApps.get(position);
		}

		public final long getItemId(int position) {
			return position;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub

//		Intent intent = new Intent(this, SecondshotActvity.class);
		Intent intent = new Intent(this, Image_Switch.class);
		intent.putExtra("image_uri",position);
		int a  = WrapperResource[position];
		Resources res = getResources();  
		bitmap = BitmapFactory.decodeResource(res,WrapperResource[position]);  
		try {
			saveMyBitmap();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

				startActivity(intent);
	}
	public void saveMyBitmap() throws IOException {
		String sdpath = Environment.getExternalStorageDirectory().toString();
        File f = new File(sdpath+"/"+"Savewallpaper.png");
//		File f = new File("/sdcard/Note/" + bitName + ".png");
		f.createNewFile();
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

