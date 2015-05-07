package com.simelabs.munchon.Fragments;


import com.simelabs.munchon.R;
import com.simelabs.munchon.Activities.ActivityMain;
import com.simelabs.munchon.Activities.ActivityMainTest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class FragmentSampleList extends ListFragment {
	public String[] menus = {"Nearby","Search", "Deals", "Account","Home"};
	
    public Integer[] icons = { R.drawable.icon_settings,R.drawable.icon_settings,
    		R.drawable.icon_settings,R.drawable.icon_settings,R.drawable.icon_settings};
   
    ActivityMain fca;
    
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list, null);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		fca = (ActivityMain) getActivity();
		
		SampleAdapter adapter = new SampleAdapter(getActivity());
		
		for (int i = 0; i < menus.length; i++) {
			adapter.add(new SampleItem(menus[i], icons[i]));
		}
		setListAdapter(adapter);

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

		Fragment newContent = null;
		
		switch (position) {
		case 0:
			newContent = new FragmentRestaurantsList();
			fca.setTitle("Nearby");
			break;
			
		case 1:
			newContent = new FragmentMyCart();
			fca.setTitle("Search");
			break;
			
		case 2:
			newContent = new FragmentDeals();
			fca.setTitle("Deals");
			break;
			
		case 3:
			newContent = new FragmentAccountDetails();
			fca.setTitle("Account");
			break;
		case 4:
			Intent i=new Intent(getActivity(), ActivityMainTest.class);
			startActivity(i);
			break;

		}
		if (newContent != null)
			switchFragment(newContent);
	}

	private class SampleItem {
		public String tag;
		public int iconRes;

		public SampleItem(String tag, int iconRes) {
			this.tag = tag;
			this.iconRes = iconRes;
		}
	}

	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;

		if (getActivity() instanceof ActivityMain) {
			
			fca.switchContent(fragment);
			
		}

	}

	public class SampleAdapter extends ArrayAdapter<SampleItem> {

		public SampleAdapter(Context context) {
			super(context, 0);
		}

		public View getView(final int position, View view, ViewGroup parent) {
			if (view == null) {
				view = LayoutInflater.from(getContext()).inflate(R.layout.row,
						null);
			}
			ImageView icon = (ImageView) view.findViewById(R.id.row_icon);
			icon.setImageResource(getItem(position).iconRes);
			final TextView title = (TextView) view.findViewById(R.id.row_title);
			
			/*Typeface typeFace=Typeface.createFromAsset(fca.getAssets(),"LaoUI.ttf");
			title.setTypeface(typeFace);*/
			
			title.setText(getItem(position).tag);

			return view;
		}
	}
}
