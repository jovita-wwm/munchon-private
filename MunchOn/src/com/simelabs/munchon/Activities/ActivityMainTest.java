package com.simelabs.munchon.Activities;


import com.simelabs.munchon.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ActivityMainTest extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	public void address(View v)
	{
		Intent i=new Intent(getApplicationContext(), ActivityAccountAddLocation.class);
		startActivity(i);
	}
	public void home(View v)
	{
		Intent i=new Intent(getApplicationContext(), ActivityPlaceOrder.class);
		startActivity(i);
	}

}
