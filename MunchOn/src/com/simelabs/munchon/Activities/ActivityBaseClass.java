package com.simelabs.munchon.Activities;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.simelabs.munchon.R;
import com.simelabs.munchon.Fragments.FragmentSampleList;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class ActivityBaseClass extends SlidingFragmentActivity {

	private int mTitleRes;
	protected ListFragment mFrag;
	OnClickListener clickListener; 
	TextView actionBarTitle;
	
	public ActivityBaseClass(int titleRes) {
		mTitleRes = titleRes;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle(mTitleRes);
		
		
		ActionBar actionBar = getSupportActionBar();

		View actionBarView = getLayoutInflater().inflate(
		            R.layout.custom_action_bar, null);

		ImageView drawer = (ImageView) actionBarView.findViewById(R.id.btn_drawer);
		ImageView search = (ImageView) actionBarView.findViewById(R.id.btn_settings);
        actionBarTitle = (TextView) actionBarView.findViewById(R.id.txt_title);
		
		 drawer.setOnClickListener(new OnClickListener() {
			 @Override
			 public void onClick(View v) {
			   toggle();   
			 }
			 });
		 
		 
		actionBar.setCustomView(actionBarView);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

		/*actionBar.setDisplayUseLogoEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		*/
		/*getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setTitle("Now Showing");
	    //getSupportActionBar().setDisplayHomeAsUpEnabled(false);
	   // getSupportActionBar().setDisplayShowHomeEnabled(true);
	   // getSupportActionBar().setIcon(R.drawable.icon_drawer);
	    //getSupportActionBar().setDisplayShowTitleEnabled(true);
	    //getActionBar().setHomeAsUpIndicator(R.drawable.icon_drawer);

	    //getSupportActionBar().setIcon(R.drawable.icon_drawer);
	    //getSupportActionBar().setDisplayShowTitleEnabled(true);
	    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	    */
		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		if (savedInstanceState == null) {
			FragmentTransaction t = this.getSupportFragmentManager()
					.beginTransaction();
			mFrag = new FragmentSampleList();
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
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		
		//getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	public void setActionBarTitle(String title)
	{
	    actionBarTitle.setText(title);
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
