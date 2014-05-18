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
	
	public static final String  urlPath = "http://chandai.tv";
	public static final String TAG_CHANDAI = "PARSE_CHANDAI";
	public ChanDaiParse() {
		// TODO Auto-generated constructor stub
	}
	public ArrayList<AlbulmOj> getAlbulmOjList( String url, Context context )
	{
		// add a progress bar de bat buoc user phai cho de load du lieu
		// add demo
		Log.d(TAG_CHANDAI,"dang chay get image dang chay get image dang chay get image");
		ProgressDialog progressDialog = new ProgressDialog(context);
		progressDialog.setTitle("Loadding....");
		progressDialog.show();		
		//
		ArrayList<AlbulmOj> albulmOjs = new ArrayList<AlbulmOj>();
		//to do code
		String html = "";
		try {
			html = (new nAsyncTaskChanDai()).execute(url).get();
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
			 Elements elementsList = document.select("a");
			 for(Element itemRoot : elementsList)
			 {
				 String linkThumbnail ="", title ="", linkWeb = "", countSpan = ""; 
				 Elements imgAndTitle = itemRoot.select("img");
		
				
				 if( imgAndTitle != null )
				 {
		//-------------------------------------IMG-THUMBNAIL &  TITLE-----------------------------------------------------------
					 String tempImg = imgAndTitle.attr("src");
					 if(tempImg != null && !tempImg.equals(""))
					 {
						 linkThumbnail = urlPath+tempImg;
						 Log.d(TAG_CHANDAI,linkThumbnail);
					 }
					 String tempTitle = imgAndTitle.attr("alt");
					 if(tempTitle != null && !tempTitle.equals(""))
					 {
						 title = tempTitle;
						 Log.d(TAG_CHANDAI,title);
					 }
		//--------------------------------------------------------------------------------------------------------
		//---------------------------------------------LINKWEB------------------------------------------------------
					 String templinkWeb = itemRoot.attr("href");
					 if( templinkWeb != null )
						 linkWeb = urlPath+templinkWeb;
					 Log.d(TAG_CHANDAI,linkWeb);
		//--------------------------------------------------------------------------------------------------------
		//---------------------------------------------SpanCount------------------------------------------------------
					 Elements spanCount = itemRoot.select("span");
					 String tempSpan = spanCount.text();
					 if(tempSpan != null)
					 {
						 countSpan = tempSpan;
					 }
					 Log.d(TAG_CHANDAI,countSpan);
		//-------------------------------------------------------------------------------------------------------
					 if( !linkThumbnail.equals("") && !linkWeb.equals("") )
					 {
						 AlbulmOj albulmOj = new AlbulmOj();
						 albulmOj.set(AlbulmOj.URL_THUMBNAIL, linkThumbnail);
						 albulmOj.set(AlbulmOj.URL, linkWeb);
						 albulmOj.set(AlbulmOj.NAME, title);
						 albulmOj.set(AlbulmOj.COUNT, countSpan);
						 albulmOjs.add(albulmOj);
					 }
					 else
						 continue;
					 
				 }
				 else
					 continue;
		
			 }
		}		
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
