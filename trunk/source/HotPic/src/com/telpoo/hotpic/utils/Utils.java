package com.telpoo.hotpic.utils;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.telpoo.anhnong.hotgirl.R;

public class Utils {
	
	public static DisplayImageOptions loadImgOption(){
		DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
		.cacheOnDisc(true)
		.cacheInMemory(false)
		.showImageOnFail(R.drawable.no_image)
		.showImageOnLoading(R.drawable.no_image)
		.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
		.build();
		return displayImageOptions;
	}

}
