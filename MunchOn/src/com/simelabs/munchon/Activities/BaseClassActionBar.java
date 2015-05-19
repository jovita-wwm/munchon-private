package com.simelabs.munchon.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockListActivity;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.simelabs.munchon.R;
import com.simelabs.munchon.Beacon.MyService;
import com.simelabs.munchon.Domain.PublicValues;
import com.simelabs.munchon.Interfaces.InterfaceUpdateTable;

public class BaseClassActionBar extends SherlockActivity implements InterfaceUpdateTable {

	TextView tableno, basketitems, actionBarTitle; // action bar
	ImageView back, table, basket; // action bar
	private int mTitleRes;
	Typeface tf, tfb;
	public View actionBarView;
	public ActionBar actionBar;
	public static InterfaceUpdateTable update;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 
		MyService.setcallback(this);
		
		actionBarView = getLayoutInflater().inflate(
				R.layout.custom_actionbar_restaurant, null);

		actionBar = getSupportActionBar();
		
		String fontPath = "fonts/LaoUI.ttf";
		String fontPathBold = "fonts/LaoUIb.ttf";

		// Loading Font Face
		tf = Typeface.createFromAsset(getAssets(), fontPath);
		tfb = Typeface.createFromAsset(getAssets(), fontPathBold);

		back = (ImageView) actionBarView.findViewById(R.id.btn_back);
		table = (ImageView) actionBarView.findViewById(R.id.btn_table);
		basket = (ImageView) actionBarView.findViewById(R.id.btn_basket);
		tableno = (TextView) actionBarView.findViewById(R.id.txt_table_no);
		basketitems = (TextView) actionBarView
				.findViewById(R.id.txt_basket_items);
		actionBarTitle = (TextView) actionBarView.findViewById(R.id.txt_title);
		
		
		
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				finish();
			}
		});
		
		actionBar.setCustomView(actionBarView);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
	}

	public void setBasketVisibility() 
	{
		basket.setVisibility(View.VISIBLE);
		basketitems.setVisibility(View.VISIBLE);
	
	}

	/*public void updateTableNumber(String tablenumber)
	{
		table = (ImageView) actionBarView.findViewById(R.id.btn_table);
		tableno = (TextView) actionBarView.findViewById(R.id.txt_table_no);
		table.setVisibility(View.VISIBLE);
		tableno.setVisibility(View.VISIBLE);
		tableno.setText("8");
		tableno.setTypeface(tf);
	}*/
	
	public void updateBasketItemCount(String itemnumber)
	{
		basketitems.setText(itemnumber);
		basketitems.setTypeface(tf);
	}
	
	public void setActionBarTitle(String title) {
		actionBarTitle.setText(title);
		Toast.makeText(getApplicationContext(), "Called", Toast.LENGTH_SHORT)
				.show();
		actionBarTitle.setTypeface(tfb);
	}

	@Override
	public void updateTable(String num) {
		// TODO Auto-generated method stub
		table = (ImageView) actionBarView.findViewById(R.id.btn_table);
		tableno = (TextView) actionBarView.findViewById(R.id.txt_table_no);
		table.setVisibility(View.VISIBLE);
		tableno.setVisibility(View.VISIBLE);
		tableno.setText(num);
		tableno.setTypeface(tf);
	}

	
}
