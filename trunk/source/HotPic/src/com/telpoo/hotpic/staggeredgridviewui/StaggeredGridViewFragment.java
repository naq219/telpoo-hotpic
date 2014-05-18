package com.telpoo.hotpic.staggeredgridviewui;

import java.io.File;
import java.util.ArrayList;





import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;

import com.etsy.android.grid.StaggeredGridView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.telpoo.frame.ui.BaseFragment;
import com.telpoo.hotpic.R;
import com.telpoo.hotpic.adapter.HotStaggeredGridViewAdapter;
import com.telpoo.hotpic.object.AlbulmOj;
import com.telpoo.hotpic.parsehtml.ChanDaiParse;



public class StaggeredGridViewFragment extends BaseFragment implements AbsListView.OnItemClickListener {
	
	StaggeredGridView staggeredGridView;
	HotStaggeredGridViewAdapter hotStaggeredGridViewAdapter;
	ArrayList<AlbulmOj> albulmArrayList;
	DisplayImageOptions displayImageOptions;
	String srcUrl;	
	int count ;
	View loadMoreStgFooter;
	boolean isLoading;
	
	
	public DisplayImageOptions getDisplayImageOptions() {
		return displayImageOptions;
	}
	public void setDisplayImageOptions(DisplayImageOptions displayImageOptions) {
		this.displayImageOptions = displayImageOptions;
	}
	public String getSrcUrl() {
		return srcUrl;
	}
	public void setSrcUrl(String srcUrl) {
		this.srcUrl = srcUrl;
	}
	//////////////////////////////////////////////////////
	/////////////////////////////////////////////////////
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.stg_fragment, container, false);
		Log.d("testSTG", "vao staggeredgrid");
		Log.d("testSTG", srcUrl);
		count = 1;
		staggeredGridView = (StaggeredGridView) rootView.findViewById(R.id.grid_view);
		loadMoreStgFooter = ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.stg_loadmore, null);
		staggeredGridView.addFooterView(loadMoreStgFooter);
		albulmArrayList = new ArrayList<AlbulmOj>();
		if( srcUrl.contains("chandai") )
		{
			isLoading = true;
			ChanDaiParse chanDaiParse = new ChanDaiParse();
			albulmArrayList = chanDaiParse.getAlbulmOjList(srcUrl, getActivity());
			isLoading = false;
		}
		
		//trimCache(getActivity());
		Log.d("testSTG", albulmArrayList.size()+"");
		hotStaggeredGridViewAdapter = new HotStaggeredGridViewAdapter( this.getActivity() , R.layout.image_item_grid, albulmArrayList, displayImageOptions);		
		
		staggeredGridView.setAdapter(hotStaggeredGridViewAdapter);		
		Log.d("testSTG", "adapter count " + hotStaggeredGridViewAdapter.getCount()+"");
		
		//TaskMinh taskMinh = new TaskMinh(getModel(), , list, context)
		staggeredGridView.setOnItemClickListener(this);
		staggeredGridView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				if(!isLoading)
				{
					
					int inLastScreen = firstVisibleItem + visibleItemCount;
					if( inLastScreen >= totalItemCount )
					{
						isLoading = true;
						if(count <= 10)
						{							
							LoadMore();
						}
						else
							staggeredGridView.removeFooterView(loadMoreStgFooter);
							
						
					}
				}				
			}
		});
		return rootView;
	}	
	public void LoadMore()
	{
		if( srcUrl.contains("chandai") )
		{
			
				ChanDaiParse chanDaiParse = new ChanDaiParse();
				ArrayList<AlbulmOj> albulmOjsTemp = new ArrayList<AlbulmOj>();
				if(count == 1)
				{
					count++;
				}
				String tempUrl = srcUrl;
				tempUrl = tempUrl + "/"+count;
				albulmOjsTemp = chanDaiParse.getAlbulmOjList(tempUrl, getActivity());
				
				Log.d("testSTG", "dang load more : "+ tempUrl);
				if( ( albulmOjsTemp != null) && ( albulmOjsTemp.size() != 0 )  )
				{
					Log.d("testSTG", "dang load more");
					albulmArrayList.addAll(albulmOjsTemp);
					count++;
					isLoading = false;
					staggeredGridView.removeFooterView(loadMoreStgFooter);
					hotStaggeredGridViewAdapter.notifyDataSetChanged();
					
				}
		}
					
					
				
		
		
		isLoading = false;
	}
	@Override
	public void onSuccess(int taskType, ArrayList<?> list, String msg) {
		// TODO Auto-generated method stub
		super.onSuccess(taskType, list, msg);
	}
	@Override
	public void onFail(int taskType, String msg) {
		// TODO Auto-generated method stub
		super.onFail(taskType, msg);
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}
	
}
