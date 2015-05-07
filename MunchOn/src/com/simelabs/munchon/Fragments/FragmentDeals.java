package com.simelabs.munchon.Fragments;

import com.actionbarsherlock.app.SherlockFragment;
import com.simelabs.munchon.R;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabWidget;
import android.widget.TextView;

public class FragmentDeals extends SherlockFragment {
	private FragmentTabHost mTabHost;
	Typeface tf, tfb;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// View view = inflater.inflate(R.layout.deals, null);
		String fontPath = "fonts/LaoUI.ttf";
		String fontPathBold = "fonts/LaoUIb.ttf";

		// Loading Font Face
		tf = Typeface.createFromAsset(getActivity().getAssets(), fontPath);
		tfb = Typeface.createFromAsset(getActivity().getAssets(), fontPathBold);

		mTabHost = new FragmentTabHost(getActivity());
		mTabHost.setup(getActivity(), getChildFragmentManager(), R.layout.deals);

		Bundle arg1 = new Bundle();
		arg1.putInt("Arg for Frag1", 1);
		mTabHost.addTab(mTabHost.newTabSpec("Tab1").setIndicator("All Deals"),
				MyDealsFragment.class, arg1);

		Bundle arg2 = new Bundle();
		arg2.putInt("Arg for Frag2", 2);
		mTabHost.addTab(mTabHost.newTabSpec("Tab2").setIndicator("My Deals"),
				AllDealsFragment.class, arg2);

		// TabHost host = (TabHost)view.findViewById(R.id.tabhost);
		TabWidget widget = mTabHost.getTabWidget();

		for (int i = 0; i < widget.getChildCount(); i++) {
			View v = widget.getChildAt(i);

			// Look for the title view to ensure this is an indicator and not a
			// divider.
			TextView tv = (TextView) v.findViewById(android.R.id.title);
			tv.setAllCaps(false);
			tv.setTypeface(tf);
			tv.setTextSize(15);

			if (tv == null) {
				continue;
			}
			v.setBackgroundResource(R.drawable.tabselector);
			mTabHost.getTabWidget().setDividerDrawable(null);
		}

		return mTabHost;

	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		mTabHost = null;
	}

}
