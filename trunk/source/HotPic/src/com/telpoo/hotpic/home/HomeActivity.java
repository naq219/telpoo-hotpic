package com.telpoo.hotpic.home;

import java.util.ArrayList;

import android.os.Bundle;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnClosedListener;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.anhnong.hotgirl.R;
import com.telpoo.hotpic.db.DbSupport;
import com.telpoo.hotpic.delegate.IOnMenuClosed;
import com.telpoo.hotpic.menu.ViewMenu;
import com.telpoo.hotpic.object.MenuOj;
import com.telpoo.hotpic.object.PicOj;
import com.telpoo.hotpic.task.TaskMinh;
import com.telpoo.hotpic.task.TaskType;

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
		viewMenu = new ViewMenu(HomeActivity.this, this);
		super.onCreate(savedInstanceState);
		me = HomeActivity.this;
		DbSupport.init(getBaseContext());
		taskMinh = new TaskMinh(model, TASK_UPDATE_MENU, null, this);
		model.exeTask(null, taskMinh);

		getSlidingMenu().setOnClosedListener(new OnClosedListener() {

			@Override
			public void onClosed() {

				iOnMenuClosed.onMenuClosed();

			}
		});

		// load list anh mac dinh
		loadDefault();

	}

	private void loadDefault() {

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

}
