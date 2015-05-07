package com.simelabs.munchon.Activities;

import java.util.ArrayList;

import com.simelabs.munchon.R;
import com.simelabs.munchon.Adapters.AdapterCardlist;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

public class ActivityPayment extends Activity{
	
	ListView cardlist;
	EditText cvv;
	AdapterCardlist adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_payment);
		
		cardlist=(ListView)findViewById(R.id.activity_payment_cardlist);
		
		ArrayList<Integer> ary=new ArrayList<Integer>();
		ary.add(1);
		ary.add(2);
		ary.add(3);
		ary.add(4);
		ary.add(5);
		ary.add(6);
		ary.add(7);
		ary.add(8);
		ary.add(9);
		ary.add(10);
		
		 adapter=new AdapterCardlist(ActivityPayment.this, ary);
		cardlist.setAdapter(adapter);
		
		/*cardlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				cardlist.setItemChecked(position, true);
				cvv=(EditText)view.findViewById(R.id.edt_activity_payment_listitem_cvv);
				cvv.setVisibility(View.VISIBLE);
				Toast.makeText(getApplicationContext(), "clicked", Toast.LENGTH_SHORT).show();
				
			}
		});*/
	}

	
	public void onItemClick(int p,View v)
	{
		for(int i=0;i<cardlist.getChildCount();i++)
		{
			adapter.notifyDataSetChanged();
			/*View vew=cardlist.getChildAt(i);
			vew.setBackgroundColor(getResources().getColor(R.color.White));
			EditText cvv=(EditText)findViewById(R.id.edt_activity_payment_listitem_cvv);
			cvv.setVisibility(View.INVISIBLE);
			RadioButton rdo=(RadioButton)findViewById(R.id.rdo_activity_payment_listitem_radio);
			rdo.setChecked(false);*/
			
		}
		
	}
	
	 public String edcvv;
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		edcvv=adapter.edt_cvv;
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

}
