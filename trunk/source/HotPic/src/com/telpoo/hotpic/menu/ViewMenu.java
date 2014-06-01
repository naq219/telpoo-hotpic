package com.telpoo.hotpic.menu;

/**
 * @author naq
 * day la class cua phan menu
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.telpoo.frame.delegate.Idelegate;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.hotpic.R;
import com.telpoo.hotpic.adapter.ExLvAdapter;
import com.telpoo.hotpic.db.DbSupport;
import com.telpoo.hotpic.db.TableDb;
import com.telpoo.hotpic.delegate.IOnMenuClosed;
import com.telpoo.hotpic.home.HomeActivity;
import com.telpoo.hotpic.home.TabId;
import com.telpoo.hotpic.object.MenuOj;
import com.telpoo.hotpic.staggeredgridviewui.StaggeredGridViewFragment;

public class ViewMenu implements IOnMenuClosed {
	Context context;
	Idelegate idelegate;
	View v;
	ExpandableListAdapter adapter;
	ExpandableListView elv;
	String urlSrc;
	DisplayImageOptions displayImageOptions1;

	public ViewMenu(Context context1, Idelegate idelegate1) {
		this.context = context1;
		this.idelegate = idelegate1;

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		v = inflater.inflate(R.layout.layout_menu, null);

		elv = (ExpandableListView) v.findViewById(R.id.elv);
		
		IOnMenuClosed abc= new IOnMenuClosed() {
			
			@Override
			public void onMenuClosed() {
				// TODO Auto-generated method stub
				
			}
		};
		
		((HomeActivity)context1).setDelegateOnMenuClosed(this);

	}

	// truyen du lieu cua menu vao o day
	public void setDataExpanableLv(List<String> listDataHeader, HashMap<String, List<BaseObject>> listChildData) {
		adapter = new ExLvAdapter(context, listDataHeader, listChildData);
		elv.setAdapter(adapter);
		Log.d("adaptersize", adapter.getGroupCount() + "");

	}

	public void LoadData() {
		List<String> listDataHeader = new ArrayList<String>();
		HashMap<String, List<BaseObject>> listChildData = new HashMap<String, List<BaseObject>>();
		ArrayList<BaseObject> menuObjects = new ArrayList<BaseObject>();
		menuObjects = DbSupport.getAllOfTable(TableDb.TABLE_VIEW_MENU);
		Log.d("test", menuObjects.size() + "");
		//
		// get listdataheader
		listDataHeader.add(menuObjects.get(0).get(MenuOj.GROUP_NAME));
		for (int i = 1; i < menuObjects.size(); i++) {
			if (!listDataHeader.contains(menuObjects.get(i).get(MenuOj.GROUP_NAME))) {
				listDataHeader.add(menuObjects.get(i).get(MenuOj.GROUP_NAME));
			}
		}
		//
		// get listchildData
		int position = -1;
		for (int i = 0; i < listDataHeader.size(); i++) {
			List<BaseObject> childList = new ArrayList<BaseObject>();
			for (int j = position + 1; j < menuObjects.size(); j++) {
				if (listDataHeader.get(i).equals(menuObjects.get(j).get(MenuOj.GROUP_NAME))) {
					childList.add(menuObjects.get(j));
					position = j;
				} else
					break;
			}
			listChildData.put(listDataHeader.get(i), childList);
			Log.d("testchild", childList.size() + "");
		}
		//
		// set adapter view menu
		setDataExpanableLv(listDataHeader, listChildData);
	}

	// set indicator group expandlistview in right
	@SuppressLint("NewApi")
	public void setIndicatorGroupELV() {
		// get width of v
		int width = v.getWidth();
		// set indicatorGroupWidth
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
			elv.setIndicatorBounds(width - (int) (50 * v.getResources().getDisplayMetrics().density + 0.5f), width
					- (int) (10 * v.getResources().getDisplayMetrics().density + 0.5f));
		} else {
			elv.setIndicatorBoundsRelative(width - (int) (50 * v.getResources().getDisplayMetrics().density + 0.5f), width
					- (int) (10 * v.getResources().getDisplayMetrics().density + 0.5f));
		}
	}

	public void setClickItemExpandLV(final FragmentManager fragmentManager, final int LayoutId,
			final DisplayImageOptions displayImageOptions) {
		elv.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

				urlSrc = ((BaseObject) adapter.getChild(groupPosition, childPosition)).get(MenuOj.URL);
				displayImageOptions1 = displayImageOptions;
				HomeActivity.getInstance().toggle();

				return true;
			}
		});

	}

	public View getView() {

		return v;
	}

	@Override
	public void onMenuClosed() {
		StaggeredGridViewFragment fragment = new StaggeredGridViewFragment();
		fragment.setSrcUrl(urlSrc);
		fragment.setDisplayImageOptions(displayImageOptions1);
		// fragmentManager.beginTransaction().replace( LayoutId,
		// fragment).addToBackStack(null).commit();

		HomeActivity.getInstance().pushFragments(TabId.home, fragment, true, null);

	}

}
