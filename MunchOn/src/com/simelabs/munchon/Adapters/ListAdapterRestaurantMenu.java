package com.simelabs.munchon.Adapters;

import java.util.ArrayList;

import com.simelabs.munchon.R;



import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListAdapterRestaurantMenu extends ArrayAdapter<String>
{
	private final Activity context;
	private final ArrayList<String> dishCategoryNames;
	private final Typeface tf,tfb;
	
	public ListAdapterRestaurantMenu(Activity context, ArrayList<String> dishCategories,Typeface tf,Typeface tfb)
	{
		// TODO Auto-generated constructor stub
		super(context, R.layout.restaurant_main_menu_item, dishCategories);
		this.tf = tf;
		this.tfb = tfb;
		this.context=context;
		this.dishCategoryNames = dishCategories;
	}
	
	
	public View getView(int position,View view,ViewGroup parent) {
		LayoutInflater inflater=context.getLayoutInflater();
		
		View rowView=inflater.inflate(R.layout.restaurant_main_menu_item, null,true);
		
		TextView categoryName = (TextView) rowView.findViewById(R.id.txt_category_name);
		
	
		categoryName.setText(dishCategoryNames.get(position));
		categoryName.setTypeface(tf);
		return rowView;
		
	};
}
