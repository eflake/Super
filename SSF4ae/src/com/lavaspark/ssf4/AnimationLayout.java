/*
 * Copyright (C) 2012 0xlab - http://0xlab.org/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Authored by Julian Chu <walkingice AT 0xlab.org>
 */

package com.lavaspark.ssf4;

import com.lavaspark.ssf4.R;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class AnimationLayout extends ViewGroup {

	public final static int DURATION = 300;
	protected boolean mFirstOpen;
	protected boolean mOpened;
	protected boolean mListClicked;
	protected View mSidebar;
	protected View mContent;
	protected int mSidebarWidth = 500; /*
										 * assign default value. It will be
										 * overwrite in onMeasure by Layout xml
										 * resource.
										 */
	protected Animation mAnimation;
	protected OpenListener mOpenListener;
	protected CloseListener mCloseListener;
	protected Listener mListener;
	protected Float firstMoveX;
	protected Float moveX;
	protected Float lastMoveX;
	protected boolean mPressed = false;
	private Object mVelocityTracker;
	private boolean first = true;
	private int delta;
	private int alldelta;
	private boolean isPopOpening;

	public AnimationLayout(Context context) {
		this(context, null);
	}

	public AnimationLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void onFinishInflate() {
		super.onFinishInflate();
		mSidebar = findViewById(R.id.animation_layout_sidebar);
		mContent = findViewById(R.id.animation_layout_content);

		if (mSidebar == null) {
			throw new NullPointerException("no view id = animation_sidebar");
		}

		if (mContent == null) {
			throw new NullPointerException("no view id = animation_content");
		}

		mOpenListener = new OpenListener(mSidebar, mContent);
		mCloseListener = new CloseListener(mSidebar, mContent);
	}

	@Override
	public void onLayout(boolean changed, int l, int t, int r, int b) {
		/* the title bar assign top padding, drop it */
		if (MainActivity.IS_PAD) {
			mFirstOpen = false;
			mOpened = true;
		}
		mSidebar.layout(l, 0, l + mSidebarWidth,
				0 + mSidebar.getMeasuredHeight());
		if (mFirstOpen) {
			if (mOpened) {
				mContent.layout(l + mSidebarWidth, 0, r + mSidebarWidth, b);
			} else {
				mContent.layout(l, 0, r, b);
			}
		} else {
			if (mOpened) {
				mContent.layout(l + mSidebarWidth, 0, r + mSidebarWidth, b);
			} else {
				mContent.layout(l, 0, r, b);
			}
		}
	}

	@Override
	public void onMeasure(int w, int h) {
		super.onMeasure(w, h);
		super.measureChildren(w, h);
		mSidebarWidth = mSidebar.getMeasuredWidth();
	}

	@Override
	protected void measureChild(View child, int parentWSpec, int parentHSpec) {
		/* the max width of Sidebar is 90% of Parent */
		if (child == mSidebar) {
			int mode = MeasureSpec.getMode(parentWSpec);
			// int width = (int)(getMeasuredWidth() * 0.8);
			int width;
			if (MainActivity.IS_PAD) {
				width = (int) (getMeasuredWidth() * 0.3);
			} else {
				width = (int) (getMeasuredWidth() * 0.7);
			}
			super.measureChild(child, MeasureSpec.makeMeasureSpec(width, mode),
					parentHSpec);
		} else {
			super.measureChild(child, parentWSpec, parentHSpec);
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (!isOpening()) {
			return false;
		}
		if (isPopOpening) {
			return false;
		} 
		int action = ev.getAction();

		if (action != MotionEvent.ACTION_UP
				&& action != MotionEvent.ACTION_DOWN) {
			return false;
		}

		/*
		 * if user press and release both on Content while sidebar is opening,
		 * call listener. otherwise, pass the event to child.
		 */
		int x = (int) ev.getX();
		int y = (int) ev.getY();
		if (mContent.getLeft() < x && mContent.getRight() > x
				&& mContent.getTop() < y && mContent.getBottom() > y) {
			if (action == MotionEvent.ACTION_DOWN) {
				mPressed = true;
			}

			if (mPressed && action == MotionEvent.ACTION_UP
					&& mListener != null) {
				mPressed = false;
				return mListener.onContentTouchedWhenOpening();
			}
		} else {
			mPressed = false;
		}
		return false;
		// return true;
	}

	// @Override
	// public boolean onTouchEvent(MotionEvent event) {
	// switch (event.getAction()) {
	// case MotionEvent.ACTION_DOWN:
	// firstMoveX = event.getRawX();
	// Log.d("test", "firstX="+Float.valueOf(firstMoveX).toString());
	// alldelta=0;
	// break;
	// case MotionEvent.ACTION_MOVE:
	// moveX = event.getRawX();
	// Log.d("test", "moveX="+Float.valueOf(moveX).toString());
	//
	// delta = (int) ((moveX -firstMoveX)/10);
	// alldelta += delta;
	// mContent.scrollBy(-delta, 0);
	// Log.d("test","alldelta=" +Float.valueOf(alldelta).toString());
	//
	// break;
	// case MotionEvent.ACTION_UP:
	// lastMoveX = event.getRawX();
	// Log.d("test","UpX=" +Float.valueOf(lastMoveX).toString());
	// Log.d("test","last alldelta=" +Float.valueOf(alldelta).toString());
	//
	// if (alldelta>0) {
	// TranslateAnimation animation = new
	// TranslateAnimation(0,mSidebarWidth-alldelta, 0, 0);
	// animation.setDuration(DURATION);
	// animation.setFillAfter(true);
	// animation.setFillEnabled(true);
	// animation.setAnimationListener(new moveListener());
	// mContent.startAnimation(animation);
	//
	//
	//
	// } else {
	// TranslateAnimation animation = new
	// TranslateAnimation(0,-(mSidebarWidth+alldelta), 0, 0);
	// animation.setDuration(DURATION);
	// animation.setFillAfter(true);
	// animation.setFillEnabled(true);
	// animation.setAnimationListener(new moveListener());
	// mContent.startAnimation(animation);
	//
	// }
	//
	// break;
	//
	// default:
	// break;
	// }
	// return true;
	//
	// }

	public void setListener(Listener l) {
		mListener = l;
	}

	/* to see if the Sidebar is visible */
	public boolean isOpening() {
		return mOpened;
	}

	public void toggleSlidebaSecond() {
		if (mContent.getAnimation() != null) {
			return;
		}
		mAnimation = new TranslateAnimation(0, mSidebarWidth, 0, 0);
		mAnimation.setDuration(DURATION);
		mAnimation.setFillAfter(true);
		mAnimation.setFillEnabled(true);
		mContent.startAnimation(mAnimation);
	}

	public void toggleSidebar() {
		if (mContent.getAnimation() != null) {
			return;
		}
		if (mFirstOpen) {
			if (mOpened) {
				/* opened, make close animation */
				mAnimation = new TranslateAnimation(0, -mSidebarWidth, 0, 0);
				mAnimation.setAnimationListener(mCloseListener);
			} else {
				/* not opened, make open animation */
				mAnimation = new TranslateAnimation(0, mSidebarWidth, 0, 0);
				mAnimation.setAnimationListener(mOpenListener);
			}
		} else {
			if (mOpened) {
				/* opened, make close animation */
				mAnimation = new TranslateAnimation(0, -mSidebarWidth, 0, 0);
				mAnimation.setAnimationListener(mCloseListener);
			} else {
				/* not opened, make open animation */
				mAnimation = new TranslateAnimation(0, mSidebarWidth, 0, 0);
				mAnimation.setAnimationListener(mOpenListener);
			}
		}
		mAnimation.setDuration(DURATION);
		mAnimation.setFillAfter(true);
		mAnimation.setFillEnabled(true);
		mContent.startAnimation(mAnimation);
	}

	public void openSidebar() {
		if (!mOpened) {
			toggleSidebar();
		}
	}

	public void closeSidebar() {
		if (mOpened) {
			toggleSidebar();
		}
	}

	class OpenListener implements Animation.AnimationListener {
		View iSidebar;
		View iContent;

		OpenListener(View sidebar, View content) {
			iSidebar = sidebar;
			iContent = content;
		}

		public void onAnimationRepeat(Animation animation) {
		}

		public void onAnimationStart(Animation animation) {
			iSidebar.setVisibility(View.VISIBLE);
		}

		public void onAnimationEnd(Animation animation) {
			iContent.clearAnimation();
			mOpened = !mOpened;
			requestLayout();
			if (mListener != null) {
				mListener.onSidebarOpened();
			}
		}
	}

	class CloseListener implements Animation.AnimationListener {
		View iSidebar;
		View iContent;

		CloseListener(View sidebar, View content) {
			iSidebar = sidebar;
			iContent = content;
		}

		public void onAnimationRepeat(Animation animation) {

		}

		public void onAnimationStart(Animation animation) {

		}

		public void onAnimationEnd(Animation animation) {
			iContent.clearAnimation();
			// iSidebar.setVisibility(View.INVISIBLE);
			mOpened = !mOpened;
			requestLayout();
			if (mListener != null) {
				mListener.onSidebarClosed();
			}
		}
	}

	// class moveListener implements AnimationListener{
	//
	// @Override
	// public void onAnimationStart(Animation animation) {
	//
	// }
	//
	// @Override
	// public void onAnimationEnd(Animation animation) {
	// mContent.clearAnimation();
	// mOpened = !mOpened;
	// requestLayout();
	// }
	//
	// @Override
	// public void onAnimationRepeat(Animation animation) {
	//
	// }
	//
	// }
	public interface Listener {
		public void onSidebarOpened();

		public void onSidebarClosed();

		public boolean onContentTouchedWhenOpening();
	}

}
