package com.telpoo.hotpic.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.telpoo.frame.object.BaseObject;
import com.telpoo.hotpic.object.AlbulmOj;

public class Data {

	public static final ArrayList<String> listWebsite() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("chandai.tv");
		list.add("tamtay.vn");
		return list;
	}

	public static final HashMap<String, List<BaseObject>> listMenu() {

		HashMap<String, List<BaseObject>> listChildData = new HashMap<String, List<BaseObject>>();
		List<BaseObject> l1 = new ArrayList<BaseObject>();
		BaseObject oj1 = new BaseObject();
		oj1.set(AlbulmOj.NAME, "anh dep");
		oj1.set(AlbulmOj.URL, "anh xau");
		l1.add(oj1);
		
		oj1.set(AlbulmOj.NAME, "anh dep");
		oj1.set(AlbulmOj.URL, "anh xau");
		l1.add(oj1);
		
		listChildData.put("chandai.tv", l1);
		
		List<BaseObject> l2 = new ArrayList<BaseObject>();
		BaseObject oj2 = new BaseObject();
		oj2.set(AlbulmOj.NAME, "anh dep");
		oj2.set(AlbulmOj.URL, "anh xau");
		l2.add(oj2);
		
		oj2.set(AlbulmOj.NAME, "anh dep");
		oj2.set(AlbulmOj.URL, "anh xau");
		l2.add(oj2);
		
		listChildData.put("tamtay.vn", l2);
		
		return listChildData;
		
		
	}
}
