package com.lavaspark.ssf4;

import com.lavaspark.adapter.BarDropListAdapter;
import com.lavaspark.adapter.PopWindowListAdapter;
import com.lavaspark.adapter.SelectCharacterListAdapter;
import com.lavaspark.adapter.SectionsPagerAdapter;
import com.lavaspark.db.DBManager;
import com.lavaspark.ssf4.CharacterFragment.CallbackDelegate;
import android.R.color;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SpinnerAdapter;

public class MainActivity extends FragmentActivity implements
		AnimationLayout.Listener, OnItemClickListener, CallbackDelegate {
	protected static AnimationLayout multiLayout;
	private final String TAG = "eflake";
	public static boolean IS_PAD = false;
	public static boolean IS_POP_OPENING = false;
	public static int character_index = 0;
	private PopupWindow barPop;
	private View layout;
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	public String[] characters;
	public final int WALLPAPER_ID = 0;
	public final int VIDEO_ID = 1;
	public final int FORUM_ID = 2;
	public final int FRAMEDATA_ID = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// BarDropListAdapter barDropListAdapter =new BarDropListAdapter(new
		// String[]{"video","video","video"},MainActivity.this);
		characters = getResources().getStringArray(R.array.character_name);
		layout = View
				.inflate(MainActivity.this, R.layout.bar_drop_layout, null);
		ListView listView = (ListView) layout.findViewById(R.id.listView1);
		
		layout.setFocusableInTouchMode(true);
		layout.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (barPop.isShowing()) {
					barPop.dismiss();
					barPop.setFocusable(false);
					barPop.setOutsideTouchable(false);
				}
				return false;
			}
		});
		layout.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (barPop.isShowing()) {
					barPop.dismiss();
					barPop.setFocusable(false);
					barPop.setOutsideTouchable(false);
				}
				return false;
			}
		});
		BarDropListAdapter barAdapter = new BarDropListAdapter(new String[] {
				"Wallpaper", "Video", "Forum", "Framedata" }, MainActivity.this);
		listView.setAdapter(barAdapter);
		listView.setOnItemClickListener(this);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				initPopWindow();
				switch (position) {
				case WALLPAPER_ID:
					toWallpaperActivity();
					break;
				case VIDEO_ID:
					toVideoActivity();
					break;
				case FORUM_ID:
					toForumActivity();
					break;
				case FRAMEDATA_ID:
					toFrameDataActivity();
					break;
				default:
					break;
				}
			}
		});
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.abbg));

		// bar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		// bar.setListNavigationCallbacks(barDropListAdapter, null);
		// bar.setSplitBackgroundDrawable(getResources().getDrawable(R.drawable.bar_bg));
		getScreenSize(this);
		multiLayout = (AnimationLayout) findViewById(R.id.animation_layout);
		multiLayout.setListener(this);
		multiLayout.mFirstOpen = true;
		ListView list = (ListView) findViewById(R.id.listView1);
		ImageView imageView = new ImageView(getApplicationContext());
		imageView.setBackgroundDrawable(getResources().getDrawable(R.drawable.slide_menu_head));
		list.addHeaderView(imageView);
		
		SelectCharacterListAdapter adapter = new SelectCharacterListAdapter(
				this);
		list.setAdapter(adapter);
		list.setOnItemClickListener(this);

		// FragmentOne one = new FragmentOne();
		// FragmentTransaction ft = getFragmentManager().beginTransaction();
		// ft.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
		// ft.add(R.id.animation_layout_content, one);
		// ft.commit();

//		LoadImageViewAsyncTask task = new LoadImageViewAsyncTask(manager,mViewPager);
//		task.execute("InitPagerAdapter");
		
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager
		.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				MainActivity.character_index = mViewPager
						.getCurrentItem();
				Log.d("111","position ="+ position );

			}
		});
		// mViewPager.setBackgroundColor(color.holo_red_dark);
		// mViewPager.setHorizontalFadingEdgeEnabled(true);
		// When swiping between different sections, select the corresponding
		// tab.
		// We can also use ActionBar.Tab#select() to do this if we have a
		// reference to the
		// Tab.
	
		// if (IS_PAD) {
		// multiLayout.toggleSidebar();
		// }
