package com.simelabs.munchon.Adapters;

import java.util.ArrayList;

import com.simelabs.munchon.R;
import com.simelabs.munchon.Activities.ActivityRestaurantMenuListTab;
import com.simelabs.munchon.Activities.ActivitySelectIngredients;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RestaurantSubMenuListAdapter extends ArrayAdapter<String> {

	private final Activity context;
	
	private final ArrayList<Bitmap> dishImagesRounded;
	private final String[] DishName;
	private final String[] DishDescription;
	private final String[] DishPrice;
	Integer count;
	TextView itemCount;
	Typeface tf,tfb;
	
	public RestaurantSubMenuListAdapter(Activity context,ArrayList<Bitmap> dishImagesBitmapArray, String[] dishNameArray,
			String[] dishDescriptionArray, String[] dishPriceArray, Typeface tf, Typeface tfb) 
	{
		super(context, R.layout.restaurant_menu_list_item,dishNameArray);
		
		this.context=context;
		this.dishImagesRounded=dishImagesBitmapArray;
		this.DishName=dishNameArray;
		this.DishDescription=dishDescriptionArray;
		this.DishPrice=dishPriceArray;
		this.tf= tf;
		this.tfb=tfb;
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	View v = convertView;
	final ViewHolder h;
	if(v == null){
	    v=inflater.inflate(R.layout.restaurant_menu_list_item, parent,false);
	    h = new ViewHolder();
	    h.DishName = (TextView)v.findViewById(R.id.textDishName);
	    h.DishDescription = (TextView)v.findViewById(R.id.textDishDescription);
	    h.DishPrice = (TextView)v.findViewById(R.id.textDishPrice);
	    h.AddButton = (TextView)v.findViewById(R.id.btn_add);
	    
	    h.ImageDish = (ImageView) v.findViewById(R.id.imageDish);
	    h.DishCount = (TextView) v.findViewById(R.id.textDishCount);
	    h.AddButton = (TextView) v.findViewById(R.id.btn_add);
	    
	    
	 
	   
		
	    v.setTag(h);
	}else
	{
	   h = (ViewHolder) v.getTag();
	}
	
    h.DishName.setTypeface(tfb);
	h.DishDescription.setTypeface(tf);
	h.DishPrice.setTypeface(tfb);
	h.AddButton.setTypeface(tf);
	h.DishCount.setTypeface(tf);
	h.DishName.setText(DishName[position]);
	h.DishDescription.setText(DishDescription[position]);
	h.DishPrice.setText("$"+DishPrice[position]);
	h.ImageDish.setImageBitmap(this.dishImagesRounded.get(position));
	
	  if(h.AddButton.getTag()==null)
	   {
		   h.AddButton.setTag(0);  
	   }
	  
	/*h.ImageDish.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			
			Intent myIntent = new Intent(context,
					ActivitySelectIngredients.class);
			myIntent.putExtra("TableNumber",23);
			context.startActivity(myIntent);
			
			
		}
	});*/
	
	/*h.AddButton.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			
			String tag = (String)v.getTag();
			
			Toast.makeText(context, tag, Toast.LENGTH_SHORT).show();
			h.DishCount.setVisibility(View.VISIBLE);
			notifyDataSetChanged();
			
			//count++;
           
			
			
		}
	});*/
	
	return v;
	} 
	
	/** 
	 * to stop recycling of listview items
	 */
	@Override
	public int getViewTypeCount() {
		return getCount();
	}

	/** 
	 * to stop recycling of listview items
	 */
	@Override
	public int getItemViewType(int position) {
		return position;
	}

	private static class ViewHolder{
	TextView DishName,DishDescription,DishPrice,AddButton,DishCount;
	ImageView ImageDish;
	
	}
}
