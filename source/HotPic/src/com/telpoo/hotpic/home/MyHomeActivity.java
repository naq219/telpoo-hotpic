package com.telpoo.hotpic.home;

import java.io.File;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

import com.google.android.gms.ads.AdView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.telpoo.frame.delegate.Idelegate;
import com.telpoo.frame.ui.BaseFragmentActivity;
import com.telpoo.hotpic.R;
import com.telpoo.hotpic.menu.ViewMenu;

public class MyHomeActivity extends SlidingFragmentActivity implements Idelegate, OnClickListener {
	DisplayImageOptions displayImageOptions;
	int screenWidthMetro;
	int resource_home;
	String toastAskExit;
	static String[] tabids = { TabId.home };
	ViewMenu viewMenu;
	ImageView btnMenu;

	AdView adView;

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

		// displayImageOptions=new DisplayImageOptions.Builder().
		//
		// ImageLoaderConfiguration configuration = new
		// ImageLoaderConfiguration.Builder(getBaseContext())
		// .defaultDisplayImageOptions(displayImageOptions).build();
		// ;
		// ImageLoader.getInstance().init(configuration);

	}

	private void setupTracking() {
		setTrackingId("UA-48151387-2");

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

	protected void setupImageLoader() {
		File cacheDir = new File(getCacheDir(), "imgcachedir");
		if (!cacheDir.exists())
			cacheDir.mkdir();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).memoryCache(new WeakMemoryCache())
				.threadPoolSize(4)
				// .memoryCacheSize(1048576 * 10)
				.discCache(new UnlimitedDiscCache(cacheDir))
				// .discCache(discCache)
				.denyCacheImageMultipleSizesInMemory().build();
		//
		ImageLoader.getInstance().init(config);
		// ////////////////////
		displayImageOptions = new DisplayImageOptions.Builder().cacheInMemory(false).cacheOnDisc(true)
				.resetViewBeforeLoading(false).bitmapConfig(Bitmap.Config.RGB_565).showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher).imageScaleType(ImageScaleType.EXACTLY).build();
		//
		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
		screenWidthMetro = displayMetrics.widthPixels - 10;

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

}
