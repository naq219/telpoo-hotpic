package com.telpoo.hotpic.detail;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyPhotoViewPager extends ViewPager {

	public MyPhotoViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MyPhotoViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		try {
			return super.onInterceptTouchEvent(arg0);
		} catch (IllegalArgumentException e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		
	}
}
