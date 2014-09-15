package com.telpoo.hotpic.detail;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wallpaper.beautifulpicture.R;
import com.telpoo.hotpic.home.MyFragment;

public class DetailFmLayout extends MyFragment {
	ImageView like, favorite1, comment, download, setting, share;
	ViewPager viewPager ;
	View popup;
	TextView name;
	
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.detail, container, false);
	}

	@Override
	public void onViewCreated(View v, Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);
		like = (ImageView) v.findViewById(R.id.like);
		favorite1 = (ImageView) v.findViewById(R.id.favorite1);
		comment = (ImageView) v.findViewById(R.id.comment);
		download = (ImageView) v.findViewById(R.id.download);
		setting = (ImageView) v.findViewById(R.id.setting);
		share = (ImageView) v.findViewById(R.id.share);
		popup= (View) v.findViewById(R.id.popup);
		name= (TextView) v.findViewById(R.id.name);
		
	}

}
