package com.telpoo.hotpic.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.telpoo.frame.model.BaseTask;
import com.telpoo.frame.model.TaskListener;
import com.telpoo.frame.model.TaskParams;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.hotpic.db.DbSupport;
import com.telpoo.hotpic.db.TableDb;
import com.telpoo.hotpic.net.Netsupport;

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
			try {
				//parse json
				ArrayList<BaseObject> jsonArrayList = Netsupport.getMenu();
				Log.d("testjsonsize", jsonArrayList.size()+"" );
				//save database
				DbSupport.removeTable(TableDb.TABLE_VIEW_MENU, context);
				DbSupport.AddData(jsonArrayList, TableDb.TABLE_VIEW_MENU);
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
