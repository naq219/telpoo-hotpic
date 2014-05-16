package com.telpoo.hotpic.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.telpoo.frame.net.BaseNetSupportBeta;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.hotpic.object.MenuOj;

public class Netsupport {
	
	public static ArrayList<BaseObject> getMenu() throws JSONException{
		String res= BaseNetSupportBeta.getInstance().method_GET("http://naq.name.vn/f/menu.json");
		ArrayList<BaseObject> ojs=new ArrayList<BaseObject>(); // mang nay se co 6 phan tu
		// root cua no la 1 array
		JSONArray arRoot= new JSONArray(res);
		
		for (int i = 0; i < arRoot.length(); i++) 
		{ // lay het cac phan tu ben trong
			JSONObject joj= arRoot.getJSONObject(i);// vi phan tu con la json object , o day co 2 object. ok?da
			String groupName= joj.getString(MenuOj.GROUP_NAME); // hieu cho nay ko ?hieu no se lay group_name trong json object (y)
			String groupId= joj.getString(MenuOj.GROUP_ID);
			
			// ben trong object lai co 1 item chua 1 array :D , item nay la 1 array  vi ben trong no la  []
			
			JSONArray arItem= joj.getJSONArray("item");
			for (int j = 0; j <arItem.length(); j++) 
			{ // lay 3 item ben trong
				
				// khai bao object de add vao joj
				BaseObject oj =new BaseObject();
				
			//	oj.set(MenuOj.NAME, arItem.getJSONObject(i).getString(MenuOj.NAME)); // hieu cho nay ko ?hieu .set value cua oj tai key la name voi gia tri la phan tu name cua oject json thu [i] trong aritem. ok
				//oj.set(MenuOj.CATEGORY, arItem.getJSONObject(i).getString(MenuOj.CATEGORY));
				//.... tuong tu, nhung neu lam nhu the nay nhe . vi du sau nay anh muon them 1 truong nua  thi minh lai phai sua code o day :D
				
				for (String key : MenuOj.keys) 
				{
					if(arItem.getJSONObject(j).has(key))  //ok?// em ko hieu cho nay lam cho has key. neu co gia trij cua key moi set.ah em hieu
						oj.set(key, arItem.getJSONObject(j).getString(key)); //ok?da. dc// chua duoc :D neu 
					//o day co nhung key ma trong json ko co , code se bi loi
					Log.d("testjson", oj.get(key)+"");
				}
				// lay them 2 cai key phia tren con thieu nua 
				oj.set(MenuOj.GROUP_ID, groupId);
				oj.set(MenuOj.GROUP_NAME, groupName); // ok ?da dc				
				ojs.add(oj);
				
			}
			
		}
		
		return ojs;		
	}
	public static LinkedHashMap<String, ArrayList<String>> getMetroView(int positionGet) throws JSONException
	{
		String res = BaseNetSupportBeta.getInstance().method_GET("http://naq.name.vn/f/minh.json");
		//Link<String, ArrayList<String> > hashMapDes = new HashMap<String, ArrayList<String>>();
		LinkedHashMap<String, ArrayList<String>> hashMapDes = new LinkedHashMap<String, ArrayList<String>>();
		JSONArray jsonArray = new JSONArray(res);
		JSONObject  objectRootJson = jsonArray.getJSONObject(positionGet);
//		String groupName = "";
//		groupName = objectRootJson.getString("name");
		JSONArray itemList  = objectRootJson.getJSONArray("item");
		for(int j =0; j < itemList.length(); j++)
		{
			String name = "";
			ArrayList<String> urlList = new ArrayList<String>();
			JSONObject itemObject = itemList.getJSONObject(j);
			name = itemObject.getString("name");
			JSONArray urlJsonArray = itemObject.getJSONArray("url");
			for(int count = 0; count < urlJsonArray.length(); count++)
			{
				JSONObject itemJoj = urlJsonArray.getJSONObject(count);
				urlList.add(itemJoj.getString("urlimage"));
			}
			Log.d("mmetroname", name);
			hashMapDes.put(name, urlList);
		}
		for(String key: hashMapDes.keySet())
		{
			Log.d("mmetroname", key);
		}
		return hashMapDes;
	}

}
