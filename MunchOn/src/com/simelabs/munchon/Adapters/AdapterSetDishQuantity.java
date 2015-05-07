package com.simelabs.munchon.Adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.simelabs.munchon.R;
import com.simelabs.munchon.Activities.ActivitySelectIngredients;

public class AdapterSetDishQuantity extends ArrayAdapter<String> {

	private final Activity context;
	private final Typeface tf, tfb;
	ArrayList<String> ingredientNameArray;

	public AdapterSetDishQuantity(
			Activity context,
			ArrayList<String> ingredientNameArray, Typeface tf, Typeface tfb) {
		// TODO Auto-generated constructor stub
		super(context, R.layout.dialog_quantity_listitem, ingredientNameArray);

		this.context = context;
		this.ingredientNameArray = ingredientNameArray;
		this.tf = tf;
		this.tfb = tfb;
		
	}
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.dialog_quantity_listitem, null, true);

		final TextView Quantity = (TextView) rowView
				.findViewById(R.id.txt_DishQuantity);
		Quantity.setText(ingredientNameArray.get(position));
		Quantity.setTypeface(tf);
		return rowView;
	
	};
}

