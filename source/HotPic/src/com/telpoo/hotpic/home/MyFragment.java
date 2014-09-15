package com.telpoo.hotpic.home;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.telpoo.frame.ui.BaseFragment;
import com.wallpaper.beautifulpicture.R;
import com.telpoo.hotpic.utils.LoadingView;

public class MyFragment extends BaseFragment{
	ProgressBar progress;
	protected View loadingView;
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		progress=(ProgressBar) view.findViewById(R.id.progress);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		loadingView= new LoadingView(getActivity());
	}
	
	protected void showProgress(){
		if(progress!=null){
			progress.setVisibility(View.VISIBLE);
		}
	}
	
	protected void closeProgress(){
		if(progress!=null){
			progress.setVisibility(View.GONE);
		}
	}

}
