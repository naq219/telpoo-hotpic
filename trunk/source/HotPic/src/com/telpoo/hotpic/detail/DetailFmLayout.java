package com.telpoo.hotpic.detail;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.telpoo.hotpic.R;
import com.telpoo.hotpic.home.MyFragment;

public class DetailFmLayout extends MyFragment {
	ImageView like, favorite, comment, download, setting, share;
	ViewPager viewPager ;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.detail, container, false);
	}

	@Override
	public void onViewCreated(View v, Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);
		like = (ImageView) v.findViewById(R.id.like);
		favorite = (ImageView) v.findViewById(R.id.favorite);
		comment = (ImageView) v.findViewById(R.id.comment);
		download = (ImageView) v.findViewById(R.id.download);
		setting = (ImageView) v.findViewById(R.id.setting);
		share = (ImageView) v.findViewById(R.id.share);
	}

}
