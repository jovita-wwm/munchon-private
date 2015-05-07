package com.simelabs.munchon.Activities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.simelabs.munchon.R;
import com.simelabs.munchon.Domain.Constants;
import com.simelabs.munchon.Domain.PublicValues;
import com.simelabs.munchon.Location.GPSTracker;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityAccountAddLocation extends Activity implements OnItemSelectedListener{

	TextView txt_defaultlocation;
	 double lat,lng;
	 Spinner spnr_city;
	 String defaultlocation="You are in: ";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addlocation);
		txt_defaultlocation=(TextView)findViewById(R.id.txt_defaultLocation);
		spnr_city = (Spinner) findViewById(R.id.spinner_city);
		txt_defaultlocation.setText(defaultlocation+PublicValues.defaultlocation);
		
		
		List<String> categories = new ArrayList<String>();
		categories.add("Select your City");
		categories.add("Cochin");
		categories.add("Ernakulam");
		categories.add("Thrissur");
		categories.add("Palakkad");
		categories.add("Kollam");
		categories.add("Kottayam");
		categories.add("Kannnur");
		
		 // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
 
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 
        // attaching data adapter to spinner
        spnr_city.setAdapter(dataAdapter);
        spnr_city.setOnItemSelectedListener(this);
		
	}
	
	public void getLocation(View v)
	{
		LocationManager	locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		Location location = new Location(locationManager.GPS_PROVIDER);
		List<Address> addresses = null;

		Geocoder geocoder = new Geocoder(this, Locale.getDefault());
		String errorMessage = "";
		
		GPSTracker gpsTracker = new GPSTracker(ActivityAccountAddLocation.this);
		
		 if (gpsTracker.canGetLocation())
	        {
			// String stringLatitude = String.valueOf(gpsTracker.latitude);
			// String stringLongitude = String.valueOf(gpsTracker.longitude);
			  lat=gpsTracker.latitude;
			  lng=gpsTracker.longitude;
	        }
		 else
			 Toast.makeText(getApplicationContext(), "Not able to get Location", Toast.LENGTH_SHORT).show();
			 
	    try {
	        addresses = geocoder.getFromLocation(
	        		lat,
	        		lng,
	                // In this sample, get just a single address.
	                1);
	    } catch (IOException ioException) {
	        // Catch network or other I/O problems.
	        errorMessage ="service_not_available";
	        Log.e("Location Error", errorMessage, ioException);
	    } catch (IllegalArgumentException illegalArgumentException) {
	        // Catch invalid latitude or longitude values.
	        errorMessage ="invalid_lat_long_used";
	        Log.e("Location Error", errorMessage + ". " +
	                "Latitude = " + location.getLatitude() +
	                ", Longitude = " +
	                location.getLongitude(), illegalArgumentException);
	    }

	    // Handle case where no address was found.
	    if (addresses == null || addresses.size()  == 0) {
	        if (errorMessage.isEmpty()) {
	            errorMessage = "No_address_found";
	            Log.e("Location Error", errorMessage);
	        }
	        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
	       // deliverResultToReceiver(Constants.FAILURE_RESULT, errorMessage);
	    } else {
	        Address address = addresses.get(0);
	        ArrayList<String> addressFragments = new ArrayList<String>();

	        // Fetch the address lines using getAddressLine,
	        // join them, and send them to the thread.
	        for(int i = 0; i < address.getMaxAddressLineIndex(); i++) {
	            addressFragments.add(address.getAddressLine(i));
	        }
	        Log.i("Location Error", "address_found"+addressFragments);
	       /* deliverResultToReceiver(Constants.SUCCESS_RESULT,
	                TextUtils.join(System.getProperty("line.separator"),
	                        addressFragments));*/
	        String combine="";
	       String[] addresfrag=addressFragments.get(addressFragments.size()-1).split(",");
	        txt_defaultlocation.setText(defaultlocation+addresfrag[0]);
	        Log.i("Location ", combine);
	        PublicValues.defaultlocation=addresfrag[0];
	        spnr_city.setSelection(0);
	    }
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		 String item = parent.getItemAtPosition(position).toString();
		 if(!item.equalsIgnoreCase("Select your City"))
		 {
		Toast.makeText(getApplicationContext(), item,Toast.LENGTH_SHORT).show();
		 txt_defaultlocation.setText(defaultlocation+item);
		 PublicValues.defaultlocation=item;
		 }
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
}
