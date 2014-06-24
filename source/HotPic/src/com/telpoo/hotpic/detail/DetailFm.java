package com.telpoo.hotpic.detail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.telpoo.frame.delegate.Idelegate;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.frame.utils.FileSupport;
import com.telpoo.frame.utils.ViewUtils;
import com.telpoo.hotpic.R;
import com.telpoo.hotpic.home.HomeActivity;
import com.telpoo.hotpic.object.PicOj;

public class DetailFm extends DetailFmLayout implements Idelegate, OnClickListener {
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.detail, container, false);
		viewPager = (MyPhotoViewPager) rootView.findViewById(R.id.photoView);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		initView();

	}

	private void initView() {

		ImageView[] imageViews = { like, favorite, comment, download, setting, share };
		ViewUtils.HighlightImageView(imageViews, Color.parseColor("#b3100b"));

		setting.setOnClickListener(this);
		favorite.setOnClickListener(this);
		share.setOnClickListener(this);
		download.setOnClickListener(this);
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
		} else
			name.setVisibility(View.VISIBLE);

	}

	@Override
	public void callBack(Object value, int where) {

		if (popup.getVisibility() == View.VISIBLE) {
			popup.setVisibility(View.GONE);
			HomeActivity.getInstance().hidetop();
		} else {
			HomeActivity.getInstance().showtop();
			popup.setVisibility(View.VISIBLE);
		}

	}

	public void setData(ArrayList<BaseObject> ojs, Integer positionFirst) {
		this.ojs = ojs;
		this.positionFirst = positionFirst;

	}

	@Override
	public void onStop() {
		super.onStop();
		HomeActivity.getInstance().showtop();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.setting:

			ImageLoader.getInstance().loadImage(ojPage.get(PicOj.URL), new SimpleImageLoadingListener() {

				@Override
				public void onLoadingStarted(String imageUri, View view) {
					super.onLoadingStarted(imageUri, view);
					showToast(getContext().getString(R.string.dang_tai_anh));
				}

				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					super.onLoadingComplete(imageUri, view, loadedImage);
					try {
						WallpaperManager.getInstance(getActivity()).setBitmap(loadedImage);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				@Override
				public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
					super.onLoadingFailed(imageUri, view, failReason);
					showToast("Lỗi kết nối");
				}
			});

			break;

		case R.id.download:

			ImageLoader.getInstance().loadImage(ojPage.get(PicOj.URL), new SimpleImageLoadingListener() {
				@Override
				public void onLoadingStarted(String imageUri, View view) {
					super.onLoadingStarted(imageUri, view);
					showToast(getContext().getString(R.string.dang_tai_anh));
				}

				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					super.onLoadingComplete(imageUri, view, loadedImage);
					File pictureFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
					// File imagesFolder = new File(pictureFolder, "/hotpic");
					FileSupport.copyfile(imageUri, pictureFolder.getAbsolutePath() + "/hot");
					showToast("Ảnh đã được lưu vào: " + pictureFolder.getAbsolutePath() + "/abc.png");
				}

				@Override
				public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
					super.onLoadingFailed(imageUri, view, failReason);
					showToast("Lỗi kết nối");
				}
			});

			break;

		default:
			break;
		}

	}

}
