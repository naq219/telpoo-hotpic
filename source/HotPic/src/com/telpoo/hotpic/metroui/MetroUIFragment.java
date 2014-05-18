package com.telpoo.hotpic.metroui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.telpoo.frame.ui.BaseFragment;
import com.telpoo.hotpic.R;
import com.telpoo.hotpic.object.MetroViewObject;
import com.telpoo.hotpic.staggeredgridviewui.StaggeredGridViewFragment;
import com.telpoo.hotpic.task.TaskMinh;
import com.telpoo.hotpic.task.TaskType;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.transition.Scene;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;
import android.widget.ViewFlipper;


public class MetroUIFragment extends BaseFragment implements TaskType, android.view.View.OnClickListener {
	
	
	int screenWidth, halfScreenwidth, quaterScreenWidth;
	ArrayList<ViewFlipper> viewFlipperList;
	ArrayList<ImageView> imageViewArrayList;
	ArrayList<String> url;
	DisplayImageOptions displayImageOptions;
	LinkedHashMap<String, ArrayList<String>> srcUrl;
	LinkedHashMap<String, ArrayList<ImageView>> imageViewArraHashMap;
	ArrayList<MetroViewObject> metroViewObjects;
	String[] chandai ={"ngaunhien","nguoimau","hotnhat","xiteen","nusinh","sexy",
			"diudang","aodai","vongmot","bikini","chandai","bannude","hangngoai"};
	boolean flag;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView =  inflater.inflate(R.layout.metro_winphone_ui, container , false);
		//screenWidth = getArguments().getInt(TaskType.SCREEN_WIDHT);
		if(screenWidth > 0  )
		{
			Log.d("widthsize", "------------------------------------------------------------------------------");
			Log.d("widthsize", screenWidth+"");
			halfScreenwidth = (int) (screenWidth* 0.5);
			quaterScreenWidth = (int) (screenWidth * 0.25);
		//-------------------------------------------------------------------------
			flag = true;
			viewFlipperList = new ArrayList<ViewFlipper>();
			imageViewArrayList = new ArrayList<ImageView>();
			//metroViewObjects = new ArrayList<MetroViewObject>();
			//srcUrl = new HashMap<String, ArrayList<String>>();
			imageViewArraHashMap = new LinkedHashMap<String, ArrayList<ImageView>>();
			url = new ArrayList<String>();
			//
			

			//
			Log.d("testtask", srcUrl.size()+"");
			for(String s:srcUrl.keySet())
				
			{
				Log.d("keysrc", s);
			}
			LoadViewFliper(rootView);
			CreateImageMap(getActivity());
			AddImageView();
			(new MyMetroTask()).execute();
			for(int i =0 ; i< viewFlipperList.size(); i++)
			{
				viewFlipperList.get(i).setOnClickListener(this);
			}
		}	
		/////////////////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////
		
		
		return rootView;
	}
