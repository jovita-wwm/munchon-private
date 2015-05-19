package com.simelabs.munchon.Activities;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.simelabs.munchon.R;
import com.simelabs.munchon.Beacon.MyService;
import com.simelabs.munchon.DB.AppController;
import com.simelabs.munchon.DB.Const;
import com.simelabs.munchon.Domain.BeaconDomain;
import com.simelabs.munchon.Domain.ImageHelper;
import com.simelabs.munchon.Domain.PublicValues;
import com.simelabs.munchon.Domain.RestaurantDomain;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
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
	Bitmap resultImage;
	private String tag_string_req = "string_req";
	int in,ListPosition;
	ArrayList<RestaurantDomain> restaurantDetails;
	 public ArrayList<BeaconDomain> allbeacons=new ArrayList<BeaconDomain>();
		

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.restaurant_home);
		pDialog = new ProgressDialog(this);
		pDialog.setMessage("Loading...");
		pDialog.setCancelable(false);

		restaurantDetails = PublicValues.allnearbyRestaurants;

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
		

		Intent intent = getIntent();
		in = intent.getIntExtra("position", 0);
		ListPosition = in;

		//setActionBarTitle(restaurantDetails.get(ListPosition).getName());

		Toast.makeText(getApplicationContext(),
				"position is " + String.valueOf(ListPosition), Toast.LENGTH_SHORT).show();

		new DownloadImage(RestaurantImage).execute(Const.RestImageUrl
				+ restaurantDetails.get(ListPosition).getRestaurantImage());
		
       /* ImageHelper imageRound = new ImageHelper();
		
		RestaurantImageRounded = imageRound.getRoundedCornerBitmap(resultImage, 50);
        RestaurantImage.setImageBitmap(RestaurantImageRounded);
       */ 
		new DownloadImage(imageRestLogo).execute(Const.RestLogoImageUrl
				+ restaurantDetails.get(ListPosition).getRestaurantlogo());
		
		
		txtRestAddress.setTypeface(tf);
		txtRestDescription.setTypeface(tf);
		btnDineIn.setTypeface(tfb);
		btnTakeAway.setTypeface(tfb);
		labelOpenTime.setTypeface(tf);
		labelCloseTime.setTypeface(tf);
		txtOpenTime.setTypeface(tfb);
		txtCloseTime.setTypeface(tfb);

		txtRestAddress.setText(restaurantDetails.get(ListPosition).getLongAddress());
		txtRestDescription.setText(restaurantDetails.get(ListPosition).getDescription());

		if ((restaurantDetails.get(ListPosition).getActivefrom().getMinutes() < 10)) {
			String appendMinutes = "0"
					+ restaurantDetails.get(ListPosition).getActivefrom().getMinutes();
			txtOpenTime.setText(restaurantDetails.get(ListPosition).getActivefrom()
					.getHours()
					+ ":" + appendMinutes);
		} else {
			txtOpenTime.setText(restaurantDetails.get(ListPosition).getActivefrom()
					.getHours()
					+ ":"
					+ restaurantDetails.get(ListPosition).getActivefrom().getMinutes());
		}

		if ((restaurantDetails.get(ListPosition).getActivetill().getMinutes() < 10)) {
			String appendMinutes = "0"
					+ restaurantDetails.get(ListPosition).getActivetill().getMinutes();
			txtCloseTime.setText(restaurantDetails.get(ListPosition).getActivetill()
					.getHours()
					+ ":" + appendMinutes);
		} else {
			txtOpenTime.setText(restaurantDetails.get(ListPosition).getActivetill()
					.getHours()
					+ ":"
					+ restaurantDetails.get(ListPosition).getActivetill().getMinutes());
		}
		// txtCloseTime.setText(restaurantDetails.get(i).getActivetill().getHours()+":"+restaurantDetails.get(i).getActivetill().getMinutes());
		// String valnow = dateFormat.format(cal.getTime());

		/*Toast.makeText(
				getApplicationContext(),
				restaurantDetails.get(i).getActivefrom().getHours() + ":"
						+ restaurantDetails.get(i).getActivefrom().getMinutes(),
				Toast.LENGTH_SHORT).show();
*/
		
		((ImageView) findViewById(R.id.btn_Call))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						String p = "tel:"
								+ restaurantDetails.get(ListPosition).getContacts();

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
				
				Intent myIntent = new Intent(getApplicationContext(),
						ActivityRestaurantMenu.class);
				myIntent.putExtra("userType", 0);
				myIntent.putExtra("position", ListPosition);
				startActivity(myIntent);

			}
		});

		btnTakeAway.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(getApplicationContext(),
						ActivityRestaurantMenu.class);
				
				myIntent.putExtra("userType", 1);
				startActivity(myIntent);
			}
		});

	}

	public String getTime(String time) {
		final SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");
		Date dateObj;
		String newDateStr = null;
		try {
			dateObj = df.parse(time);
			SimpleDateFormat fd = new SimpleDateFormat("hh:mm");
			newDateStr = fd.format(dateObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newDateStr;
	}

	// DownloadImage AsyncTask
		private class DownloadImage extends AsyncTask<String, Void, Bitmap> {
	 
			ProgressDialog mProgressDialog;
			ImageView bmImage;

			public DownloadImage(ImageView bmImage) {
				this.bmImage = bmImage;
			}

			
			public DownloadImage() {
				// TODO Auto-generated constructor stub
			}


			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				// Create a progressdialog
				//mProgressDialog = new ProgressDialog(ActivityRestaurantHome.this);
				
				// Set progressdialog message
				//mProgressDialog.setMessage("Please wait while loading...");

				//mProgressDialog.setIndeterminate(false);
				// Show progressdialog
				//mProgressDialog.show();
			}
	 
			@Override
			protected Bitmap doInBackground(String... URL) {
	 
				String imageURL = URL[0];
	 
				Bitmap bitmap = null;
				try {
					// Download Image from URL
					InputStream input = new java.net.URL(imageURL).openStream();
					// Decode Bitmap
					bitmap = BitmapFactory.decodeStream(input);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return bitmap;
			}
	 
			@Override
			protected void onPostExecute(Bitmap result) {
				// Set the bitmap into ImageView
				
				bmImage.setImageBitmap(result);
				resultImage = result;
				
				// Close progressdialog
			//	mProgressDialog.dismiss();
				
			}
		}
	

		String str_responce;
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
							 str_responce = response;
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
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PublicValues.allbeacons=allbeacons;
		Log.i("beacon table data", allbeacons+"");
		
		MyService s=new MyService(ActivityRestaurantHome.this);
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