//		DBManager dbManager = new DBManager(getApplicationContext());
//		dbManager.openDatabase();
//		dbManager.database.close();

	}

	/* 初始化一个弹窗 */
	private void initPopWindow() {
		if (barPop == null) {
			barPop = new PopupWindow(layout, LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
		}
		if (barPop.isShowing()) {
			barPop.dismiss();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_add:
			initPopWindow();
			barPop.setFocusable(true);
			// barPop.setAnimationStyle(R.style.AnimationFade);
			// barPop.showAtLocation(findViewById(R.id.animation_layout_content),
			// Gravity.TOP | Gravity.RIGHT, 0, 48);
			barPop.showAsDropDown(findViewById(R.id.menu_add));
			barPop.setOutsideTouchable(true);
			MainActivity.IS_POP_OPENING = true;
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onSidebarOpened() {

	}

	@Override
	public void onSidebarClosed() {

	}

	@Override
	public boolean onContentTouchedWhenOpening() {
		if (IS_PAD) {
		} else {
			multiLayout.toggleSidebar();
		}
		return false;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// Toast.makeText(this, "back pressed", Toast.LENGTH_SHORT).show();
			if (!multiLayout.mOpened) {
				if (IS_PAD) {
				} else {
					multiLayout.toggleSidebar();
				}
			} else {
				new AlertDialog.Builder(this)
						.setIcon(R.drawable.ic_launcher)
						.setTitle("锟斤拷锟斤拷")
						.setMessage("确锟斤拷要锟诫开锟斤拷")
						.setPositiveButton("锟角碉拷",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										android.os.Process
												.killProcess(android.os.Process
														.myPid());
									}
								})
						.setNegativeButton("取锟斤拷",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.cancel();
									}
								}).show();
			}
		}

		return false;
	}

	public boolean getScreenSize(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		Log.d(TAG, "getScreenSize() 锟� " + dm.widthPixels);
		Log.d(TAG, "getScreenSize() 锟斤拷 锟斤拷" + dm.heightPixels);
		Log.d(TAG, "getScreenSize() 锟杰讹拷 锟斤拷" + dm.density);
		Log.d(TAG, "getScreenSize() 一英锟斤拷锟斤拷锟斤拷锟斤拷兀锟�" + dm.density * 160);
		double diagonalPixels = Math.sqrt((Math.pow(dm.widthPixels, 2) + Math
				.pow(dm.heightPixels, 2)));
		double screenSize = diagonalPixels / (160 * dm.density);
		Log.d(TAG, "getScreenSize() 锟斤拷锟斤拷叽纾�" + screenSize);

		if (screenSize > 6) {
			Log.d(TAG, "getScreenSize() screenSize > 6  平锟斤拷 ");
			MainActivity.IS_PAD = true;
			return true;
		} else {
			Log.d(TAG, "getScreenSize() screenSize < 6 锟街伙拷 ");
			MainActivity.IS_PAD = false;
			return false;
		}
	}

	@Override
	public void chooseCharacter() {
		multiLayout.toggleSidebar();

	}

	public void toWallpaperActivity() {
		Intent intent = new Intent(this, WallpaperActivity.class);
		startActivity(intent);
	}

	public void toVideoActivity() {
		Intent intent = new Intent(this, VideoActivity.class);
		startActivity(intent);
	}

	public void toOtherActivity(View v) {
		Intent intent = new Intent(this, OtherActivity.class);
		startActivity(intent);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		mViewPager.setCurrentItem(position-1, false);
		MainActivity.character_index = position-1;

		if (!IS_PAD) {
			multiLayout.toggleSidebar();
		}

	}

	public void toFrameDataActivity() {
		Intent intent = new Intent(this, FrameDataActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("character", characters[MainActivity.character_index]);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	@Override
	public void toZhaoShiActivity(Bundle s) {
		// Intent intent1 = new Intent(this, ZhaoShiActivity.class);
		// intent1.putExtras(s);
		// startActivity(intent1);
		// overridePendingTransition(R.anim.fade, R.anim.hold);
	}

	@Override
	public void toPanDingActivity(Bundle s) {
		Intent intent2 = new Intent(this, PanDingActivity.class);
		intent2.putExtras(s);
		startActivity(intent2);
		overridePendingTransition(R.anim.fade, R.anim.hold);

	}

	public void toForumActivity() {
		Intent intent2 = new Intent(this, ForumActivity.class);
		Bundle bundle3 = new Bundle();
		bundle3.putString("character", characters[MainActivity.character_index]);
		intent2.putExtras(bundle3);
		startActivity(intent2);
	}

}
