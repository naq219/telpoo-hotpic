package com.telpoo.hotpic.detail;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PTViewPageAdapter extends FragmentStatePagerAdapter{

	//private ArrayList<BaseObject> mOjs;
	List<PhotoViewFragment> fragments;
	//ArrayList<String> readUrl;
	public PTViewPageAdapter(FragmentManager fm,List<PhotoViewFragment> fragments ) {
		super(fm);
		// TODO Auto-generated constructor stub
		//this.mOjs = mOjs;
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return fragments.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fragments.size();
	}
	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return POSITION_NONE;
	}

}
