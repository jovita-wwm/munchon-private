package com.simelabs.munchon.Adapters;

import java.util.ArrayList;

import com.simelabs.munchon.R;
import com.simelabs.munchon.Activities.ActivityPayment;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class AdapterCardlist
extends BaseAdapter
{
	private final Activity context;
	ArrayList<Integer> item;
	Integer count;
	ArrayList la;
	ArrayList bd;
	public int currentpositions;
	public static String edt_cvv;
	 RadioButton rdo;
	EditText cvv;

	public AdapterCardlist(Activity context,ArrayList<Integer> names) 
	{
	this.context = context;
	this.item = names;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return item.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView= inflater.inflate(R.layout.activity_payment_carlist_item, null, true);
		rowView.setOnClickListener(new OnItemClickListener(position,rowView));
		 cvv=(EditText)rowView.findViewById(R.id.edt_activity_payment_listitem_cvv);
		  rdo=(RadioButton)rowView.findViewById(R.id.rdo_activity_payment_listitem_radio);
		  cvv.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				edt_cvv=s.toString()+"@@@"+position;
			}
		});
		  
		if(position==currentpositions)
		{
			
			 cvv.setVisibility(View.VISIBLE);
			 if(edt_cvv!=null)
			 {
			  String[] edt=edt_cvv.split("@@@");
			  if(Integer.parseInt(edt[1])==currentpositions)
			  {
				  cvv.setText(edt[0]);
			  }
			 }
		      
		      rowView.setBackgroundColor(context.getResources().getColor(R.color.txt_graylight));
		       rowView.setSelected(true);
		      
				rdo.setChecked(true);
		}
    	  
    	 
    	  return rowView;
	}

	  /********* Called when Item click in ListView ************/
    private class OnItemClickListener  implements OnClickListener{           
        private int mPosition;
        View v;
        OnItemClickListener(int position,View vew){
        	 mPosition = position;
        	 v=vew;
        }
        
        @Override
        public void onClick(View v) {
        	
        	edt_cvv=null;
      	  ActivityPayment sct = (ActivityPayment)context ;
          
      	sct.onItemClick(mPosition,v);
      	currentpositions=mPosition;
      	
        	
        	Toast.makeText(context.getApplicationContext(), "clicked in adapter", Toast.LENGTH_SHORT).show();
       EditText cvv=(EditText)v.findViewById(R.id.edt_activity_payment_listitem_cvv);
       cvv.setVisibility(View.VISIBLE);
    v.setBackgroundColor(context.getResources().getColor(R.color.txt_graylight));
       v.setSelected(true);
       RadioButton rdo=(RadioButton)v.findViewById(R.id.rdo_activity_payment_listitem_radio);
		rdo.setChecked(true);
        	
        
        	
        	
        }               
    }  

	
	
	}