package com.simelabs.kmb;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseCrashReporting;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.PushService;
import com.parse.SaveCallback;
import com.simelabs.kmb.activities.MainActivity;
import com.simelabs.kmb.activities.Splash;

import android.app.Application;
import android.util.Log;

public class KMB extends Application{

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
	//	 Parse.initialize(getApplicationContext(), "mlm6jJ4AzXluhavPPae6LhElkXn6jpNNaeIOoJFK", "4VPoi1uDVUd6oHu9AlHS19drw1Z4Dkc20BtTmWRy");
		 
/*
		 Parse.initialize(getApplicationContext(), "mlm6jJ4AzXluhavPPae6LhElkXn6jpNNaeIOoJFK", "4VPoi1uDVUd6oHu9AlHS19drw1Z4Dkc20BtTmWRy");
	        PushService.setDefaultPushCallback(this, Splash.class);
	        ParseInstallation.getCurrentInstallation().saveInBackground();*/
	        
	        
	        ParseCrashReporting.enable(this);

	        // Enable Local Datastore.
	        Parse.enableLocalDatastore(this);

	        // Add your initialization code here
	      //  Parse.initialize(getApplicationContext(), "mlm6jJ4AzXluhavPPae6LhElkXn6jpNNaeIOoJFK", "4VPoi1uDVUd6oHu9AlHS19drw1Z4Dkc20BtTmWRy");
	        Parse.initialize(this, "Fq6eNo8uZPZOIrYCOSapn6fw1fbbEmFwQh5eItUj", "xcUlSyuUZeVZaB16ab93QS2g6AWIcOltPpY8l3QV");
	

	        ParseUser.enableAutomaticUser();
	        ParseACL defaultACL = new ParseACL();
	        // Optionally enable public read access.
	        // defaultACL.setPublicReadAccess(true);
	        ParseACL.setDefaultACL(defaultACL, true);
	       
	}
}
