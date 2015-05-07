package com.simelabs.kmb.locationmap;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.net.ftp.FTPReply;
import org.w3c.dom.Document;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.simelabs.kmb.launch.R;
import com.simelabs.kmb.activities.MainActivity;
import com.simelabs.kmb.domain.LocationDetails;
import com.simelabs.kmb.managers.Decompress;
import com.simelabs.kmb.managers.FTPUtil;
import com.simelabs.kmb.managers.JsonManager;
import com.simelabs.kmb.service.PublicValues;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.Toast;

public class MapManagerOutside extends Activity {

	// Google Map
		private GoogleMap googleMap;
		String venue;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.locationmap_allvenues);

			
			
			try {
				// Loading map
				initilizeMap();

				// Changing map type
				googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
				// googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
				// googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
				// googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
				// googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);

				// Showing / hiding your current location
				googleMap.setMyLocationEnabled(true);

				// Enable / Disable zooming controls
				googleMap.getUiSettings().setZoomControlsEnabled(true);

				// Enable / Disable my location button
				googleMap.getUiSettings().setMyLocationButtonEnabled(false);

				// Enable / Disable Compass icon
				googleMap.getUiSettings().setCompassEnabled(true);

				// Enable / Disable Rotate gesture
				googleMap.getUiSettings().setRotateGesturesEnabled(true);

				// Enable / Disable zooming functionality
				googleMap.getUiSettings().setZoomGesturesEnabled(true);

				venue=getIntent().getExtras().getString("venue");
				int venueno=getIntent().getExtras().getInt("venueno");
				
				if(venue!=null)
				{
				if(venue.equalsIgnoreCase("all"))
				{
					allVenues();
				}
				}else
				{
					
					Log.i("cliked:", venueno+"");
					LatLng l;
					 l=PublicValues.venuesLatlng.get(venueno);
						toTheVenue(l);
						addMarker(l, PublicValues.venueTitle.get(venueno).toString());
						
					
				}
				
			
				

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		@Override
		protected void onResume() {
			super.onResume();
			initilizeMap();
		}

		/**
		 * function to load map If map is not created it will create it for you
		 * */
		private void initilizeMap() {
			if (googleMap == null) {
				googleMap = ((MapFragment) getFragmentManager().findFragmentById(
						R.id.map)).getMap();

				// check if map is created successfully or not
				if (googleMap == null) {
					Toast.makeText(getApplicationContext(),
							"Sorry! unable to create maps", Toast.LENGTH_SHORT)
							.show();
				}
			}
		}

		public void allVenues()
		{
			LatLng allvenuzoom=new LatLng(9.9500000, 76.2750000);
			
			ArrayList<LocationDetails> locations = PublicValues.alllocationdetails;
			
			for(LocationDetails l:locations)
					{
				MarkerOptions marker = new MarkerOptions().position(l.getLatlng())
						.title(l.getName());
				marker.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
				googleMap.addMarker(marker);
					}
			
			
				
			
		cameraZoom(allvenuzoom);
			
			
			
				
		}
		
		public void toTheVenue(LatLng latlong)
		{
			
			
			
			 GPSTracker gpsTracker = new GPSTracker(this);
			 
			 if (gpsTracker.canGetLocation())
		        {
				// String stringLatitude = String.valueOf(gpsTracker.latitude);
				// String stringLongitude = String.valueOf(gpsTracker.longitude);
				 double lat=gpsTracker.latitude;
				 double lng=gpsTracker.longitude;
				 
			 Log.i("lat and long", ""+lat+"  "+lng);
			  sourcePosition=new LatLng(lat, lng);
			 
			  new Getdirection(latlong).execute("");
			  
            
		        }
			 else
			 {
				 Toast.makeText(getApplicationContext(), "Location not found", Toast.LENGTH_SHORT).show();
			 }
            
		}
	
		 LatLng sourcePosition,destPosition;
		private class Getdirection extends AsyncTask<String, Void, PolylineOptions> {

			

			public Getdirection(LatLng latlong) {

				 destPosition = latlong;
			}

			protected PolylineOptions doInBackground(String... str) {
				
				GMapV2Direction md = new GMapV2Direction();
				googleMap = ((MapFragment) getFragmentManager().findFragmentById(
						R.id.map)).getMap();
			
				
				
				Document doc = md.getDocument(sourcePosition, destPosition,
				        GMapV2Direction.MODE_DRIVING);
				
				ArrayList<LatLng> directionPoint = md.getDirection(doc);
				PolylineOptions rectLine = new PolylineOptions().width(3).color(
				        Color.RED);

				for (int i = 0; i < directionPoint.size(); i++) {
				    rectLine.add(directionPoint.get(i));
				}
				
				

				return rectLine;
			}

			 
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				
			}

			
			protected void onPostExecute(PolylineOptions rectLine) {

				Polyline polylin = googleMap.addPolyline(rectLine);
				cameraZoom(sourcePosition);
			}	
		}
		
		public void cameraZoom(LatLng latlng)
		{
			// Move the camera to last position with a zoom level
			
			CameraPosition cameraPosition = new CameraPosition.Builder()
					.target(latlng).zoom(13).build();

			googleMap.animateCamera(CameraUpdateFactory
					.newCameraPosition(cameraPosition));
		}
		
		public void addMarker(LatLng latlng,String title)
		{
			MarkerOptions maker = new MarkerOptions().position(latlng)
					.title(title);
			maker.icon(BitmapDescriptorFactory
					.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
			googleMap.addMarker(maker).showInfoWindow();
		}
		
}
