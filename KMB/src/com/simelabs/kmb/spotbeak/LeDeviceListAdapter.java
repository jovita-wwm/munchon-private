package com.simelabs.kmb.spotbeak;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import com.simelabs.kmb.launch.R;
import com.simelabs.kmb.gallery.ImageLoader;
import com.simelabs.kmb.locationmap.LocationMapAdapter;
import com.simelabs.kmb.service.PublicValues;



/**
 * Displays basic information about beacon.
 *
 * @author Jovita
 */
public class LeDeviceListAdapter extends BaseAdapter {

  private ArrayList<Beacon> beacons;
  private LayoutInflater inflater;
  Context c;
  public ImageLoader imageLoader; 

  public LeDeviceListAdapter(Context context) {
    this.inflater = LayoutInflater.from(context);
    this.beacons = new ArrayList<Beacon>();
    this.c=context;
    imageLoader=new ImageLoader(context);
  }

  public void replaceWith(Collection<Beacon> newBeacons) {
    this.beacons.clear();
    this.beacons.addAll(newBeacons);
    notifyDataSetChanged();
  }

  @Override
  public int getCount() {
    return beacons.size();
  }

  @Override
  public Beacon getItem(int position) {
    return beacons.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }
boolean viewstatus;
  @Override
  public View getView(int position, View view, ViewGroup parent) {
	/* if(view==null)
	 {
		 viewstatus=false; 
	 }*/
    view = inflateIfRequired(view, 0, parent);
    bind(getItem(position), view);
    
 /*  
    if(position==0 && viewstatus==false)
    {
    Animation animation = AnimationUtils.loadAnimation(c, (position > lastPosition) ? R.anim.zoomin : R.anim.zoomin);
    view.startAnimation(animation);
    lastPosition = position;
    }*/
   /* Animation animation = AnimationUtils.loadAnimation(c, (position > lastPosition) ? R.anim.zoomin : R.anim.zoomin);
    view.startAnimation(animation);
    lastPosition = position;*/
    
    return view;
  }

 
  private void bind(Beacon beacon, View view) {
    ViewHolder holder = (ViewHolder) view.getTag();
    
    //gettt data from beacon  json
    Beacondetails detail=getbeacondetail(beacon);
    
    
 
    holder.titleTextView.setText(detail.getName());
  // holder.titleTextView.setText("Accu:"+beacon.getAccuracy());
    holder.descTextView.setText(detail.getDescription());
   // holder.beakimg.setBackground(background);
   // new DownloadImageTask(holder.beakimg).execute(detail.getImageurl());
    imageLoader.DisplayImage(detail.getImageurl(), holder.beakimg);
    //imageLoader.DisplayImage("https://s3-us-west-2.amazonaws.com/biennaleimageupload/beaconimagesizetest.jpg", holder.beakimg);
    
    holder.beaconndetailIMG.setTag(detail.getDescriptionimageurl()+"#details#"+
    		detail.getartistNameEnglish()+"#details#"+detail.getartistDescriptionEnglish()+"#details#"+
    	    		detail.getInstallationEnglish()+"#details#"+detail.getInstallationDescriptionEnglish()+"#details#"+
    	    	    		detail.getDescriptionenglish()+"#details#"+detail.getArtistNameMalayalam()+"#details#"+
    	    		detail.getArtistDescriptionMalayalam()+"#details#"+
    	    	    	    		detail.getDescriptionMalayalam());

    holder.beaconvideo.setTag(detail.getVideourl());
    holder.beacondistance.setVisibility(View.INVISIBLE);
    holder.beaconshare.setTag(detail.getName()+"#share#"+detail.getDescription()+"#share#"+
    detail.getDetailurl()+"#share#"+detail.getImageurl());
    
   // holder.beacondistance.setText(beacon.getMeasuredPower());
   // holder.minorTextView.setText("Minor: " + beacon.getMinor());
   // holder.measuredPowerTextView.setText("MPower: " + beacon.getMeasuredPower());
   
    
    // holder.macTextView.setText(String.format("MAC: %s (%.2fm)", beacon.getMacAddress(), Utils.computeAccuracy(beacon)));
    
    
    
    
    
    
  }
 // private int lastPosition = -1;

  private View inflateIfRequired(View view, int position, ViewGroup parent) {
    if (view == null) {
      view = inflater.inflate(R.layout.list_item_handle_left, null);
      view.setTag(new ViewHolder(view));
      
     
     /* Animation animation = AnimationUtils.loadAnimation(c, (position > lastPosition) ? R.anim.zoomin : R.anim.zoomin);
      view.startAnimation(animation);
      lastPosition = position;*/
      
		
    }
    return view;
  }

  static class ViewHolder {
    //final TextView macTextView;
    final TextView titleTextView;
  //  final TextView minorTextView;
   // final TextView measuredPowerTextView;
    final TextView descTextView,beacondistance;
    final ImageView beakimg,beaconndetailIMG,beaconvideo,beaconshare;

    ViewHolder(View view) {
     // macTextView = (TextView) view.findViewWithTag("mac");
      titleTextView = (TextView) view.findViewWithTag("title");
    //  minorTextView = (TextView) view.findViewWithTag("minor");
    //  measuredPowerTextView = (TextView) view.findViewWithTag("mpower");
      descTextView = (TextView) view.findViewWithTag("description");
      beakimg=(ImageView)view.findViewWithTag("beakimg");
      beaconndetailIMG=(ImageView)view.findViewById(R.id.beaconndetailIMG);
      beaconvideo=(ImageView)view.findViewById(R.id.beaconvideo);
      beacondistance=(TextView)view.findViewById(R.id.beacondistance);
      beaconshare=(ImageView)view.findViewById(R.id.beaconshare);
      
      
      
    }
  }
  
  public Beacondetails getbeacondetail(Beacon b)
  {
	  Beacondetails detail = null;
	  ArrayList<Beacondetails> fullbeacon=PublicValues.beacondetails;
	  for(Beacondetails beak:fullbeacon)
	  {
		  String beaconori=b.getProximityUUID().toUpperCase()+b.getMajor()+b.getMinor();
			String beaconlist=beak.getId().toUpperCase()+beak.getMajor()+beak.getMinor();
			if(beaconori.equalsIgnoreCase(beaconlist))
			{
				detail=beak;
			}
	  }
	return detail;
	  
	  
	  
  }
  
  private class DownloadImageTask extends AsyncTask<String, Void, Drawable> {


  	ImageView bmImage;
  	 Drawable d ;

  	public DownloadImageTask(ImageView v) {
  	   
  		bmImage=v;
  		
  	}

  	protected Drawable doInBackground(String... urls) {
  		
  		
  	   String urldisplay = urls[0];
  	    
  	  
  	    	 Bitmap mIcon11 = null;
		    	    try {
		    	        InputStream in = new java.net.URL(urldisplay).openStream();
		    	        mIcon11 = BitmapFactory.decodeStream(in);
		    	        
		    	         d = new BitmapDrawable(c.getResources(),mIcon11);
		    	        
  	    
  	   
  	        
  	        
  	    } catch (Exception e) {
  	        Log.e("Error", e.getMessage());
  	        e.printStackTrace();
  	    }
		    	    
		    	    return d;
  	    }
			
  	


  	protected void onPostExecute(Drawable s) {
  		
  		 //set adapter tp venues list
	    	bmImage.setBackground(s);
  		
  	}
  }
  
 
}
