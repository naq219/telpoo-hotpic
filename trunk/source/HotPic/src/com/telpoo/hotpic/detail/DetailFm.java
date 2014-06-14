package com.telpoo.hotpic.detail;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.telpoo.frame.delegate.Idelegate;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.hotpic.R;
import com.telpoo.hotpic.object.PicOj;

public class DetailFm extends DetailFmLayout implements Idelegate {
	PhoToViewAdapter phoToViewAdapter;
	private ArrayList<BaseObject> ojs;
	ViewPager viewPager;
	BaseObject ojPage=new BaseObject();

	public static DetailFm newInstance(int sectionNumber) {
		DetailFm fragment = new DetailFm();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	public DetailFm() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.detail, container, false);
		viewPager = (MyPhotoViewPager) rootView.findViewById(R.id.photoView);

		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();

		phoToViewAdapter = new PhoToViewAdapter(ojs);
		phoToViewAdapter.setDelegate(this);

		viewPager.setAdapter(phoToViewAdapter);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				
			}
			
			@Override
			public void onPageScrolled(int position, float arg1, int arg2) {
				
				ojPage = ojs.get(position);
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				
				switch (state) {
				case 0:
					name.setText(ojPage.get(PicOj.NAME));
					break;

				default:
					break;
				}
				
				
			}
		});
		
	}

	@Override
	public void callBack(Object value, int where) {
		
		if(popup.getVisibility()==View.VISIBLE){
			popup.setVisibility(View.GONE);
		}
		else popup.setVisibility(View.VISIBLE);
		/*
		 * viewPager.setCurrentItem(1); phoToViewAdapter.notifyDataSetChanged();
		 */

	}

	public void setData(ArrayList<BaseObject> ojs) {
		this.ojs = ojs;

	}

}
