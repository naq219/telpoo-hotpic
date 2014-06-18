package com.telpoo.hotpic.detail;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher.OnViewTapListener;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.telpoo.frame.delegate.Idelegate;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.frame.utils.Mlog;
import com.telpoo.hotpic.object.AlbulmOj;

public class PhoToViewAdapter extends PagerAdapter {
	PhotoView photoView;
	ArrayList<BaseObject> ojs;
	ViewGroup container1;
	Idelegate idelegate;
	Bitmap bn;
	public PhoToViewAdapter(ArrayList<BaseObject> ojs) {
		this.ojs = ojs;
	}

	
	public void setDelegate(Idelegate idelegate) {
		this.idelegate = idelegate;
	}

	@Override
	public int getCount() {
		return ojs.size();
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		photoView = new PhotoView(container.getContext());

		 bn = ImageLoader.getInstance().loadImageSync(ojs.get(position).get(AlbulmOj.URL_THUMBNAIL));
			
		
		Mlog.T(""+ojs.get(position).get(AlbulmOj.URL_THUMBNAIL));
		photoView.setImageBitmap(bn);
		container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		
		
		
		photoView.setOnViewTapListener(new OnViewTapListener() {

			@Override
			public void onViewTap(View view, float x, float y) {
				idelegate.callBack(1, 1);

			}
		});

		return photoView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {

		container.removeView((View) object);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}
	
	
	
	

}
