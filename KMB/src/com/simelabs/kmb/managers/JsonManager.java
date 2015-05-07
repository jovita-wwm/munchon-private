package com.simelabs.kmb.managers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;
import com.simelabs.kmb.domain.GalleryDomain;
import com.simelabs.kmb.domain.LocationDetails;
import com.simelabs.kmb.network.DownloadFiles;
import com.simelabs.kmb.service.PublicValues;
import com.simelabs.kmb.spotbeak.Beacondetails;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class JsonManager {
	FileInputStream stream = null ;
	String jsonStr = null;
	Context context;
	
	public JsonManager(Context cont) {
		// TODO Auto-generated constructor stub
		context=cont;
	}
	
	public Map<String, String> getfromConfig(Context c)
	{
		context=c;
		Map<String, String> mMap = new HashMap<String, String>();
		
		
		String versiondetail = null;
		File config = new File(Environment.getExternalStorageDirectory()
				.toString() + "/.biennale/config.json");
		// reading json from sdcard
				 try {
					 stream = new FileInputStream(config);
					
					
					 FileChannel fc = stream.getChannel();
		             MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());

		             jsonStr = Charset.defaultCharset().decode(bb).toString();
		             
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
		            try {
						stream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            catch (NullPointerException e2) {
						// TODO: handle exception
		            	Log.i("Exception"," Null pointer on closing sstream");
					}
		          }
				 
				 
					// reading from json
					 try {
						JSONObject jsonObj = new JSONObject(jsonStr);
						JSONObject items=jsonObj.getJSONObject("configResponse");
						
						//zip folder details
						JSONObject zipversion=items.getJSONObject("versionDetails");
						
						versiondetail=zipversion.getString("version")+"#version#"+zipversion.getString("url");
						mMap.put("Zip Version", versiondetail);
						
						//gallery json details
						JSONObject galleryversion=items.getJSONObject("galleryDetails");
						
						
						versiondetail=galleryversion.getString("version")+"#version#"+galleryversion.getString("url");
						mMap.put("Gallery Version", versiondetail);
						
						//Location json details
						JSONObject locationversion=items.getJSONObject("locationDetails");
						
						
						versiondetail=locationversion.getString("version")+"#version#"+locationversion.getString("url");
						mMap.put("Location Version", versiondetail);
						
						
						//Beacon json details
						JSONObject beaconversion=items.getJSONObject("beaconDetails");
						
						versiondetail=beaconversion.getString("version")+"#version#"+beaconversion.getString("url");
						mMap.put("Beacon Version", versiondetail);
						
						
						int timeinterval=4;
						try{
							
					
						if(items.getInt("beaconRefreshInterval")>=0)
						timeinterval=items.getInt("beaconRefreshInterval");
						}
						catch(NullPointerException e)
						{
							
						}
						PublicValues.BeaconRefreshInterval=timeinterval;		
					
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}catch (NullPointerException e) {
						// TODO: handle exception
						
						Log.e("Null exception", "Null pointer while reding from config.json");
					}
				 
					 PublicValues.AllVersionDetails=mMap;
		return mMap;
		
	}
	
	public ArrayList<String> getfromGallery(Activity a)
	{
		  ArrayList<String> ImgUrls =new ArrayList<String>();;
		  ArrayList<String> fullImgUrlslocal =new ArrayList<String>();;
		  
		  String timestamp = PreferenceManager.getDefaultSharedPreferences(context).getString("gallery version",null);
		 
		  Map<String, String> allversions=getfromConfig(context);
			String zipversion= allversions.get("Gallery Version");
		
			if(zipversion!=null)
			{
			
			String[] s=zipversion.split("#version#");
			
			
			File gallery = new File(Environment.getExternalStorageDirectory()
					.toString() + "/.biennale/gallery.json");
						BufferedReader br;
						String line = null;
						StringBuilder text = new StringBuilder();
						  try {
							 br = new BufferedReader(new FileReader(gallery));
							 line=br.readLine();
							 
							 while ((line = br.readLine()) != null) {
							        text.append(line);
							       
							    }
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							
						}
				 
			if(!s[0].equalsIgnoreCase(timestamp) || timestamp==null || (!gallery.isFile())||text.length()<5 )
			{
				
				PreferenceManager.getDefaultSharedPreferences(context).edit().putString("gallery version",s[0]).commit(); 
				String remoteFilePath = s[1];
				
				DownloadFiles download=new DownloadFiles(context,a);
				download.jsondownload(remoteFilePath,"gallery.json","activitygallery");
			}
			

			 try {
				 stream = new FileInputStream(gallery);
				
				
				 FileChannel fc = stream.getChannel();
		
             MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());

             jsonStr = Charset.defaultCharset().decode(bb).toString();
             
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            try {
				stream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
          }
		 
		 
		// reading from json
		 try {
			JSONObject jsonObj = new JSONObject(jsonStr);
			
			JSONObject items=jsonObj.getJSONObject("gallery");
			
			//getting categoriies
			ArrayList<String> categoryids=new ArrayList<String>();
			JSONArray categoryarray=items.getJSONArray("categories");
			for(int i=0;i<categoryarray.length();i++)
			{
				JSONObject id = categoryarray.getJSONObject(i);
				String name=id.getString("name")+"##categoryid##"+id.getInt("id");
				categoryids.add(name);
			
			}
			PublicValues.gallerycategorydetails=categoryids;
			
			//getiinng image details
			JSONArray itemarray=items.getJSONArray("items");
			ArrayList<GalleryDomain> gallerydomain=new ArrayList<GalleryDomain>();
			
			for(int i=0;i<itemarray.length();i++)
			{
				GalleryDomain g=new GalleryDomain();
				JSONObject Jsonitem = itemarray.getJSONObject(i);
				String category=Jsonitem.getString("categoryid");
				String url=Jsonitem.getString("thumbnailimageurl")+"##category##"+category;
				String full=Jsonitem.getString("imageurl")+"##category##"+category;
				
				fullImgUrlslocal.add(full);
				ImgUrls.add(url);
				
				g.setCatid(Integer.parseInt(category));
				g.setDescription(Jsonitem.getString("description"));
				g.setImagethumurl(Jsonitem.getString("thumbnailimageurl"));
				g.setImageurl(Jsonitem.getString("imageurl"));
				g.setName(Jsonitem.getString("name"));
				
				gallerydomain.add(g);
			}
			PublicValues.gallerydomain=gallerydomain;
			PublicValues.Imagefullviewurls=fullImgUrlslocal;
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			}
			else
			{
				Toast.makeText(context, "You will need internet for first one time usage!!", Toast.LENGTH_SHORT).show();
			}
		return ImgUrls;
		 
		 
	}
	
	int counter;
	
	public ArrayList<String > getDatafromLocation(Activity a,String attr)
	{
  ArrayList<String> locationdetails =new ArrayList<String>();;
		  
		
		  
		  
		  String timestamp = PreferenceManager.getDefaultSharedPreferences(context).getString("location version",null);
		 
		  Map<String, String> allversions=getfromConfig(context);
			String zipversion= allversions.get("Location Version");
			
			
			if(zipversion!=null)
			{
				String[] s=zipversion.split("#version#");
			
			File location = new File(Environment.getExternalStorageDirectory()
					.toString() + "/.biennale/location.json");
			
						BufferedReader br;
						String line = null;
						StringBuilder text = new StringBuilder();
						  try {
							 br = new BufferedReader(new FileReader(location));
							 line=br.readLine();
							 
							 while ((line = br.readLine()) != null) {
							        text.append(line);
							        
							    }
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							
						}
						  counter++;
			if((!s[0].equalsIgnoreCase(timestamp) || timestamp==null || (!location.isFile())|| text.length()<5) && counter<4)
			{
				
				PreferenceManager.getDefaultSharedPreferences(context).edit().putString("location version",s[0]).commit(); 
				String remoteFilePath = s[1];
				
				DownloadFiles download=new DownloadFiles(context,a);
				String progress;
				if(attr.equalsIgnoreCase("activityLocation"))
				{
				 progress=download.jsondownload(remoteFilePath,"location.json","activityLocation");
				}
				else
				{
					progress=download.jsondownload(remoteFilePath,"location.json","activityArtist");
				}
			}
			

		 try {
			 stream = new FileInputStream(location);
			
			
			 FileChannel fc = stream.getChannel();
             MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());

             jsonStr = Charset.defaultCharset().decode(bb).toString();
             
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            try {
				stream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
          }
		 
		 
		// reading from json
		 try {
			JSONObject jsonObj = new JSONObject(jsonStr);
			JSONObject items=jsonObj.getJSONObject("locations");
			JSONArray itemarray=items.getJSONArray("items");
			 PublicValues.alllocationdetails.clear();
			for(int i=0;i<itemarray.length();i++)
			{
				LocationDetails loc=new LocationDetails();
				JSONObject Jsonitem = itemarray.getJSONObject(i);
				String det=Jsonitem.getString("details");
				if(det==null || det.length()<2)
				{
					det="No Data Available";
				}
				
			
				locationdetails.add(Jsonitem.getString("name")+"#location#"+
						Jsonitem.getString("venueimage")+"#location#"+
						Jsonitem.getString("latitude")+"#location#"+
						Jsonitem.getString("longitude")+"#location#"+
						det+"#location#"+
						Jsonitem.getInt("id")+"#location#"+
						Jsonitem.getString("floorimage"));
				loc.setName(Jsonitem.getString("name"));
	    		 loc.setVenueimageurl(Jsonitem.getString("venueimage"));
	    		 double lat=Double.parseDouble(Jsonitem.getString("latitude"));
	    		 double lng=Double.parseDouble(Jsonitem.getString("longitude"));
	    		 loc.setLatlng(new LatLng(lat,lng));
	    		 
	    		 loc.setDetails(det);
	    		 loc.setId(Jsonitem.getInt("id"));
	    		 loc.setFloorname(Jsonitem.getString("floorimage"));
	    		 PublicValues.alllocationdetails.add(loc);
				
			}
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
			else
			{
				Toast.makeText(context, "You will need internet for first one time usage!!", Toast.LENGTH_SHORT).show();
			}
		 
		 
		return locationdetails;
		
	}
	
	
	public void getDatafromBeacon(Activity a)
	{
  ArrayList<Beacondetails> Beacondetailsarraylist =new ArrayList<Beacondetails>();;
		  
	
             String json = null;
 		    
 		    try {

 		        InputStream is = context.getAssets().open("beacons.json.php");

 		        int size = is.available();

 		        byte[] buffer = new byte[size];

 		        is.read(buffer);

 		        is.close();

 		        json = new String(buffer, "utf-8");


 		    }
 		    catch (IOException ex) {
 		        ex.printStackTrace();
 		       
 		    }
 		    
 		    

             jsonStr = json;
             
		 
		 
		// reading from json
		 try {
			JSONObject jsonObj = new JSONObject(jsonStr);
			JSONObject items=jsonObj.getJSONObject("beacons");
			JSONArray itemarray=items.getJSONArray("items");
			for(int i=0;i<itemarray.length();i++)
			{
				
				if(i==102)
				{
					
					int k=100;
				}
				JSONObject Jsonitem = itemarray.getJSONObject(i);
				
				
			Beacondetails beacondetail = new Beacondetails();
			beacondetail.setName(Jsonitem.getString("name"));
			beacondetail.setDescription(Jsonitem.getString("description"));
			beacondetail.setDetailurl(Jsonitem.getString("detailsurl"));
			
			beacondetail.setDescriptionimageurl(Jsonitem.getString("descriptionimageurl"));
			beacondetail.setartistNameEnglish(Jsonitem.getString("artistnameenglish"));
			beacondetail.setartistDescriptionEnglish(Jsonitem.getString("artistdecrpenglish"));
			beacondetail.setInstallationEnglish(Jsonitem.getString("installationenglish"));
			beacondetail.setInstallationDescriptionEnglish(Jsonitem.getString("installationdescpenglish"));
			beacondetail.setDescriptionEnglish(Jsonitem.getString("descriptionenglish"));
			beacondetail.setArtistNameMalayalam(Jsonitem.getString("artistnamemalayalm"));
			beacondetail.setArtistDescriptionMalayalam(Jsonitem.getString("artistdecrpmalayalm"));
			beacondetail.setDescriptionMalayalam(Jsonitem.getString("descriptionmalayal"));
			
			String majorminoor=Jsonitem.getString("majorminor");
			String split[]=majorminoor.split("-");
			
			beacondetail.setMajor(Integer.parseInt(split[0]));
			beacondetail.setMinor(Integer.parseInt(split[1]));
			
			beacondetail.setId(Jsonitem.getString("id"));
			beacondetail.setImageurl(Jsonitem.getString("imageurl"));
			beacondetail.setVideourl(Jsonitem.getString("videourl"));
			beacondetail.setLocationid(Jsonitem.getInt("locationid"));
			beacondetail.setX(Jsonitem.getInt("xcordinate"));
			beacondetail.setY(Jsonitem.getInt("ycordinate"));
			
			String artist=Jsonitem.getString("artistid");
			if(!artist.equalsIgnoreCase(""))
			{
			beacondetail.setArtistid(Jsonitem.getInt("artistid"));
			}
			else
			{
				beacondetail.setArtistid(0);
			}
			String message = null;
			if(Jsonitem.getString("entrymessage")!=null)
			{
				message=Jsonitem.getString("entrymessage");
			}
			else if(Jsonitem.getString("exitmessage")!=null)
			{
				message=Jsonitem.getString("exitmessage");
			}
			else
			{
				message="";
			}
			
			beacondetail.setMessage(message);
			
			
			
			Beacondetailsarraylist.add(beacondetail);
				
			}
			
			PublicValues.beacondetails=Beacondetailsarraylist;
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (NullPointerException e) {
			// TODO: handle exception
			Toast.makeText(context, "nullpoint,", Toast.LENGTH_SHORT).show();
		}
		 
	}
	
	
}