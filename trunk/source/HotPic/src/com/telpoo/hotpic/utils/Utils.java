package com.telpoo.hotpic.utils;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.hinhnen.anhnong.hotgirl.R;

public class Utils {
	
	public static DisplayImageOptions loadImgOption(){
		DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
		.showImageOnFail(R.drawable.ic_launcher)
		.showImageOnLoading(R.drawable.load)
		.cacheOnDisc()
		.cacheOnDisc(true)
		.cacheOnDisk(true)
		.build();
		return displayImageOptions;
	}
	
	

}
