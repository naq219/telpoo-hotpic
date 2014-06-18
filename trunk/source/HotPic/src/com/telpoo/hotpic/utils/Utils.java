package com.telpoo.hotpic.utils;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.telpoo.hotpic.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public class Utils {
	
	public static DisplayImageOptions loadImgOption(){
		DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
		.cacheOnDisc(true)
		.cacheInMemory(false)
		.showImageOnFail(R.drawable.no_image)
		.showImageOnLoading(R.drawable.no_image)
		.build();
		

		
		return displayImageOptions;
	}

}
