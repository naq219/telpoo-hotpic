package com.telpoo.hotpic.parsehtml;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.telpoo.frame.net.BaseNetSupportBeta;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.hotpic.object.MenuOj;
import com.telpoo.hotpic.object.MyObject;
import com.telpoo.hotpic.object.PicOj;
import com.telpoo.hotpic.utils.Constant;

public class HdWallParse {

	public static final String TAG_CHANDAI = "PARSE_wall";
	
	public static ArrayList<BaseObject> Parse1(BaseObject oj) throws JSONException  {
		int page = oj.getInt(MyObject.PAGE);
		long dem= page*10;
		String key= "SELECT * FROM `picture` WHERE `category`='"+oj.get(MenuOj.CATEGORY)+"' limit "+dem+",10";
		String url="http://naq.name.vn/f/API/hotpicindian/naq.php?key="+key;
		String res= BaseNetSupportBeta.getInstance().method_GET(url);
		if(res==null) return null;
		
		
		JSONArray array=new JSONArray(res);
		ArrayList<BaseObject> arRe=new ArrayList<BaseObject>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject joj= array.getJSONObject(i);
			BaseObject oj1=new BaseObject();
			for (String key1 : PicOj.keys) {
				if(joj.has(key1)) oj1.set(key1, joj.getString(key1));
			}
			
			oj1.set(PicOj.GROUP_ID, Constant.GroupSource.GROUP_HDWALL);
			
			arRe.add(oj1);
			
		}
		
		
		return arRe;
		
		
	}
	
	
	public static String parseUrlDetail(String url){
		return url;
	}
	

}
