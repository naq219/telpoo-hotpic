package com.telpoo.hotpic.db;

import java.util.ArrayList;

import com.telpoo.frame.object.BaseObject;
import com.telpoo.hotpic.object.MenuOj;
import com.telpoo.hotpic.object.PicOj;

import android.content.Context;
import android.widget.Toast;

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
	
	public static boolean addFabvorite(BaseObject oj,Context ct){
		ArrayList<BaseObject> ojs=new ArrayList<BaseObject>();
		ojs.add(oj);
		boolean status=Mydb.addToTable(ojs, TableDb.TABLE_FAVORITE);
		if(!status){
			Mydb.deleteRowInTable(TableDb.TABLE_FAVORITE, PicOj.URL_THUMBNAIL, oj.get(PicOj.URL_THUMBNAIL));
		}
		
		return status;
		
	}
	
	public static ArrayList<BaseObject> getFabvorite(){
		return Mydb.getAllOfTable(TableDb.TABLE_FAVORITE, PicOj.keys);
	}
	

}
