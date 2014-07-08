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
import com.telpoo.hotpic.object.PicOj;
import com.telpoo.hotpic.utils.Constant;
import com.telpoo.hotpic.utils.Utils;

public class DepvdParse {
	
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
						albulmOj.set(MenuOj.GROUP_ID, Constant.GroupSource.GROUP_CHANDAITV);
						albulmOj.set(AlbulmOj.URL_THUMBNAIL, linkThumbnail);
						albulmOj.set(AlbulmOj.URL, linkWeb);
						albulmOj.set(AlbulmOj.NAME, title);					
						albulmOj.set(AlbulmOj.COUNT, countSpan);
						albulmOj.set(AlbulmOj.TYPE_CUT, Constant.TYPE_CUT_ALBULM);
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
								picOj.set(MenuOj.GROUP_ID, Constant.GroupSource.GROUP_CHANDAITV);
								picOj.set(PicOj.TYPE_CUT, Constant.TYPE_CUT_PICTURE);
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

}
