package com.telpoo.hotpic.db;
import android.os.Environment;

import com.telpoo.hotpic.object.MenuOj;

public class TableDb {
	
	public static String DB_NAME = "dbHotPic.sqlite";
	public static String pathDbName="/sdcard/+.sqlitedbHotPic.sqlite";
	//		
	//public static String[] keystable ={MenuOj.keys}; 
	public static String TABLE_NAMES[] = {"viewMenu"};	
	public static String[][] keys = {MenuOj.keys_db};
	//
	public static String TABLE_VIEW_MENU = TABLE_NAMES[0];
	

}
