package com.simelabs.munchon.Activities;

import java.util.ArrayList;

import android.app.ListActivity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.simelabs.munchon.R;
import com.simelabs.munchon.Domain.DishCategoriesDomain;
import com.simelabs.munchon.Domain.PublicValues;
import com.simelabs.munchon.Domain.RestaurantDomain;

public class ActivityRestaurantMenuListTab extends TabActivity {
	TabHost tabHost;
	HorizontalScrollView mHorizontalScrollView;
	int tabnum;
	String RestaurantName;
	ArrayList<String> categoryNames;
	ArrayList<String> categories;
	Typeface tf;
	ImageView basket, tableImg;
	TextView tableno;
	static TextView basketitems;
	int userType,ListPosition;
	static int count;
	ArrayList<DishCategoriesDomain> categoriesList;
	ArrayList<RestaurantDomain> restaurantDetails;
	String restId ;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.restaurant_menu_list_tab);

		String fontPath = "fonts/LaoUI.ttf";
		String fontPathBold = "fonts/LaoUIb.ttf";

		Intent userIntent = getIntent();
		userType = userIntent.getIntExtra("userType", 0);
		tabnum = userIntent.getIntExtra("position", 0);
		ListPosition = userIntent.getIntExtra("Restposition", 0);
		restId = userIntent.getStringExtra("restId");
		
		// Loading Font Face
		tf = Typeface.createFromAsset(getAssets(), fontPath);
		Typeface tfb = Typeface.createFromAsset(getAssets(), fontPathBold);

		categoriesList = PublicValues.allDishCategories;
		restaurantDetails = PublicValues.allnearbyRestaurants;
		
		ImageView back = (ImageView) findViewById(R.id.btn_back);
		TextView actionBarTitle = (TextView) findViewById(R.id.txt_title);
		tableImg = (ImageView) findViewById(R.id.btn_table);
		tableno = (TextView) findViewById(R.id.txt_table_no);

		basket = (ImageView) findViewById(R.id.btn_basket);
		basketitems = (TextView) findViewById(R.id.txt_basket_items);

		RestaurantName = "Sliding Lounge";
		actionBarTitle.setText(restaurantDetails.get(ListPosition).getName());
		actionBarTitle.setTypeface(tfb);
		tableno.setTypeface(tf);
		basketitems.setTypeface(tf);

		basket.setVisibility(View.VISIBLE);

		
		if (userType == 0) {
			// Toast.makeText(getApplicationContext(), "Dine in",
			// Toast.LENGTH_SHORT).show();
			tableImg.setVisibility(View.VISIBLE);
			addTableNumber(1);
		} else {
			tableno.setVisibility(View.GONE);
			tableImg.setVisibility(View.GONE);
		}

		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				finish();
			}
		});

		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		mHorizontalScrollView = (HorizontalScrollView) findViewById(R.id.hs);

		// categories = new ArrayList<String>();
		// categories = getIntent().getStringArrayListExtra("categoryArray");
		/*
		 * Toast.makeText(getApplicationContext(), "Selected tab is " + tabnum,
		 * Toast.LENGTH_SHORT).show();
		 */

		for (Integer i = 0; i < categoriesList.size(); i++) {
			String name = categoriesList.get(i).getCategoryName();
			createTabs(name);
		}

	}

	private void createTabs(String name) {
		// TODO Auto-generated method stub
		TabWidget widget = tabHost.getTabWidget();

		TabSpec tab = tabHost.newTabSpec(name);
		tab.setIndicator(name);
		tab.setContent(new Intent(this, ActivityDishCategory.class).putExtra("restId", restId));
		tabHost.addTab(tab);
		mHorizontalScrollView.setSmoothScrollingEnabled(true);
		tabHost.setCurrentTab(tabnum);
		mHorizontalScrollView.scrollTo(tabnum, 0);

		float scale = getResources().getDisplayMetrics().density;
		final double tabWidth = (int) (150 * scale + 0.5f);

		for (int i = 0; i < tabHost.getTabWidget().getTabCount(); i++) {
			tabHost.getTabWidget().getChildTabViewAt(i).getLayoutParams().width = (int) tabWidth;
		}

		final double screenWidth = getWindowManager().getDefaultDisplay()
				.getWidth();

		tabHost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				int nrOfShownCompleteTabs = ((int) (Math.floor(screenWidth
						/ tabWidth) - 1) / 2) * 2;
				int remainingSpace = (int) ((screenWidth - tabWidth - (tabWidth * nrOfShownCompleteTabs)) / 2);

				int a = (int) (tabHost.getCurrentTab() * tabWidth);
				int b = (int) ((int) (nrOfShownCompleteTabs / 2) * tabWidth);
				int offset = (a - b) - remainingSpace;

				mHorizontalScrollView.scrollTo(offset, 0);

			}
		});

		for (int i = 0; i < widget.getChildCount(); i++) {
			View v = widget.getChildAt(i);

			TextView tv = (TextView) v.findViewById(android.R.id.title);
			tv.setTypeface(tf);
			tv.setAllCaps(false);
			tv.setTextSize(15);

			if (tv == null) {
				continue;
			}
			tabHost.setCurrentTab(tabnum);
			v.setBackgroundResource(R.drawable.tabselector);
			tabHost.getTabWidget().setDividerDrawable(null);

		}
	}

	public void addTableNumber(int number) {
		String table = String.valueOf(number);
		tableno.setText(table);
		tableno.setVisibility(View.VISIBLE);
	}

	public static void addBasketItems(Integer addToBasketCount) {
		// TODO Auto-generated method stub
		try {

			basketitems.setVisibility(View.VISIBLE);
			basketitems.setText(addToBasketCount.toString());
		} catch (NullPointerException e) {
			Log.e("MunchOn", "Exception", e);
		}
	}

}
