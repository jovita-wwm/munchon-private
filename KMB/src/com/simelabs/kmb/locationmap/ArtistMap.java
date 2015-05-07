package com.simelabs.kmb.locationmap;


import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.android.gms.internal.mp;
import com.simelabs.kmb.launch.R;
import com.simelabs.kmb.activities.ArtistsDetailPage;
import com.simelabs.kmb.domain.ArtistDomain;
import com.simelabs.kmb.domain.LocationDetails;
import com.simelabs.kmb.gallery.ImageLoader;
import com.simelabs.kmb.managers.JsonManager;
import com.simelabs.kmb.service.PublicValues;
import com.simelabs.kmb.socialmedia.FacebookClass;
import com.simelabs.kmb.spotbeak.Beacon;
import com.simelabs.kmb.spotbeak.BeaconDetailViewActivity;
import com.simelabs.kmb.spotbeak.Beacondetails;
import com.simelabs.kmb.spotbeak.Videoview;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff.Mode;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
 
public class ArtistMap extends Activity implements OnClickListener {
 
    ArrayList<Beacondetails> beaconsonlocation,beacons;
   // ArrayList<LocationDetails> alllocations;
    LinearLayout container;
    boolean maptype;
    String desImgUrl,artNameEng,artDesEng,instEng,instDesEng,desEng,artNameMal,artDescMal,descMal;
	 
    
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_inside);
   
        
         container=(LinearLayout)findViewById(R.id.mapcontainer);
        
        ImageView marker = (ImageView) findViewById(R.id.marker);
      
       //........................................................... 
      
        JsonManager beakjson=new JsonManager(getApplicationContext());
		beakjson.getDatafromBeacon(this);
		//ArrayList<ArtistDomain> arayy=(ArrayList<ArtistDomain>)getIntent().getSerializableExtra("arraaytest");
		
        String Location=getIntent().getExtras().getString("location").toLowerCase();
       Map<String, ArrayList<Beacondetails>> beaconshashmap=new HashMap<String, ArrayList<Beacondetails>>();
        beaconshashmap=PublicValues.venubeacondetails;
        
        //ArrayList<Beacondetails> artistbeacons=(ArrayList<Beacondetails>)getIntent().getSerializableExtra("artistbeacons");
       // ArrayList<Beacondetails> artistbeacons=ArtistsDetailPage.s;
        ArrayList<Beacondetails> rawbeacolist=new ArrayList<Beacondetails>();
        ArrayList<Beacondetails> singlevenubeacondetails=new ArrayList<Beacondetails>();
       
        
        Iterator it = beaconshashmap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            String s=pairs.getKey().toString().toLowerCase();
            if(s.contains(Location))
            {
            	singlevenubeacondetails=(ArrayList<Beacondetails>) pairs.getValue();
            }

            it.remove();
        }
       
       // Map<Integer, ArrayList<Beacondetails>> map = new HashMap<Integer, ArrayList<Beacondetails>>();
      /*  Iterator<Map.Entry<String, ArrayList<Beacondetails>>> entries = beaconshashmap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, ArrayList<Beacondetails>> entry = entries.next();
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            if(Location.equalsIgnoreCase(entry.getKey().toString()))
            {
            	ArrayList<Beacondetails> arry=entry.getValue();
            	rawbeacolist=entry.getValue();
          //  rawbeacolist.add(entry.getValue());
            }
        }*/
        
        
        
        
        
        int mapid = R.drawable.aspinwall;
        int layoutid=R.layout.layout_aspinwall;
        //String name=loc.getFloorname().toLowerCase();
        if(Location.contains("aspin"))
        {
        	mapid=R.drawable.aspinwall;
        	layoutid=R.layout.layout_aspinwall;
        }
        else if(Location.contains("david"))
        {
        	mapid=R.drawable.david_hall;
        	layoutid=R.layout.layout_davidhall;
        }else if(Location.contains("kashi"))
        {
        	mapid=R.drawable.kashi_art_gallery;
        	layoutid=R.layout.layout_kashi;
        }
        else if(Location.contains("pepper"))
        {
        	mapid=R.drawable.pepper_house;
        	layoutid=R.layout.layout_pepper;
        }
        else if(Location.contains("durbar"))
        {
        	mapid=R.drawable.durbar_hall;
        	layoutid=R.layout.layout_durbarhall;
        }
        else if(Location.contains("cabral"))
        {
        	mapid=R.drawable.cabral_yard;
        	layoutid=R.layout.layout_cabral_yard;
        }
        else if(Location.contains("vasco"))
        {
        	mapid=R.drawable.vasco_da_gama_square;
        	layoutid=R.layout.layout_vasco;
        }
        else if(Location.contains("csi"))
        {
        	mapid=R.drawable.csi_banglow;
        	layoutid=R.layout.layout_csi_banglow;
        }
       // String mapid=R.drawable+".loc.getFloorname()";
       // drawmarkers(mapid,beaconsonlocation,marker);
     //  marker.setImageResource(mapid);
        //...................................................
        
       // marker.setOnTouchListener(this);
        setlayout(layoutid,singlevenubeacondetails);
        
    }
    
    
 
    private void setlayout(int layoutid,ArrayList<Beacondetails> last) {
		// TODO Auto-generated method stub
    	
    	ImageView bac;
    	final Animation b1ball=AnimationUtils.loadAnimation(this, R.anim.mapbeaconanimation);
    	View child = getLayoutInflater().inflate(layoutid, null);
    	container.addView(child);
    	switch (layoutid) {
		case R.layout.layout_aspinwall:
		
			int frontbuttons[]={R.id.img_beak1,R.id.img_beak2,R.id.img_beak3,R.id.img_beak4,R.id.img_beak5,R.id.img_beak6,R.id.img_beak7,R.id.img_beak8,R.id.img_beak9,R.id.img_beak10,R.id.img_beak10_1
	    			,R.id.img_beak11,R.id.img_beak12,R.id.img_beak12_1,R.id.img_beak12_2,R.id.img_beak12_3,R.id.img_beak13,R.id.img_beak14,R.id.img_beak15,R.id.img_beak16,R.id.img_beak17,R.id.img_beak18,R.id.img_beak18_1,R.id.img_beak18_2,R.id.img_beak18_3,R.id.img_beak19,R.id.img_beak19_1,R.id.img_beak20
	    			,R.id.img_beak21,R.id.img_beak22,R.id.img_beak22_1,R.id.img_beak23,R.id.img_beak24,R.id.img_beak25,R.id.img_beak26,R.id.img_beak27,R.id.img_beak28,R.id.img_beak29,R.id.img_beak30
	    			,R.id.img_beak31,R.id.img_beak32,R.id.img_beak33,R.id.img_beak34,R.id.img_beak35,R.id.img_beak36,R.id.img_beak36_1,R.id.img_beak36_2,R.id.img_beak37,R.id.img_beak38,R.id.img_beak39,R.id.img_beak40
	    			,R.id.img_beak41,R.id.img_beak42,R.id.img_beak42_1,R.id.img_beak43,R.id.img_beak44,R.id.img_beak44_1,R.id.img_beak45,R.id.img_beak46,R.id.img_beak47,R.id.img_beak48,R.id.img_beak49,R.id.img_beak50
	    			,R.id.img_beak51,R.id.img_beak52,R.id.img_beak53,R.id.img_beak53_1,R.id.img_beak54,R.id.img_beak55,R.id.img_beak56,R.id.img_beak57,R.id.img_beak57_1,R.id.img_beak58,R.id.img_beak58_1,R.id.img_beak58_2,R.id.img_beak59,R.id.img_beak60
	    			,R.id.img_beak61,R.id.img_beak62,R.id.img_beak63,R.id.img_beak64,R.id.img_beak65,R.id.img_beak66,R.id.img_beak67,R.id.img_beak68,R.id.img_beak69};
	    		
	    
			
	    	ArrayList<ImageView> lastbeaconimage = new ArrayList<ImageView>();
				
				for(int i:frontbuttons)
		    	{
					
		    		ImageView dot=(ImageView)findViewById(i);
		    		
		    		dot.setVisibility(View.INVISIBLE);
		    		for(Beacondetails beaklist:last)
		    		{
			    		if(Integer.parseInt(dot.getTag().toString())==beaklist.getMajor())
			    		{
			    			lastbeaconimage.add(dot);
			    		//dot.setVisibility(View.INVISIBLE);
			    		}
		    		}
		    			
		    	}
				if(lastbeaconimage.size()>0)
				{
					for(ImageView img:lastbeaconimage)
					{
				img.setVisibility(View.VISIBLE);
				img.setAnimation(b1ball);
				img.setOnClickListener(ArtistMap.this);
					}
				}
			
			break;
			
		case R.layout.layout_cabral_yard:
			 bac=(ImageView)findViewById(R.id.img_cabral_beak);
			
			bac.setOnClickListener(ArtistMap.this);
			bac.startAnimation(b1ball);
			break;
		case R.layout.layout_csi_banglow:
			
			int csifrontbuttons[]={R.id.img_csi_beak87,R.id.img_csi_beak85,R.id.img_csi_beak90,R.id.img_csi_beak86,R.id.img_csi_beak89,R.id.img_csi_beak12,R.id.img_csi_beak88};
			
			
			ArrayList<ImageView> csilastbeaconimage = new ArrayList<ImageView>();
			
				for(int i:csifrontbuttons)
		    	{
					
		    		ImageView dot=(ImageView)findViewById(i);
		    		dot.clearAnimation();
		    		dot.setVisibility(View.INVISIBLE);
		    		for(Beacondetails beaklist:last)
		    		{
		    		
		    		if(Integer.parseInt(dot.getTag().toString())==beaklist.getMajor())
		    		{
		    			csilastbeaconimage.add(dot);
		    		//dot.setVisibility(View.INVISIBLE);
		    		}
		    		}
		    			
		    	}
				if(csilastbeaconimage.size()>0)
				{
					for(ImageView img:csilastbeaconimage)
					{
				img.setVisibility(View.VISIBLE);
				img.setAnimation(b1ball);
				img.setOnClickListener(ArtistMap.this);
					}
				}
			
			break;
			
case R.layout.layout_davidhall:
			
int davidfrontbuttons[]={R.id.img_david_beak81,R.id.img_david_beak82,R.id.img_david_beak83,R.id.img_david_beak12};
	
	ArrayList<ImageView> davidlastbeaconimage = new ArrayList<ImageView>();
	
		for(int i:davidfrontbuttons)
    	{
			
    		ImageView dot=(ImageView)findViewById(i);
    		dot.clearAnimation();
    		dot.setVisibility(View.INVISIBLE);
    		for(Beacondetails beaklist:last)
    		{
    		
    		if(Integer.parseInt(dot.getTag().toString())==beaklist.getMajor())
    		{
    			davidlastbeaconimage.add(dot);
    		//dot.setVisibility(View.INVISIBLE);
    		}
    		}
    			
    	}
		if(davidlastbeaconimage.size()>0)
		{
			for(ImageView img:davidlastbeaconimage)
			{
		img.setVisibility(View.VISIBLE);
		img.setAnimation(b1ball);
		img.setOnClickListener(ArtistMap.this);
			}
		}
			
			break;
			
case R.layout.layout_durbarhall:
	
int durbarfrontbuttons[]={R.id.img_durbar_beak95,R.id.img_durbar_beak96,R.id.img_durbar_beak98,R.id.img_durbar_beak97,R.id.img_durbar_beak99,R.id.img_durbar_beak100,R.id.img_durbar_beak94,R.id.img_durbar_beak93,R.id.img_durbar_beak92,R.id.img_durbar_beak91};
		
	
	
	ArrayList<ImageView> durbarlastbeaconimage = new ArrayList<ImageView>();
	
		for(int i:durbarfrontbuttons)
    	{
			
    		ImageView dot=(ImageView)findViewById(i);
    		dot.clearAnimation();
    		dot.setVisibility(View.INVISIBLE);
    		
    		for(Beacondetails beaklist:last)
    		{
    		
    		if(Integer.parseInt(dot.getTag().toString())==beaklist.getMajor())
    		{
    			durbarlastbeaconimage.add(dot);
    		//dot.setVisibility(View.INVISIBLE);
    		}
    		}
    			
    	}
		if(durbarlastbeaconimage.size()>0)
		{
			for(ImageView img:durbarlastbeaconimage)
			{
		img.setVisibility(View.VISIBLE);
		img.setAnimation(b1ball);
			}
		}

	break;
	
case R.layout.layout_kashi:
	
int kashifrontbuttons[]={R.id.img_kashi_beak84};
		
	
	
	
	for(int i:kashifrontbuttons)
	{
		ImageView dot=(ImageView)findViewById(i);
		dot.setAnimation(b1ball);
		dot.startAnimation(b1ball);
		dot.setOnClickListener(this);
	}
	
	
	break;
	
case R.layout.layout_pepper:
	
	int peperfrontbuttons[]={R.id.img_pepper_beak74,R.id.img_pepper_beak75,R.id.img_pepper_beak73,R.id.img_pepper_beak76,R.id.img_pepper_beak76_1,R.id.img_pepper_beak77,R.id.img_pepper_beak71,R.id.img_pepper_beak72,R.id.img_pepper_beak78,R.id.img_pepper_beak79};
		
	

	ArrayList<ImageView> peperlastbeaconimage = new ArrayList<ImageView>();
	
		for(int i:peperfrontbuttons)
    	{
			
    		ImageView dot=(ImageView)findViewById(i);
    		dot.clearAnimation();
    		dot.setVisibility(View.INVISIBLE);
    		for(Beacondetails beaklist:last)
    		{
    		
    		if(Integer.parseInt(dot.getTag().toString())==beaklist.getMajor())
    		{
    			peperlastbeaconimage.add(dot);
    		//dot.setVisibility(View.INVISIBLE);
    		}
    		}
    			
    	}
		if(peperlastbeaconimage.size()>0)
		{
			for(ImageView img:peperlastbeaconimage)
			{
		img.setVisibility(View.VISIBLE);
		img.setAnimation(b1ball);
			}
		}

	break;
	
case R.layout.layout_vasco:
	
	 bac=(ImageView)findViewById(R.id.img_vaso_beak80);
	
	bac.setOnClickListener(ArtistMap.this);
	bac.startAnimation(b1ball);
	
	
	break;
	default:
	break;
		}
    	
    }



    Dialog beak;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.beaconndetailIMG)
		{
			
			 try
			 {
		     
			 String []details =v.getTag().toString().split("#details#");
				 
			 desImgUrl = details[0];
			 artNameEng = details[1];
			 artDesEng = details[2];
			 instEng = details[3];
			 instDesEng = details[4];
			 desEng = details[5];
			 artNameMal = details[6];
			 artDescMal = details[7];
			 descMal = details[8];
			 }
			 
			 catch(ArrayIndexOutOfBoundsException e)
			 {
		
				 artDesEng = "   "; 
				 instEng = "   "; 
				 instDesEng = "   "; 
				 artNameMal = "   "; 
				 artDescMal = "   "; 
				 descMal = "   "; 
			 }
			 
             Intent i=new Intent(this, BeaconDetailViewActivity.class);
			 
			 i.putExtra("desImgUrl", desImgUrl);
			 i.putExtra("artNameEng", artNameEng);
			 i.putExtra("artDesEng", artDesEng);
			 i.putExtra("instEng", instEng);
			 i.putExtra("instDesEng", instDesEng);
			 i.putExtra("desEng", desEng);
			 i.putExtra("artNameMal", artNameMal);
			 i.putExtra("artDescMal", artDescMal);
			 i.putExtra("descMal", descMal);
			 
			 startActivity(i);
			/* Intent i=new Intent(this, BeaconDetailViewActivity.class);
			 i.putExtra("url", v.getTag().toString());
			 startActivity(i);*/
			
		}
		else if(v.getId()==R.id.beaconvideo)
		{
			 Intent intent=new Intent(this, Videoview.class);
			 intent.putExtra("url", v.getTag().toString());
			 startActivity(intent);
		}else if(v.getId()==R.id.beaconshare)
		{
			 String []details=v.getTag().toString().split("#share#");
				String myAccessToken = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("Access token",null);
			   String  accesstoken = myAccessToken;
			        
				 if (accesstoken== null) 
			     {
					 Toast.makeText(getApplicationContext(), "Please login to Facebook!!!", Toast.LENGTH_LONG).show();
				 }
				 else
				 {
					 FacebookClass fb= new FacebookClass(ArtistMap.this);
					 fb.publishFeedDialog(details[0], details[1], details[2], details[3]);
				 }
		}
	else
	{
		 imageLoader=new ImageLoader(ArtistMap.this);
		// Toast.makeText(getApplicationContext(), "click", Toast.LENGTH_SHORT).show();
		beak=new Dialog(ArtistMap.this);
		beak.setContentView(R.layout.dialog_bacondtails);
		 TextView titleTextView = (TextView) beak.findViewById(R.id.textView2);
		    //  minorTextView = (TextView) view.findViewWithTag("minor");
		    //  measuredPowerTextView = (TextView) view.findViewWithTag("mpower");
		      TextView descTextView = (TextView) beak.findViewById(R.id.textView3);
		      ImageView beakimg = (ImageView)beak.findViewById(R.id.imageView4);
		      ImageView beaconndetailIMG = (ImageView)beak.findViewById(R.id.beaconndetailIMG);
		      ImageView beaconvideo = (ImageView)beak.findViewById(R.id.beaconvideo);
		      TextView beacondistance = (TextView)beak.findViewById(R.id.beacondistance);
		      ImageView beaconshare = (ImageView)beak.findViewById(R.id.beaconshare);
		      beaconndetailIMG.setOnClickListener(this);
		      beaconvideo.setOnClickListener(this);
		      beaconshare.setOnClickListener(this);
		
		int id=v.getId();
		
		int tag=Integer.parseInt(v.getTag().toString());
		beak.setTitle("Installation : "+tag);
		
		ArrayList<Beacondetails> Beacondetailsarraylist =new ArrayList<Beacondetails>();
    	Beacondetailsarraylist=PublicValues.beacondetails;
    	
    	for(Beacondetails b:Beacondetailsarraylist)
    	{
    		int major=b.getMajor();
    		if(major==tag)
    		{
    			titleTextView.setText(b.getName());
    			descTextView.setText(b.getDescription());
    			  imageLoader.DisplayImage(b.getImageurl(), beakimg);
    			  beaconndetailIMG.setTag(b.getDescriptionimageurl()+"#details#"+
    			    		b.getartistNameEnglish()+"#details#"+b.getartistDescriptionEnglish()+"#details#"+
  	    	    		b.getInstallationEnglish()+"#details#"+b.getInstallationDescriptionEnglish()+"#details#"+
  	    	    	    		b.getDescriptionenglish()+"#details#"+b.getArtistNameMalayalam()+"#details#"+
  	    	    		b.getArtistDescriptionMalayalam()+"#details#"+
  	    	    	    	    		b.getDescriptionMalayalam());
      			  
    			  beaconvideo.setTag(b.getVideourl());
    			  beacondistance.setVisibility(View.INVISIBLE);
    			  beaconshare.setTag(b.getName()+"#share#"+b.getDescription()+"#share#"+
    					    b.getDetailurl()+"#share#"+b.getImageurl());
    			  break;
    		}
    	}
    	
    	
    	
    	
		
		//Toast.makeText(getApplicationContext(), "clicked at :"+id, Toast.LENGTH_SHORT).show();
			beak.show();
			
	}
		
	}
	
	public ImageLoader imageLoader; 
	
	 public void BeacondetailViewclick(View v)
	  {
		 
		 Intent i=new Intent(this, BeaconDetailViewActivity.class);
		 i.putExtra("url", v.getTag().toString());
		 startActivity(i);
		  //Toast.makeText(getApplicationContext(), "detaiil clicked", Toast.LENGTH_SHORT).show();
	  }
	 
	 public void Beaconshareclick(View v)
	 {
		 //Toast.mak eText(getApplicationContext(), "Please login to Facebook!!!", Toast.LENGTH_LONG).show();
			
		 String []details=v.getTag().toString().split("#share#");
		String myAccessToken = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("Access token",null);
	   String  accesstoken = myAccessToken;
	        
		 if (accesstoken== null) 
	     {
			 Toast.makeText(getApplicationContext(), "Please login to Facebook!!!", Toast.LENGTH_LONG).show();
		 }
		 else
		 {
			 FacebookClass fb= new FacebookClass(ArtistMap.this);
			 fb.publishFeedDialog(details[0], details[1], details[2], details[3]);
		 }
	 } 
	 
	 public void BeaconVideoclick(View v)
	 {
		/* Intent i=new Intent(this, BeaconVideoViewActivity.class);
		 i.putExtra("url", v.getTag().toString());
		 Log.i("video url",  v.getTag().toString());
		 startActivity(i);*/
		 
	/*	 Intent intent=new Intent(this, Videoview.class);
		 intent.putExtra("url", v.getTag().toString());
		 startActivity(intent);*/
		 Intent i = new Intent(Intent.ACTION_VIEW);
		 
		// Toast.makeText(getApplicationContext(), v.getTag().toString(), Toast.LENGTH_SHORT).show();
	        i.setData(Uri.parse(v.getTag().toString()));
	        ArtistMap.this.startActivity(i);
		 //Toast.makeText(getApplicationContext(), "video", Toast.LENGTH_SHORT).show();
	 }
}

