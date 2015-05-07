package com.simelabs.kmb.locationmap;

import com.simelabs.kmb.launch.R;
import com.simelabs.kmb.domain.LocationDetails;
import com.simelabs.kmb.network.Internet;
import com.simelabs.kmb.service.PublicValues;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LocationDetailView extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_locationdetailview);
		
		int position =getIntent().getExtras().getInt("position");
		
		LocationDetails details=PublicValues.alllocationdetails.get(position);
		
		RelativeLayout venuedetailviewbgpic=(RelativeLayout)findViewById(R.id.venuedetailviewbgpic);
		TextView venuedetailviewtitle=(TextView)findViewById(R.id.venuedetailviewtitle);
		ImageView venuedetailviewInsideNavigation=(ImageView)findViewById(R.id.venuedetailviewInsideNavigation);
		ImageView venuedetailviewOusideNavigation=(ImageView)findViewById(R.id.venuedetailviewOusideNavigation);
		TextView venuedetailstxt=(TextView)findViewById(R.id.venuedetailstxt);
		
		
		venuedetailviewbgpic.setBackground(PublicValues.venuesbackgroung.get(position));
		venuedetailviewtitle.setText(details.getName());
		venuedetailviewInsideNavigation.setTag(position);
		venuedetailviewOusideNavigation.setTag(position);
		venuedetailstxt.setText(details.getDetails());
		
		
	}
	public void InsideNavigation(View v) {
	    // does something very interesting
		//Intent intent = new Intent(this, ImageDisplayActivity.class);
		Intent intent = new Intent(this, MapManagerInside.class);
		Integer venueno=(Integer)v.getTag();
		intent.putExtra("position", venueno);
        startActivity(intent);
		
	}
	
	public void OutsideNavigation(View v) {
	    // does something very interesting

		/*Intent intent =new Intent(getApplicationContext(), MapManagerOutside.class);
		
		Integer venueno=(Integer)v.getTag();
		
		intent.putExtra("venueno", venueno);
		startActivity(intent);*/
		
		/* GPSTracker gpsTracker = new GPSTracker(this);
		 
		 if (gpsTracker.canGetLocation())
	        {
			// String stringLatitude = String.valueOf(gpsTracker.latitude);
			// String stringLongitude = String.valueOf(gpsTracker.longitude);
			 double lat=gpsTracker.latitude;
			 double lng=gpsTracker.longitude;
			 
		Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
			    Uri.parse("http://maps.google.com/maps?saddr="+lat+","+lng+"&daddr=9.9666790,76.2427970"));
			startActivity(intent);
		
		
		
	        }
		*/
		
		Internet net = new Internet(this);
		boolean netstatus = net.isAvailable();
		if (netstatus != false) {
	
		Integer venueno=(Integer)v.getTag();
		
		Log.i("outside map cliked at", venueno+"");
		DirectionInGoogleMap map=new DirectionInGoogleMap(venueno,this);
		map.getdirection();
		}
		else
			Toast.makeText(getApplicationContext(), "Check Network Connection!!", Toast.LENGTH_SHORT).show();
		
	}
}
