package com.telpoo.hotpic.home;

import java.util.ArrayList;

import com.telpoo.hotpic.task.TaskMinh;
import com.telpoo.hotpic.task.TaskType;

import android.os.Bundle;

public class HomeActivity extends MyHomeActivity implements TaskType {
	TaskMinh taskMinh;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		taskMinh = new TaskMinh(model, TASK_UPDATE_MENU, null, this);

	}
	
	@Override
	public void onSuccess(int taskType, ArrayList<?> list, String msg) {
		super.onSuccess(taskType, list, msg);
		
		switch (taskType) {
		case TASK_UPDATE_MENU:
			
			//update vao menu 
			break;

		default:
			break;
		}
	}

}
