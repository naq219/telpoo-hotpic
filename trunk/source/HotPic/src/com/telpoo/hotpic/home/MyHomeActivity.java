package com.telpoo.hotpic.home;

import java.util.ArrayList;

import android.R.color;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.telpoo.frame.delegate.Idelegate;
import com.telpoo.frame.ui.BaseFragmentActivity;
import com.telpoo.frame.utils.Mlog;
import com.telpoo.anhnong.hotgirl.R;
import com.telpoo.hotpic.menu.ViewMenu;

public class MyHomeActivity extends SlidingFragmentActivity implements Idelegate, OnClickListener {

	int resource_home;
	String toastAskExit;
	static String[] tabids = { TabId.home };
	ViewMenu viewMenu;
	ImageView btnMenu;
	View fm_top;
	TextView tvTitle, tvnotifi;

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
		fm_top = findViewById(R.id.fm_top);
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		tvnotifi = (TextView) findViewById(R.id.notifi);

		int a = 3;
		int b = 3;
		b += 1;
		if ((a > b ? a > b : a < b))
			a++;
		Mlog.E("a=" + a);

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

	@SuppressLint("ResourceAsColor")
	public void showtop() {
		fm_top.setVisibility(View.VISIBLE);
	}

	public void hidetop() {
		fm_top.setVisibility(View.GONE);
	}

}
