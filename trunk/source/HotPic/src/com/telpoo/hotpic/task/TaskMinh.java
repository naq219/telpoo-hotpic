package com.telpoo.hotpic.task;

import java.util.ArrayList;

import android.content.Context;

import com.telpoo.frame.model.BaseTask;
import com.telpoo.frame.model.TaskListener;
import com.telpoo.frame.model.TaskParams;

public class TaskMinh extends BaseTask implements TaskType{

	public TaskMinh(TaskListener taskListener, int taskType, ArrayList<?> list,
			Context context) {
		super(taskListener, taskType, list, context);
	}
	
	@Override
	protected Boolean doInBackground(TaskParams... params) {
		
		switch (taskType) {
		case TASK_UPDATE_MENU:
			
			
			// get data
			
			//parse json
			
			//save database
			
			// return data;
			break;

		default:
			break;
		}
		
		
		msg="no tasktype";
		return TASK_FAILED;
		
	}

}
