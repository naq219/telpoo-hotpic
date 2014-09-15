package com.telpoo.hotpic.home;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.app.DownloadManager.Query;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.telpoo.frame.delegate.Idelegate;
import com.telpoo.frame.ui.BaseFragmentActivity;
import com.telpoo.frame.utils.FileSupport;
import com.telpoo.frame.utils.IntentSupport;
import com.telpoo.frame.utils.Mlog;
import com.telpoo.frame.utils.ScreenUtils;
import com.telpoo.hotpic.menu.ViewMenu;
import com.wallpaper.beautifulpicture.R;

public class MyHomeActivity extends SlidingFragmentActivity implements Idelegate, OnClickListener {

	int resource_home;
	String toastAskExit;
	static String[] tabids = { TabId.home };
	ViewMenu viewMenu;
	ImageView btnMenu;
	View fm_top;
	TextView tvTitle, tvnotifi;
	AdView adView;
	protected DownloadManager dm;
	protected long enqueue;
	public int loadDetailType;

	public MyHomeActivity() {
		super(tabids, R.id.realTabContent, "Bấm thêm lần nữa để thoát");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setupTracking();
		super.onCreate(savedInstanceState);
		initView();
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		initAds();
		dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
		BroadcastReceiver receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
					long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
					Query query = new Query();
					query.setFilterById(enqueue);
					Cursor c = dm.query(query);
					if (c.moveToFirst()) {
						int columnIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
						if (DownloadManager.STATUS_SUCCESSFUL == c.getInt(columnIndex)) {

							String uriString = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
							Mlog.T(uriString);
							
							Uri ur=Uri.parse(uriString);
							if (loadDetailType == 0) {
	
							//float ratio = (float) ScreenUtils.getWindowsHeigh(MyHomeActivity.this) / (float) ScreenUtils.getWindowsWidth(MyHomeActivity.this);
								
							try {
								ContentResolver cr = getBaseContext().getContentResolver();
								InputStream data= cr.openInputStream(ur);
								WallpaperManager.getInstance(MyHomeActivity.this).setStream(data);
								showToast(getContext().getString(R.string.da_cai_lam_hinh_nen));
							} catch (IOException e) {
								e.printStackTrace();
								showToast(getContext().getString(R.string.have_error));
							}
							
							//cropImage(ur, 1231, MyHomeActivity.this);
							
	
						} else if (loadDetailType == 1) {
	
							showToast(getContext().getString(R.string.anh_da_duoc_luu_vao) + ur.getPath());
	
						}
							
						}
					}
				}
			}
		};

		registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
	}

	@SuppressLint("NewApi")
	public void doDownload(String urlDetail) {
		File pictureFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		String fileName = urlDetail.substring(urlDetail.lastIndexOf('/') + 1, urlDetail.length());
		String pathStorage = pictureFolder.getAbsolutePath() + "/hotpic/" + fileName;
		dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

		android.app.DownloadManager.Request request = new android.app.DownloadManager.Request(Uri.parse(urlDetail));
		request.setTitle(fileName);
		if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.HONEYCOMB_MR2) {
			request.allowScanningByMediaScanner();
			request.setNotificationVisibility(android.app.DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
		}

		File f = new File(pathStorage);
		f.mkdirs();
		Uri dst_uri = Uri.fromFile(f);
		request.setDestinationUri(dst_uri);
		enqueue = dm.enqueue(request);
	}

	private void initAds() {
		adView = new AdView(getBaseContext());
		adView.setAdUnitId(getString(R.string.admobid));
		adView.setAdSize(AdSize.SMART_BANNER);
		((RelativeLayout) findViewById(R.id.adView)).addView(adView);
		 TelephonyManager tm =(TelephonyManager)getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);

		String deviceid = tm.getDeviceId();
		adView.loadAd(new AdRequest.Builder().addTestDevice(deviceid).build());

	}

	private void setupTracking() {
		setTrackingId("UA-33222928-5");

	}

	private void initView() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		getSlidingMenu().setMode(SlidingMenu.LEFT);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		setBehindContentView(viewMenu.getView());

		getSlidingMenu().setShadowWidthRes(R.dimen.shadow_width);
		getSlidingMenu().setShadowDrawable(R.drawable.shadow);
		getSlidingMenu().setBehindOffsetRes(R.dimen.slidingmenu_offset);
		getSlidingMenu().setFadeDegree(0.35f);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		btnMenu = (ImageView) findViewById(R.id.btnMenu);
		btnMenu.setOnClickListener(this);
		fm_top = findViewById(R.id.fm_top);
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		tvnotifi = (TextView) findViewById(R.id.notifi);

	}

	@Override
	public void callBack(Object value, int where) {
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onSuccess(int taskType, ArrayList<?> list, String msg) {
		super.onSuccess(taskType, list, msg);
		if (list == null || list.size() == 0)
			return;
	}

	@Override
	public void onFail(int taskType, String msg) {
		super.onFail(taskType, msg);
	}

	@Override
	public BaseFragmentActivity getRootFA() {
		return super.getRootFA();
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnMenu:

			toggle();

			break;

		default:
			break;
		}

	}

	public void toggle() {
		getSlidingMenu().toggle(true);
	}

	public void showLoadingBar() {
		View v = findViewById(R.id.progress);
		if (v != null)
			v.setVisibility(View.VISIBLE);

	}

	public void closeLoadingBar() {
		View v = findViewById(R.id.progress);
		if (v != null)
			v.setVisibility(View.GONE);

	}

	public void showtop() {
		fm_top.setVisibility(View.VISIBLE);
		findViewById(R.id.adView).setVisibility(View.VISIBLE);
	}

	public void hidetop() {
		fm_top.setVisibility(View.GONE);
		findViewById(R.id.adView).setVisibility(View.GONE);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == 1231) { // crop image
			Bitmap loadedImage = IntentSupport.getIntentCropImage(data);
			if (loadedImage == null) {
				showToast(getContext().getString(R.string.da_huy));
				return;
			}
			try {
				WallpaperManager.getInstance(MyHomeActivity.this).setBitmap(loadedImage);
				showToast(getContext().getString(R.string.da_cai_lam_hinh_nen));
			} catch (IOException e) {
				e.printStackTrace();
				showToast(getContext().getString(R.string.have_error));
			}
		}
		
		
	}
	
	public boolean cropImage(Uri picUri, int requsetCode, Activity activity) {
		try {

			Intent cropIntent = new Intent("com.android.camera.action.CROP");
			cropIntent.setDataAndType(picUri, "image/*");
			cropIntent.putExtra("crop", "true");
			// cropIntent.putExtra("aspectX", 1);
			// cropIntent.putExtra("aspectY", 1);
			cropIntent.putExtra("outputX", 200);
			cropIntent.putExtra("outputY", 200);
			cropIntent.putExtra("scale", true);
			cropIntent.putExtra("return-data", true);
			cropIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

			activity.startActivityForResult(cropIntent, requsetCode);
			return true;
		} catch (ActivityNotFoundException anfe) {
			return false;
		}
	}

}
