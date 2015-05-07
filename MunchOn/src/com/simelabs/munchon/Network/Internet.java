package com.simelabs.munchon.Network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
/**
 * 
 * @author Jovita P J created on:17-March-2015
 * 
 * To check internet connection status and download files.
 *
 */
public class Internet {

	Context context;
	public Internet(Context c) {
	// TODO Auto-generated constructor stub
		this.context=c;
	}
	
	/*
	 * to check whether there is a net connection 
	 */
	public boolean isAvailable()
	{
		Log.d("context", ""+context);
		ConnectivityManager cn = null;
		try{
		  cn=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		}catch(NullPointerException e)
		{
			Log.e("null error on connectivity manager", "null error on connectivity manager");
		}
		    NetworkInfo conMgr;
		    
		    if(cn!=null)
		    {
		//Mobile data usage    
		NetworkInfo Mob = cn.getNetworkInfo(
	            ConnectivityManager.TYPE_MOBILE);
		
		//Wifi connection
		 NetworkInfo wifi = cn.getNetworkInfo(
		            ConnectivityManager.TYPE_WIFI);
		    
		  if (Mob == null && wifi==null)
		    return false;
		  if (!Mob.isConnected() && !wifi.isConnected())
		    return false;
		  if (!Mob.isAvailable() && !wifi.isAvailable())
		    return false;
		    }
		    else
		    	return false;
		    
		  return true;
		
	}
}
