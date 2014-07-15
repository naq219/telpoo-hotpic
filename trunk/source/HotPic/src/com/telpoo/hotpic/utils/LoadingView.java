package com.telpoo.hotpic.utils;

import com.hinhnen.anhnong.hotgirl.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public class LoadingView extends View {

	public LoadingView(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.loading, null);
	}

}
