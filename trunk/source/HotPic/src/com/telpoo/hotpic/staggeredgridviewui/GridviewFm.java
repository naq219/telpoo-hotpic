package com.telpoo.hotpic.staggeredgridviewui;
/**
 * Fragment chứa listivew hình ảnh.
 * @author naq
 */

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.telpoo.frame.object.BaseObject;
import com.telpoo.hotpic.R;
import com.telpoo.hotpic.adapter.HotStaggeredGridViewAdapter;
import com.telpoo.hotpic.detail.DetailFm;
import com.telpoo.hotpic.home.HomeActivity;
import com.telpoo.hotpic.home.TabId;
import com.telpoo.hotpic.task.TaskNaq;
import com.telpoo.hotpic.task.TaskType;

public class GridviewFm extends GridviewFmLayout implements TaskType {
	BaseObject oj;
	HotStaggeredGridViewAdapter adapter;

	@SuppressLint("NewApi")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		ArrayList<BaseObject> list = new ArrayList<BaseObject>();
		list.add(oj);
		TaskNaq taskNaq = new TaskNaq(getModel(), TASK_GET_LIST_IMAGE, list, getActivity());
		getModel().exeTask(null, taskNaq);
		adapter= new HotStaggeredGridViewAdapter(getActivity()	, R.layout.image_item_grid, new ArrayList<BaseObject>());
		staggeredGridView.setAdapter(adapter);
		
		staggeredGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				DetailFm fm = new DetailFm();
				fm.setData(adapter.getAll());
				HomeActivity.getInstance().pushFragments(TabId.home, fm, true, null);
				
			}
		});
	}

	/**
	 * Truyền MenuOj vào để lấy link cắt
	 * @param oj
	 */
	public void setData(BaseObject oj) {
		this.oj = oj;
	}
	
	@Override
	public void onSuccess(int taskType, ArrayList<?> list, String msg) {
		super.onSuccess(taskType, list, msg);
		
		switch (taskType) {
		case TASK_GET_LIST_IMAGE:
			
			ArrayList<BaseObject> ojsRes=(ArrayList<BaseObject>) list;
			adapter.Adds(ojsRes);
			adapter.notifyDataSetChanged();
			
			break;

		default:
			break;
		}
	}
	
	@Override
	public void onFail(int taskType, String msg) {
		super.onFail(taskType, msg);
		
		
	}

}
