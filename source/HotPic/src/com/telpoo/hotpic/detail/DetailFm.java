package com.telpoo.hotpic.detail;

import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.telpoo.frame.delegate.Idelegate;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.frame.utils.ViewUtils;
import com.telpoo.hotpic.R;
import com.telpoo.hotpic.object.PicOj;

public class DetailFm extends DetailFmLayout implements Idelegate {
	PhoToViewAdapter phoToViewAdapter;
	private ArrayList<BaseObject> ojs;
	ViewPager viewPager;
	BaseObject ojPage = new BaseObject();
	private Integer positionFirst;

	public static DetailFm newInstance(int sectionNumber) {
		DetailFm fragment = new DetailFm();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	public DetailFm() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.detail, container, false);
		viewPager = (MyPhotoViewPager) rootView.findViewById(R.id.photoView);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		ImageView[] imageViews = { like, favorite, comment, download, setting,
				share };
		ViewUtils.HighlightImageView(imageViews, Color.parseColor("#b3100b"));

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

					setTextName(ojPage.get(PicOj.NAME));
					break;

				default:
					break;
				}

			}
		});

		if (positionFirst == 0) {
			setTextName(ojs.get(0).get(PicOj.NAME));

		}

		viewPager.setCurrentItem(positionFirst, true);

	}

	protected void setTextName(String string) {
		name.setText(string);
		if (string == null || string.length() == 0) {
			name.setVisibility(View.GONE);
		}
		else name.setVisibility(View.VISIBLE);

	}

	@Override
	public void callBack(Object value, int where) {

		if (popup.getVisibility() == View.VISIBLE) {
			popup.setVisibility(View.GONE);
		} else
			popup.setVisibility(View.VISIBLE);

	}

	public void setData(ArrayList<BaseObject> ojs, Integer positionFirst) {
		this.ojs = ojs;
		this.positionFirst = positionFirst;

	}

}
