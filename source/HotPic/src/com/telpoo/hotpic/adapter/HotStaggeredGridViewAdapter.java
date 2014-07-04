package com.telpoo.hotpic.adapter;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.etsy.android.grid.util.DynamicHeightImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ViewScaleType;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.hotpic.R;
import com.telpoo.hotpic.object.AlbulmOj;
import com.telpoo.hotpic.utils.Utils;

public class HotStaggeredGridViewAdapter extends ArrayAdapter<BaseObject>{

	
	Context context;
	 int resource;
	 ArrayList<BaseObject> objects;
	 Random random;
	 LayoutInflater inflater;
	 private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();
	 public HotStaggeredGridViewAdapter(Context context, int resource,
			 ArrayList<BaseObject> objects) {
			super(context, resource, objects);
			// TODO Auto-generated constructor stub
			this.context = context;
			this.resource = resource;
			this.objects = objects;
			this.inflater =  LayoutInflater.from(context);
			this.random = new Random();
			for(BaseObject albulmOj: objects)
			{
				Log.d("testSTG", albulmOj.get(AlbulmOj.URL_THUMBNAIL));
			}
		}	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		//View v = convertView;
		if( convertView == null )
		{
						
			convertView = inflater.inflate( R.layout.image_item_grid, parent ,false);
			holder = new ViewHolder();
			holder.dynamicHeightImageView = (DynamicHeightImageView) convertView.findViewById(R.id.imgViewGrid);
			holder.textViewTittle = (TextView) convertView.findViewById(R.id.tittleImageGrid);
			//
			convertView.setTag(holder);			
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}		
		double positionHeight = getPositionRatio(position);
		 
		String imgLink = objects.get(position).get(AlbulmOj.URL_THUMBNAIL);
		//
		if( !objects.get(position).get(AlbulmOj.NAME).equals(""))
		{
			holder.textViewTittle.setText( objects.get(position).get(AlbulmOj.NAME));			
		}
		else
			holder.textViewTittle.setVisibility(View.GONE);
		Log.d("testSTG", imgLink);
		
		ImageLoader.getInstance().loadImage(imgLink,Utils.loadImgOption(), new SimpleImageLoadingListener(){
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				super.onLoadingComplete(imageUri, view, loadedImage);
				holder.dynamicHeightImageView.setImageBitmap(loadedImage);
			}
		});
		holder.dynamicHeightImageView.setHeightRatio(positionHeight);
		//notifyDataSetChanged();		
		return convertView;
	}
	private double getPositionRatio(final int position) {
       double ratio = sPositionHeightRatios.get(position, 0.0);
       // if not yet done generate and stash the columns height
       // in our real world scenario this will be determined by
       // some match based on the known height and width of the image
       // and maybe a helpful way to get the column height!
       if (ratio == 0) {
           ratio = getRandomHeightRatio();
           sPositionHeightRatios.append(position, ratio);
           Log.d("", "getPositionRatio:" + position + " ratio:" + ratio);
       }
       return ratio;
   }

   private double getRandomHeightRatio() {
       return (random.nextDouble() / 2.0) + 1.0; // height will be 1.0 - 1.5 the width
   }
	class ViewHolder{
		DynamicHeightImageView dynamicHeightImageView;
		TextView textViewTittle;
	}
	public void Adds(List<BaseObject> items) {
		if (items != null) {
			for (BaseObject item : items) {
				add(item);
			}
		}
	}
	
	public void setData(List<BaseObject> items){
		clear();
		Adds(items);
		
	}
	
	public ArrayList<BaseObject> getAll(){
		return objects;
	}
	

}
