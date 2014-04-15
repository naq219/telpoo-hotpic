package com.telpoo.hotpic.parsehtml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.telpoo.hotpic.object.AlbulmOj;

public class ChanDaiParse {
	
	public static final String  urlPath = "http://chandai.tv/";
	public ChanDaiParse() {
		// TODO Auto-generated constructor stub
	}
	public ArrayList<AlbulmOj> getAlbulmOjList( String url, Context context )
	{
		// add a progress bar de bat buoc user phai cho de load du lieu
		// add demo
		ProgressDialog progressDialog = new ProgressDialog(context);
		progressDialog.setTitle("Loadding....");
		progressDialog.show();		
		//
		ArrayList<AlbulmOj> albulmOjs = new ArrayList<AlbulmOj>();
		//to do code
		String html = "";
		try {
			html = (new nAsyncTaskChanDai()).execute("url").get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Document document = null;
		// neu html co noi dung 
		if( !html.equals(""))
		{			
			// parse noi dung html vao doccument
			 document = Jsoup.parse(html);
			 // cat cac the <a trong html
			 Elements elements = document.select("a");
			 for(int i =0; i < elements.size(); i++)
			 {
				 // khoi tao cac string chua gia tri lay dc
				 String linkWeb = "", linkImg = "", title = "", count ="";
				 // lay link anh dai dien album bang cat cat the img trong the <a
				 Elements imgLinkAndTitle = elements.get(i).select("img") ;
				 for(Element link : imgLinkAndTitle)
				 {
					 // link anh se chua trong attr src nhung ko day du 
					 linkImg = urlPath + link.attr("src");
					 String temp = link.attr("alt");
					 // neu nhu gia tri cua link lay dc != ""
					 if(!temp.equals(""))
						 title = temp;// gan gia tri temp cho title
					 
				 }
				// cat the div de nhan ten cua hinh hay albulm
				 Elements divCr = elements.get(i).select("div");
				 for(Element divItem : divCr)
				 {// lay link web hien album
					 String temp = "";
					 temp = divItem.attr("data-href");
					 if( !temp.equals("") )
					 {
						 linkWeb = temp;
					 }					 
					
				 }
				 // tao element de nhan dem so hinh trong album
				 // neu so hinh lon hon 0 thi hien thi o dang nao do
				 Elements countElements = elements.get(i).select("span");
				 for(Element countElement : countElements)
				 {
					 String temp = countElement.text();
					 if(!temp.equals(""))
					 {
						 count = temp;
					 }
				 }
				 if(!linkWeb.equals("") && !linkImg.equals(""))
				 {
					 Log.d("test div","linkWeb : "+ linkWeb +" " + "linkImg: "+linkImg +" title: "+ title + "count : "+ count);
					 Log.d("test", "-----------------------------------------------");
					 // create object albulm 
					 AlbulmOj albulmOj = new AlbulmOj();
					 // set value 
					 albulmOj.set(AlbulmOj.NAME, title);
					 albulmOj.set(AlbulmOj.URL, linkWeb);
					 albulmOj.set(AlbulmOj.URL_THUMBNAIL, linkImg);
					 albulmOj.set(AlbulmOj.COUNT, count);
					 // add object to my list
					 albulmOjs.add(albulmOj);
				 }
				 // new ko se bo qua gia tri cua vong lap trong the <a nay ko nhan sang vong tiep the
				 else
				 {
					 continue;
				 }				
			 }			
		}
		else
		{
			// tat progress bar
			progressDialog.dismiss();
			return albulmOjs;
			
		}		
		//tra ve albulm va tat progress bar
		progressDialog.dismiss();
		return albulmOjs;
	}
	//
	public class nAsyncTaskChanDai extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			Document document = null;
			try {
				document = Jsoup.connect(params[0]).get();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return document.toString();
		}
		
	}

}
