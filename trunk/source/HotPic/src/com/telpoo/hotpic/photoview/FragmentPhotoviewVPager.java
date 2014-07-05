package com.telpoo.hotpic.photoview;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telpoo.frame.ui.BaseFragment;
import com.telpoo.anhnong.hotgirl.R;

public class FragmentPhotoviewVPager extends BaseFragment{	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.photo_view_fm, container, false);
		
		return rootView;
	}
}
