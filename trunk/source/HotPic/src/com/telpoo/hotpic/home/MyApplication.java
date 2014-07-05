package com.telpoo.hotpic.home;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.telpoo.anhnong.hotgirl.R;
import com.telpoo.hotpic.utils.Utils;

public class MyApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		setupImageLoader();

	}

	protected void setupImageLoader() {
		ImageLoaderConfiguration configuration= new ImageLoaderConfiguration.Builder(getBaseContext()).defaultDisplayImageOptions(Utils.loadImgOption()).build();
		
		ImageLoader.getInstance().init(configuration);
	}
}
