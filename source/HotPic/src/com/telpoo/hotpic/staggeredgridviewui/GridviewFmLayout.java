package com.telpoo.hotpic.staggeredgridviewui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etsy.android.grid.StaggeredGridView;
import com.wallpaper.beautifulpicture.R;
import com.telpoo.hotpic.home.MyFragment;

public class GridviewFmLayout extends MyFragment {
	StaggeredGridView gridView;
	View loadMore;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.stg_fragment, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		gridView = (StaggeredGridView) view.findViewById(R.id.grid_view);
		loadMore= view.findViewById(R.id.loadMore);
		
	}
	
	
}
