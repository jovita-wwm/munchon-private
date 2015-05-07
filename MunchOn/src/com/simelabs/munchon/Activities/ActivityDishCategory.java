package com.simelabs.munchon.Activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.simelabs.munchon.R;
import com.simelabs.munchon.Adapters.RestaurantSubMenuListAdapter;
import com.simelabs.munchon.Domain.ImageHelper;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ActivityDishCategory extends ListActivity {
	Integer[] DishImageArray = { R.drawable.dish, R.drawable.dishone,
			R.drawable.dishtwo, R.drawable.dishthree };
	String[] DishNameArray = { "Mini Mac & Cheese Bites", "Pizza Italian",
			"Burger & Fries", "Schezuan fish dry" };
	String[] DishDescriptionArray = {
			"Made a first batch substituting gruyere for the American cheese and doubled the paprika and still found them to be pretty bland.",
			"Made a first batch substituting gruyere for the American cheese and doubled the paprika and still found them to be pretty bland.",
			"Made a first batch substituting gruyere for the American cheese and doubled the paprika and still found them to be pretty bland.",
			"Made a first batch substituting gruyere for the American cheese and doubled the paprika and still found them to be pretty bland." };
	String[] DishPriceArray = { "8.00", "1.00", "2.50", "1.50" };

	ArrayList<Bitmap> DishImagesBitmapArray;
	Bitmap DishImagesRounded;
	public static Integer addToBasketCount = 0;
	TextView DishCount;
	ListView list;
	int totalcount;
	ArrayList<Integer> counter;
	ActivityRestaurantMenuListTab obj;
	int clickCount;
	Typeface tf, tfb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.restaurant_submenu);

		String fontPath = "fonts/LaoUI.ttf";
		String fontPathBold = "fonts/LaoUIb.ttf";

		// Loading Font Face
		tf = Typeface.createFromAsset(getAssets(), fontPath);
		tfb = Typeface.createFromAsset(getAssets(), fontPathBold);

		counter = new ArrayList<Integer>();

		obj = new ActivityRestaurantMenuListTab();

		DishImagesBitmapArray = new ArrayList<Bitmap>();
		for (Integer i = 0; i < DishImageArray.length; i++) {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 2;

			Bitmap icon = BitmapFactory.decodeResource(getApplicationContext()
					.getResources(), DishImageArray[i], options);
			ImageHelper image = new ImageHelper();
			DishImagesRounded = image.getRoundedCornerBitmap(icon, 50);

			DishImagesBitmapArray.add(DishImagesRounded);

			RestaurantSubMenuListAdapter adapter = new RestaurantSubMenuListAdapter(
					this, DishImagesBitmapArray, DishNameArray,
					DishDescriptionArray, DishPriceArray, tf, tfb);
			setListAdapter(adapter);

			list = getListView();
			list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						final int position, long id) {
					// TODO Auto-generated method stub

					// parent.getItemAtPosition(position);

					final TextView add = (TextView) view
							.findViewById(R.id.btn_add);

					DishCount = (TextView) view
							.findViewById(R.id.textDishCount);

					((TextView) view.findViewById(R.id.btn_add))
							.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// addCount(add.getTag());

									addToBasketCount++;
									
									/*SharedPreferences.Editor editor = getSharedPreferences(
											"Basket Item Count", MODE_PRIVATE).edit();
									editor.putInt("Count", addToBasketCount);
									editor.commit();*/
									
									Object cc = add.getTag();
									int id = Integer.parseInt(cc.toString());
									clickCount = id + 1;
									add.setTag(clickCount);

									updateView(position);

									Toast.makeText(
											ActivityDishCategory.this,
											"Added to Basket "
													+ String.valueOf(clickCount)
													+ " at "
													+ String.valueOf(position),
											Toast.LENGTH_SHORT).show();
								
									
									ActivityRestaurantMenuListTab.addBasketItems(addToBasketCount);

								}

							});

				}

			});

		}

	}

	private void updateView(int index) {

		View v = list.getChildAt(index - list.getFirstVisiblePosition());

		if (v == null)
			return;

		TextView dishCount = (TextView) v.findViewById(R.id.textDishCount);
		dishCount.setVisibility(View.VISIBLE);
		dishCount.setTypeface(tf);
		dishCount.setText(String.valueOf(clickCount));
	}

}
