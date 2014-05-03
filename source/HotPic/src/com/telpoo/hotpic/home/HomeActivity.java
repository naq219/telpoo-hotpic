package com.telpoo.hotpic.home;

import java.util.ArrayList;

import com.telpoo.frame.delegate.Idelegate;
import com.telpoo.hotpic.db.DbSupport;
import com.telpoo.hotpic.menu.ViewMenu;
import com.telpoo.hotpic.task.TaskMinh;
import com.telpoo.hotpic.task.TaskType;

import android.os.Bundle;

public class HomeActivity extends MyHomeActivity implements TaskType {
	TaskMinh taskMinh;
	//ViewMenu viewMenu;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//viewMenu = new ViewMenu(getBaseContext(), this);// cai nay lam dung ko anh sao em show ra ko dc
		DbSupport.initDB(this);
		taskMinh = new TaskMinh(model, TASK_UPDATE_MENU, null, this);
		model.exeTask(null, taskMinh);
		
	}
	
	@Override
	public void onSuccess(int taskType, ArrayList<?> list, String msg) {
		super.onSuccess(taskType, list, msg);
		
		switch (taskType) {
		case TASK_UPDATE_MENU:
			
			//update vao menu 
			viewMenu.LoadData();			
			viewMenu.setIndicatorGroupELV();  // gi day em?set cai icon ben phai ma an di da
			// icon ben phai??? da. icon cua cai group cua expandlistview @@
			
			// ok roi do sao vay?
			// roi do 
			
			
			break;

		default:
			break;
		}
	}

}
