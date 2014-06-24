package com.telpoo.hotpic.detail;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher.OnViewTapListener;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.telpoo.frame.delegate.Idelegate;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.frame.utils.Mlog;
import com.telpoo.hotpic.object.AlbulmOj;
import com.telpoo.hotpic.parsehtml.ChanDaiParse;
import com.telpoo.hotpic.parsehtml.ParseSupport;

public class PhoToViewAdapter extends PagerAdapter {
	PhotoView photoView;
	ArrayList<BaseObject> ojs;
	ViewGroup container1;
	Idelegate idelegate;
	Bitmap bn;
	private Context context;
	RequestQueue queue;
	SparseArray<String> sparseRealUrl = new SparseArray<String>();

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
		String realUrl = null;
		// lay lai url da get ve truoc do
		String cacheRealUrl = sparseRealUrl.get(position, null);

		if (cacheRealUrl == null) {
			realUrl = ParseSupport.parseUrlDetail(ojs.get(position), context);

			sparseRealUrl.put(position, realUrl);
		} else
			realUrl = cacheRealUrl;

		bn = ImageLoader.getInstance().loadImageSync(realUrl);
		
		Mlog.T("" + ojs.get(position).get(AlbulmOj.URL_THUMBNAIL));
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
