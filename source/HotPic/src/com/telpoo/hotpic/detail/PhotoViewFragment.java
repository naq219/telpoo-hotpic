package com.telpoo.hotpic.detail;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher.OnViewTapListener;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.wallpaper.beautifulpicture.R;
import com.telpoo.frame.delegate.Idelegate;
import com.telpoo.frame.model.BaseModel;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.frame.utils.Mlog;
import com.telpoo.hotpic.home.MyFragment;
import com.telpoo.hotpic.object.PicOj;
import com.telpoo.hotpic.task.TaskNaq;
import com.telpoo.hotpic.task.TaskType;

public class PhotoViewFragment extends MyFragment {
	BaseModel model;
	TaskNaq taskNaq;
	private PhotoView photoView;
	BaseObject oj;
	private View viewLoadUrl;
	public static String KEY_URL = "KEYURL";
	public static String KEY_OBJ = "KEYOBJ";
	private static Idelegate idelegate2;
	boolean finishLoadUrl = false;

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

		// lay object anh chi tiet truyen tu listview
		oj = getArguments().getParcelable(KEY_OBJ);
		photoView = (PhotoView) rootView.findViewById(R.id.myphotoview);
		viewLoadUrl = (View) rootView.findViewById(R.id.viewLoadUrl);

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

		ImageLoader.getInstance().loadImage(oj.get(PicOj.URL_THUMBNAIL), new SimpleImageLoadingListener() {
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				super.onLoadingComplete(imageUri, view, loadedImage);
				photoView.setImageBitmap(loadedImage);

			}
		});

		ArrayList<BaseObject> arrSend = new ArrayList<BaseObject>();
		arrSend.add(oj);
		model = new BaseModel() {
			@Override
			public void onSuccess(int taskType, ArrayList<?> list, String msg) {
				super.onSuccess(taskType, list, msg);
				final String realUrl = (String) list.get(0);
				Mlog.T("realUrl=" + realUrl);
				LoadImage(realUrl);
				finishLoadUrl = true;

			}

			@Override
			public void onFail(int taskType, String msg) {
				super.onFail(taskType, msg);
				viewLoadUrl.setVisibility(View.GONE);
				finishLoadUrl = true;
			}
		};

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				if (!finishLoadUrl)
					viewLoadUrl.setVisibility(View.VISIBLE);
			}
		}, 2000);
		taskNaq = new TaskNaq(model, TaskType.TASK_PARSE_DETAIL, arrSend, getActivity());
		model.exeTask(null, taskNaq);

	}

	public void LoadImage(String url) {

		ImageLoader.getInstance().loadImage(url, new SimpleImageLoadingListener() {
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				super.onLoadingComplete(imageUri, view, loadedImage);
				photoView.setImageBitmap(loadedImage);
				viewLoadUrl.setVisibility(View.GONE);
				finishLoadUrl = true;
			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
				super.onLoadingFailed(imageUri, view, failReason);
				viewLoadUrl.setVisibility(View.GONE);
				finishLoadUrl = true;
				System.gc();
			}

			@Override
			public void onLoadingCancelled(String imageUri, View view) {
				super.onLoadingCancelled(imageUri, view);
				viewLoadUrl.setVisibility(View.GONE);
			}
		});
	}

}
