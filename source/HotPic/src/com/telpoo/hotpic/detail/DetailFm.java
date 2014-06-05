package com.telpoo.hotpic.detail;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.telpoo.frame.delegate.Idelegate;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.hotpic.R;

public class DetailFm extends DetailFmLayout implements Idelegate {
	PhoToViewAdapter phoToViewAdapter;
	private ArrayList<BaseObject> ojs;
	ViewPager viewPager;

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
	}

	@Override
	public void callBack(Object value, int where) {
		Toast.makeText(getActivity(), "call back", 1).show();
		/*
		 * viewPager.setCurrentItem(1); phoToViewAdapter.notifyDataSetChanged();
		 */

	}

	public void setData(ArrayList<BaseObject> ojs) {
		this.ojs = ojs;

	}

}
