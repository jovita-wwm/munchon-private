package com.simelabs.munchon.Fragments;


import java.util.ArrayList;

import com.simelabs.munchon.R;
import com.simelabs.munchon.Activities.ActivityRestaurantHome;
import com.simelabs.munchon.Activities.ActivityRestaurantMenu;
import com.simelabs.munchon.Adapters.AdapterRestaurantList;
import com.simelabs.munchon.DB.Restaurants;
import com.simelabs.munchon.Domain.PublicValues;
import com.simelabs.munchon.Domain.RestaurantDomain;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class FragmentRestaurantsList extends Fragment{

	 private View mContentView;
	 Context context;
	 ListView list;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 super.onCreateView(inflater, container, savedInstanceState);
		
		context=getActivity().getApplicationContext();
		
			 mContentView = inflater.inflate(R.layout.fragment_restaurantlist, container, false);
			  return mContentView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		 View v = getView();
		 
		list=(ListView)v.findViewById(R.id.lst_fragment_restaurantlist);
		
		Restaurants rest=new Restaurants(getActivity().getApplicationContext());
		//ArrayList<ArrayList<String>> reastaurants=rest.getDatafromRestaurants();
		ArrayList<RestaurantDomain> restaurants=PublicValues.allnearbyRestaurants;
		
		
		AdapterRestaurantList adpater=new AdapterRestaurantList(getActivity(), restaurants);
		list.setAdapter(adpater);
		
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent myIntent = new Intent(context,
						ActivityRestaurantHome.class);
				//myIntent.putExtra("restName", RestaurantName);
				myIntent.putExtra("userType", 0);
				startActivity(myIntent);

			}
		});
	}
}
