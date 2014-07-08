package com.telpoo.hotpic.detail;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher.OnViewTapListener;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.telpoo.anhnong.hotgirl.R;
import com.telpoo.frame.delegate.Idelegate;
import com.telpoo.frame.model.BaseModel;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.frame.utils.Mlog;
import com.telpoo.hotpic.home.MyFragment;
import com.telpoo.hotpic.task.TaskNaq;
import com.telpoo.hotpic.task.TaskType;

public class PhotoViewFragment extends MyFragment {

	private PhotoView photoView;
	BaseObject oj;
	public static String KEY_URL = "KEYURL";
	public static String KEY_OBJ = "KEYOBJ";
	private static Idelegate idelegate2;

	public static PhotoViewFragment newInstance(BaseObject oj, Idelegate idelegate) {
		idelegate2 = idelegate;
		PhotoViewFragment fragment = new PhotoViewFragment();
		Bundle bundle = new Bundle();
		bundle.putParcelable(KEY_OBJ, oj);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.photo_view_fm, container, false);

		photoView = (PhotoView) rootView.findViewById(R.id.myphotoview);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		photoView.setOnViewTapListener(new OnViewTapListener() {
			
			@Override
			public void onViewTap(View view, float x, float y) {
				idelegate2.callBack(1, 1);
				
			}
		});
		
		
		// lay object anh chi tiet truyen tu listview
		oj = getArguments().getParcelable(KEY_OBJ);

		BaseModel model = new BaseModel() {
			@Override
			public void onSuccess(int taskType, ArrayList<?> list, String msg) {
				super.onSuccess(taskType, list, msg);
				String realUrl = (String) list.get(0);
				Mlog.T("realUrl=" + realUrl);
				ImageLoader.getInstance().loadImage(realUrl, new SimpleImageLoadingListener() {
					@Override
					public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
						super.onLoadingComplete(imageUri, view, loadedImage);
						photoView.setImageBitmap(loadedImage);

					}
				});

			}

			@Override
			public void onFail(int taskType, String msg) {
				super.onFail(taskType, msg);

			}
		};

		ArrayList<BaseObject> arrSend = new ArrayList<BaseObject>();
		arrSend.add(oj);

		TaskNaq taskNaq = new TaskNaq(model, TaskType.TASK_PARSE_DETAIL, arrSend, getActivity());
		model.exeTask(null, taskNaq);
	}
}
