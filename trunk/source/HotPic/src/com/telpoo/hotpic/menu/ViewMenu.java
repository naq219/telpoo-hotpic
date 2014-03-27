package com.telpoo.hotpic.menu;

/**
 * @author naq
 * day la class cua phan menu
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.telpoo.frame.delegate.Idelegate;
import com.telpoo.hotpic.R;

public class ViewMenu {
	Context context;
	Idelegate idelegate;
	View v;

	public ViewMenu(Context context1, Idelegate idelegate1) {
		this.context = context1;
		this.idelegate = idelegate1;

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		v = inflater.inflate(R.layout.layout_menu, null);

	}

	public View getView() {

		return v;
	}

}
