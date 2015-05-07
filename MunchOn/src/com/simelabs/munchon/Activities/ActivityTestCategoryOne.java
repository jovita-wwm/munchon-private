package com.simelabs.munchon.Activities;

import java.util.ArrayList;

import android.app.ListActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.simelabs.munchon.R;
import com.simelabs.munchon.Adapters.RestaurantSubMenuListAdapter;
import com.simelabs.munchon.Domain.ImageHelper;

public class ActivityTestCategoryOne extends ListActivity {
	Integer[] DishImageArray = { R.drawable.dishtwo, R.drawable.dishthree,R.drawable.dish, R.drawable.dishone
			 };
	String[] DishNameArray = { "Burger & Fries", "Schezuan fish dry","Mini Mac & Cheese Bites", "Pizza Italian"
			 };
	String[] DishDescriptionArray = {
			"Made a first batch substituting gruyere for the American cheese and doubled the paprika and still found them to be pretty bland.",
			"Made a first batch substituting gruyere for the American cheese and doubled the paprika and still found them to be pretty bland.",
			"Made a first batch substituting gruyere for the American cheese and doubled the paprika and still found them to be pretty bland.",
			"Made a first batch substituting gruyere for the American cheese and doubled the paprika and still found them to be pretty bland." };
	String[] DishPriceArray = { "8.00", "1.00", "2.50", "1.50" };

	ListView list;
	ArrayList<Bitmap> DishImagesBitmapArray;
	Bitmap DishImagesRounded;
	Integer addToBasketCount=0;
	TextView DishCount;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.restaurant_submenu);

		String fontPath = "fonts/LaoUI.ttf";
		String fontPathBold = "fonts/LaoUIb.ttf";

		// Loading Font Face
		Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
		Typeface tfb = Typeface.createFromAsset(getAssets(), fontPathBold);

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

			final ListView list = getListView();
			list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						final int position, long id) {
					// TODO Auto-generated method stub
						
						/*TextView add = (TextView) view
								.findViewById(R.id.btn_add);
						*/DishCount = (TextView) view
								.findViewById(R.id.textDishCount);
						
						
						/*((TextView) view
						.findViewById(R.id.btn_add)).setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								
			
								//Toast.makeText(getApplicationContext(),String.valueOf(position), Toast.LENGTH_SHORT).show();
								
								// TODO Auto-generated method stub
								
								
								addToBasketCount++;
								Toast.makeText(getApplicationContext(), "Added to Basket", Toast.LENGTH_SHORT).show();
								
								DishCount.setVisibility(View.VISIBLE);
								DishCount.setText(addToBasketCount.toString());
								
							}
						});*/
						
					}
					
				
			});

		}

	}
	void changeAdd(int count)
	{
		
        addToBasketCount++;
		Toast.makeText(getApplicationContext(), "Added to Basket", Toast.LENGTH_SHORT).show();
		
		DishCount.setVisibility(View.VISIBLE);
		DishCount.setText(addToBasketCount.toString());
	
	}
}

