package com.simelabs.kmb.locationmap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.widget.Toast;

import com.google.android.gms.internal.ct;
import com.google.android.gms.maps.model.LatLng;
import com.simelabs.kmb.service.PublicValues;

public class DirectionInGoogleMap {

	
	int Venuno;
	Activity activity;
	public DirectionInGoogleMap(int vn,Activity c) {
		// TODO Auto-generated constructor stub
	
	Venuno=vn;
	activity=c;
	}
	
	 public void buildAlertMessageNoGps()
	  {
	     AlertDialog.Builder builder = new AlertDialog.Builder(activity);
	    builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
	           .setCancelable(false)
	           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	               public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
	                   activity.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
	               }
	           })
	           .setNegativeButton("No", new DialogInterface.OnClickListener() {
	               public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
	                    dialog.cancel();
	               }
	           });
	    final AlertDialog alert = builder.create();
	    alert.show();
	}
	public void getdirection()
	{
		
		final LocationManager manager = (LocationManager) activity.getSystemService( Context.LOCATION_SERVICE );

	    if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
	        this.buildAlertMessageNoGps();
	    }

	 
	
		
		
		
		LatLng l;
		 l=PublicValues.alllocationdetails.get(Venuno).getLatlng();
		 Toast.makeText(activity, PublicValues.alllocationdetails.get(Venuno).getName(), Toast.LENGTH_SHORT).show();
		 
 GPSTracker gpsTracker = new GPSTracker(activity);
		 
		 if (gpsTracker.canGetLocation())
	        {
			// String stringLatitude = String.valueOf(gpsTracker.latitude);
			// String stringLongitude = String.valueOf(gpsTracker.longitude);
			 double lat=gpsTracker.latitude;
			 double lng=gpsTracker.longitude;
			 
		Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
			    Uri.parse("http://maps.google.com/maps?saddr="+lat+","+lng+"&daddr="+l.latitude+","+l.longitude+"\""));
			activity.startActivity(intent);
			
	        }
	}
}
