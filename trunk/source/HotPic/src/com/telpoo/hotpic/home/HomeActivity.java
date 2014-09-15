package com.telpoo.hotpic.home;

import java.util.ArrayList;

import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;

import com.wallpaper.beautifulpicture.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnClosedListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenedListener;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.hotpic.db.DbSupport;
import com.telpoo.hotpic.delegate.IOnMenuClosed;
import com.telpoo.hotpic.menu.ViewMenu;
import com.telpoo.hotpic.object.MenuOj;
import com.telpoo.hotpic.staggeredgridviewui.GridviewFm;
import com.telpoo.hotpic.task.TaskMinh;
import com.telpoo.hotpic.task.TaskType;
import com.telpoo.hotpic.utils.Constant;
import com.telpoo.hotpic.utils.Utils;

public class HomeActivity extends MyHomeActivity implements TaskType {
	TaskMinh taskMinh;
	// ViewMenu viewMenu;

	private static HomeActivity me;
	IOnMenuClosed iOnMenuClosed;

	public static HomeActivity getInstance() {
		return me;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		setting();

		viewMenu = new ViewMenu(HomeActivity.this, this);
		super.onCreate(savedInstanceState);
		me = HomeActivity.this;

		taskMinh = new TaskMinh(model, TASK_UPDATE_MENU, null, this);
		model.exeTask(null, taskMinh);

		getSlidingMenu().setOnClosedListener(new OnClosedListener() {

			@Override
			public void onClosed() {

				iOnMenuClosed.onMenuClosed();

			}
		});

		getSlidingMenu().setOnOpenedListener(new OnOpenedListener() {

			@Override
			public void onOpened() {
				viewMenu.updateFv();

			}
		});

		// load list anh mac dinh
		loadDefault();

	}

	private void setting() {
		setupImageLoader();
		DbSupport.init(getBaseContext());
	}

	protected void setupImageLoader() {
		ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(getBaseContext())
		.threadPoolSize(5)
    	.memoryCacheExtraOptions(480, 800) // max width, max height
		.threadPoolSize(3)
		.threadPriority(Thread.NORM_PRIORITY - 1)
		.denyCacheImageMultipleSizesInMemory()
		.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation
		.discCacheFileNameGenerator(new HashCodeFileNameGenerator())
		.tasksProcessingOrder(QueueProcessingType.FIFO)
		.defaultDisplayImageOptions(Utils.loadImgOption())
		.build();
		            

		ImageLoader.getInstance().init(configuration);
	}

	private void loadDefault() {

		BaseObject oj = new BaseObject();
		oj.set(MenuOj.GROUP_ID, Constant.GroupSource.GROUP_HDWALL);
		oj.set(MenuOj.CATEGORY, 1);
		oj.set(MenuOj.NAME, "Home");
		GridviewFm fragment = new GridviewFm();
		fragment.setData(oj);
		HomeActivity.getInstance().pushFragments(TabId.home, fragment, true, null);

	}

	@SuppressWarnings("unchecked")
	@Override
	public void onSuccess(int taskType, ArrayList<?> list, String msg) {
		super.onSuccess(taskType, list, msg);

		switch (taskType) {
		case TASK_UPDATE_MENU:

			// update vao menu
			viewMenu.LoadData();
			viewMenu.setClickItemExpandLV(getSupportFragmentManager(), R.id.realTabContent);
			viewMenu.setIndicatorGroupELV();

			break;

		default:
			break;
		}
	}

	/**
	 * Class ViewMenu se set listener nay de lang nghe su kien khi menu da dong
	 * lai khi click vao 1 row trong ViewMenu
	 * 
	 * @author naq
	 * @param iOnMenuClosed
	 */
	public void setDelegateOnMenuClosed(IOnMenuClosed iOnMenuClosed) {
		this.iOnMenuClosed = iOnMenuClosed;

	}

	public void setUptitle(String title) {
		tvTitle.setText("" + title);
	}

}
