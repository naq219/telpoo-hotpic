package com.telpoo.hotpic.parsehtml;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;

import com.telpoo.frame.object.BaseObject;
import com.telpoo.hotpic.object.MenuOj;
import com.telpoo.hotpic.object.PicOj;
import com.telpoo.hotpic.utils.Constant;

public class ParseSupport {

	public static ArrayList<BaseObject> parse(BaseObject oj) throws IOException {
		ArrayList<BaseObject> res = null;
		int groupId = oj.getInt(MenuOj.GROUP_ID);
		
		
		switch (groupId) {
		case Constant.GroupSource.GROUP_CHANDAITV:  // cắt trang chandai.tv
			 res = ChanDaiParse.Parse(oj);

		default:
			break;
		}
		

		return res;

	}
	
	
	public static String parseUrlDetail(BaseObject oj,Context context){
		int groupId = oj.getInt(MenuOj.GROUP_ID);
		switch (groupId) {
		case Constant.GroupSource.GROUP_CHANDAITV:  // cắt trang chandai.tv
			return ChanDaiParse.parseUrlImg(oj.get(PicOj.URL));

		default:
			break;
		}

		return null;
	}

}
