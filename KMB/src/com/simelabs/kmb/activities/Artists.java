package com.simelabs.kmb.activities;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.simelabs.kmb.launch.R;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Artists extends Fragment
{
	 
	 private View parentView;
	 private ListView listView;    
	 Context context;
	 ArrayList<String> artistName;
	 ArrayList<String> artistDescription;
	 ArrayList<Integer> artistId;
	 
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	    {
		 parentView = inflater.inflate(R.layout.artists_and_installations, container, false);
		  artistName = new ArrayList<String>();
		  artistDescription = new ArrayList<String>();
		  artistId =  new ArrayList<Integer>(); 
		 JSONObject obj;
		
		 try 
		 {
			JSONObject jsonObj = new JSONObject(loadJSONFromAsset());
			
			JSONObject items=jsonObj.getJSONObject("artists");
		//	Log.d("Inside item json", ""+items);
			
			JSONArray itemarray=items.getJSONArray("items");
		//	Log.d("Inside item json", ""+itemarray);
			
			for(int i=0;i<itemarray.length();i++)
			{
				
				JSONObject Jsonitem = itemarray.getJSONObject(i);
				
				String name = Jsonitem.getString("name");
				String description = Jsonitem.getString("description");
				String details=Jsonitem.getString("details");
				Integer artistid=Jsonitem.getInt("artistid");
				
				artistId.add(artistid);
				artistName.add(name);
				artistDescription.add(description);
				
			}
		} 
		 catch (JSONException e) 
		 {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		  
		  CustomList adapter = new CustomList(getActivity(),artistName,artistDescription,artistId);
			
			listView=(ListView) parentView.findViewById(R.id.artistsList);

			/*AnimationAdapter animationAdapter = new AlphaInAnimationAdapter(adapter);
	        animationAdapter.setAbsListView(listView);
	        listView.setAdapter(animationAdapter);
*/

			/* Animation animation_up = AnimationUtils.loadAnimation(getActivity(),
	      	            R.anim.slide_up);
			 animation_up.setDuration(700);
	              
	         listView.startAnimation(animation_up);   
	         listView.startAnimation(animation_up);*/
	        
   	        listView.setAdapter(adapter);
			
			
	    
		 return parentView;
	    }
	 
	   public String loadJSONFromAsset() 
	   {
		    String json = null;
		    
		    try {

		        InputStream is = getActivity().getAssets().open("artists.json.txt");

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

	    
