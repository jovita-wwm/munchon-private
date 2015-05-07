package com.simelabs.kmb.locationmap;


import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;
import java.util.ArrayList;

import com.google.android.gms.internal.mp;
import com.simelabs.kmb.launch.R;
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
 
public class MapManagerInside extends Activity implements OnTouchListener,OnClickListener {
 
    private static final String TAG = "Touch";
    @SuppressWarnings("unused")
    private static final float MIN_ZOOM = 1f, MAX_ZOOM = 1f;
 
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();
 
    // The 3 states (events) which the user is trying to perform
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;
    Beacondetails lastbeaconfound;
    // these PointF objects are used to record the point(s) the user is touching
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;
    ArrayList<Beacondetails> beaconsonlocation,beacons;
    ArrayList<LocationDetails> alllocations;
    LinearLayout container;
    boolean maptype;
    
 
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
		
        int position=getIntent().getExtras().getInt("position");
        alllocations=PublicValues.alllocationdetails;
        LocationDetails loc = null;
        
         lastbeaconfound=PublicValues.lastbeaconfound;
         beaconsonlocation=new ArrayList<Beacondetails>();
        
        if(position!=9999)
        {
        	maptype=false;
        	loc=PublicValues.alllocationdetails.get(position);
        int locationid=loc.getId();
        
        beacons=PublicValues.beacondetails;
        
       
        
        for(Beacondetails beacon:beacons)
        {
        	if(beacon.getLocationid()==locationid)
        	{
        		beaconsonlocation.add(beacon);
        	}
        }
        
        
       // drawmarkers(R.drawable.aspinwallmap,beaconsonlocation,marker);
        
        }
        else
        {
        	maptype=true;
        	if(lastbeaconfound!=null)
        	{
        	beaconsonlocation.add(lastbeaconfound);
        	int locationid;
        	for(LocationDetails all:alllocations)
        	{
        		if(all.getId()==lastbeaconfound.getLocationid())
        		{
        			loc=all;
        			break;
        		}
        	}
        	
        	}else
        	{
        		Toast.makeText(getApplicationContext(), "You are not near to any installations!!", Toast.LENGTH_SHORT).show();
        	}
        }
        int mapid = R.drawable.aspinwall;
        int layoutid=R.layout.layout_aspinwall;
        String name=loc.getFloorname().toLowerCase();
        if(name.contains("aspin"))
        {
        	mapid=R.drawable.aspinwall;
        	layoutid=R.layout.layout_aspinwall;
        }
        else if(name.contains("david"))
        {
        	mapid=R.drawable.david_hall;
        	layoutid=R.layout.layout_davidhall;
        }else if(name.contains("kashi"))
        {
        	mapid=R.drawable.kashi_art_gallery;
        	layoutid=R.layout.layout_kashi;
        }
        else if(name.contains("pepper"))
        {
        	mapid=R.drawable.pepper_house;
        	layoutid=R.layout.layout_pepper;
        }
        else if(name.contains("durbar"))
        {
        	mapid=R.drawable.durbar_hall;
        	layoutid=R.layout.layout_durbarhall;
        }
        else if(name.contains("cabral"))
        {
        	mapid=R.drawable.cabral_yard;
        	layoutid=R.layout.layout_cabral_yard;
        }
        else if(name.contains("vasco"))
        {
        	mapid=R.drawable.vasco_da_gama_square;
        	layoutid=R.layout.layout_vasco;
        }
        else if(name.contains("csi"))
        {
        	mapid=R.drawable.csi_banglow;
        	layoutid=R.layout.layout_csi_banglow;
        }
       // String mapid=R.drawable+".loc.getFloorname()";
       // drawmarkers(mapid,beaconsonlocation,marker);
     //  marker.setImageResource(mapid);
        //...................................................
        
