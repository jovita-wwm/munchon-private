package com.simelabs.kmb.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ClearCacheRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.simelabs.kmb.launch.R;
import com.simelabs.kmb.locationmap.LocationMapAdapter.ViewHolder;
import com.simelabs.kmb.managers.JsonManager;
import com.simelabs.kmb.network.Internet;
import com.simelabs.kmb.notification.AppNotification;
import com.simelabs.kmb.service.PublicValues;
import com.simelabs.kmb.spotbeak.Beacon;
import com.simelabs.kmb.spotbeak.AllDemosActivity;
import com.simelabs.kmb.spotbeak.BeaconManager;
import com.simelabs.kmb.spotbeak.BeaconManager.RangingListener;
import com.simelabs.kmb.spotbeak.Beacondetails;
import com.simelabs.kmb.spotbeak.DistanceBeaconActivity;
import com.simelabs.kmb.spotbeak.LazyBeaconAdapter;
import com.simelabs.kmb.spotbeak.LeDeviceListAdapter;
import com.simelabs.kmb.spotbeak.ListBeaconsActivity;
import com.simelabs.kmb.spotbeak.Region;
import com.simelabs.kmb.spotbeak.Utils;
import com.special.ResideMenu.ResideMenu;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SpotBeak extends Fragment
{
	 private View mContentView;
	 private static final String TAG = ListBeaconsActivity.class.getSimpleName();

	  public static final 
	  String EXTRAS_TARGET_ACTIVITY = "extrasTargetActivity";
	  public static final String EXTRAS_BEACON = "extrasBeacon";

	  private static final int REQUEST_ENABLE_BT = 1234;
	  private static final Region ALL_ESTIMOTE_BEACONS_REGION = new Region("rid", null, null, null);

	  private BeaconManager beaconManager;
	  private LeDeviceListAdapter adapter;
	  RelativeLayout nobeak;
	  ListView list;
	  private Activity mContext;
	  Activity act;
	  String olddata;
	
	  
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	    {
	        mContentView = inflater.inflate(R.layout.main, null);

	        /*MainActivity m=new MainActivity();
	        m.addNotificationToRightMenu("jghjg");*/
	        
	        act = getActivity();
	        
	       
	        return mContentView;
	    }
	    @Override
	    public void onActivityCreated(Bundle savedInstanceState) {
	    	 super.onActivityCreated(savedInstanceState);
	    	 
	    	/* 
	    	// adapter = new LeDeviceListAdapter(getActivity());
	    	 adapter = new LeDeviceListAdapter(getActivity());
	    	 nobeak=(RelativeLayout)getActivity().findViewById(R.id.nobeaconlayout);
	    	 
	    	     list = (ListView)getActivity().findViewById(R.id.device_list);
	    	    list.setAdapter(adapter);
	    	  
	    	    beaconManager = new BeaconManager(getActivity());
	     		// Check if device supports Bluetooth Low Energy.
	     	    if (!beaconManager.hasBluetooth()) {
	     	    //  Toast.makeText(this, "Device does not have Bluetooth Low Energy", Toast.LENGTH_LONG).show();
	     	    	nobeak.setVisibility(View.GONE);
	     	      return;
	     	    }

	     	    // If Bluetooth is not enabled, let user enable it.
	     	    if (!beaconManager.isBluetoothEnabled()) {
	     	      Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	     	      startActivityForResult(enableBtIntent, 1234);
	     	    	Toast.makeText(getActivity(), "Turn ON your BLuetooth if you are in /n Kochi Muziris Biennale", Toast.LENGTH_LONG).show();
	     	    	nobeak.setVisibility(View.VISIBLE);
	     	    } else {
	     	    	  beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
	     	    	      @Override
	     	    	      public void onServiceReady() {
	     	    	        try {
	     	    	        	  Log.i("Beacon manager", "onserviceready");
	     	    	          beaconManager.startRanging(ALL_ESTIMOTE_BEACONS_REGION);
	     	    	        } catch (RemoteException e) {
	     	    	       //   Toast.makeText(MyService.this, "Cannot start ranging, something terrible happened",
	     	    	          //    Toast.LENGTH_LONG).show();
	     	    	          Log.e(TAG, "Cannot start ranging", e);
	     	    	        }
	     	    	      }
	     	    	    });
	     	    }
	       
	     	
	    	   startranging(); */
	    	
	    	 setup();
	    	
	    }
	    
	    LayoutAnimationController control;
	   private void setup() {
			// TODO Auto-generated method stub
		   
	    	// adapter = new LeDeviceListAdapter(getActivity());
	    	 adapter = new LeDeviceListAdapter(getActivity());
	    	 
	    	 
	    	 nobeak=(RelativeLayout)getActivity().findViewById(R.id.nobeaconlayout);
	    	 
	    	     list = (ListView)getActivity().findViewById(R.id.device_list);
	    	     list.setFadingEdgeLength(0);
	    	     
	    	     
	    	    /* AnimationSet set=new AnimationSet(true);
	    	    // Animation animatio=new TranslateAnimation(0, 0, 0, toYDelta)
	    	     Animation animation=new AlphaAnimation(0.0f, 1.0f);
	    	     animation.setDuration(500);
	    	     
	    	     animation = new TranslateAnimation(
	    	              Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
	    	              Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f
	    	          );
	    	          animation.setDuration(400);
	    	          set.addAnimation(animation);
	    	     
	    	     set.addAnimation(animation);
	    	     
	    	      control=new LayoutAnimationController(set, 0.25f);
	    	     list.setLayoutAnimation(control);*/
	    	     
	    	    list.setAdapter(adapter);
	    	  
	    	    beaconManager = new BeaconManager(getActivity());
	     		// Check if device supports Bluetooth Low Energy.
	     	    if (!beaconManager.hasBluetooth()) {
	     	    //  Toast.makeText(this, "Device does not have Bluetooth Low Energy", Toast.LENGTH_LONG).show();
	     	    	nobeak.setVisibility(View.GONE);
	     	      return;
	     	    }

	     	    // If Bluetooth is not enabled, let user enable it.
	     	    if (!beaconManager.isBluetoothEnabled()) {
	     	      Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	     	      startActivityForResult(enableBtIntent, 1234);
	     	    	Toast.makeText(getActivity(), "Turn ON your BLuetooth if you are in Kochi Muziris Biennale", Toast.LENGTH_LONG).show();
	     	    	nobeak.setVisibility(View.VISIBLE);
	     	    } else {
	     	    	  beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
	     	    	      @Override
	     	    	      public void onServiceReady() {
	     	    	        try {
	     	    	        	  //Log.i("Beacon manager", "onserviceready");
	     	    	          beaconManager.startRanging(ALL_ESTIMOTE_BEACONS_REGION);
	     	    	        } catch (RemoteException e) {
	     	    	       //   Toast.makeText(MyService.this, "Cannot start ranging, something terrible happened",
	     	    	          //    Toast.LENGTH_LONG).show();
	     	    	        //  Log.e(TAG, "Cannot start ranging", e);
	     	    	        }
	     	    	      }
	     	    	    });
	     	    }
	       
	     	
	    	   startranging(); 
		}
	private void startranging() {
			// TODO Auto-generated method stub
		   ArrayList<Beacondetails> Beacondetailsarraylist =new ArrayList<Beacondetails>();
	    	Beacondetailsarraylist=PublicValues.beacondetails;
   	  if(Beacondetailsarraylist.size()>0)
   	  {
   	    
    //  Log.i("Beacon manager", "defined");
       beaconManager.setRangingListener(new BeaconManager.RangingListener() {
   		
   		@Override
   		public void onBeaconsDiscovered(Region paramRegion, List<Beacon> beacons) {
   			// TODO Auto-generated method stub
   			nobeak.setVisibility(View.GONE);
   			// Log.i("beacon", "detected" + beacons.size());
   			// NotificationsFragment.adapter.replaceWith(beacons);
   			
   			 List<Beacon> newselctedbeacons=displaybeacon(beacons);

   			 
   			 List<Beacon> sortedbeacon=getSortedBeacons(newselctedbeacons);
   			 
   		/*	 MainActivity main=new MainActivity();
	    			main.addNotificationToRightMenu("uyfgihj");*/
   			 
   			
   		adapter.replaceWith(sortedbeacon);
   	
   			if(sortedbeacon.size()<=0)
   			{
   				nobeak.setVisibility(View.VISIBLE);
   			}
   			 
   			 
//   			 adapter.replaceWith(newselctedbeacons);
//   			 adapter.notifyDataSetChanged();
//   		int count= adapter.getCount();
//   			 if(count>=2)
//   			 {
//   			 swap(newselctedbeacons);
//   			 }
   			 
   		}
   	});
	        
	        
   	  }
   	  else
   	  {
   			JsonManager beakjson=new JsonManager(getActivity().getApplicationContext());
				beakjson.getDatafromBeacon(getActivity());
				startranging();
   	  }
		}


	public  List<Beacon> orderedbeacons;
	    int position;
	    public void swap(List<Beacon> beacons)
	    {
	    	Beacon shortest;
	    	 orderedbeacons = beacons;
	    	
	    	shortest=beacons.get(0);
	    	  position=0;
	    	for(int i=1;i<beacons.size();i++)
	    	{
	    		if(shortest.getRssi()>=beacons.get(i).getRssi())
	    		{
	    			shortest=beacons.get(i);
	    			position=i;
	    		}
	    	}
	    	
	    	final View c=list.getChildAt(position);
	    	
	    	int distance=c.getHeight()*2;
	    	Animation animation = new TranslateAnimation(0, 0, 0, -distance);
			animation.setDuration(300);
			
			c.startAnimation(animation);
			animation.setAnimationListener(new AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub
					
					orderedbeacons.remove(position);
					 adapter.notifyDataSetChanged();
				}
				
				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationEnd(Animation animation) {
					// TODO Auto-generated method stub
				
					c.clearAnimation();
					/*orderedbeacons.remove(position);
					 adapter.notifyDataSetChanged();*/
						
					
				}
			});
	    	//return orderedbeacons;
	    }
	    public void push()
	    {
	    	((MainActivity) act).addNotificationToRightMenu("jghjg");
	    }
	    
	    public List<Beacon> displaybeacon(List<Beacon> beacons)
		{
	    
	    
	    	
	    
	    	List<Beacon> newbeacons=new ArrayList<Beacon>();
	    	ArrayList<Beacondetails> Beacondetailsarraylist =new ArrayList<Beacondetails>();
	    	Beacondetailsarraylist=PublicValues.beacondetails;
	    	

	    	for(Beacon b:beacons)
			{
			
	    		for(Beacondetails becdetail:Beacondetailsarraylist)
	    		{
	    			String beaconori=b.getProximityUUID().toUpperCase()+b.getMajor()+b.getMinor();
	    			String beaconlist=becdetail.getId().toUpperCase()+becdetail.getMajor()+becdetail.getMinor();
	    			if((beaconori.equalsIgnoreCase(beaconlist)) && ( b.getRssi()<=-0 && b.getRssi()>=-95))
	    			{
	    				double accuracy=Utils.computeAccuracy(b);
	    				b.setAccuracy(accuracy);
	    				newbeacons.add(b);
	    		    	 PublicValues.lastbeaconfound=becdetail;
	    				
	    			ArrayList<Beacon> beaconsalreadyfound=PublicValues.beaconsalreadyfound;
	    				if(beaconsalreadyfound==null)
	    				{
	    			piwik();
	    				
	    			notifybeacon(becdetail.getName());
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
	    							 notifybeacon(becdetail.getMessage());
	    						 }
	    		    			
	    		    			PublicValues.beaconsalreadyfound.add(b);
	    		    			
	    		    		//	push();
	    		    		 
	    		    			
	    		    			
	    					}
	    				}
	    			
	    			}
	    			
	    		}
			}
	    	
	    	//adapter.replaceWith(newbeacons);
	    	
	    	return newbeacons;
	    	
	    	
			/*for(Beacon b:beacons)
			{
				if(b.getMajor()==7771 &&( b.getRssi()<=-10 && b.getRssi()>=-80))
				{
					Toast.makeText(getApplicationContext(), ""+b.getMajor(), Toast.LENGTH_SHORT).show();
					 Intent intent;
					    intent = new Intent(this, beacondatafullview.class);
					    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					    intent.putExtra("id", R.drawable.page2);
					    startActivity(intent);
				}
				else if(b.getMajor()==8881 &&( b.getRssi()<=-10 && b.getRssi()>=-80))
				{
					Toast.makeText(getApplicationContext(), ""+b.getMajor(), Toast.LENGTH_SHORT).show();
					 Intent intent;
					    intent = new Intent(this, beacondatafullview.class);
					    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					    intent.putExtra("id", R.drawable.page2);
					    startActivity(intent);
				}
				
				else if( b.getRssi()<=-10 && b.getRssi()>=-80)
				{
					Toast.makeText(getApplicationContext(), ""+b.getMajor(), Toast.LENGTH_SHORT).show();
					 Intent intent;
					    intent = new Intent(this, beacondatafullview.class);
					    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					    intent.putExtra("id", R.drawable.page1);
					    startActivity(intent);
				}
			}*/
		}
	    AppNotification notify;
	    private void notifybeacon(String beaconmsg) {
			// TODO Auto-generated method stub
			
	    	// notify = new AppNotification();
	        //  AppNotification.generateNotification(getActivity(), beaconmsg);
	          AppNotification.sideMenuNotif(beaconmsg);
		}
		public void piwik()
	    {
	    	RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
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
				//	Log.i("Piwik:", "responce Message ;  "+arg0
					//		);
				}
			}, new Response.ErrorListener() {
			    @Override
			    public void onErrorResponse(VolleyError error) {
			      //  mTextView.setText("That didn't work!");
			    	
			    	//Log.i("Piwik:", "Error Message ;  "+error);
			    }
			});
			// Add the request to the RequestQueue.
			queue.add(stringRequest);
	    }
		
		 public final List<Beacon> getSortedBeacons(List<Beacon> sort)
		  {
		   // ArrayList<Beacon> sortedBeacons = new ArrayList(this.beacons.keySet());
			 Collections.sort(sort);
		    return sort;
		  }

		 @Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			// TODO Auto-generated method stub
			super.onActivityResult(requestCode, resultCode, data);
			if(requestCode==1234 && resultCode==1)
			{
				//Toast.makeText(getActivity().getApplicationContext(), resultCode, Toast.LENGTH_SHORT).show();
				setup();
			}
		}
}
