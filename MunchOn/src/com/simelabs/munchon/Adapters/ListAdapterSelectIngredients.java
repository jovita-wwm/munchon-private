package com.simelabs.munchon.Adapters;


import com.simelabs.munchon.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class ListAdapterSelectIngredients extends ArrayAdapter<String>
{
	private final Activity context;
	private final String[] dishCategoryNames;
	
	public ListAdapterSelectIngredients(Activity context, String[] dishCategoryArray)
	{
		// TODO Auto-generated constructor stub
		super(context, R.layout.restaurant_main_menu_item, dishCategoryArray);
		this.context=context;
		this.dishCategoryNames = dishCategoryArray;
	}
	
	public View getView(int position,View view,ViewGroup parent) {
		LayoutInflater inflater=context.getLayoutInflater();
		
		View rowView=inflater.inflate(R.layout.restaurant_main_menu_item, null,true);
		
		TextView categoryName = (TextView) rowView.findViewById(R.id.txt_category_name);
		
	
		categoryName.setText(dishCategoryNames[position]);
		
		return rowView;
		
	};

}
