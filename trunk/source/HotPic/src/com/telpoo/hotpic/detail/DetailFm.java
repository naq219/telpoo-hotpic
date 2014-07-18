package com.telpoo.hotpic.detail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.FacebookDialog.PendingCall;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;
import com.hinhnen.anhnong.hotgirl.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.telpoo.frame.delegate.Idelegate;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.frame.utils.FileSupport;
import com.telpoo.frame.utils.IntentSupport;
import com.telpoo.frame.utils.ViewUtils;
import com.telpoo.hotpic.db.DbSupport;
import com.telpoo.hotpic.home.HomeActivity;
import com.telpoo.hotpic.object.PicOj;
import com.telpoo.hotpic.task.TaskNaq;
import com.telpoo.hotpic.task.TaskType;

public class DetailFm extends DetailFmLayout implements Idelegate, OnClickListener {
	// PhoToViewAdapter phoToViewAdapter;
	private ArrayList<BaseObject> ojs;
	BaseObject ojPage = new BaseObject();
	private Integer positionFirst;
	ImageView sharebtn;
	private UiLifecycleHelper mUiFaceLifecycleHelper;
	private ArrayList<PhotoViewFragment> mListFragment;
	private PTViewPageAdapter adapter;
	private int loadDetailType;

	public static DetailFm newInstance(int sectionNumber) {
		DetailFm fragment = new DetailFm();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	public DetailFm() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.detail, container, false);
		viewPager = (MyPhotoViewPager) rootView.findViewById(R.id.photoView);
		sharebtn = (ImageView) rootView.findViewById(R.id.share);
		mListFragment = new ArrayList<PhotoViewFragment>();
		mUiFaceLifecycleHelper = new UiLifecycleHelper(getActivity(), callback);
		mUiFaceLifecycleHelper.onCreate(savedInstanceState);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		initView();

	}

	private void initView() {

		ImageView[] imageViews = { like, favorite1, comment, download, setting, share };
		ViewUtils.HighlightImageView(imageViews, Color.parseColor("#b3100b"));

		setting.setOnClickListener(this);
		favorite1.setOnClickListener(this);
		
//		favorite1.setOnTouchListener(new OnTouchListener() {
//			
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				showToast("sds");
//				return true;
//			}
//		});
		
		share.setOnClickListener(this);
		download.setOnClickListener(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		mUiFaceLifecycleHelper.onResume();
		LoadListFragment();
		adapter = new PTViewPageAdapter(getChildFragmentManager(), ojs,this);

		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {

			}

			@Override
			public void onPageScrolled(int position, float arg1, int arg2) {

				ojPage = ojs.get(position);

			}

			@Override
			public void onPageScrollStateChanged(int state) {

				switch (state) {
				case 0:

					setTextName(ojPage.get(PicOj.NAME));

					break;

				default:
					break;
				}

			}
		});

		if (positionFirst == 0) {
			setTextName(ojs.get(0).get(PicOj.NAME));

		}

		viewPager.setCurrentItem(positionFirst, true);
		sharebtn.setOnClickListener(this);

	}

	protected void setTextName(String string) {
		name.setText(string);
		if (string == null || string.length() == 0) {
			name.setVisibility(View.GONE);
		} else
			name.setVisibility(View.VISIBLE);

	}

	@Override
	public void callBack(Object value, int where) {

		if (popup.getVisibility() == View.VISIBLE) {
			popup.setVisibility(View.GONE);
			HomeActivity.getInstance().hidetop();
		} else {
			HomeActivity.getInstance().showtop();
			popup.setVisibility(View.VISIBLE);
		}

	}

	public void setData(ArrayList<BaseObject> ojs, Integer positionFirst) {
		this.ojs = ojs;
		this.positionFirst = positionFirst;

	}

	@Override
	public void onStop() {
		super.onStop();
		mUiFaceLifecycleHelper.onStop();
		HomeActivity.getInstance().showtop();
	}

	@Override
	public void onDestroy() {

		super.onDestroy();
		mUiFaceLifecycleHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mUiFaceLifecycleHelper.onSaveInstanceState(outState);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		
		case R.id.favorite1:
			boolean status=DbSupport.addFabvorite(ojPage,getActivity());
			if(!status){
				showToast("Đã xóa khỏi yêu thích");
				favorite1.setImageResource(R.drawable.ic_chitiet_favprite_normal);
			}
			else {
				showToast("Đã thêm vào yêu thích");
				favorite1.setImageResource(R.drawable.ic_chitiet_favprite_select);
			}
			break;
		
		case R.id.setting:
			loadDetailType = 0;

			ArrayList<BaseObject> arrSend = new ArrayList<BaseObject>();
			arrSend.add(ojPage);

			TaskNaq taskNaq = new TaskNaq(getModel(), TaskType.TASK_PARSE_DETAIL, arrSend, getActivity());
			getModel().exeTask(null, taskNaq);

			break;

		case R.id.download:
			loadDetailType = 1;
			ArrayList<BaseObject> arrSend1 = new ArrayList<BaseObject>();
			arrSend1.add(ojPage);

			TaskNaq taskNaq1 = new TaskNaq(getModel(), TaskType.TASK_PARSE_DETAIL, arrSend1, getActivity());
			getModel().exeTask(null, taskNaq1);

			break;
		case R.id.share:
			final String linkImage = ojPage.get(PicOj.URL_THUMBNAIL);
			final String des = ojPage.get(PicOj.NAME);
			final String urlAppPlayStore = "https://play.google.com/store/apps/details?id=" + getActivity().getApplicationContext().getPackageName();
			if (linkImage != null) {
				Session.openActiveSession(getActivity(), DetailFm.this, true, new Session.StatusCallback() {

					// callback when session changes state
					@Override
					public void call(Session session, SessionState state, Exception exception) {
						if (session.isOpened()) {

							// make request to the /me API
							Request.newMeRequest(session, new Request.GraphUserCallback() {

								@Override
								public void onCompleted(GraphUser user, Response response) {
									if (user != null) {
										// ShareFaceDialog("via Viet Foodie",
										// data.getTitle(), faceShare, "", "");
										publishFeedDialog("" + des, "App hot girl for Android", "Wallpaper, picture", linkImage, urlAppPlayStore);
									}
								}
							}).executeAsync();
						}
					}
				});
			}

			break;

		default:
			break;
		}

	}

	@Override
	public void onSuccess(int taskType, ArrayList<?> list, String msg) {
		super.onSuccess(taskType, list, msg);

		switch (taskType) {
		case TaskType.TASK_PARSE_DETAIL:
			String urlDetail = (String) list.get(0);
			ImageLoader.getInstance().loadImage(urlDetail, new SimpleImageLoadingListener() {

				@Override
				public void onLoadingStarted(String imageUri, View view) {
					super.onLoadingStarted(imageUri, view);
					showToast(getContext().getString(R.string.dang_tai_anh));
				}
				

				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					super.onLoadingComplete(imageUri, view, loadedImage);
					File pictureFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

					String fileName = imageUri.substring(imageUri.lastIndexOf('/') + 1, imageUri.length());
					String path = pictureFolder.getAbsolutePath() + "/hotpic/" + fileName + ".png";
					FileSupport.saveBitmap(loadedImage, path);
					
					
					
					if (loadDetailType == 0) {
						IntentSupport.cropImage(Uri.fromFile(new File(path)), 1231, getActivity());
					} else if (loadDetailType == 1) {
						
						showToast(getContext().getString(R.string.anh_da_duoc_luu_vao) + path);

					}

				}

				@Override
				public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
					super.onLoadingFailed(imageUri, view, failReason);
					showToast(getContext().getString(R.string.loi_ket_noi));
					System.gc();
				}
			});

			break;

		default:
			break;
		}
	}

	@Override
	public void onFail(int taskType, String msg) {
		// TODO Auto-generated method stub
		super.onFail(taskType, msg);
	}

	// --------------------------------------------------------------------------------------------------------------------------
	// FACEBOOK SHARE
	// --------------------------------------------------------------------------------------------------------------------------

	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
		if (state.isOpened()) {
			Log.i("FaceBookDT", "Logged in...");
		} else if (state.isClosed()) {
			Log.i("FaceBookDT", "Logged out...");
		}
	}

	private Session.StatusCallback callback = new StatusCallback() {

		@Override
		public void call(Session session, SessionState state, Exception exception) {
			// TODO Auto-generated method stub
			onSessionStateChange(session, state, exception);

		}
	};

	public void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode==1231){ //crop image
			Bitmap loadedImage = IntentSupport.getIntentCropImage(data);
			
			try {
				WallpaperManager.getInstance(getActivity()).setBitmap(loadedImage);
				showToast(getContext().getString(R.string.da_cai_lam_hinh_nen));
			} catch (IOException e) {
				e.printStackTrace();
				showToast(getContext().getString(R.string.have_error));
			}
		}
		
		else{ //share
			Session.getActiveSession().onActivityResult(getActivity(), requestCode, resultCode, data);
			mUiFaceLifecycleHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {

				@Override
				public void onError(PendingCall pendingCall, Exception error, Bundle data) {
					// TODO Auto-generated method stub
					Log.e("Activity", String.format("Error: %s", error.toString()));
				}

				@Override
				public void onComplete(PendingCall pendingCall, Bundle data) {
					// TODO Auto-generated method stub
					Log.i("Activity", "Success!");
				}
			});
		}
		
	
		
		
		
		
	};

	private void publishFeedDialog(String name, String caption, String description, String pictureLink, String link) {
		Bundle params = new Bundle();
		params.putString("name", name);
		params.putString("caption", caption);
		params.putString("description", description);
		params.putString("picture", pictureLink);
		params.putString("link", link);

		WebDialog feedDialog = (new WebDialog.FeedDialogBuilder(getActivity(), Session.getActiveSession(), params)).setOnCompleteListener(new OnCompleteListener() {

			@Override
			public void onComplete(Bundle values, FacebookException error) {
				// TODO Auto-generated method stub
				if (error == null) {
					// When the story is posted, echo the success
					// and the post Id.
					final String postId = values.getString("post_id");
					if (postId != null) {
						Toast.makeText(getActivity(), "Chia sẻ thành công", Toast.LENGTH_SHORT).show();

					} else {
						// User clicked the Cancel button
						Toast.makeText(getActivity().getApplicationContext(), "Đã hủy chia sẻ", Toast.LENGTH_SHORT).show();
					}
				} else if (error instanceof FacebookOperationCanceledException) {
					// User clicked the "x" button

				} else {
					// Generic, ex: network error
				}

			}

		}).build();
		feedDialog.show();
	}

	private void LoadListFragment() {
		for (int i = 0; i < ojs.size(); i++) {
			mListFragment.add(PhotoViewFragment.newInstance(ojs.get(i), this));
		}
	}
}
