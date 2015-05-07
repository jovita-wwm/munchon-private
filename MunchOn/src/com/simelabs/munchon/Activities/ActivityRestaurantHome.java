package com.simelabs.munchon.Activities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.simelabs.munchon.R;
import com.simelabs.munchon.Beacon.MyService;
import com.simelabs.munchon.DB.AppController;
import com.simelabs.munchon.DB.Const;
import com.simelabs.munchon.DB.ServiceRequests;
import com.simelabs.munchon.Domain.BeaconDomain;
import com.simelabs.munchon.Domain.ImageHelper;
import com.simelabs.munchon.Domain.PublicValues;
import com.simelabs.munchon.Domain.RestaurantDomain;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityRestaurantHome extends BaseClassActionBar {

	Button btnDineIn, btnTakeAway;
	TextView txtRestName, txtRestAddress, txtOpenTime, txtCloseTime,
			labelOpenTime, labelCloseTime, txtRestDescription, actionBarTitle;
	ImageView call, direction, RestaurantImage, imageRestLogo;
	Bitmap RestaurantImageRounded;

	String RestaurantName = "Slide Lounge";
	String Address = "41 Oxford Street | On the Hyde Park, Sydney, New South Wales 2010, Australia";
	String RestaurantDescription = "Serves an outstanding New American-Swedish menu with a touch of Asian influence";
	String PhoneNum = "+918086828722";
	
	private String tag_string_req = "string_req";
	 String str_responce;
	 public ArrayList<BeaconDomain> allbeacons=new ArrayList<BeaconDomain>();
	
	 
	 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.restaurant_home);

		pDialog = new ProgressDialog(this);
		pDialog.setMessage("Loading...");
		pDialog.setCancelable(false);

		
		
		String fontPath = "fonts/LaoUI.ttf";
		String fontPathBold = "fonts/LaoUIb.ttf";

		// Loading Font Face
		Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
		Typeface tfb = Typeface.createFromAsset(getAssets(), fontPathBold);

		RestaurantImage = (ImageView) findViewById(R.id.imageRest);

		imageRestLogo = (ImageView) findViewById(R.id.img_RestaurantLogo);

		txtRestAddress = (TextView) findViewById(R.id.txt_RestaurantAddress);
		labelOpenTime = (TextView) findViewById(R.id.label_opens);
		labelCloseTime = (TextView) findViewById(R.id.label_closes);
		txtOpenTime = (TextView) findViewById(R.id.txt_OpeningTime);
		txtCloseTime = (TextView) findViewById(R.id.txt_ClosingTime);
		txtRestDescription = (TextView) findViewById(R.id.txt_RestaurantDescription);

		btnDineIn = (Button) findViewById(R.id.btn_DineIn);
		btnTakeAway = (Button) findViewById(R.id.btn_TakeAway);

		
		imageRestLogo.setImageResource(R.drawable.rest_logo_one);
		
		setActionBarTitle(RestaurantName);
		
		txtRestAddress.setTypeface(tf);
		txtRestDescription.setTypeface(tf);
		btnDineIn.setTypeface(tfb);
		btnTakeAway.setTypeface(tfb);
		labelOpenTime.setTypeface(tf);
		labelCloseTime.setTypeface(tf);
		txtOpenTime.setTypeface(tfb);
		txtCloseTime.setTypeface(tfb);

		txtRestAddress.setText(Address);

		txtRestDescription.setText(RestaurantDescription);

		Bitmap icon = BitmapFactory.decodeResource(getApplicationContext()
				.getResources(), R.drawable.rest_two);
		ImageHelper image = new ImageHelper();
		RestaurantImageRounded = image.getRoundedCornerBitmap(icon, 50);

		RestaurantImage.setImageBitmap(RestaurantImageRounded);

		((ImageView) findViewById(R.id.btn_Call))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						String p = "tel:" + PhoneNum;

						Intent callIntent = new Intent(Intent.ACTION_CALL);
						callIntent.setData(Uri.parse(p));
						startActivity(callIntent);
					}
				});

		((ImageView) findViewById(R.id.btn_Directions))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Toast.makeText(getApplicationContext(),
								"clicked on direction button",
								Toast.LENGTH_SHORT).show();
					}
				});

		btnDineIn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				makeStringReq(Const.URL_beacon_request, "beacon");
				//ServiceRequests service=new ServiceRequests(getApplicationContext());
				//service.makeStringReq(Const.URL_beacon_request, "beacon");
				Intent myIntent = new Intent(getApplicationContext(),
						ActivityRestaurantMenu.class);
				myIntent.putExtra("restName", RestaurantName);
				myIntent.putExtra("userType", 0);
				startActivity(myIntent);

			}
		});

		btnTakeAway.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(getApplicationContext(),
						ActivityRestaurantMenu.class);
				myIntent.putExtra("restName", RestaurantName);
				myIntent.putExtra("userType", 1);
				startActivity(myIntent);
			}
		});

	}
	
	public void makeStringReq(String request,String type) {
		//showProgressDialog();

		 
		StringRequest strReq = new StringRequest(Method.GET,
				request, new Response.Listener<String>() {

				@Override
					public void onResponse(String response) {
						Log.d("db connect for restaurant list", response.toString());
						//Toast.makeText(getApplicationContext(), response.toString()+"", Toast.LENGTH_SHORT).show();
					//	PublicValues.restaurantresponce=response;
						readBeaconJson(response.toString());
						str_responce=response;
						hideProgressDialog();

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d("db connect for beacon list", "Error: " + error.getMessage());
						hideProgressDialog();
					}
				});

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
		

	}

public void readBeaconJson(String responce)
{

	try 
	 {
		JSONObject jsonObj = new JSONObject(responce);
		
		//JSONObject items=jsonObj.getJSONObject("artists");
	//	Log.d("Inside item json", ""+items);
		
		JSONArray itemarray=jsonObj.getJSONArray("beaconVersionDetails");
	//	Log.d("Inside item json", ""+itemarray);
		
		for(int i=0;i<itemarray.length();i++)
		{
			
			JSONObject Jsonitem = itemarray.getJSONObject(i);
			
			BeaconDomain beacon=new BeaconDomain();
			
			beacon.setTableName(Jsonitem.getString("tableName"));
			beacon.setUuid(Jsonitem.getString("uuid"));
			beacon.setMajor(Jsonitem.getInt("major"));
			beacon.setMinor(Jsonitem.getInt("minor"));
			beacon.setEntryMessage(Jsonitem.getString("entryMessage"));
			beacon.setExitMessage(Jsonitem.getString("exitMessage"));
			beacon.setRestaurantID(Jsonitem.getInt("restaurantId"));
			beacon.setUpdatedUser(Jsonitem.getString("updatedUser"));
			beacon.setDescription(Jsonitem.getString("description"));
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss"); 
			Date updatedon = dateFormat.parse(Jsonitem.getString("updated_at"));
			
			
			allbeacons.add(beacon);
		}
		
	} 
	 catch (JSONException e) 
	 {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	PublicValues.allbeacons=allbeacons;
	Log.i("beacon table data", allbeacons+"");
	startService(new Intent(getApplicationContext(),MyService.class));

	
}


private ProgressDialog pDialog;
private void showProgressDialog() {
	if (!pDialog.isShowing())
		pDialog.show();
}
private void hideProgressDialog() {
	if (pDialog.isShowing())
		pDialog.dismiss();
}
}
