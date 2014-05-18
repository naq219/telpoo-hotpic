package com.telpoo.hotpic.adapter;

import java.util.List;

import uk.co.senab.photoview.PhotoView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.telpoo.hotpic.object.PicOj;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;

public class PhoToViewPagerAdapter extends PagerAdapter{

	List<PicOj>  arrayPicOjs;
	DisplayImageOptions displayImageOptions;
	List<Bitmap> bitmaps;
	public PhoToViewPagerAdapter( List<PicOj>  arrayPicOjs,List<Bitmap> bitmaps , DisplayImageOptions displayImageOptions )
	{
		this.arrayPicOjs = arrayPicOjs;
		this.bitmaps = bitmaps;
		this.displayImageOptions = displayImageOptions;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayPicOjs.size();
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		PhotoView photoView = new PhotoView(container.getContext());		
		photoView.setImageBitmap(bitmaps.get(position));		
		container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);		
		return photoView;
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		container.removeView((View) object);
	}
	@Override
	public boolean isViewFromObject(View view, Object object) {
		// TODO Auto-generated method stub
		return view == object;
	}

	
}
