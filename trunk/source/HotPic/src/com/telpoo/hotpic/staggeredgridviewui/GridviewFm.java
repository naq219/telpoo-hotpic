package com.telpoo.hotpic.staggeredgridviewui;

/**
 * Fragment chứa listivew hình ảnh.
 * @author naq
 */

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.telpoo.anhnong.hotgirl.R;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.frame.utils.Mlog;
import com.telpoo.hotpic.adapter.HotStaggeredGridViewAdapter;
import com.telpoo.hotpic.detail.DetailFm;
import com.telpoo.hotpic.home.HomeActivity;
import com.telpoo.hotpic.home.TabId;
import com.telpoo.hotpic.object.MenuOj;
import com.telpoo.hotpic.object.MyObject;
import com.telpoo.hotpic.object.PicOj;
import com.telpoo.hotpic.task.TaskNaq;
import com.telpoo.hotpic.task.TaskType;
import com.telpoo.hotpic.utils.Constant;

public class GridviewFm extends GridviewFmLayout implements TaskType {
	BaseObject ojToParse;
	HotStaggeredGridViewAdapter adapter;
	boolean isLoadingMore = false;
	int page = 0; // tra da load

	@SuppressLint("NewApi")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		String groupName=ojToParse.get(MenuOj.GROUP_NAME);
		String name=ojToParse.get(MenuOj.NAME);
		
		if(name!=null&&groupName!=null)
		HomeActivity.getInstance().setUptitle(groupName+"-"+name);

		runTaskGetImage(ojToParse);
		
		adapter = new HotStaggeredGridViewAdapter(getActivity(), R.layout.image_item_grid, new ArrayList<BaseObject>());
		gridView.setAdapter(adapter);

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				BaseObject oj = (BaseObject) arg0.getAdapter().getItem(position);

				int typeCut = oj.getInt(PicOj.TYPE_CUT);
				if (typeCut == Constant.TYPE_CUT_ALBULM) { // albulm oj , tiep
															// tuc parse
					// chuyen sang cat picture
					oj.set(PicOj.TYPE_CUT, Constant.TYPE_CUT_PICTURE);

					GridviewFm gridviewFm = new GridviewFm();
					gridviewFm.setData(oj);
					HomeActivity.getInstance().pushFragments(TabId.home, gridviewFm, true, null);

				} else { // picoj
					DetailFm fm = new DetailFm();
					fm.setData(adapter.getAll(), position);
					HomeActivity.getInstance().pushFragments(TabId.home, fm, true, null);
				}

			}
		});

		gridView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

				if (totalItemCount - firstVisibleItem - visibleItemCount < 1) {
					if (!isLoadingMore) { // khong co task loadmore nao ca
						runTaskLoadMore(ojToParse);
					}
				}

			}
		});

	}

	protected void runTaskLoadMore(BaseObject oj2) {
		isLoadingMore = true;
		loadMore.setVisibility(View.VISIBLE);
		page++;
		oj2.set(MyObject.PAGE, page); // truyen them page vao object
		ArrayList<BaseObject> list = new ArrayList<BaseObject>();
		list.add(oj2);

		TaskNaq taskNaq = new TaskNaq(getModel(), TASK_GET_LIST_IMAGE_LOADMORE, list, getActivity());
		getModel().exeTask(null, taskNaq);

	}

	private void runTaskGetImage(BaseObject oj2) {
		page = 1;
		oj2.set(MyObject.PAGE, 1); // chuyen ve trang dau tien
		ArrayList<BaseObject> list = new ArrayList<BaseObject>();

		list.add(oj2);

		showProgress();
		TaskNaq taskNaq = new TaskNaq(getModel(), TASK_GET_LIST_IMAGE, list, getActivity());
		getModel().exeTask(null, taskNaq);

	}

	/**
	 * Truyền MenuOj vào để lấy link cắt
	 * 
	 * @param oj
	 */
	public void setData(BaseObject oj) {
		this.ojToParse = oj;

	}

	@Override
	public void onSuccess(int taskType, ArrayList<?> list, String msg) {
		super.onSuccess(taskType, list, msg);

		switch (taskType) {
		case TASK_GET_LIST_IMAGE:
			closeProgress();
			ArrayList<BaseObject> ojsRes = (ArrayList<BaseObject>) list;
			adapter.setData(ojsRes);
			adapter.notifyDataSetChanged();

			break;

		case TASK_GET_LIST_IMAGE_LOADMORE:
			isLoadingMore = false;

			loadMore.setVisibility(View.GONE);
			Mlog.T("list.size()=" + list.size());
			if (list.size() == 0) {
				isLoadingMore = true; // khong con anh de loadmore nua
				return;
			}
			adapter.Adds((ArrayList<BaseObject>) list);
			adapter.notifyDataSetChanged();
			break;

		default:
			break;
		}
	}

	@Override
	public void onFail(int taskType, String msg) {
		super.onFail(taskType, msg);

		switch (taskType) {
		case TASK_GET_LIST_IMAGE:
			closeProgress();
			showToast("" + msg);
			break;

		case TASK_GET_LIST_IMAGE_LOADMORE:
			isLoadingMore = false;
			loadMore.setVisibility(View.GONE);
			showToast("" + msg);
			page--;
			break;

		default:
			break;
		}

	}

}
