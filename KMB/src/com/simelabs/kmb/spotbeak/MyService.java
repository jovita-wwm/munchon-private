package com.simelabs.kmb.spotbeak;
import java.util.ArrayList;
import java.util.List;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.simelabs.kmb.activities.MainActivity;
import com.simelabs.kmb.notification.AppNotification;
import com.simelabs.kmb.service.PublicValues;


import android.app.Activity;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {
	private static final String TAG = "MyService";
	 private BeaconManager beaconManager;
	 private static final Region ALL_ESTIMOTE_BEACONS_REGION = new Region("rid", null, null, null);
	 
	 public int bcountold,bcountnew;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	
	@Override
	public void onCreate() {
	//	Toast.makeText(this, "My Service Created", Toast.LENGTH_LONG).show();
	//	Log.d(TAG, "onCreate");
		
		
		
		
		
	}

	@Override
	public void onDestroy() {
		//Toast.makeText(this, "My Service Stopped", Toast.LENGTH_LONG).show();
	//	Log.d(TAG, "onDestroy");
		//player.stop();
	}
	
	@Override
	public void onStart(Intent intent, int startid) {
		//Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();
		//Log.d(TAG, "onStart");
		
		 beaconManager = new BeaconManager(this);
		// Check if device supports Bluetooth Low Energy.
	    if (!beaconManager.hasBluetooth()) {
	    //  Toast.makeText(this, "Device does not have Bluetooth Low Energy", Toast.LENGTH_LONG).show();
	      return;
	    }

	    // If Bluetooth is not enabled, let user enable it.
	    if (!beaconManager.isBluetoothEnabled()) {
	      /*Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	      startActivityForResult(enableBtIntent, 1234);*/
	    	//Toast.makeText(this, "Turn ON your BLuetooth", Toast.LENGTH_LONG).show();
	    } else {
	    	  beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
	    	      @Override
	    	      public void onServiceReady() {
	    	        try {
	    	        	//  Log.i("Beacon manager", "onserviceready");
	    	          beaconManager.startRanging(ALL_ESTIMOTE_BEACONS_REGION);
	    	        } catch (RemoteException e) {
	    	       //   Toast.makeText(MyService.this, "Cannot start ranging, something terrible happened",
	    	          //    Toast.LENGTH_LONG).show();
	    	         // Log.e(TAG, "Cannot start ranging", e);
	    	        }
	    	      }
	    	    });
	    }
		
	  
	  //  Log.i("Beacon manager", "defined");
	    beaconManager.setRangingListener(new BeaconManager.RangingListener() {
			
			@Override
			public void onBeaconsDiscovered(Region paramRegion, List<Beacon> beacons) {
				// TODO Auto-generated method stub
				// Log.i("beacon", "detected" + beacons.size());
				// NotificationsFragment.adapter.replaceWith(beacons);
			/*	bcountnew=beacons.size();
				
				 List<Beacon> newbaconlist=checkbeacon(beacons);
				
				 displaybeacon(newbaconlist);
				// Toast.makeText(MyService.this, beacons.size()+"\n"+newbaconlist.size(), Toast.LENGTH_SHORT).show();
				 Log.i("count all  :   new", beacons.size()+"    nnnnn   "+newbaconlist.size());
			
					*/ 
				 
				 notifynewbeacon(beacons);
				 
			}

			private void notifynewbeacon(List<Beacon> beacons) {
				// TODO Auto-generated method stub
				
				ArrayList<Beacondetails> Beacondetailsarraylist =new ArrayList<Beacondetails>();
		    	List<Beacon> newbeacons=new ArrayList<Beacon>();
		    	Beacondetailsarraylist=PublicValues.beacondetails;
		    	
		    	for(Beacon b:beacons)
				{
				
		    		for(Beacondetails becdetail:Beacondetailsarraylist)
		    		{
		    			String beaconori=b.getProximityUUID().toUpperCase()+b.getMajor()+b.getMinor();
		    			String beaconlist=becdetail.getId().toUpperCase()+becdetail.getMajor()+becdetail.getMinor();
		    			if((beaconori.equalsIgnoreCase(beaconlist)) && ( b.getRssi()<=-0 && b.getRssi()>=-90))
		    			{
		    				double accuracy=Utils.computeAccuracy(b);
		    				b.setAccuracy(accuracy);
		    				newbeacons.add(b);
		    				
		    			ArrayList<Beacon> beaconsalreadyfound=PublicValues.beaconsalreadyfound;
		    				if(beaconsalreadyfound==null)
		    				{
		    			piwik();
		    				
		    			 AppNotification.generateNotification(getApplicationContext(), becdetail.getName());
		    			PublicValues.beaconsalreadyfound.add(b);
		    				}
		    				else
		    				{
		    					int count=0;
		    					for(Beacon beak:beaconsalreadyfound)
		    					{
		    						if(beak.getMajor()==b.getMajor())
		    						{
		    							count=1;
		    							break;
		    						}
		    					}
		    					if(count==0)
		    					{
		    						piwik();
		    						 PublicValues.lastbeaconfound=becdetail;
		    						 
		    						if(becdetail.getMessage()!=null && becdetail.getMessage().length()>2)
		    						 {
		    							 AppNotification.generateNotification(getApplicationContext(), becdetail.getMessage());
		    						}
		    						 
		    		    			PublicValues.beaconsalreadyfound.add(b);
		    					}
		    				}
		    			
		    			}
		    			
		    		}
				}
			}
		});
		//player.start();
	}
	
	public void displaybeacon(List<Beacon> beacons)
	{
		for(Beacon b:beacons)
		{
			if(b.getMajor()==7771 &&( b.getRssi()<=-10 && b.getRssi()>=-80))
			{
				//Toast.makeText(getApplicationContext(), ""+b.getMajor(), Toast.LENGTH_SHORT).show();
				/* Intent intent;
				    intent = new Intent(this, beacondatafullview.class);
				    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				    intent.putExtra("id", R.drawable.page2);
				    startActivity(intent);*/
			}
			else if(b.getMajor()==8881 &&( b.getRssi()<=-10 && b.getRssi()>=-120))
			{
				//Toast.makeText(getApplicationContext(), ""+b.getMajor(), Toast.LENGTH_SHORT).show();
			/*	 Intent intent;
				    intent = new Intent(this, beacondatafullview.class);
				    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				    intent.putExtra("id", R.drawable.page2);
				    startActivity(intent);*/
			}
			
			else if( b.getRssi()<=-10 && b.getRssi()>=-80)
			{
			//	Toast.makeText(getApplicationContext(), ""+b.getMajor(), Toast.LENGTH_SHORT).show();
				/* Intent intent;
				    intent = new Intent(this, beacondatafullview.class);
				    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				    intent.putExtra("id", R.drawable.page1);
				    startActivity(intent);*/
			}
		}
	}
	
	public boolean notnotcheck()
	{
		if(MainActivity.oldbeacons==null || MainActivity.oldbeacons.equalsIgnoreCase(""))
		{
			return false;
		}
		else
			return true;
		
		
	}
	public List<Beacon> checkbeacon(List<Beacon> beak)
	{
		if(bcountold!=bcountnew)
		{
			MainActivity.oldbeacons=null;
			bcountold=bcountnew;
		}
		List<Beacon> newbeacons=new ArrayList<Beacon>();
	
	if(!notnotcheck())
	{
		
		for(Beacon b:beak)
		{
			
			MainActivity.oldbeacons=MainActivity.oldbeacons+"###"+b.getMacAddress();
			//Log.i("oldbeacon", MainActivity.oldbeacons+"");
		}
		
			return beak;
		}else{
			
			String[] oldbeaconmacs=MainActivity.oldbeacons.split("###");
		for(Beacon b:beak)
		{
			boolean ans=false;
		for(int i=0;i<=oldbeaconmacs.length-1;i++)
		{
			 if(b.getMacAddress().equalsIgnoreCase(oldbeaconmacs[i]))
			 {
				 ans=true;
			 }
		//	Log.i("oldbeacon : ",oldbeaconmacs.length+"");
			//Log.i("b  :  be",b.getMacAddress()+"     : "+oldbeaconmacs[i]);
			
		}
		if(!ans)
		{
			
			newbeacons.add(b);
			MainActivity.oldbeacons=MainActivity.oldbeacons+"###"+b.getMacAddress();
			//beaconsnottodetectold.add(b);
			
		}
		}
		
		return newbeacons;
		}
		
	}
	public static String oldbeacons;
	public static List<Beacon> beaconsnottodetectold=new ArrayList<Beacon>();
	public static List<Beacon> beaconsnottodetectnew=new ArrayList<Beacon>();
	
	
	public void piwik()
    {
    	RequestQueue queue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
		String url ="http://ec2-54-69-223-100.us-west-2.compute.amazonaws.com/binservices/testphppiwik.php?" +
				"OS:="+PublicValues.os+"&Brand:="+PublicValues.brand+"&Device:="+PublicValues.device+
				"&Display:="+PublicValues.display+"&Model:="+PublicValues.model+
				"&Number:="+PublicValues.number+"&Operator:="+PublicValues.operator;
		
		// Request a string response from the provided URL.
		@SuppressWarnings({ "rawtypes", "unchecked" })
		StringRequest stringRequest =  new StringRequest(Request.Method.GET, url,
	            new Response.Listener() {
			@Override
			public void onResponse(Object arg0) {
				// TODO Auto-generated method stub
				//Log.i("Piwik:", "responce Message ;  "+arg0);
						
			}
		}, new Response.ErrorListener() {
		    @Override
		    public void onErrorResponse(VolleyError error) {
		      //  mTextView.setText("That didn't work!");
		    	
		    //	Log.i("Piwik:", "Error Message ;  "+error);
		    }
		});
		// Add the request to the RequestQueue.
		queue.add(stringRequest);
    }
}