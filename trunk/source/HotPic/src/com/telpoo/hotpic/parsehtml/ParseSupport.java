package com.telpoo.hotpic.parsehtml;

import java.io.IOException;
import java.util.ArrayList;

import com.telpoo.frame.object.BaseObject;
import com.telpoo.hotpic.object.MenuOj;
import com.telpoo.hotpic.utils.Constant;

public class ParseSupport {

	public static ArrayList<BaseObject> parse(BaseObject oj) throws IOException {
		
		int groupId = oj.getInt(MenuOj.GROUP_ID);

		switch (groupId) {
		case Constant.GroupSource.GROUP_CHANDAITV:  // cắt trang chandai.tv
			return ChanDaiParse.Parse(oj);

		default:
			break;
		}

		return null;

	}

}
