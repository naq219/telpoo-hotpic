package com.telpoo.hotpic.db;
import android.os.Environment;

import com.telpoo.hotpic.object.MenuOj;
import com.telpoo.hotpic.object.PicOj;

public class TableDb {
	
	//public static String DB_NAME = "dbHotPic.sqlite";
	public static String pathDbName="hotpic.sqlite";
	//		
	public static String TABLES[] = {"viewMenu","favorite"};	
	public static String[][] keys = {MenuOj.keys,PicOj.keys};
	//
	public static String TABLE_VIEW_MENU = TABLES[0];
	public static String TABLE_FAVORITE= TABLES[1];
	

}
