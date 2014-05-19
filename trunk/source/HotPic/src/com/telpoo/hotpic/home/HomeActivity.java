package com.telpoo.hotpic.home;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.telpoo.frame.delegate.Idelegate;
import com.telpoo.hotpic.R;
import com.telpoo.hotpic.db.DbSupport;
import com.telpoo.hotpic.menu.ViewMenu;
import com.telpoo.hotpic.metroui.MetroUIFragment;
import com.telpoo.hotpic.task.TaskMinh;
import com.telpoo.hotpic.task.TaskType;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.Log;

public class HomeActivity extends MyHomeActivity implements TaskType {
	TaskMinh taskMinh;
	//ViewMenu viewMenu;
	LinkedHashMap<String, ArrayList<String>> srcUrl;
	int screenWidthMetro;
	DisplayImageOptions displayImageOptions;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//viewMenu = new ViewMenu(getBaseContext(), this);// cai nay lam dung ko anh sao em show ra ko dc
		DbSupport.initDB(this);
		taskMinh = new TaskMinh(model, TASK_UPDATE_MENU, null, this);
		model.exeTask(null, taskMinh);
		
		///////////////////
		///////////////////
		File cacheDir = new File(getCacheDir(), "imgcachedir");			
		 if ( !cacheDir.exists() )
		        cacheDir.mkdir();	
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
		.memoryCache(new WeakMemoryCache())
		.threadPoolSize(5)
		//.memoryCacheSize(1048576 * 10)
		.discCache(new UnlimitedDiscCache(cacheDir))
		//.discCache(discCache)
		.denyCacheImageMultipleSizesInMemory()					
		.build();
		//
		ImageLoader.getInstance().init(config);
		//////////////////////
		displayImageOptions = new DisplayImageOptions.Builder()
		.cacheInMemory(true)
		.resetViewBeforeLoading(false)
		.bitmapConfig(Bitmap.Config.RGB_565)										
		.showImageForEmptyUri(R.drawable.ic_launcher)
		.showImageOnFail(R.drawable.ic_launcher)
		
		//.imageScaleType(ImageScaleType.EXACTLY)										
		.cacheOnDisc(true).build();
		//
		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
		screenWidthMetro = displayMetrics.widthPixels - 10; 
		//pushFragments(TabId.metro, MetroUIFragment.newInstance(screenWidth), true, null);
		taskMinh =  new TaskMinh(model, TASK_GET_METRO, null, this);
		model.exeTask(null, taskMinh);
		//
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onSuccess(int taskType, ArrayList<?> list, String msg) {
		super.onSuccess(taskType, list, msg);
		
		switch (taskType) {
		case TASK_UPDATE_MENU:
			
			//update vao menu 
			viewMenu.LoadData();			
			viewMenu.setClickItemExpandLV(getSupportFragmentManager(), R.id.realTabContent, displayImageOptions);
			viewMenu.setIndicatorGroupELV(); 	
			
			
			break;
		case TASK_GET_METRO:
			
				Log.d("testtask", "------------------------------------------------------------------------------");
				Log.d("testtask", "dang chay task metro");
				srcUrl= new LinkedHashMap<String, ArrayList<String>>();
				srcUrl = (LinkedHashMap<String, ArrayList<String>>) list.get(0);
				Log.d("testmetrotask", srcUrl.size()+"");
				for(String key: srcUrl.keySet())
				{
					Log.d("mmetroname", key);
				}
				FragmentManager fragmentManager = getSupportFragmentManager();
				//
				MetroUIFragment metroUIFragment = new MetroUIFragment();
				metroUIFragment.setSrcUrl(srcUrl);
				metroUIFragment.setDisplayImageOptions(displayImageOptions);
				metroUIFragment.setScreenWidth(screenWidthMetro);
				//
				fragmentManager.beginTransaction().add(R.id.realTabContent,metroUIFragment, TabId.metro ).addToBackStack(null).commit();
				break;
				


		default:
			break;
		}
	}

}
