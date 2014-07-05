package com.telpoo.hotpic.task;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;

import com.telpoo.frame.model.BaseTask;
import com.telpoo.frame.model.TaskListener;
import com.telpoo.frame.model.TaskParams;
import com.telpoo.frame.net.BaseNetSupportBeta;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.anhnong.hotgirl.R;
import com.telpoo.hotpic.parsehtml.ParseSupport;

public class TaskNaq extends BaseTask implements TaskType {

	public TaskNaq(TaskListener taskListener, int taskType, ArrayList<?> list, Context context) {
		super(taskListener, taskType, list, context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Boolean doInBackground(TaskParams... params) {

		switch (taskType) {
		case TASK_GET_LIST_IMAGE_LOADMORE:
		case TASK_GET_LIST_IMAGE:
			if (!BaseNetSupportBeta.isNetworkAvailable(context)) {
				msg = context.getString(R.string.no_network);
				return TASK_FAILED;
			}
			
			//object của 1 row trong menu  // bat dau tu day
			BaseObject ojList = (BaseObject) dataFromModel.get(0);

			ArrayList<BaseObject> ojStrageList = null;
			try {
				ojStrageList = ParseSupport.parse(ojList);  
			} catch (IOException e) {
				msg = context.getString(R.string.khong_co_anh_nao);
				return TASK_FAILED;
			}

			

			dataReturn = ojStrageList;
			return TASK_DONE;


		default:
			break;
		}

		return super.doInBackground(params);
	}

}
