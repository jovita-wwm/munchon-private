package com.simelabs.munchon.Activities;


import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import com.simelabs.munchon.Domain.PublicValues;
import com.simelabs.munchon.Domain.RestaurantDomain;
import com.simelabs.munchon.Location.GPSTracker;
import com.simelabs.munchon.Network.Internet;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
/**
 * 
 * @author Jovita P J created on:17-March-2015
 * 
 *         Splash screen and checks internet connection ,downloads all files if
 *         net available
 */
public class ActivitySplash extends Activity{

	private String tag_string_req = "string_req";
	public ArrayList<RestaurantDomain> allrestuarants=new ArrayList<RestaurantDomain>();
	String lat="47.468449",lng="19.057197";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splashh);
		
	
		pDialog = new ProgressDialog(this);
		pDialog.setMessage("Loading...");
		pDialog.setCancelable(false);

		//getTime(ActivityMain.class);
		
		Internet net = new Internet(getApplicationContext());
		boolean netstatus = net.isAvailable();
		if (netstatus != false) {
			
			
			getTime(ActivityMain.class);
		}
		 else {
				Toast.makeText(getApplicationContext(), "You Need Internet for this Application",
						Toast.LENGTH_SHORT).show();
			
				getTime(null);
		 		}
	}


	private void getTime(final Class class1) {
		// TODO Auto-generated method stub
		
Handler handler = new Handler();
		
		handler.postDelayed(new Runnable() {
		    public void run() {
		    	
		    	if(class1!=null)
		    	{
		    		//enable bluetooth
		    		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();    
		    		if (!mBluetoothAdapter.isEnabled()) {
		    		    mBluetoothAdapter.enable(); 
		    		} 
		    		getLocation();
		    		makeStringReq(class1);
		    	
		    	}
		    	
		    }
		},3000);
		
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
	
	/**
	 * Making json object request
	 * */
	private void makeStringReq(final Class c) {
		//showProgressDialog();

		String url=Const.URL_nearby_Restaurants_request+"//"+lat+"//"+lng;
		Log.i("restaurant request", url);
		Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();
		StringRequest strReq = new StringRequest(Method.GET,
				url, new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						Log.d("db connect for restaurant list", response.toString());
						//Toast.makeText(getApplicationContext(), response.toString()+"", Toast.LENGTH_SHORT).show();
						PublicValues.restaurantresponce=response;
						readJson(response.toString(),c);
						ServiceRequests service=new ServiceRequests(getApplicationContext());
						hideProgressDialog();

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d("db connect for restaurant list", "Error: " + error.getMessage());
						hideProgressDialog();
					}
				});

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

	}


	protected void readJson(String responce,Class class1) {
		// TODO Auto-generated method stub
		
		try 
		 {
			JSONObject jsonObj = new JSONObject(responce);
			
			//JSONObject items=jsonObj.getJSONObject("artists");
		//	Log.d("Inside item json", ""+items);
			
			JSONArray itemarray=jsonObj.getJSONArray("restaurants");
		//	Log.d("Inside item json", ""+itemarray);
			
			for(int i=0;i<itemarray.length();i++)
			{
				
				JSONObject Jsonitem = itemarray.getJSONObject(i);
				
				RestaurantDomain restaurant=new RestaurantDomain();
				restaurant.setId(Jsonitem.getInt("restaurantId"));
				restaurant.setName(Jsonitem.getString("restaurantName"));
				restaurant.setActive(Jsonitem.getString("active"));
				//restaurant.setActivefrom(Jsonitem.getString("activeFrom"));
			//	restaurant.setActivetill(Jsonitem.getString("activeTill"));
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss"); 
				Date activefrom = dateFormat.parse(Jsonitem.getString("activeFrom"));
				Date activetill = dateFormat.parse(Jsonitem.getString("activeTill"));
				
				restaurant.setActivefrom(activefrom);
				restaurant.setActivetill(activetill);
				
				
				restaurant.setCity(Jsonitem.getString("city"));
				//restaurant.setContacts(Jsonitem.getLong("restaurantPhone"));
				restaurant.setCountry(Jsonitem.getString("country"));
				//restaurant.setCountrycode(Jsonitem.getInt("countryCode"));
				//restaurant.setCreatedon(Jsonitem.getDouble("restaurantCreatedOn"));
				restaurant.setDescription(Jsonitem.getString("description"));
				//restaurant.setLatitude(Jsonitem.getLong("latitude"));
				restaurant.setLongAddress(Jsonitem.getString("restaurantLongAddress"));
				//restaurant.setLongitude(Jsonitem.getLong("longitude"));
				//restaurant.setMailId(Jsonitem.getString("restaurantMailId"));
				//restaurant.setNooftable(Jsonitem.getInt("numberOfTables"));
				restaurant.setRate(Jsonitem.getInt("diningCostRate"));
				restaurant.setRestaurantImage(Jsonitem.getString("restaurantImage"));
				restaurant.setRestaurantlogo(Jsonitem.getString("restaurantLogo"));
				restaurant.setShortAddress(Jsonitem.getString("restaurantShortAddress"));
				restaurant.setStatus(Jsonitem.getString("todaysStatus"));
				//restaurant.setUpdatedon(Jsonitem.getDouble("restaurantUpdatedOn"));
				restaurant.setUpdateduser(Jsonitem.getString("updatedUser"));
				
				Toast.makeText(getApplicationContext(), restaurant.getName(), Toast.LENGTH_SHORT).show();
				allrestuarants.add(restaurant);
			}
			
			//trs
		} 
		 catch (JSONException e) 
		 {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			Intent i = new Intent(getApplicationContext(), class1);
			startActivity(i);
			ActivitySplash.this.finish();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Intent i = new Intent(getApplicationContext(), class1);
			startActivity(i);
			ActivitySplash.this.finish();
		}
		
		PublicValues.allnearbyRestaurants=allrestuarants;
		Toast.makeText(getApplicationContext(), "no:"+allrestuarants.size(), Toast.LENGTH_SHORT).show();
		Log.i("table data", allrestuarants+"");
		
		
		Intent i = new Intent(getApplicationContext(), class1);
		startActivity(i);
		ActivitySplash.this.finish();
	}
	
	public void getLocation()
	{
		LocationManager	locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		Location location = new Location(locationManager.GPS_PROVIDER);
		List<Address> addresses = null;

		Geocoder geocoder = new Geocoder(this, Locale.getDefault());
		String errorMessage = "";
		
		GPSTracker gpsTracker = new GPSTracker(ActivitySplash.this);
		
		 if (gpsTracker.canGetLocation())
	        {
			// String stringLatitude = String.valueOf(gpsTracker.latitude);
			// String stringLongitude = String.valueOf(gpsTracker.longitude);
			  lat=""+gpsTracker.latitude;
			  lng=""+gpsTracker.longitude;
	        }
		 else
			 Toast.makeText(getApplicationContext(), "Not able to get Location", Toast.LENGTH_SHORT).show();
			 
	   
	}
	}