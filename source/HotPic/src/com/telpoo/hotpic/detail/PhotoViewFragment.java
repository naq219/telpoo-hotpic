package com.telpoo.hotpic.detail;

import uk.co.senab.photoview.PhotoView;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.telpoo.anhnong.hotgirl.R;
import com.telpoo.frame.delegate.Idelegate;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.hotpic.home.MyFragment;

public class PhotoViewFragment extends MyFragment {
	
	private PhotoView photoView;
	private String mSparseRealUrl;
	BaseObject oj;
	public static String KEY_URL = "KEYURL";
	public static String KEY_OBJ = "KEYOBJ";
	Bitmap bitmap ;
	Idelegate idelegate;
	

	public static PhotoViewFragment newInstance( BaseObject oj, String mSparseRealUrl  )
	{
		PhotoViewFragment fragment = new PhotoViewFragment();
		Bundle bundle = new Bundle();
		bundle.putString(KEY_URL, mSparseRealUrl);
		bundle.putParcelable(KEY_OBJ, oj);
		fragment.setArguments(bundle);
		return fragment;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.photo_view_fm, container, false);
		//
		mSparseRealUrl = getArguments().getString(KEY_URL);
		oj = getArguments().getParcelable(KEY_OBJ);
		//
		photoView = (PhotoView) rootView.findViewById(R.id.myphotoview);
		photoView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.no_image));
		//
		return rootView;
	}
	//
	//
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();		
		//
		if(bitmap == null)
		{
			//(new MyAsynctask()).execute();
			ImageLoader.getInstance().loadImage(mSparseRealUrl, new SimpleImageLoadingListener(){
				@Override
				public void onLoadingComplete(String imageUri, View view,
						Bitmap loadedImage) {
					// TODO Auto-generated method stub
					super.onLoadingComplete(imageUri, view, loadedImage);
					photoView.setImageBitmap(bitmap);
					bitmap = loadedImage;
					
				}
			});
//			bitmap = ImageLoader.getInstance().loadImageSync(mSparseRealUrl);
//			photoView.setImageBitmap(bitmap);
		}
		else
			photoView.setImageBitmap(bitmap);
	}
	

}
