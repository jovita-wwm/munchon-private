package com.simelabs.munchon.Activities;

import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.internal.widget.IcsLinearLayout;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.simelabs.munchon.R;
import com.simelabs.munchon.Adapters.AdapterPickupTimeGrid;
import com.simelabs.munchon.Adapters.AdapterPlaceorderItemList;
import com.simelabs.munchon.Beacon.MyService;
import com.simelabs.munchon.DB.AppController;
import com.simelabs.munchon.DB.Const;
import com.simelabs.munchon.DB.ServiceRequests;
import com.simelabs.munchon.Domain.HttprequestsentFeedback;
import com.simelabs.munchon.Domain.PublicValues;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityPlaceOrder extends Activity implements OnClickListener,HttprequestsentFeedback{

	LinearLayout itemlist;
	ImageView emailstatus;
	Dialog changenumberdialog;
	int numberposition,newquantity;
	float totalamount=0;
	ImageView close;
	TextView txt_totalamount,txt_pickuptime;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_placeorder);
		itemlist=(LinearLayout)findViewById(R.id.lnr_orderd_items);
		emailstatus=(ImageView)findViewById(R.id.img_activity_placeorder_emailstatus);
		txt_totalamount=(TextView)findViewById(R.id.txt_totalamount);
		txt_pickuptime=(TextView)findViewById(R.id.txt_pickuptime);
	
		ServiceRequests.setcallback(this);
		
		ArrayList<ArrayList> items=new ArrayList<ArrayList>();
		
		ArrayList names=new ArrayList<>();
		ArrayList qty=new ArrayList<>();
		ArrayList price=new ArrayList<>();
		
		names.add("Green Vally");
		names.add("Mint Chip Ice Cream");
		names.add("Cheese Bite");
		names.add("Chocolate Ganache");
		names.add("Apple Crisps");
		names.add("Apple Lattice Fruit Bake");
		qty.add(2);
		qty.add(1);
		qty.add(2);
		qty.add(2);
		qty.add(4);
		qty.add(1);
		price.add(4.00);
		price.add(8.00);
		price.add(5.00);
		price.add(20.00);
		price.add(14.00);
		price.add(10.00);
		
		items.add(names);
		items.add(qty);
		items.add(price);
		
		/*AdapterPlaceorderItemList adapter=new AdapterPlaceorderItemList(ActivityPlaceOrder.this, items);
		itemlist.setAdapter(adapter);*/
		
		float servicecharge=Float.parseFloat("1.30");
		totalamount=totalamount+servicecharge;
    
		for( int i=0;i<items.get(0).size();i++)
		{
			final int pos=i;
			View v=LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_placeorder_items_listitem,null, false);
			
			TextView txtTitle = (TextView) v.findViewById(R.id.txt_item);
			TextView quantity=(TextView)v.findViewById(R.id.txt_quantity);
			TextView rate=(TextView)v.findViewById(R.id.txt_price);
			TextView total=(TextView)v.findViewById(R.id.txt_total);
			
			 txtTitle.setText(items.get(0).get(i).toString());
	    	  quantity.setText(items.get(1).get(i).toString());
	    	  rate.setText("$"+items.get(2).get(i).toString());
	    	  float r=Float.parseFloat(items.get(1).get(i).toString())*Float.parseFloat(items.get(2).get(i).toString());
	    	  total.setText("$"+r);
	    	  totalamount=totalamount+r;
	    	  
	    	  itemlist.addView(v);
	    	  RelativeLayout num=(RelativeLayout)v.findViewById(R.id.num);
	  		   num.setOnClickListener(new View.OnClickListener() {
	  			
	  			@Override
	  			public void onClick(View v) {
	  				// TODO Auto-generated method stub
	  				numberposition=pos;
	  			    changenumber();
	  				
	  			}
	  		});
		}
		txt_totalamount.setText("$"+totalamount);
		
		emailstatus.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(v.getTag().toString().equalsIgnoreCase("no"))
				{
					emailstatus.setBackgroundResource(R.drawable.yes);
					emailstatus.setTag("yes");
				}
				else
				{
					emailstatus.setBackgroundResource(R.drawable.no);
					emailstatus.setTag("no");
				}
			}
		});
	}
	
	
	public void pickup(View v)
	{
		final Dialog pickup=new Dialog(ActivityPlaceOrder.this);
		pickup.requestWindowFeature(Window.FEATURE_NO_TITLE);
		pickup.setContentView(R.layout.dialog_placeorder_pickuptime);
		pickup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(0,0,0,0)));
		//adding dynamic pickup times
		Calendar starttime,endtime = Calendar.getInstance();
		
		endtime.add(Calendar.HOUR, 8);
		Calendar temp=Calendar.getInstance();
		
		ArrayList<String> timelist=new ArrayList<String>();  
		while(temp.before(endtime))
		{
			
		
		Calendar current_time = (Calendar) temp.clone();	
		current_time.add(Calendar.MINUTE, 30);
		
		Calendar newtime=current_time;
		Log.i("time after 30 minutes", newtime+"");
		String zone="AM";
		if(newtime.get(Calendar.AM_PM)==1)
		{
			zone="PM";
		}
	//	Toast.makeText(getApplicationContext(), newtime.get(Calendar.HOUR)+":"+newtime.get(Calendar.MINUTE)+" "+zone+"\n"+"end: "+endtime.get(Calendar.HOUR), Toast.LENGTH_SHORT).show();
		String hour=newtime.get(Calendar.HOUR)+"";
		if(newtime.get(Calendar.HOUR)<10)
		{
			hour="0"+hour;
		}
		String minute=newtime.get(Calendar.MINUTE)+"";
		if(newtime.get(Calendar.MINUTE)<10)
		{
			minute="0"+minute;
		}
		timelist.add(hour+":"+minute+" "+zone);
		temp=newtime;
		}
		
		
		GridView timegrid=(GridView)pickup.findViewById(R.id.timegrid);
		AdapterPickupTimeGrid pickupadapter=new AdapterPickupTimeGrid(this, timelist);
		timegrid.setAdapter(pickupadapter);
		
		timegrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				txt_pickuptime.setText(view.getTag().toString());
			}
		});
		
		Button confirm=(Button)pickup.findViewById(R.id.timeconfirm);
		confirm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pickup.dismiss();
			}
		});
		ImageView close=(ImageView)pickup.findViewById(R.id.close);
		close.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pickup.dismiss();
			}
		});;
		pickup.show();
	}
	
	public void placeorder(View v)
	{
		/*stopService(new Intent(getApplicationContext(),MyService.class));
		//Disable bluetooth
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();    
		if (mBluetoothAdapter.isEnabled()) {
		    mBluetoothAdapter.disable(); 
		} */
		
		
		

		ArrayList<String> alldishes=new ArrayList<String>();
		alldishes.add("1");
		alldishes.add("2");
		alldishes.add("3");
		
		JSONObject jsonBody = new JSONObject();
		try {
			jsonBody.put("tableNo", "");
			jsonBody.put("restaurantId", 1);
			jsonBody.put("totalAmount", 78);
			jsonBody.put("customerID", 20);
			jsonBody.put("customerDeliveryTime", "2015-05-06 17:30:00");
			jsonBody.put("dineIn", 0);
			jsonBody.put("mailPaymentReceipt", 1);
			/*dishes*/
			
			
			
		} catch (JSONException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		JSONObject dish;
		JSONArray dishArray = new JSONArray();
		
		for(String i:alldishes)
		{
			 dish = new JSONObject();
			try {
				dish.put("parameter1_option_price", 5);
				dish.put("parameter5_Option", "");
				dish.put("parameter4_option_price", 0);
				dish.put("parameter4_Option", "");
				dish.put("parameter1_Option", "");
				dish.put("parameter3_Option", "");
				dish.put("parameter2_Option", "");
				dish.put("quantity", 4);
				dish.put("parameter_4", "");
				dish.put("parameter_3", "");
				dish.put("parameter_2", "");
				dish.put("parameter_1", "");
				dish.put("parameter_5", "");
				dish.put("dishName", "James Boags Premium");
				dish.put("price", 8);
				dish.put("parameter2_option_price", 1);
				dish.put("parameter5_option_price", 0);
				dish.put("cookingTime", "10");
				dish.put("parameter3_option_price", 0);
				dish.put("customerInstruction", "no remarks");
				dish.put("dishId", 11);
				dishArray.put(dish);
				
			} catch (JSONException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
		}
		
		try {
			jsonBody.put("dishes", dishArray);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.i("jsonbody",""+jsonBody);
		
		
		ServiceRequests request=new ServiceRequests(getApplicationContext());
		request.PostServiceRequest(Const.URL_place_order, jsonBody);
	
		
		/*
		JsonObjectRequest obj=new JsonObjectRequest(Request.Method.POST,Const.URL_place_order, jsonBody, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), arg0+"", Toast.LENGTH_SHORT).show();
				Log.i("responce",""+arg0);
				
			}
			
			
		}, new ErrorListener() {
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), arg0+"", Toast.LENGTH_SHORT).show();
				Log.i("responce",""+arg0);
			}
		});
		
		AppController.getInstance().addToRequestQueue(obj, "placeorder");*/
		
		
		final Dialog credit=new Dialog(ActivityPlaceOrder.this);
		credit.requestWindowFeature(Window.FEATURE_NO_TITLE);
		credit.setContentView(R.layout.dialog_creditcard_missing);
		credit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(0,0,0,0)));
		//adding dynamic credit
		credit.show();
		
		ImageView close=(ImageView)credit.findViewById(R.id.button_close);
		close.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				credit.dismiss();
			}
		});
		
		Intent i=new Intent(getApplicationContext(), ActivityPayment.class);
		startActivity(i);
		
	}
	
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		int id=v.getId();
		switch (id) {
		case R.id.txt_one:
			
			newquantity=1;
			changeQuantityInList();
			break;
			case R.id.txt_two:
				newquantity=2;
				changeQuantityInList();
			break;
			case R.id.txt_three:
				newquantity=3;
				changeQuantityInList();
				break;
			case R.id.txt_four:
				newquantity=4;
				changeQuantityInList();
				break;
			case R.id.txt_five:
				newquantity=5;
				changeQuantityInList();
				break;
			case R.id.txt_six:
				newquantity=6;
				changeQuantityInList();
				break;
			case R.id.txt_seven:
				newquantity=7;
				changeQuantityInList();
				break;
			case R.id.txt_eight:
				newquantity=8;
				changeQuantityInList();
				break;
			case R.id.txt_nine:
				newquantity=9;
				changeQuantityInList();
				break;
				case R.id.close:
					dismiss();
				break;
				

		default:
			break;
		}
		
		
	}
	
	private void changeQuantityInList() {
		// TODO Auto-generated method stub
		
		View row=itemlist.getChildAt(numberposition);
		TextView qty=(TextView)row.findViewById(R.id.txt_quantity);
		qty.setText(newquantity+"");
		TextView total=(TextView)row.findViewById(R.id.txt_total); 
		 totalamount=totalamount-Float.parseFloat(total.getText().toString().substring(1));
		TextView rate=(TextView)row.findViewById(R.id.txt_price);
		String s=rate.getText().toString().substring(1);
		
		 float r=newquantity*Float.parseFloat(s);
   	  total.setText("$"+r);
   	  
   	 
   	 totalamount=totalamount+Float.parseFloat(total.getText().toString().substring(1));
   	 txt_totalamount.setText("$"+totalamount);
		dismiss();
	}


	public void changenumber()
	{
		Toast.makeText(getApplicationContext(), "change no dialog", Toast.LENGTH_SHORT).show();
			changenumberdialog=new Dialog(ActivityPlaceOrder.this);
			changenumberdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			changenumberdialog.setContentView(R.layout.dialog_activity_placeorder_changenumber);
			changenumberdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(0,0,0,0)));
			changenumberdialog.show();
			
			
			close=(ImageView)changenumberdialog.findViewById(R.id.close);
			close.setOnClickListener(this);
			
			TextView one,two,three,four,five,six,seven,eight,nine;
			one=(TextView)changenumberdialog.findViewById(R.id.txt_one);
			two=(TextView)changenumberdialog.findViewById(R.id.txt_two);
			three=(TextView)changenumberdialog.findViewById(R.id.txt_three);
			four=(TextView)changenumberdialog.findViewById(R.id.txt_four);
			five=(TextView)changenumberdialog.findViewById(R.id.txt_five);
			six=(TextView)changenumberdialog.findViewById(R.id.txt_six);
			seven=(TextView)changenumberdialog.findViewById(R.id.txt_seven);
			eight=(TextView)changenumberdialog.findViewById(R.id.txt_eight);
			nine=(TextView)changenumberdialog.findViewById(R.id.txt_nine);
			one.setOnClickListener(this);
			
			two.setOnClickListener(this);
			three.setOnClickListener(this);
			four.setOnClickListener(this);
			five.setOnClickListener(this);
			six.setOnClickListener(this);
			seven.setOnClickListener(this);
			eight.setOnClickListener(this);
			nine.setOnClickListener(this);
			
	}
	
	public void dismiss()
	{
		changenumberdialog.dismiss();
	}


	@Override
	public void oncomplete(String name) {
		// TODO Auto-generated method stub
		
		Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
	}
}
