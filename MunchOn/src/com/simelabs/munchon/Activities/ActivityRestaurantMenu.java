package com.simelabs.munchon.Activities;

import java.util.ArrayList;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockListActivity;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingListActivity;
import com.simelabs.munchon.R;
import com.simelabs.munchon.Adapters.ListAdapterRestaurantMenu;
import com.simelabs.munchon.Domain.ImageHelper;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityRestaurantMenu extends BaseClassActionBar {
	ListView list;
	String RestaurantName = "Slide Lounge";
	String[] menuCategories = { "Breakfast", "Coffee", "Appetizers", "Desserts" };
	ArrayList<String> dishCategories;
	ImageView imageRest, imageRestLogo;
	TextView restaurantName, actionBarTitle, tableno;
	Bitmap RestImageRounded;
	Integer image = R.drawable.rest_two;
	Integer selectedCategory;
	ImageView basket, table;
	int userType;
	ListView menuList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.restaurant_main_menu);

		dishCategories = new ArrayList<String>();

		menuList = (ListView) findViewById(R.id.listview);

		dishCategories.add("BreakFast");
		dishCategories.add("Kids meal");
		dishCategories.add("Lunch");
		dishCategories.add("Coffee");
		dishCategories.add("Appetizers");
		dishCategories.add("BreakFast");
		dishCategories.add("Kids meal");
		dishCategories.add("Lunch");
		dishCategories.add("Coffee");
		dishCategories.add("Appetizers");

		String fontPath = "fonts/LaoUI.ttf";
		String fontPathBold = "fonts/LaoUIb.ttf";

		// Loading Font Face
		Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
		Typeface tfb = Typeface.createFromAsset(getAssets(), fontPathBold);

		setActionBarTitle(RestaurantName);
		setBasketVisibility();
		
		Intent intent = getIntent();
		userType = intent.getIntExtra("userType", 0);

		if (userType == 0) {
			updateTableNumber("23");

		}

		imageRest = (ImageView) findViewById(R.id.imageRest);
		imageRestLogo = (ImageView) findViewById(R.id.img_RestaurantLogo);

		Bitmap icon = BitmapFactory.decodeResource(getApplicationContext()
				.getResources(), image);
		ImageHelper img = new ImageHelper();

		imageRest.setImageBitmap(img.getRoundedCornerBitmap(icon, 50));
		imageRestLogo.setImageResource(R.drawable.rest_logo_one);

		ListAdapterRestaurantMenu adapter = new ListAdapterRestaurantMenu(this,
				dishCategories, tf, tfb);
		menuList.setAdapter(adapter);

		menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				String selection = menuList.getItemAtPosition(position)
						.toString();
				Toast.makeText(getApplicationContext(), selection,
						Toast.LENGTH_LONG).show();

				Intent myIntent = new Intent(getApplicationContext(),
						ActivityRestaurantMenuListTab.class);
				myIntent.putStringArrayListExtra("categoryArray",
						dishCategories);
				myIntent.putExtra("category", selectedCategory);
				myIntent.putExtra("position", position);
				myIntent.putExtra("userType", userType);

				startActivity(myIntent);
			}

		});

	}

}
