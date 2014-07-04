package com.telpoo.hotpic.detail;

import uk.co.senab.photoview.PhotoView;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.telpoo.frame.delegate.Idelegate;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.hotpic.R;
import com.telpoo.hotpic.home.MyFragment;
import com.telpoo.hotpic.parsehtml.ParseSupport;

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
		//
		if(bitmap == null)
		{
			(new MyAsynctask()).execute();
		}
		else
			photoView.setImageBitmap(bitmap);		
		//
		return rootView;
	}
	private class MyAsynctask extends AsyncTask<String, Void, Bitmap>
	{		

		@Override
		protected Bitmap doInBackground(String... params) {
			// TODO Auto-generated method stub
			String readUrl;
			
			if(mSparseRealUrl == null)
			{
				readUrl = ParseSupport.parseUrlDetail(oj, getActivity());
				//mSparseRealUrl;
				bitmap = ImageLoader.getInstance().loadImageSync(readUrl);
			}		
			
			return bitmap;
		}
		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			//
			//
			
			photoView.setImageBitmap(result);
		}
	}
}
