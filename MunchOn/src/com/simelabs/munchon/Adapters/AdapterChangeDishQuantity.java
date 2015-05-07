/*package com.simelabs.munchon.Adapters;

import java.util.ArrayList;

import com.sinergia.munchon.R;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class AdapterChangeDishQuantity extends ArrayAdapter<String> {

	private final Activity context;
	private final Typeface tf, tfb;
	Dialog changeIngredient;
	ArrayList<String> ingredientNameArray;
	ArrayList<Double> ingredientPriceArray;

	public AdapterChangeDishQuantity(Activity context,
			ArrayList<Integer> ingredientNameArray,
			ArrayList<Double> ingredientPriceArray2, Typeface tf, Typeface tfb,
			Dialog changeIngredient) {
		super(context, R.layout.dialog_listitem, ingredientNameArray);

		this.context = context;
		this.ingredientNameArray = ingredientNameArray;
		this.ingredientPriceArray = ingredientPriceArray2;
		this.tf = tf;
		this.tfb = tfb;
		this.changeIngredient = changeIngredient;
	}

	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.dialog_listitem, null, true);

		final TextView txtName = (TextView) rowView
				.findViewById(R.id.txt_IngredientName);
		final TextView txtPrice = (TextView) rowView
				.findViewById(R.id.txt_IngredientPrice);

		
		 * add.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) {
		 * 
		 * count++; itemCount.setText(count.toString()); Toast.makeText(context,
		 * count.toString(), Toast.LENGTH_SHORT).show();
		 * itemCount.setVisibility(View.VISIBLE);
		 * 
		 * 
		 * 
		 * } });
		 

		txtName.setText(ingredientNameArray.get(position).toString());
		txtName.setTypeface(tf);
		txtPrice.setTypeface(tf);
		Double priceValue = (ingredientPriceArray.get(position));
		txtPrice.setText("$" + priceValue);

		
		 * txtName.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { Toast.makeText(context,
		 * txtName.getText(), Toast.LENGTH_SHORT).show();
		 * Toast.makeText(context, txtPrice.getText(),
		 * Toast.LENGTH_SHORT).show(); changeIngredient.dismiss(); } });
		 
		return rowView;

	};
}
*/