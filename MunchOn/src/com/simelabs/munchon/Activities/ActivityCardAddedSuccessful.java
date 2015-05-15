package com.simelabs.munchon.Activities;

import com.simelabs.munchon.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ActivityCardAddedSuccessful extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card_added_successful);
		
		
		
	}
	
	public void addAnotherCard(View v)
	{
		Intent card = new Intent(ActivityCardAddedSuccessful.this, ActivityCreditcardSample.class);
		startActivity(card);
	}
	public void backToOrder(View v)
	{
		
	}
}
