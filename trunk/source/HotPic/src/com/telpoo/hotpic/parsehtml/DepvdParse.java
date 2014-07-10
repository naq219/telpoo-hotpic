package com.telpoo.hotpic.parsehtml;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.telpoo.frame.net.NetUtils;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.hotpic.object.AlbulmOj;
import com.telpoo.hotpic.object.MenuOj;
import com.telpoo.hotpic.object.MyObject;
import com.telpoo.hotpic.object.PicOj;
import com.telpoo.hotpic.utils.Constant;

public class DepvdParse {
	
	
	public static ArrayList<BaseObject> Parse(BaseObject oj) throws IOException {
		int typeCut = oj.getInt(MenuOj.TYPE_CUT);
		String url = oj.get(MenuOj.URL);
		int page = oj.getInt(MyObject.PAGE);
		if (page > 1) {// chen them trang de dung url (truong hop loadmore)
			url = url + "/p" + page;
			url.replace("//", "/");
		}

		if (typeCut == Constant.TYPE_CUT_ALBULM) { // cắt lấy ra albulm

			return getAlbumDepvd(url);
		}

		if (typeCut == Constant.TYPE_CUT_PICTURE) {
			return getPicDepvdParse(url);
		}

		else
			return null;
	}

	public static String parseUrlDetail(String url){
		return url;
	}
	
	public static ArrayList<BaseObject> getAlbumDepvd(String url) throws IOException
	{
		Document html = null;
		ArrayList<BaseObject> res = new ArrayList<BaseObject>();
		html = Jsoup.connect(url).userAgent(NetUtils.UserAgent.IPHONE4).get();
		//
		//--------------------start parse
		
		Elements rootElement = html.select("a");
		for(Element item : rootElement)
		{
			String linkThumbnail = "", title = "", linkWeb = "", countSpan = "";
			//
			linkWeb = item.attr("href");
			Elements imgElement = item.select("img");
			if(imgElement != null)
			{
				if(imgElement.hasAttr("class") && imgElement.attr("class").equals("lazy") )
				{
					
					title = imgElement.attr("alt");
					linkThumbnail = imgElement.attr("src");
					//
					if (!linkThumbnail.equals("") && !linkWeb.equals("")) {
						AlbulmOj albulmOj = new AlbulmOj();
						albulmOj.set(AlbulmOj.TYPE_CUT, Constant.TYPE_CUT_ALBULM);
						albulmOj.set(MenuOj.GROUP_ID, Constant.GroupSource.GROUP_DEPVD);
						albulmOj.set(AlbulmOj.URL_THUMBNAIL, linkThumbnail);
						albulmOj.set(AlbulmOj.URL, linkWeb);
						albulmOj.set(AlbulmOj.NAME, title);					
						albulmOj.set(AlbulmOj.COUNT, countSpan);
						res.add(albulmOj);
				}
					else
						continue;
				
				} else
					continue;
			}
			else
				continue;			
			
		}
		return res;
		
	}
	public static ArrayList<BaseObject> getPicDepvdParse(String url) throws IOException
	{
		Document html = null;
		ArrayList<BaseObject> res = new ArrayList<BaseObject>();
		html = Jsoup.connect(url).userAgent(NetUtils.UserAgent.IPHONE4).get();
		//
		//--------------------start parse------------------
		Elements rootElements = html.select("div");
		for(Element item : rootElements)
		{
			if( (item.hasAttr("id")) && (item.attr("id").equals("vd-view-carousel")) )
			{
				Elements divsItem = item.select("div");
				for(int i =0; i < divsItem.size(); i++)
				{
					if(divsItem.get(i).attr("class").equals("carousel-inner"))
					{
						 
						for(Element div : divsItem.get(i).select("div")){
							Elements img = div.select("img");	
							for(Element imgLink : img)
							{
								String imgURL = imgLink.attr("data-original");
								//
								PicOj picOj = new PicOj();
								picOj.set(MenuOj.GROUP_ID, Constant.GroupSource.GROUP_DEPVD);
								picOj.set(PicOj.TYPE_CUT, Constant.TYPE_CUT_PICTURE);
								String temp = getNameDepPic(html);
								if(temp != null && !temp.equals(""))
								{
									picOj.set(PicOj.NAME, temp);
								}
								else
									picOj.set(PicOj.NAME, "Depvd");
								
								picOj.set(PicOj.URL, imgURL);
								picOj.set(PicOj.URL_THUMBNAIL, imgURL);
								picOj.set(AlbulmOj.TYPE_CUT, Constant.TYPE_CUT_PICTURE);
								//
								res.add(picOj);
							}
							break;
						}
						break;
						
					}
				}
				break;
			}
		}
		
		return res;
		
	}
	public static String getNameDepPic(Document html)
	{
		String name  = "";
		Elements rootElements = html.select("p");
		for(Element item : rootElements)
		{
			if(item.hasAttr("class"))
			{
				if(item.attr("class").equals("vd-model"))
				{
					Element a = item.select("a").get(0);
					if(a != null)
					{
						name = a.select("span").get(0).text();
					}
					break;
				}
				
			}
		}
		return name;
	}

}
