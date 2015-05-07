package com.simelabs.munchon.DB;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class Restaurants {
	
	Context context;
	public Restaurants(Context c) {
		// TODO Auto-generated constructor stub
		context=c;
	}

	public ArrayList<ArrayList<String>> getDatafromRestaurants()
	{
		ArrayList<ArrayList<String>> alldeatils=new ArrayList<ArrayList<String>>();
		ArrayList<String> names=new ArrayList<String>();
		ArrayList<String> status=new ArrayList<String>();
		ArrayList<String> rate=new ArrayList<String>();
		ArrayList<String> address=new ArrayList<String>();
		
		 try 
		 {
			JSONObject jsonObj = new JSONObject(loadJSONFromAsset());
			
			//JSONObject items=jsonObj.getJSONObject("restaurants");
			
			JSONArray itemarray=jsonObj.getJSONArray("restaurants");
			
			for(int i=0;i<itemarray.length();i++)
			{
				
				JSONObject Jsonitem = itemarray.getJSONObject(i);
				
				names.add(Jsonitem.getString("restaurantName"));
				status.add(Jsonitem.getString("active"));
				rate.add(Jsonitem.getString("rate"));
				
				address.add(Jsonitem.getString("restaurantAddress"));
				
			}
			
		} 
		 catch (JSONException e) 
		 {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
alldeatils.add(names);
alldeatils.add(status);
alldeatils.add(rate);
alldeatils.add(address); 
		 return alldeatils;
	}
	 public String loadJSONFromAsset() 
	   {
		    String json = null;
		    
		    try {

		        InputStream is = context.getAssets().open("Restaurants.json.txt");

		        int size = is.available();

		        byte[] buffer = new byte[size];

		        is.read(buffer);

		        is.close();

		        json = new String(buffer, "ISO-8859-1");


		    }
		    catch (IOException ex) {
		        ex.printStackTrace();
		        return null;
		    }
		    return json;

		}
}
