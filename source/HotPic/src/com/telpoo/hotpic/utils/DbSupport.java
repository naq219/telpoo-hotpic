package com.telpoo.hotpic.utils;
//package com.telpoo.hotpic.db;
//
//import java.util.ArrayList;
//
//import android.content.Context;
//
//import com.telpoo.frame.database.BaseDBSupport;
//import com.telpoo.frame.object.BaseObject;
//import com.telpoo.hotpic.object.MenuOj;
//
//public class DbSupport extends BaseDBSupport{
//
//	
//	protected DbSupport(Context context) {
//		super(context);
//		// TODO Auto-generated constructor stub
//		
//	}
////	public static DbSupport getInstance(Context context)
////	{
////		return DbSupport.getInstance(context);
////	}
//	public static boolean initDB(Context context)
//	{
//		boolean intit =  init(TableDb.TABLE_NAMES, TableDb.keys, context, TableDb.DB_NAME, 1);		
//		return intit;
//	}
//	
//	public static boolean AddData(ArrayList<BaseObject> arrBaseObject, String tableName )
//	{
//		boolean check = false ;
//		if( arrBaseObject != null )
//		{
//			//openDB();
//			check =  DbSupport.addListNoCheck(arrBaseObject, tableName);
//		}
//		
//		
//		//closeDB();
//		return check; 
//	}	
//	public static  boolean removeTable(String tableName, Context context)
//	{
//		
//		openDB();
//		DbSupport.getInstance(context).getSQLiteDatabase().execSQL("delete from "+ tableName);
//		if(getAllOfTable(tableName) == null)
//		{
//			closeDB();
//			return true;
//		}
//		closeDB();
//		return false;
//	}
//	public static ArrayList<BaseObject> getAllOfTable(String tableName)
//	{
//		return getAllOfTable(tableName, MenuOj.keys);
//	}
//}
