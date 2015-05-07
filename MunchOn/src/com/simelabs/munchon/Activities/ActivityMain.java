package com.simelabs.munchon.Activities;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.simelabs.munchon.R;
import com.simelabs.munchon.Fragments.FragmentRestaurantsList;
import com.simelabs.munchon.Fragments.SampleListFragment;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.util.Base64;
import android.util.Log;

public class ActivityMain extends BaseActivity {

	private Fragment mContent;
	private FragmentTabHost mTabHost;
	public static String oldbeacons;
	
	public ActivityMain() {
		super(R.string.demo);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	
		setSlidingActionBarEnabled(true);
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(
					savedInstanceState, "mContent");

		if (mContent == null)
			mContent = new FragmentRestaurantsList();

		// set the Above View
		setContentView(R.layout.content_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, mContent).commit();

		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new SampleListFragment()).commit();
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
