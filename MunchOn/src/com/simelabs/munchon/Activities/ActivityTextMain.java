package com.simelabs.munchon.Activities;


import com.simelabs.munchon.R;
import com.simelabs.munchon.Fragments.FragmentBlank;
import com.simelabs.munchon.Fragments.FragmentRestaurantsList;
import com.simelabs.munchon.Fragments.FragmentSampleList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class ActivityTextMain extends ActivityBaseClass {

	private Fragment mContent;
	public static String oldbeacons;
	
	public ActivityTextMain() {
		super(R.string.app_name);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
	
		setSlidingActionBarEnabled(true);
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(
					savedInstanceState, "mContent");

		if (mContent == null)
			mContent = new FragmentBlank();

		// set the Above View
		setContentView(R.layout.content_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, mContent).commit();

		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new FragmentSampleList()).commit();
	}

	public void switchContent(Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
		getSlidingMenu().showContent();
		
	}
	
	public void setTitle(String title)
	{
		setActionBarTitle(title);
	}
}