       // marker.setOnTouchListener(this);
        setlayout(layoutid,maptype,lastbeaconfound);
        
    }
    
    
 
    private void setlayout(int layoutid,boolean type,Beacondetails last) {
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
	    		
	    
	    	for(int i:frontbuttons)
	    	{
	    		ImageView dot=(ImageView)findViewById(i);
	    		dot.setAnimation(b1ball);
	    		dot.startAnimation(b1ball);
	    		dot.setOnClickListener(this);
	    	}
			
	    	ArrayList<ImageView> lastbeaconimage = new ArrayList<ImageView>();
			if(maptype==true)
			{
				
				
		    	
				if(last!=null)
				for(int i:frontbuttons)
		    	{
					
		    		ImageView dot=(ImageView)findViewById(i);
		    		dot.clearAnimation();
		    		dot.setVisibility(View.INVISIBLE);
		    		if(Integer.parseInt(dot.getTag().toString())==last.getMajor())
		    		{
		    			lastbeaconimage.add(dot);
		    		//dot.setVisibility(View.INVISIBLE);
		    		}
		    			
		    	}
				if(lastbeaconimage.size()>0)
				{
					for(ImageView img:lastbeaconimage)
					{
				img.setVisibility(View.VISIBLE);
				img.setAnimation(b1ball);
					}
				}
			}
			break;
			
		case R.layout.layout_cabral_yard:
			 bac=(ImageView)findViewById(R.id.img_cabral_beak);
			
			bac.setOnClickListener(MapManagerInside.this);
			bac.startAnimation(b1ball);
			break;
		case R.layout.layout_csi_banglow:
			
			int csifrontbuttons[]={R.id.img_csi_beak87,R.id.img_csi_beak85,R.id.img_csi_beak90,R.id.img_csi_beak86,R.id.img_csi_beak89,R.id.img_csi_beak12,R.id.img_csi_beak88};
			
			
			
			
			for(int i:csifrontbuttons)
			{
				ImageView dot=(ImageView)findViewById(i);
				dot.setAnimation(b1ball);
				dot.startAnimation(b1ball);
				dot.setOnClickListener(this);
			}
			
			ArrayList<ImageView> csilastbeaconimage = new ArrayList<ImageView>();
			if(maptype==true)
			{
				
				
		    	
				if(last!=null)
				for(int i:csifrontbuttons)
		    	{
					
		    		ImageView dot=(ImageView)findViewById(i);
		    		dot.clearAnimation();
		    		dot.setVisibility(View.INVISIBLE);
		    		if(Integer.parseInt(dot.getTag().toString())==last.getMajor())
		    		{
		    			csilastbeaconimage.add(dot);
		    		//dot.setVisibility(View.INVISIBLE);
		    		}
		    			
		    	}
				if(csilastbeaconimage.size()>0)
				{
					for(ImageView img:csilastbeaconimage)
					{
				img.setVisibility(View.VISIBLE);
				img.setAnimation(b1ball);
					}
				}
			}
			break;
			
case R.layout.layout_davidhall:
			
int davidfrontbuttons[]={R.id.img_david_beak81,R.id.img_david_beak82,R.id.img_david_beak83,R.id.img_david_beak12};
		
	
	
	
	for(int i:davidfrontbuttons)
	{
		ImageView dot=(ImageView)findViewById(i);
		dot.setAnimation(b1ball);
		dot.startAnimation(b1ball);
		dot.setOnClickListener(this);
	}
	
	ArrayList<ImageView> davidlastbeaconimage = new ArrayList<ImageView>();
	if(maptype==true)
	{
		
		
    	
		if(last!=null)
		for(int i:davidfrontbuttons)
    	{
			
    		ImageView dot=(ImageView)findViewById(i);
    		dot.clearAnimation();
    		dot.setVisibility(View.INVISIBLE);
    		if(Integer.parseInt(dot.getTag().toString())==last.getMajor())
    		{
    			davidlastbeaconimage.add(dot);
    		//dot.setVisibility(View.INVISIBLE);
    		}
    			
    	}
		if(davidlastbeaconimage.size()>0)
		{
			for(ImageView img:davidlastbeaconimage)
			{
		img.setVisibility(View.VISIBLE);
		img.setAnimation(b1ball);
			}
		}
	}
			
			
			break;
			
case R.layout.layout_durbarhall:
	
int durbarfrontbuttons[]={R.id.img_durbar_beak95,R.id.img_durbar_beak96,R.id.img_durbar_beak98,R.id.img_durbar_beak97,R.id.img_durbar_beak99,R.id.img_durbar_beak100,R.id.img_durbar_beak94,R.id.img_durbar_beak93,R.id.img_durbar_beak92,R.id.img_durbar_beak91};
		
	
	
	
	for(int i:durbarfrontbuttons)
	{
		ImageView dot=(ImageView)findViewById(i);
		dot.setAnimation(b1ball);
		dot.startAnimation(b1ball);
		dot.setOnClickListener(this);
	}
	
	ArrayList<ImageView> durbarlastbeaconimage = new ArrayList<ImageView>();
	if(maptype==true)
	{
		
		
    	
		if(last!=null)
		for(int i:durbarfrontbuttons)
    	{
			
    		ImageView dot=(ImageView)findViewById(i);
    		dot.clearAnimation();
    		dot.setVisibility(View.INVISIBLE);
    		if(Integer.parseInt(dot.getTag().toString())==last.getMajor())
    		{
    			durbarlastbeaconimage.add(dot);
    		//dot.setVisibility(View.INVISIBLE);
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
		
	
	
	
	for(int i:peperfrontbuttons)
	{
		ImageView dot=(ImageView)findViewById(i);
		dot.setAnimation(b1ball);
		dot.startAnimation(b1ball);
		dot.setOnClickListener(this);
	}
	
	ArrayList<ImageView> peperlastbeaconimage = new ArrayList<ImageView>();
	if(maptype==true)
	{
		
		
    	
		if(last!=null)
		for(int i:peperfrontbuttons)
    	{
			
    		ImageView dot=(ImageView)findViewById(i);
    		dot.clearAnimation();
    		dot.setVisibility(View.INVISIBLE);
    		if(Integer.parseInt(dot.getTag().toString())==last.getMajor())
    		{
    			peperlastbeaconimage.add(dot);
    		//dot.setVisibility(View.INVISIBLE);
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
	}
		
	
	break;
	
case R.layout.layout_vasco:
	
	 bac=(ImageView)findViewById(R.id.img_vaso_beak80);
	
	bac.setOnClickListener(MapManagerInside.this);
	bac.startAnimation(b1ball);
	
	
	break;

		default:
			break;
		}
    	
	}



	private void drawmarkers(int mapid,ArrayList<Beacondetails> beacons,ImageView view) {
		// TODO Auto-generated method stub
		
    	  BitmapFactory.Options myOptions = new BitmapFactory.Options();
          myOptions.inDither = true;
          myOptions.inScaled = false;
          myOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// important
          myOptions.inPurgeable = true;
          myOptions.inSampleSize=4;

         // Bitmap bitmap = BitmapFactory.decodeResource(getResources(),mapid);
          Bitmap bitmap = BitmapFactory.decodeResource(getResources(), mapid, myOptions);
          
          Paint paint = new Paint();
          paint.setAntiAlias(true);
          paint.setColor(Color.BLUE);


          Bitmap workingBitmap = Bitmap.createBitmap(bitmap);
          Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);


          Canvas canvas = new Canvas(mutableBitmap);
          
         /* canvas.drawCircle(1000, 1000, 20, paint);
          canvas.drawCircle(500 , 580, 20, paint);
          canvas.drawCircle(760 , 200, 20, paint);
          canvas.drawCircle(870, 50, 20, paint);
          canvas.drawCircle(200 , 700, 20, paint);*/
          
       /*   
          for(Beacondetails beak:beacons)
          {
          canvas.drawCircle(beak.getX(), beak.getY(), 15, paint);

          }
          */
          
        
         
          
          view.setAdjustViewBounds(true);
          view.setImageBitmap(mutableBitmap);
	}



	@Override
    public boolean onTouch(View v, MotionEvent event) {
        ImageView view =(ImageView)v;
        view.setScaleType(ImageView.ScaleType.MATRIX);
        float scale;
 
        dumpEvent(event);
        // Handle touch events here...
 
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
        case MotionEvent.ACTION_DOWN: // first finger down only
            savedMatrix.set(matrix);
            start.set(event.getX(), event.getY());
            Log.d(TAG, "mode=DRAG"); // write to LogCat
            mode = DRAG;
            break;
 
        case MotionEvent.ACTION_UP: 
        case MotionEvent.ACTION_POINTER_UP: 
 
            mode = NONE;
            Log.d(TAG, "mode=NONE");
            break;
 
        case MotionEvent.ACTION_POINTER_DOWN:
            oldDist = spacing(event);
            Log.d(TAG, "oldDist=" + oldDist);
            if (oldDist > 5f) {
                savedMatrix.set(matrix);
                midPoint(mid, event);
                mode = ZOOM;
                Log.d(TAG, "mode=ZOOM");
            }
            break;
 
        case MotionEvent.ACTION_MOVE:
 
            if (mode == DRAG) {
                matrix.set(savedMatrix);
                matrix.postTranslate(event.getX() - start.x, event.getY()
                        - start.y); /*
                                     * create the transformation in the matrix
                                     * of points
                                     */
            } else if (mode == ZOOM) {
                // pinch zooming
                float newDist = spacing(event);
                Log.d(TAG, "newDist=" + newDist);
                if (newDist > 5f) {
                    matrix.set(savedMatrix);
                    scale = newDist / oldDist;
                    /*
                     * setting the scaling of the matrix...if scale > 1 means
                     * zoom in...if scale < 1 means zoom out
                     */
                    matrix.postScale(scale, scale, mid.x, mid.y);
                }
            }
            break;
        }
 
        view.setImageMatrix(matrix); // display the transformation on screen
 
        return true;
    }
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return FloatMath.sqrt(x * x + y * y);
    }
 
 
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }
 
    private void dumpEvent(MotionEvent event) {
        String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE",
                "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
        StringBuilder sb = new StringBuilder();
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        sb.append("event ACTION_").append(names[actionCode]);
 
        if (actionCode == MotionEvent.ACTION_POINTER_DOWN
                || actionCode == MotionEvent.ACTION_POINTER_UP) {
            sb.append("(pid ").append(
                    action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
            sb.append(")");
        }
 
        sb.append("[");
        for (int i = 0; i < event.getPointerCount(); i++) {
            sb.append("#").append(i);
            sb.append("(pid ").append(event.getPointerId(i));
            sb.append(")=").append((int) event.getX(i));
            sb.append(",").append((int) event.getY(i));
            if (i + 1 < event.getPointerCount())
                sb.append(";");
        }
 
        sb.append("]");
        Log.d("Touch Event", sb.toString());
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
					 FacebookClass fb= new FacebookClass(MapManagerInside.this);
					 fb.publishFeedDialog(details[0], details[1], details[2], details[3]);
				 }
		}
	else
	{
		 imageLoader=new ImageLoader(MapManagerInside.this);
		 
		beak=new Dialog(MapManagerInside.this);
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
	
	String desImgUrl,artNameEng,artDesEng,instEng,instDesEng,desEng,artNameMal,artDescMal,descMal;
	public ImageLoader imageLoader; 
	
	 public void BeacondetailViewclick(View v)
	  {
		 int g=0;
		 String []details =v.getTag().toString().split("#details#");
Toast.makeText(getApplicationContext(), details[1]+g, Toast.LENGTH_SHORT).show();
		 try
		 {
	     
		
			 
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
			 FacebookClass fb= new FacebookClass(MapManagerInside.this);
			 fb.publishFeedDialog(details[0], details[1], details[2], details[3]);
		 }
	 } 
	 
	 public void BeaconVideoclick(View v)
	 {
		/* Intent i=new Intent(this, BeaconVideoViewActivity.class);
		 i.putExtra("url", v.getTag().toString());
		 Log.i("video url",  v.getTag().toString());
		 startActivity(i);*/
		 
		/* Intent intent=new Intent(this, Videoview.class);
		 intent.putExtra("url", v.getTag().toString());
		 startActivity(intent);*/
		 Intent i = new Intent(Intent.ACTION_VIEW);
		 
		// Toast.makeText(getApplicationContext(), v.getTag().toString(), Toast.LENGTH_SHORT).show();
	        i.setData(Uri.parse(v.getTag().toString()));
	        MapManagerInside.this.startActivity(i);
		 //Toast.makeText(getApplicationContext(), "video", Toast.LENGTH_SHORT).show();
	 }
}

