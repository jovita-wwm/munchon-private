package com.simelabs.munchon.DB;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.simelabs.munchon.Domain.BeaconDomain;
import com.simelabs.munchon.Domain.PublicValues;
import com.simelabs.munchon.Domain.RestaurantDomain;
import com.simelabs.munchon.Interfaces.InterfaceHttprequestsentFeedback;

public class ServiceRequests {

	private String tag_string_req = "string_req";
	Context context;
	 String str_responce;
	 public ArrayList<RestaurantDomain> allrestuarants=new ArrayList<RestaurantDomain>();
	 public ArrayList<BeaconDomain> allbeacons=new ArrayList<BeaconDomain>();
	 static InterfaceHttprequestsentFeedback feedback;
	public ServiceRequests(Context cotxt) {
		// TODO Auto-generated constructor stub
	context=cotxt;
	}

	/**
	 * Making json object request
	 * */
	public void makeStringReq(String request) {
		//showProgressDialog();

		 
		StringRequest strReq = new StringRequest(Method.GET,
				request, new Response.Listener<String>() {

				@Override
					public void onResponse(String response) {
						Log.d("db connect for restaurant list", response.toString());
						//Toast.makeText(getApplicationContext(), response.toString()+"", Toast.LENGTH_SHORT).show();
					//	PublicValues.restaurantresponce=response;
						//readBeaconJson(response.toString());
						feedback.oncomplete(response);
						str_responce=response;
						hideProgressDialog();

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d("db connect for beacon list", "Error: " + error.getMessage());
						feedback.oncomplete("error");
						
						
						hideProgressDialog();
					}
				});

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
		

	}

	public void PostServiceRequest(String url,JSONObject jsonBody)
	{
		JsonObjectRequest obj=new JsonObjectRequest(Request.Method.POST,url, jsonBody, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(context, arg0+"", Toast.LENGTH_SHORT).show();
				Log.i("responce",""+arg0);
				feedback.oncomplete(arg0+"");
			}
			
			
		}, new ErrorListener() {
			
			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(context, arg0+"", Toast.LENGTH_SHORT).show();
				Log.i("responce",""+arg0);
				feedback.oncomplete("error");
			}
		});
		
		AppController.getInstance().addToRequestQueue(obj, "post");
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
	

	
}

	public void readRestaurantJson(String responce) {
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
				
				Toast.makeText(context, restaurant.getName(), Toast.LENGTH_SHORT).show();
				allrestuarants.add(restaurant);
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
		
		PublicValues.allnearbyRestaurants=allrestuarants;
		Toast.makeText(context, "no:"+allrestuarants.size(), Toast.LENGTH_SHORT).show();
		Log.i("table data", allrestuarants+"");
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
	
	
	public static void setcallback(InterfaceHttprequestsentFeedback instance) {

		feedback = instance;

	}
	
}
