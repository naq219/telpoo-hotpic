package com.telpoo.hotpic.db;

import java.util.ArrayList;

import com.telpoo.frame.object.BaseObject;
import com.telpoo.hotpic.object.MenuOj;

import android.content.Context;

public class DbSupport {

	public static void init(Context context) {

		Mydb.init(TableDb.TABLES, TableDb.keys, context, TableDb.pathDbName, 2);
	}
	
	public static void updateMenu(ArrayList<BaseObject> ojs){
		if(ojs==null||ojs.size()==0) return;
		Mydb.removeAllInTable(TableDb.TABLE_VIEW_MENU);
		Mydb.addToTable(ojs, TableDb.TABLE_VIEW_MENU);
	}
	
	public static ArrayList<BaseObject> getMenu(){
		return Mydb.getAllOfTable(TableDb.TABLE_VIEW_MENU, MenuOj.keys);
	}
	
	
	

}
