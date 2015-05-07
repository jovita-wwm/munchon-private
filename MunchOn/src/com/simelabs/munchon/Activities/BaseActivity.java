package com.simelabs.munchon.Activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.simelabs.munchon.R;
import com.simelabs.munchon.Fragments.SampleListFragment;

public class BaseActivity extends SlidingFragmentActivity {

	private int mTitleRes;
	protected ListFragment mFrag;
	OnClickListener clickListener; 
	TextView actionBarTitle;
	DisplayMetrics dm;
	Typeface tf,tfb;
	ImageView drawer,settings;
	View actionBarView;
	
	public BaseActivity(int titleRes) {
		mTitleRes = titleRes;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle(mTitleRes);
		
		dm = getResources().getDisplayMetrics(); 
		
		ActionBar actionBar = getSupportActionBar();

		View actionBarView = getLayoutInflater().inflate(
		            R.layout.custom_action_bar, null);

		drawer = (ImageView) actionBarView.findViewById(R.id.btn_drawer);
		settings = (ImageView) actionBarView.findViewById(R.id.btn_settings);
        actionBarTitle = (TextView) actionBarView.findViewById(R.id.txt_title);
        
        String fontPath = "fonts/LaoUI.ttf";
		String fontPathBold = "fonts/LaoUIb.ttf";

		// Loading Font Face
		tf = Typeface.createFromAsset(getAssets(), fontPath);
		tfb = Typeface.createFromAsset(getAssets(), fontPathBold);

		actionBarTitle.setTypeface(tfb);
		
		
		
		 drawer.setOnClickListener(new OnClickListener() {
			 @Override
			 public void onClick(View v) {
			   toggle();   
			 }
			 });
		 
		 
		actionBar.setCustomView(actionBarView);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		if (savedInstanceState == null) {
			FragmentTransaction t = this.getSupportFragmentManager()
					.beginTransaction();
			mFrag = new SampleListFragment();
			t.replace(R.id.menu_frame, mFrag);
			t.commit();
		} else {
			mFrag = (ListFragment) this.getSupportFragmentManager()
					.findFragmentById(R.id.menu_frame);
		}

		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setBehindWidth((dm.widthPixels)/3);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		
		//getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	public void setActionBarTitle(String title)
	{
	    actionBarTitle.setText(title);
	    actionBarTitle.setTypeface(tfb);
	}

	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
