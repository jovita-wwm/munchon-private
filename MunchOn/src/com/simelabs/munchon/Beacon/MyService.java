package com.simelabs.munchon.Beacon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.simelabs.munchon.Activities.ActivityRestaurantMenu;
import com.simelabs.munchon.Activities.BaseClassActionBar;
import com.simelabs.munchon.Domain.BeaconDomain;
import com.simelabs.munchon.Domain.PublicValues;


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
	Activity act;
	 public MyService(Activity a) {
		// TODO Auto-generated constructor stub
		 act=a;
	}
	 
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
	      Toast.makeText(this, "Device does not have Bluetooth Low Energy", Toast.LENGTH_LONG).show();
	      return;
	    }

	    // If Bluetooth is not enabled, let user enable it.
	    if (!beaconManager.isBluetoothEnabled()) {
	     /* Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	      startActivityForResult(enableBtIntent, 1234);*/
	    	//Toast.makeText(this, "Turn ON your BLuetooth", Toast.LENGTH_LONG).show();
	    } else {
	    	  beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
	    	      @Override
	    	      public void onServiceReady() {
	    	        try {
	    	          beaconManager.startRanging(ALL_ESTIMOTE_BEACONS_REGION);
	    	        } catch (RemoteException e) {
	    	       //   Toast.makeText(MyService.this, "Cannot start ranging, something terrible happened",
	    	        }
	    	      }
	    	    });
	    }
		
	  
	    beaconManager.setRangingListener(new BeaconManager.RangingListener() {
			
			@Override
			public void onBeaconsDiscovered(Region paramRegion, List<Beacon> beacons) {
				// TODO Auto-generated method stub
	
				
				
				 List<Beacon> newselctedbeacons=displaybeacon(beacons);

	   			 
	   			 List<Beacon> sortedbeacon=getSortedBeacons(newselctedbeacons);
	   			 
	   			if(sortedbeacon!=null && sortedbeacon.size()>0){
	   			 Beacon b=sortedbeacon.get(0);
	   			
	   		/*Toast.makeText(getApplicationContext(), "Major:"+b.getMajor()+"\n"+
	   				"Rssi:"+b.getRssi()+"\n"+
	   				"MP:"+b.getMeasuredPower()+"\n"
	   				+"accuracy"+b.getAccuracy(), Toast.LENGTH_SHORT).show(); */
	   			 
	   		PublicValues.tableno=b.getTable_name()+"";
	   		
	   		Toast.makeText(getApplicationContext(), ""+b.getTable_name(), Toast.LENGTH_SHORT).show();
	   		
	   		ActivityRestaurantMenu act=new ActivityRestaurantMenu();
	   		act.update(b.getTable_name());
	   		
	   			}
			}

			
		});
	}
	
	
	
	 public final List<Beacon> getSortedBeacons(List<Beacon> sort)
	  {
		 Collections.sort(sort);
	    return sort;
	  }
	public boolean notnotcheck()
	{
		if(PublicValues.oldbeacons==null || PublicValues.oldbeacons.equalsIgnoreCase(""))
		{
			return false;
		}
		else
			return true;
		
		
	}

	public static String oldbeacons;
	public static List<Beacon> beaconsnottodetectold=new ArrayList<Beacon>();
	public static List<Beacon> beaconsnottodetectnew=new ArrayList<Beacon>();
	
	
	  public List<Beacon> displaybeacon(List<Beacon> beacons)
			{
		    
		    
		    	
		    
		    	List<Beacon> newbeacons=new ArrayList<Beacon>();
		    	ArrayList<BeaconDomain> allbeacondetails =new ArrayList<BeaconDomain>();
		    	allbeacondetails=PublicValues.allbeacons;
		    	

		    	for(Beacon b:beacons)
				{
		    			
		    		for(BeaconDomain becdetail:allbeacondetails)
		    		{
		    			String beaconori=b.getProximityUUID().toUpperCase()+b.getMajor()+b.getMinor();
		    			String beaconlist=becdetail.getUuid().toUpperCase()+becdetail.getMajor()+becdetail.getMinor();
		    			if((beaconori.equalsIgnoreCase(beaconlist)) && becdetail.getRestaurantID()==PublicValues.restaurantid)
		    			{
		    				double accuracy=Utils.computeAccuracy(b);
		    				b.setAccuracy(accuracy);
		    				b.setTable_name(becdetail.getTableName());
		    				newbeacons.add(b);
		    			
		    			}
		    			
		    		}
				}
		    	
		    	
		    	return newbeacons;
		    	
		    	
			}

}