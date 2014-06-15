package com.telpoo.hotpic.parsehtml;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

import com.telpoo.frame.object.BaseObject;
import com.telpoo.frame.utils.Mlog;
import com.telpoo.hotpic.object.AlbulmOj;
import com.telpoo.hotpic.object.MenuOj;
import com.telpoo.hotpic.object.MyObject;
import com.telpoo.hotpic.object.PicOj;
import com.telpoo.hotpic.utils.Constant;

public class ChanDaiParse {

	public static final String urlPath = "http://chandai.tv";
	public static final String TAG_CHANDAI = "PARSE_CHANDAI";

	public static ArrayList<BaseObject> Parse(BaseObject oj) throws IOException {
		int typeCut = oj.getInt(MenuOj.TYPE_CUT);
		String url = oj.get(MenuOj.URL);

		if (typeCut == Constant.TYPE_CUT_ALBULM) { // cắt lấy ra albulm

			return getAlbulmOjList(url);
		}

		if (typeCut == Constant.TYPE_CUT_PICTURE) {
			return getPicList(url);
		}

		else
			return null;
	}

	public static ArrayList<BaseObject> getAlbulmOjList(String url) throws IOException {
		// add a progress bar de bat buoc user phai cho de load du lieu
		// add demo
		//
		ArrayList<BaseObject> albulmOjs = new ArrayList<BaseObject>();
		// to do code
		String html = "";
		// cai nay chay dat vao asynctask ma khi nay dat roi
		html = Jsoup.connect(url).get().toString();
		Document document = null;
		// neu html co noi dung
		if (!html.equals("")) {
			// parse noi dung html vao doccument
			document = Jsoup.parse(html);
			// cat cac the <a trong html
			Elements elementsList = document.select("a"); // chay den day hinh
															// nhu ok, de anh
															// debug cho xem
			for (Element itemRoot : elementsList) {
				String linkThumbnail = "", title = "", linkWeb = "", countSpan = "";
				Elements imgAndTitle = itemRoot.select("img");

				if (imgAndTitle != null) // sao o day em check null trong khi ko
											// co du lieu thi no van la "[]"
				{
					// -------------------------------------IMG-THUMBNAIL &
					// TITLE-----------------------------------------------------------
					String tempImg = imgAndTitle.attr("src");
					if (tempImg != null && !tempImg.equals("")) {
						linkThumbnail = urlPath + tempImg;
					}
					String tempTitle = imgAndTitle.attr("alt");
					if (tempTitle != null && !tempTitle.equals("")) {
						title = tempTitle;
					}
					// --------------------------------------------------------------------------------------------------------
					// ---------------------------------------------LINKWEB------------------------------------------------------
					String templinkWeb = itemRoot.attr("href");
					if (templinkWeb != null)
						linkWeb = urlPath + templinkWeb;
					// --------------------------------------------------------------------------------------------------------
					// ---------------------------------------------SpanCount------------------------------------------------------
					Elements spanCount = itemRoot.select("span");
					String tempSpan = spanCount.text();
					if (tempSpan != null) {
						countSpan = tempSpan;
					}
					// -------------------------------------------------------------------------------------------------------
					if (!linkThumbnail.equals("") && !linkWeb.equals("")) {
						AlbulmOj albulmOj = new AlbulmOj();
						albulmOj.set(AlbulmOj.TYPE_CUT, Constant.TYPE_CUT_ALBULM);
						albulmOj.set(MenuOj.GROUP_ID, Constant.GroupSource.GROUP_CHANDAITV);
						albulmOj.set(AlbulmOj.URL_THUMBNAIL, linkThumbnail);
						albulmOj.set(AlbulmOj.URL, linkWeb);
						albulmOj.set(AlbulmOj.NAME, title);
						albulmOj.set(AlbulmOj.COUNT, countSpan);
						albulmOj.set(AlbulmOj.COUNT, countSpan);
						albulmOj.set(AlbulmOj.TYPE_CUT, Constant.TYPE_CUT_ALBULM);
						albulmOjs.add(albulmOj);
					} else
						continue;

				} else
					continue;

			}
		}
		return albulmOjs;
	}

	public static ArrayList<BaseObject> getPicList(String url) throws IOException {
		ArrayList<BaseObject> res = getAlbulmOjList(url);

		ArrayList<BaseObject> ojs = new ArrayList<BaseObject>();

		for (BaseObject baseObject : res) {

			PicOj picOj = new PicOj();
			picOj.set(MenuOj.GROUP_ID, Constant.GroupSource.GROUP_CHANDAITV);
			picOj.set(PicOj.TYPE_CUT, Constant.TYPE_CUT_PICTURE);
			picOj.set(PicOj.NAME, baseObject.get(AlbulmOj.NAME));
			picOj.set(PicOj.URL, baseObject.get(AlbulmOj.URL));
			picOj.set(PicOj.URL_THUMBNAIL, baseObject.get(AlbulmOj.URL_THUMBNAIL));
			picOj.set(AlbulmOj.TYPE_CUT, Constant.TYPE_CUT_PICTURE);

			ojs.add(picOj);

		}

		return ojs;

	}

}
