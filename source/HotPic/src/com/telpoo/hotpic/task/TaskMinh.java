package com.telpoo.hotpic.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.telpoo.frame.model.BaseModel;
import com.telpoo.frame.model.BaseTask;
import com.telpoo.frame.model.TaskListener;
import com.telpoo.frame.model.TaskParams;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.hotpic.db.DbSupport;
import com.telpoo.hotpic.net.Netsupport;
import com.telpoo.hotpic.parsehtml.DepvdParse;

public class TaskMinh extends BaseTask implements TaskType{

	
	public TaskMinh(BaseModel baseModel, int taskType, ArrayList<?> list, Context context) {
		super(baseModel, taskType, list, context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Boolean doInBackground(TaskParams... params) {
		
		switch (taskType) {
		case TASK_UPDATE_MENU:
			
			
			// get data
			try {
				//parse json
				ArrayList<BaseObject> jsonArrayList = Netsupport.getMenu();
				//save database

				DbSupport.updateMenu(jsonArrayList);	
				
				// return data;
				dataReturn = jsonArrayList;
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				msg="no tasktype";
				return TASK_FAILED;
			}
			return TASK_DONE;
		case TASK_GET_METRO:
			//
			try {
				LinkedHashMap<String, ArrayList<String>> metroReturn = Netsupport.getMetroView(0);
				Log.d("testmetrojson", metroReturn.size()+"");
				//List<E>
				ArrayList<LinkedHashMap<String, ArrayList<String>>> arrayList = new ArrayList<LinkedHashMap<String,ArrayList<String>>>();
				arrayList.add(metroReturn);
				dataReturn = arrayList;
				Log.d("testmetrojson", dataReturn.size()+"");
				//return TASK_DONE;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return TASK_FAILED;
			}
			return TASK_DONE;
		default:
			break;
		}
		
		
		msg="no tasktype";
		return TASK_FAILED;
		
	}

}
