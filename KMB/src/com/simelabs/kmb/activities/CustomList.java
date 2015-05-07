

package com.simelabs.kmb.activities;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.simelabs.kmb.activities.Artists;
import com.simelabs.kmb.activities.MainActivity;
import com.simelabs.kmb.domain.LocationDetails;
import com.simelabs.kmb.launch.R;
import com.simelabs.kmb.locationmap.ArtistMap;
import com.simelabs.kmb.managers.DialogArrayAdapter;
import com.simelabs.kmb.managers.JsonManager;
import com.simelabs.kmb.network.Internet;
import com.simelabs.kmb.service.PublicValues;
import com.simelabs.kmb.spotbeak.BeaconDetailViewActivity;
import com.simelabs.kmb.spotbeak.Beacondetails;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources.Theme;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomList extends ArrayAdapter<String>

{
	HashMap<String, ArrayList<Beacondetails>> locationbeacons=new HashMap<String, ArrayList<Beacondetails>>();
	private final Activity context;
	ArrayList<String> names;
	ArrayList<String> des;
	ArrayList<Integer> artistId;
	Integer count;
	ArrayList<LocationDetails> la;
	ArrayList<Beacondetails> bd;
	  
	public CustomList(Activity context,ArrayList<String> names,ArrayList<String> des,ArrayList<Integer> artistId) 
	{
	super(context, R.layout.artist_list, names);
	this.context = context;
	this.names = names;
	this.des = des;
	this.artistId = artistId;
	}
	
	
	@Override
	public View getView(final int position, View view, ViewGroup parent)
	{
	LayoutInflater inflater = context.getLayoutInflater();
	View rowView= inflater.inflate(R.layout.artist_list, null, true);
	
	TextView txtTitle = (TextView) rowView.findViewById(R.id.txt_name);
	final TextView txtDesc = (TextView) rowView.findViewById(R.id.txt_description);
	
	TextView map = (TextView) rowView.findViewById(R.id.txt_map);
	TextView details = (TextView) rowView.findViewById(R.id.txt_details);
	
	txtTitle.setText(names.get(position));
	txtDesc.setText(des.get(position));
	map.setTag(artistId);
	details.setTag(artistId);

	
	map.setOnClickListener(new View.OnClickListener() {

	  @Override
	  public void onClick(View v)
	  {
		Internet net = new Internet(context);
		 boolean netstatus = net.isAvailable();
		if (netstatus != false)
		{
		getdata(position,v);
		  showDialog(position,v);
		  //Toast.makeText(context, "Map  "+artistId.get(position), Toast.LENGTH_LONG).show();
		}
		else
			Toast.makeText(context, "Check Network Connection", Toast.LENGTH_SHORT).show();
	  }
	   
	});
	


	details.setOnClickListener(new View.OnClickListener() {

	  @Override
	  public void onClick(View v) 
	  {
		  Internet net = new Internet(context);
			 boolean netstatus = net.isAvailable();
			if (netstatus != false)
			{
		  getdata(position,v);
		  showDialog(position,v);
			}
			else
				Toast.makeText(context, "Check Network Connection", Toast.LENGTH_SHORT).show();	
	  }

	});
	
	return rowView;
	}
	
	public void getdata(int position,View v)
	{
		 ArrayList<Beacondetails> beacondetailarray  =  PublicValues.beacondetails; //get all details from beacons.json
	     bd = new ArrayList<Beacondetails>();  //new arraylist 
		    
		    for(Beacondetails b:beacondetailarray) //iterate through all items in json
		    {
		    	if((b.getArtistid())==(artistId.get(position)))  //check whether each id in beacons json is equal to our artist id
		    	{
		    		bd.add(b);  // if yes,add all those id from beacons json to new arraylist....
		    		            //  iterates through all the items in beacons json and finds all entries of the selected artist
		    	}
		    }
		    
		    //now bd contains all installations of clicked artist
		    
		    JsonManager Jmanager = new JsonManager(context.getApplicationContext());
		    ArrayList<String> locationdata=Jmanager.getDatafromLocation(context,"activityArtist");
		    ArrayList<LocationDetails> locationarray = PublicValues.alllocationdetails;
		    la = new ArrayList<LocationDetails>();  // new arraylist for location
		    
		    ArrayList<LocationDetails> listname = new ArrayList<LocationDetails>(); //arraylist for getting name of location
		   
		    for(LocationDetails lo :locationarray)
		    {
		    	  for(Beacondetails be:bd) 
		    	{
		    		if(be.getLocationid()==lo.getId())  // get location id of each items in new list and
		    			                               //  check whether it is equal to  the location id of the installations we have
		    		{
		    			
		    			if(listname.size()>0)    // if new list size is greater than 0,
		    			{
		    				boolean l=false;
		    				for(LocationDetails listn:listname)  // iterate through all elements in new list
		    				{
		    					if(listn==lo)        
		    					{
		    						l=true;
		    						break;
		    					}
		    					
		    				}
		    			if(l==false)
		    			{
		    			la.add(lo);
		    			la.get(0).getName();
		    			}
		    			}
		    			else
		    			{
		    				//listname.add(lo);
		    				la.add(lo);
			    			la.get(0).getName();
		    			}
		    				
		    		}
		    	}
		    }
		
		    locationbeacons.clear();
		    for(LocationDetails uniquelocs:la)
		    {
		    	ArrayList<Beacondetails> newarray=new ArrayList<Beacondetails>();
		    	for(Beacondetails allartistbeacons:bd)
		    	{
		    		if(uniquelocs.getId()==allartistbeacons.getLocationid())
		    		{
		    			newarray.add(allartistbeacons);
		    			
		    		}
		    	}
		    	locationbeacons.put(uniquelocs.getName(), newarray);
		    	
		    }
	
	       
		    
		    PublicValues.venubeacondetails=locationbeacons;
		    
		     
		    
	}
	
	public void showDialog(final int itempos,final View txtname)
	{
	  if(la.size()>0)
	    {
	    if(la.size()>1)
	    {
	    	    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context,AlertDialog.THEME_HOLO_LIGHT);
		        LayoutInflater inflater = context.getLayoutInflater();
		        View convertView = (View) inflater.inflate(R.layout.artistdial, null);
		        alertDialog.setView(convertView);
		        alertDialog.setTitle("Select venue");
		        
		        ListView lv = (ListView) convertView.findViewById(R.id.artistdialoglist);
             
		        DialogAdapter adapter = new DialogAdapter(context,la);
		        lv.setAdapter(adapter);
		        
		        lv.setOnItemClickListener( new OnItemClickListener()
		        { 
                    @Override
		            public void onItemClick(AdapterView<?> parent, View v, int position,
		                    long id) 
		            {
		              
		             if(txtname.getId()==R.id.txt_map)
		         	    {
		            	   // Toast.makeText(context, "Clicked on mapppp", Toast.LENGTH_LONG).show();
		            	    getdata(itempos,txtname);
		            	    Intent intent = new Intent(context, ArtistMap.class);
		                 	intent.putExtra("location",la.get(position).getName());
		                 	context.startActivity(intent);
		         	    }
		             else
		            	 if(txtname.getId()==R.id.txt_details)
			         	    {
		            		// Toast.makeText(context, "Clicked on "+la.get(position).getName(), Toast.LENGTH_LONG).show();
		            		
		            			 Intent i=new Intent(context, BeaconDetailViewActivity.class);
			           			 
			           			 i.putExtra("desImgUrl", bd.get(position).getDescriptionimageurl());
			           			 i.putExtra("artNameEng", bd.get(position).getartistNameEnglish());
			           			 i.putExtra("artDesEng", bd.get(position).getartistDescriptionEnglish());
			           			 i.putExtra("instEng", bd.get(position).getInstallationEnglish());
			           			 i.putExtra("instDesEng", bd.get(position).getInstallationDescriptionEnglish());
			           			 i.putExtra("desEng", bd.get(position).getDescriptionenglish());
			           			 i.putExtra("artNameMal", bd.get(position).getArtistNameMalayalam());
			           			 i.putExtra("artDescMal", bd.get(position).getArtistDescriptionMalayalam());
			           			 i.putExtra("descMal", bd.get(position).getDescriptionMalayalam());
			           			 
			           			 context.startActivity(i);
		            		
		            		
		             			
		           			}
		           		
                 	//Toast.makeText(context, "Clicked on "+la.get(position).getName(), Toast.LENGTH_LONG).show();
		               
		            }
		        } );
		       
		        alertDialog.show();
		        
        
		    }
	    else
	    {
	    	 if(txtname.getId()==R.id.txt_map)
      	    {
	    		 Intent intent = new Intent(context, ArtistMap.class);
	         	intent.putExtra("location",la.get(0).getName());
	         	context.startActivity(intent);
      	    }
	    	 if(txtname.getId()==R.id.txt_details)
      	    {
	    		 
        		 Intent i=new Intent(context, BeaconDetailViewActivity.class);
       			 
       			 i.putExtra("desImgUrl", bd.get(0).getDescriptionimageurl());
       			 i.putExtra("artNameEng", bd.get(0).getartistNameEnglish());
       			 i.putExtra("artDesEng", bd.get(0).getartistDescriptionEnglish());
       			 i.putExtra("instEng", bd.get(0).getInstallationEnglish());
       			 i.putExtra("instDesEng", bd.get(0).getInstallationDescriptionEnglish());
       			 i.putExtra("desEng", bd.get(0).getDescriptionenglish());
       			 i.putExtra("artNameMal", bd.get(0).getArtistNameMalayalam());
       			 i.putExtra("artDescMal", bd.get(0).getArtistDescriptionMalayalam());
       			 i.putExtra("descMal", bd.get(0).getDescriptionMalayalam());
       			 
       			 context.startActivity(i);  
      	    }
	    	
	    }
	  }
	    /*else
	    {
	    	Toast.makeText(context, "No Location details found", Toast.LENGTH_SHORT).show();
	    }*/
}
	
	
	}