public int getScreenWidth() {
		return screenWidth;
	}
	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}
	public DisplayImageOptions getDisplayImageOptions() {
		return displayImageOptions;
	}
	public void setDisplayImageOptions(DisplayImageOptions displayImageOptions) {
		this.displayImageOptions = displayImageOptions;
	}
	public LinkedHashMap<String, ArrayList<String>> getSrcUrl() {
		return srcUrl;
	}
	public void setSrcUrl(LinkedHashMap<String, ArrayList<String>> srcUrl) {
		this.srcUrl = srcUrl;
	}

	@Override
	public void onFail(int taskType, String msg) {
		// TODO Auto-generated method stub
		super.onFail(taskType, msg);
		Toast.makeText(getActivity(), "fail", Toast.LENGTH_LONG).show();
	}
	/*
	 * *set id for arraylist viewflipper 
	 */
	public void LoadViewFliper( View v )
	{
		ViewFlipper viewFlipper, viewFlipper2, viewFlipper3, viewFlipper4, viewFlipper5, viewFlipper6
		, viewFlipper7, viewFlipper8, viewFlipper9, viewFlipper10, viewFlipper11, viewFlipper12, viewFlipper13;
		//
		viewFlipper = (ViewFlipper) v.findViewById(R.id.ngaunhien_cd);
		viewFlipper2 = (ViewFlipper) v.findViewById(R.id.nguoimau_cd);
		viewFlipper3 = (ViewFlipper) v.findViewById(R.id.hotnhat_cd);
		viewFlipper4 = (ViewFlipper) v.findViewById(R.id.nusinh_cd);
		viewFlipper5 = (ViewFlipper) v.findViewById(R.id.diudang_cd);
		viewFlipper6 = (ViewFlipper) v.findViewById(R.id.sexy_cd);
		viewFlipper7 = (ViewFlipper) v.findViewById(R.id.xiteen_cd);
		viewFlipper8 = (ViewFlipper) v.findViewById(R.id.aodai_cd);
		viewFlipper9 = (ViewFlipper) v.findViewById(R.id.vongmot_cd);
		viewFlipper10 = (ViewFlipper) v.findViewById(R.id.bikini_cd);
		viewFlipper11 = (ViewFlipper) v.findViewById(R.id.chandai_cd);
		viewFlipper12 = (ViewFlipper) v.findViewById(R.id.bannude_cd);
		viewFlipper13 = (ViewFlipper) v.findViewById(R.id.hangngoai_cd);
		//
		viewFlipperList.add(viewFlipper);
		viewFlipperList.add(viewFlipper2);
		viewFlipperList.add(viewFlipper3);
		viewFlipperList.add(viewFlipper4);
		viewFlipperList.add(viewFlipper5);
		viewFlipperList.add(viewFlipper6);
		viewFlipperList.add(viewFlipper7);
		viewFlipperList.add(viewFlipper8);
		viewFlipperList.add(viewFlipper9);
		viewFlipperList.add(viewFlipper10);
		viewFlipperList.add(viewFlipper11);
		viewFlipperList.add(viewFlipper12);
		viewFlipperList.add(viewFlipper13);				
	}
	/*
	 * *create imagelist with count 
	 */
	public void CreateImageviewList(Context context)
	{
		for(int i = 0; i < 26; i++)
		{
			ImageView imageView = new ImageView(context);
			imageView.setScaleType(ScaleType.FIT_XY);			
			imageViewArrayList.add(imageView);
		}
	}
	/*
	 * * load url image 
	 */
	
	public void CreateImageMap(Context context)
	{
		for(int i =0; i < srcUrl.size(); i++)
		{
			//Log.d("testrun", srcUrl.keySet() );
			//String name ="";
			ArrayList<ImageView> imageViews = new ArrayList<ImageView>();
			for(int j=0; j < srcUrl.get(chandai[i]).size(); j++)
			{
				
				ImageView imageView = new ImageView(context);
				imageView.setScaleType(ScaleType.FIT_XY);
				ImageLoader.getInstance().displayImage(srcUrl.get(chandai[i]).get(j), imageView, displayImageOptions);
				imageViews.add(imageView);
			}
			imageViewArraHashMap.put(chandai[i], imageViews);
		}
	}
	public void AddImageView()
	
	{
		int count =0;
		for(String key: srcUrl.keySet())
		{
			int sizeViewWidth = halfScreenwidth;
			int sizeHeightView = halfScreenwidth;
			if( key.equals("hotnhat") || key.equals("vongmot") )
			{
				viewFlipperList.get(count).setPadding(2, 2, 2, 2);
				sizeViewWidth = screenWidth;
				sizeHeightView = halfScreenwidth;
			}
			else
				if(key.equals("nusinh") || key.equals("diudang") || key.equals("aodai")|| key.equals("xiteen"))
				{
					
					sizeHeightView = quaterScreenWidth;
					sizeViewWidth = quaterScreenWidth;
					if( key.equals("xiteen") )
					{
						viewFlipperList.get(count).setPadding(2, 2, 1, 1);
					}
					if(key.equals("diudang"))
					{
						viewFlipperList.get(count).setPadding(2, 1, 1, 2);
					}
					if(key.equals("nusinh") )
					{
						viewFlipperList.get(count).setPadding(1, 2, 2, 1);
					}
					if(key.equals("aodai") )
					{
						viewFlipperList.get(count).setPadding(1, 1, 2, 2);;
					}
						
				}
				else
				{
					viewFlipperList.get(count).setPadding(2, 2, 2, 2);
				}
				
			for(int j =0 ; j < srcUrl.get(key).size(); j++  )
			{
				
				viewFlipperList.get(count).addView(imageViewArraHashMap.get(key).get(j), sizeViewWidth, sizeHeightView);
			}
			count++;
		}
	}
	
	public class MyMetroTask extends AsyncTask<Void, Integer, Void>
	{

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			while(flag == true)
			{
				Log.d("testrun", "dang chay metro spin");
				Random random = new Random();
				int[] positions = new int[2];
				int i = random.nextInt(10);
				if( i == 10 )
				{
					positions[0] = i ;
					positions[1] = random.nextInt( positions[0] - 1) ;
				}
				else
					if( i == 0 )
					{
						positions[0] = i;
						positions[1] = random.nextInt(3)+ 1;
					}
					else
					{
						positions[0] = i;
						positions[1]  = (i - 1)/2 ;
					}
				//fliper.showNext();
				for(int j = 0; j < positions.length ; j++ )
				{
					//viewFlipperList.get(positions[j]).clearAnimation();
					
					viewFlipperList.get(positions[j]).setOutAnimation(getActivity(), R.anim.top_out);
					viewFlipperList.get(positions[j]).setInAnimation(getActivity(), R.anim.bottom_in);	
					publishProgress(positions[j]);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				try {
					Thread.sleep(9000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return null;
		}
		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			viewFlipperList.get(values[0]).showNext();
		}
		
	}
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		flag = false;
	}	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
		StaggeredGridViewFragment gridViewFragment = new StaggeredGridViewFragment();
		switch (v.getId()) {
		case R.id.ngaunhien_cd:
			
			
			gridViewFragment.setDisplayImageOptions(displayImageOptions);
			gridViewFragment.setSrcUrl("http://chandai.tv/random");
			fragmentManager.beginTransaction().replace(R.id.realTabContent, gridViewFragment, "stag").addToBackStack(null).commit();
			
			break;
		case R.id.hotnhat_cd:
			
			
			gridViewFragment.setDisplayImageOptions(displayImageOptions);
			gridViewFragment.setSrcUrl("http://chandai.tv/hot");
			fragmentManager.beginTransaction().replace(R.id.realTabContent, gridViewFragment, "stag").addToBackStack(null).commit();
			
			break;
		case R.id.nguoimau_cd:
			
			gridViewFragment.setDisplayImageOptions(displayImageOptions);
			gridViewFragment.setSrcUrl("http://chandai.tv/models");
			fragmentManager.beginTransaction().replace(R.id.realTabContent, gridViewFragment, "stag").addToBackStack(null).commit();
			
			break;
		case R.id.xiteen_cd:
			gridViewFragment.setDisplayImageOptions(displayImageOptions);
			gridViewFragment.setSrcUrl("http://chandai.tv/xi-tin");
			fragmentManager.beginTransaction().replace(R.id.realTabContent, gridViewFragment, "stag").addToBackStack(null).commit();
			
			break;
		case R.id.nusinh_cd:
			
			gridViewFragment.setDisplayImageOptions(displayImageOptions);
			gridViewFragment.setSrcUrl("http://chandai.tv/nu-sinh");
			fragmentManager.beginTransaction().replace(R.id.realTabContent, gridViewFragment, "stag").addToBackStack(null).commit();
			
			break;
		case R.id.diudang_cd:			
			gridViewFragment.setDisplayImageOptions(displayImageOptions);
			gridViewFragment.setSrcUrl("http://chandai.tv/diu-dang");
			fragmentManager.beginTransaction().replace(R.id.realTabContent, gridViewFragment, "stag").addToBackStack(null).commit();
			
			break;
		case R.id.aodai_cd:
			
			gridViewFragment.setDisplayImageOptions(displayImageOptions);
			gridViewFragment.setSrcUrl("http://chandai.tv/ao-dai");
			fragmentManager.beginTransaction().replace(R.id.realTabContent, gridViewFragment, "stag").addToBackStack(null).commit();
			
			break;
		case R.id.sexy_cd:
			
			gridViewFragment.setDisplayImageOptions(displayImageOptions);
			gridViewFragment.setSrcUrl("http://chandai.tv/sexy");
			fragmentManager.beginTransaction().replace(R.id.realTabContent, gridViewFragment, "stag").addToBackStack(null).commit();
		
		case R.id.vongmot_cd:
			
			gridViewFragment.setDisplayImageOptions(displayImageOptions);
			gridViewFragment.setSrcUrl("http://chandai.tv/vong-1");
			fragmentManager.beginTransaction().replace(R.id.realTabContent, gridViewFragment, "stag").addToBackStack(null).commit();
			
			break;
		case R.id.chandai_cd:
			
			gridViewFragment.setDisplayImageOptions(displayImageOptions);
			gridViewFragment.setSrcUrl("http://chandai.tv/chan-dai");
			fragmentManager.beginTransaction().replace(R.id.realTabContent, gridViewFragment, "stag").addToBackStack(null).commit();
		case R.id.bikini_cd:
			
			gridViewFragment.setDisplayImageOptions(displayImageOptions);
			gridViewFragment.setSrcUrl("http://chandai.tv/bikini");
			fragmentManager.beginTransaction().replace(R.id.realTabContent, gridViewFragment, "stag").addToBackStack(null).commit();
			
			break;
		case R.id.bannude_cd:
			
			gridViewFragment.setDisplayImageOptions(displayImageOptions);
			gridViewFragment.setSrcUrl("http://chandai.tv/ban-nude");
			fragmentManager.beginTransaction().replace(R.id.realTabContent, gridViewFragment, "stag").addToBackStack(null).commit();	
			break;
		case R.id.hangngoai_cd:
			
			gridViewFragment.setDisplayImageOptions(displayImageOptions);
			gridViewFragment.setSrcUrl("http://chandai.tv/hang-ngoai");
			fragmentManager.beginTransaction().replace(R.id.realTabContent, gridViewFragment, "stag").addToBackStack(null).commit();	
			break;

		default:
			break;
		}
 		
	}	
}
