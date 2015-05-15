package com.simelabs.munchon.Fragments;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragment;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.simelabs.munchon.R;
import com.simelabs.munchon.Adapters.AdapterDealsList;
import com.simelabs.munchon.Adapters.DealsListAdapter;
import com.simelabs.munchon.DB.AppController;
import com.simelabs.munchon.DB.Const;
import com.simelabs.munchon.Domain.DealsDomain;
import com.simelabs.munchon.Domain.ImageHelper;
import com.simelabs.munchon.Domain.PublicValues;
import com.simelabs.munchon.Domain.RestaurantDomain;
import com.simelabs.munchon.Network.Internet;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyDealsFragment extends ListFragment {
	
	ListView list;
	SlidingMenu sm;

	private String tag_string_mydeals_req = "tag_string_mydeals_req";
	public ArrayList<DealsDomain> userDealsList;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.dealslist, null);

		int id = 1;                //Get user id 
		String URL = Const.URL_MY_DEALS_LIST;   //Append user id to url 
		userDealsList = new ArrayList<DealsDomain>();
		
		Internet net = new Internet(getActivity());
		boolean netstatus = net.isAvailable();
		if (netstatus != false) {

			makeStringReq(URL);
		} else {
			Toast.makeText(getActivity(),
					"Please check network connection!", Toast.LENGTH_SHORT)
					.show();

		}
		
		
		sm = new SlidingMenu(getActivity());

		
		return view;
	}

	/**
	 * Making json object request
	 * */
	private void makeStringReq(String url) {

		StringRequest strReq = new StringRequest(Method.GET, url,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						Log.d("Retrieving user deals list ", response.toString());
						PublicValues.userDealsListResponse = response;
						readMyDealsJson(response.toString());

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d("Retrieving user deals list error", "Error: "
								+ error.getMessage());

					}
				});

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(strReq,
				tag_string_mydeals_req);

	}
	
	protected void readMyDealsJson(String response) {
		// TODO Auto-generated method stub
		
		try 
		 {
			JSONObject jsonObj = new JSONObject(response);
			
			JSONArray itemarray=jsonObj.getJSONArray("deals");
		    Log.d("Deals Inside item json", ""+itemarray);
			
			for(int i=0;i<itemarray.length();i++)
			{
				
				JSONObject Jsonitem = itemarray.getJSONObject(i);
				
				DealsDomain mydeals =new DealsDomain();
				
				mydeals.setDealID(Jsonitem.getInt("dealID"));
				mydeals.setRestaurantID(Jsonitem.getInt("restaurantID"));
				mydeals.setCouponCode(Jsonitem.getString("couponCode"));
				mydeals.setMinimumPurchase(Jsonitem.getDouble("minimumPurchase"));
				mydeals.setUserID(Jsonitem.getInt("userID"));
				mydeals.setImage(Jsonitem.getString("image"));
				mydeals.setDiscount(Jsonitem.getDouble("discount"));
				mydeals.setRestaurantName(Jsonitem.getString("restaurantName"));
				mydeals.setDealValidityDate(Jsonitem.getString("dealValidityDate"));

				userDealsList.add(mydeals);
			}
			
		} 
		 catch (JSONException e) 
		 {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PublicValues.userDeals=userDealsList;
		Toast.makeText(getActivity(), "no:"+userDealsList.size(), Toast.LENGTH_SHORT).show();
		Log.i("table data", userDealsList+"");
		
		AdapterDealsList
		adapter = new AdapterDealsList(getActivity(),userDealsList);
		
		setListAdapter(adapter);
        
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onResume() {
		super.onResume();

		if (sm.isMenuShowing()) {
			sm.showContent();
		}
	}
}
