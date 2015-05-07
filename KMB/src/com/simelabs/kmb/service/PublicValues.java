package com.simelabs.kmb.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.model.LatLng;
import com.simelabs.kmb.domain.ArtistDomain;
import com.simelabs.kmb.domain.GalleryDomain;
import com.simelabs.kmb.domain.LocationDetails;
import com.simelabs.kmb.spotbeak.Beacon;
import com.simelabs.kmb.spotbeak.Beacondetails;

public class PublicValues {

	
	public static String GallerysavePath;
	
	/** 
	 * @author Jovita
	 * for location use
	 */
		
		 public static ArrayList<String>venueTitle=new ArrayList<String>();
		 public static ArrayList<LatLng>  venuesLatlng=new ArrayList<LatLng>();
		 public static ArrayList<Drawable>  venuesbackgroung=new ArrayList<Drawable>();
		 
		 public static String unzipedpath;
		 
		 public static ArrayList<Beacondetails> beacondetails=new ArrayList<Beacondetails>();
		 
		 public static String os,brand,device,display,model,number,operator;
		 
		 public static ArrayList<Beacon> beaconsalreadyfound=new ArrayList<Beacon>();
		 public static ArrayList<Beacon> onlinebeaconsalreadyfound=new ArrayList<Beacon>();
		 
		 public static ArrayList<LocationDetails> alllocationdetails=new ArrayList<LocationDetails>();
		 
		 public static Beacondetails lastbeaconfound;
		 public static Map<String, String> AllVersionDetails=new HashMap<String, String>();
		 public static ArrayList<String> Imagefullviewurls=new ArrayList<String>();
		 public static ArrayList<GalleryDomain> gallerydomain=new ArrayList<GalleryDomain>();
		 
		 public static int BeaconRefreshInterval;
		 
		 
		 public static ArrayList<ArtistDomain> AllartistDetails=new ArrayList<ArtistDomain>();
		 
		 public static ArrayList<String> gallerycategorydetails=new ArrayList<String>();
		 public static HashMap<String, ArrayList<Beacondetails>> venubeacondetails=new HashMap<String, ArrayList<Beacondetails>>();
}
