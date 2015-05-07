package com.simelabs.munchon.Fragments;

import java.util.ArrayList;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.simelabs.munchon.R;
import com.simelabs.munchon.Adapters.DealsListAdapter;
import com.simelabs.munchon.Domain.ImageHelper;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyDealsFragment extends ListFragment {
	Integer[] RestImageArray = { R.drawable.dishthree, R.drawable.dishtwo,
			R.drawable.dishone, R.drawable.dish, R.drawable.dish,
			R.drawable.dishone };
	String[] RestNameArray = { "Slide Lounge", "Le Pelican", "Slide Lounge",
			"Le Pelican", "Slide Lounge", "Le Pelican", "Slide Lounge",
			"Le Pelican", "Slide Lounge", "Le Pelican" };
	String[] DealDescArray = { "30% off on any Pizza", "20% off on any Muffin",
			"30% off on any Pizza", "20% off on any Muffin",
			"30% off on any Pizza", "20% off on any Muffin",
			"30% off on any Pizza", "20% off on any Muffin",
			"30% off on any Pizza", "20% off on any Muffin" };
	String[] DealValidityArray = { "3-03-2015", "13-04-2015", "3-03-2015",
			"13-04-2015", "3-03-2015", "13-04-2015", "3-03-2015", "13-04-2015",
			"3-03-2015", "13-04-2015" };
	String[] DistanceArray = { "2.5", "1.3", "2.5", "1.3", "2.5", "1.3", "2.5",
			"1.3", "2.5", "1.3" };
	String[] DealPercentageArray = { "30", "25", "30", "25", "30", "25", "30",
			"25", "30", "25" };

	ArrayList<String> RestaurantNameArray, CouponCode, ValidityDate;
	ArrayList<Bitmap> RestaurantImageArray;

	Typeface tf, tfb;

	Bitmap RestImagesRounded;
	ListView list;
	SlidingMenu sm;

	ArrayList<Bitmap> RestaurentImages;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dealslist, null);

		// View view = inflater.inflate(R.layout.deals, null);
		String fontPath = "fonts/LaoUI.ttf";
		String fontPathBold = "fonts/LaoUIb.ttf";

		// Loading Font Face
		tf = Typeface.createFromAsset(getActivity().getAssets(), fontPath);
		tfb = Typeface.createFromAsset(getActivity().getAssets(), fontPathBold);

		RestaurantImageArray = new ArrayList<Bitmap>();
		RestaurantNameArray = new ArrayList<String>();
		CouponCode = new ArrayList<String>();
		ValidityDate = new ArrayList<String>();

		sm = new SlidingMenu(getActivity());

		RestaurentImages = new ArrayList<Bitmap>();
		for (Integer i = 0; i < RestImageArray.length; i++) {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 2;

			Bitmap icon = BitmapFactory.decodeResource(getActivity()
					.getResources(), RestImageArray[i], options);
			ImageHelper img = new ImageHelper();

			RestImagesRounded = img.getRoundedCornerBitmap(icon, 50);
			RestaurantImageArray.add(RestImagesRounded);
		}

		RestaurantNameArray.add("Slide Lounge");
		RestaurantNameArray.add("Le Pelican");
		RestaurantNameArray.add("Slide Lounge");
		RestaurantNameArray.add("Le Pelican");
		RestaurantNameArray.add("Slide Lounge");
		RestaurantNameArray.add("Le Pelican");

		CouponCode.add("MO232");
		CouponCode.add("MO122");
		CouponCode.add("MO124");
		CouponCode.add("MO124");
		CouponCode.add("MO124");
		CouponCode.add("MO124");

		ValidityDate.add("12/12/2015");
		ValidityDate.add("12/12/2015");
		ValidityDate.add("12/12/2015");
		ValidityDate.add("12/12/2015");
		ValidityDate.add("12/12/2015");
		ValidityDate.add("12/12/2015");

		// list = (ListView) view.findViewById(R.id.list);

		DealsListAdapter adapter = new DealsListAdapter(getActivity(),
				RestaurantImageArray, RestaurantNameArray, CouponCode,
				ValidityDate,tf,tfb);
		setListAdapter(adapter);

		return view;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onResume() {
		super.onResume();

		if (sm.isMenuShowing()) {
			sm.showContent();
		}
	}
}
