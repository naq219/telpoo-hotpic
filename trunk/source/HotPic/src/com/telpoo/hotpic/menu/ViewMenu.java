package com.telpoo.hotpic.menu;

/**
 * @author naq
 * day la class cua phan menu
 */
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.telpoo.frame.delegate.Idelegate;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.hotpic.R;
import com.telpoo.hotpic.adapter.ExLvAdapter;

public class ViewMenu {
	Context context;
	Idelegate idelegate;
	View v;
	ExpandableListAdapter adapter;
	ExpandableListView elv;

	public ViewMenu(Context context1, Idelegate idelegate1) {
		this.context = context1;
		this.idelegate = idelegate1;

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		v = inflater.inflate(R.layout.layout_menu, null);
		
		elv=(ExpandableListView) v.findViewById(R.id.elv);
		
		
	}
	
	//truyen du lieu cua menu vao o day
	public void setDataExpanableLv( List<String> listDataHeader, HashMap<String, List<BaseObject>> listChildData){
		adapter=new ExLvAdapter(context, listDataHeader, listChildData);
		elv.setAdapter(adapter);
	}

	public View getView() {

		return v;
	}

}
