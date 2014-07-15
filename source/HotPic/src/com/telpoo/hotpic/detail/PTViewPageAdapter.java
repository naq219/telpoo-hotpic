package com.telpoo.hotpic.detail;

import java.util.ArrayList;
import java.util.List;

import com.telpoo.frame.delegate.Idelegate;
import com.telpoo.frame.object.BaseObject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PTViewPageAdapter extends FragmentStatePagerAdapter{

	private ArrayList<BaseObject> ojs;
	private Idelegate idelegate;
	public PTViewPageAdapter(FragmentManager fm, ArrayList<BaseObject> ojs,Idelegate idelegate) {
		super(fm);
		this.ojs = ojs;
		this.idelegate = idelegate;
		
	}
	//private ArrayList<BaseObject> mOjs;
	//ArrayList<String> readUrl;
//	public PTViewPageAdapter(FragmentManager fm,List<PhotoViewFragment> fragments ) {
//		super(fm);
//		// TODO Auto-generated constructor stub
//		//this.mOjs = mOjs;
//		this.fragments = fragments;
//	}
	
	

	@Override
	public Fragment getItem(int arg0) {
		return PhotoViewFragment.newInstance(ojs.get(arg0), idelegate);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		//return fragments.size();
		return ojs.size();
	}
	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return POSITION_NONE;
	}

}
