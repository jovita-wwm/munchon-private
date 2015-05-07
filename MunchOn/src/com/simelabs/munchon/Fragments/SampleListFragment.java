package com.simelabs.munchon.Fragments;

import com.simelabs.munchon.R;
import com.simelabs.munchon.Activities.ActivityMain;
import com.simelabs.munchon.Activities.ActivityPlaceOrder;
import com.simelabs.munchon.Activities.CreditcardSampleActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class SampleListFragment extends ListFragment {
	public String[] menus = { "Home", "My Cart", "Deals", "Payment", "Account" };

	public Integer[] icons = { R.drawable.icon_home, R.drawable.icon_basket,
			R.drawable.icon_deals, R.drawable.icon_payment,
			R.drawable.icon_account };

	ActivityMain fca;
	ImageView icon;
    Typeface tf,tfb;

	/*
	 * public SampleListFragment(Typeface tf, Typeface tfb) { this.tf = tf;
	 * this.tfb = tfb; }
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list, null);

	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		fca = (ActivityMain) getActivity();

		// View view = inflater.inflate(R.layout.deals, null);
		String fontPath = "fonts/LaoUI.ttf";
		String fontPathBold = "fonts/LaoUIb.ttf";

		// Loading Font Face
		tf = Typeface.createFromAsset(getActivity().getAssets(), fontPath);
		tfb = Typeface.createFromAsset(getActivity().getAssets(), fontPathBold);

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
			/*newContent = new FragmentMyCart();
			fca.setTitle("My Cart");*/
			Intent card = new Intent(getActivity(), CreditcardSampleActivity.class);
			startActivity(card);
			break;

		case 2:
			newContent = new FragmentDeals();
			fca.setTitle("Deals");
			break;

		case 3:
			/*
			 * newContent = new FragmentAccountDetails();
			 * fca.setTitle("Account");
			 */
			Intent i = new Intent(getActivity(), ActivityPlaceOrder.class);
			startActivity(i);
			break;

		case 4:
			newContent = new FragmentAccountDetails();
			fca.setTitle("Account");
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
			// LinearLayout selectionline = (LinearLayout)
			// view.findViewById(R.id.selection_line);

			icon = (ImageView) view.findViewById(R.id.row_icon);
			// ImageView strip = (ImageView) view.findViewById(R.id.imageView1);
			icon.setImageResource(getItem(position).iconRes);
			final TextView title = (TextView) view.findViewById(R.id.row_title);
			// final ImageView color = (ImageView)
			// view.findViewById(R.id.row_color);

			// selectionline.setBackgroundColor(getResources().getColor(R.color.black));
			title.setText(getItem(position).tag);

			title.setTypeface(tf);
			// color.setBackgroundColor(getResources().getColor(R.color.black));
			return view;
		}
	}
}
