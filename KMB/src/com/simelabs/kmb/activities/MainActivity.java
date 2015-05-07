package com.simelabs.kmb.activities;


import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.Session;
import com.facebook.UiLifecycleHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.simelabs.kmb.launch.R;
import com.simelabs.kmb.locationmap.DirectionInGoogleMap;
import com.simelabs.kmb.locationmap.ImageDisplayActivity;
import com.simelabs.kmb.locationmap.LocationDetailView;
import com.simelabs.kmb.locationmap.MapManagerInside;
import com.simelabs.kmb.network.Internet;
import com.simelabs.kmb.notification.AppNotification;
import com.simelabs.kmb.service.PublicValues;
import com.simelabs.kmb.socialmedia.FActivity;
import com.simelabs.kmb.socialmedia.FacebookClass;
import com.simelabs.kmb.spotbeak.BeaconDetailViewActivity;
import com.simelabs.kmb.spotbeak.BeaconVideoViewActivity;
import com.simelabs.kmb.spotbeak.MyService;
import com.simelabs.kmb.spotbeak.Videoview;
import com.simelabs.kmb.spotbeak.Youtube;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements View.OnClickListener  
/**
 * 
 * @author Jasmine & Jovita
 * 
 *       
 */
{
	String desImgUrl,artNameEng,artDesEng,instEng,instDesEng,desEng,artNameMal,artDescMal,descMal;
	 public static String oldbeacons;
        WebView wv; 
	    String fileAddress;

	    public static final String EXTRA_MESSAGE = "message";
	    public static final String PROPERTY_REG_ID = "registration_id";
	    private static final String PROPERTY_APP_VERSION = "appVersion";
	    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	    String SENDER_ID = "294189318330";
	    GoogleCloudMessaging gcm;
	    AtomicInteger msgId = new AtomicInteger();
	    String regid;
	    static final String TAG = "GCMDemo";
	    
	    
	    final Activity activity = this;
	    public static Context context;
	    private static ResideMenu resideMenu;
	    private MainActivity mContext;
	    private ResideMenuItem itemHome;
	    private ResideMenuItem itemWhatsGoingOnToday;
	    private ResideMenuItem itemSpotBeak;
	    private ResideMenuItem itemEvents;
	    private ResideMenuItem itemNewsVideosAndUpdates;
	    private ResideMenuItem itemArtistsAndInstallations;
	    private ResideMenuItem itemGallery;
	    private ResideMenuItem itemLocationMap;
	    private ResideMenuItem itemAboutUs;
	    ImageView logo;
	    TextView title;
	    ImageButton loginbtn;
	    ImageButton myButton;
	    String mesg,loc;
	    String myAccessTokenValue,myName,myLocation,myImage ;
	    public static String facebook_profilename;
	    public static String saved_access_token;
	    public static Bitmap facebookimage;
        String resourceFolder;
        public static final String PREFS_NAME = "MyFacebookPrefsFile";
        String[] notification={"Welcome to pisa master italian",
	    		"Welcome to Earth model","Bye from Pisa master italian","Bye from Earth model"};
	    String key = "key";
	    String name,imagePath;
	    AppNotification notify;
	    String notifmessage;
	    String eventName,eventLocation,strDate;
	    String HTMLpath;
	    String zipname;
	    Internet checkNet;
	    boolean netstatus;
	    String accesstoken,myAccessToken;
	    private Toast toast;
		private long lastBackPressTime = 0;
		
	  @Override
      protected void onStart() 
	{
	
		    super.onStart();
	       
	      
	     
	      /* hash key
	       *  PackageInfo info;
	        try {
	            info = getPackageManager().getPackageInfo("com.simelabs.kmb", PackageManager.GET_SIGNATURES);
	            for (Signature signature : info.signatures) {
	                MessageDigest md;
	                md = MessageDigest.getInstance("SHA");
	                md.update(signature.toByteArray());
	                String something = new String(Base64.encode(md.digest(), 0));
	                //String something = new String(Base64.encodeBytes(md.digest()));
	                Log.e("hash key", something);
	            }
	        } catch (NameNotFoundException e1) {
	            Log.e("name not found", e1.toString());
	        } catch (NoSuchAlgorithmException e) {
	            Log.e("no such an algorithm", e.toString());
	        } catch (Exception e) {
	            Log.e("exception", e.toString());
	        }*/
	       
	       /*   boolean s=isMyServiceRunning(MyService.class);
	        if(s==false)
	        {*/
	        //startService(new Intent(this, MyService.class));
	      
	       
	}
	         
	  
 	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		 ParseAnalytics.trackAppOpenedInBackground(getIntent());
		 
		setContentView(R.layout.activity_main);
	
		
        title = (TextView)findViewById(R.id.title);
        
        context =getApplicationContext();
		mContext = this;
        
		resideMenu = new ResideMenu(this);
		
		setUpMenu();
        changeFragment(new Fragment());
		
        resourceFolder= PreferenceManager.getDefaultSharedPreferences(context).getString("UnzipFilePath",null);
        zipname = resourceFolder;
        
       // Toast.makeText(mContext, resourceFolder, Toast.LENGTH_LONG).show();
        //Toast.makeText(mContext, resourceFolder, Toast.LENGTH_LONG).show();
     //   Log.i("Unzipped path.....", resourceFolder+"");
        HTMLpath =  "file://"+Environment.getExternalStorageDirectory()+"/.biennale/unzipped";
        
         wv = (WebView) findViewById(R.id.webView1); 
         wv.getSettings().setPluginState(PluginState.ON);
        wv.getSettings().setPluginState(PluginState.ON);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setAllowFileAccess(true);
        wv.getSettings().setAllowFileAccessFromFileURLs(true);
        wv.getSettings().setAllowUniversalAccessFromFileURLs(true);
        wv.setWebChromeClient(new WebChromeClient());
        
        wv.setWebChromeClient(new WebChromeClient());
        wv.getSettings().setPluginState(WebSettings.PluginState.ON);
        wv.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);
        //wv.setWebViewClient(new WebViewClient());
        wv.getSettings().setJavaScriptEnabled(true);
        
         
        
        /**
         * Retrieving facebook data
         */
       /* myAccessToken = PreferenceManager.getDefaultSharedPreferences(context).getString("Access token",null);
        accesstoken = myAccessToken;
        String myName = PreferenceManager.getDefaultSharedPreferences(context).getString("Name",null);
        name = myName;
        String myImagePath = PreferenceManager.getDefaultSharedPreferences(context).getString("Profile picture path",null);
        imagePath = myImagePath;;
        //Toast.makeText(getApplicationContext(), "Image path in shared preference is: " +imagePath, Toast.LENGTH_LONG).show();
        Bitmap bmp = BitmapFactory.decodeFile(imagePath);
        
        if (accesstoken != null) 
        {
        	resideMenu.getName(name);
        	resideMenu.getPic(bmp);
            resideMenu.loginButtonVis();
        	
        }*/
        
        uiHelper = new UiLifecycleHelper(this,null);
        uiHelper.onCreate(savedInstanceState);
        
        myAccessToken = PreferenceManager.getDefaultSharedPreferences(context).getString("Access token",null);
        accesstoken = myAccessToken;
        if (accesstoken != null) 
        {
        	Internet net = new Internet(this);
            boolean netstatus = net.isAvailable();
            if (netstatus != false)
            {
               FacebookClass fb= new FacebookClass(mContext);
                fb.login();
                }


        	
        }
        
        
        
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            Log.d("Response", "No SDCARD");
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ViewGroup vg = (ViewGroup) inflater.inflate(R.layout.webpagesneterror, null);
            setContentView(vg);
        } 
        else 
        {
            wv.loadUrl(HTMLpath+"/"+zipname+"/"+"home.html");
        }
     
        wv.setWebViewClient(new WebViewClient() {

    		@Override

    		public boolean shouldOverrideUrlLoading (WebView view, String url)

    		{
    			if(url.startsWith("unsafe:a:"))
                {
    				
    				String parsedjson = url.substring(2);
                    String[] seperatedjson = parsedjson.split(":a:");
    				String start = seperatedjson[0];
    				String eventdata = seperatedjson[1];
    				
    				JSONObject jObject;
					try 
					{
						jObject = new JSONObject(eventdata);
						String placename = jObject.getString("name");
						String evDate = jObject.getString("date");
						String[] nameSep = placename.split(", ");
						eventName = nameSep[0];
						eventLocation = nameSep[1];
						strDate = evDate;
						
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
            try {
				addCalendarEvent();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return true;
            }

    			else
    			{
    				return false;
    				//view.loadUrl(url);
    			}
			
		
	}
});
        
               
        notify = new AppNotification(mContext);
       //notify.generateNotification(activity, "Message 1", key);		
        getDeviceData();
        
       /* if (checkPlayServices()) {
        	 gcm = GoogleCloudMessaging.getInstance(this);
             regid = getRegistrationId(context);

             if (regid.isEmpty()) {
                 registerInBackground();
             }
         } else {
             Log.i(TAG, "No valid Google Play Services APK found.");
         }*/
        
       
        
	}
  
	

	private void setUpMenu() {

        // attach to current activity;
        
        resideMenu.setBackground(R.drawable.menu_bg);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip. 
        resideMenu.setScaleValue(0.6f);
  
     
        // create menu items;
        itemHome     = new ResideMenuItem(this, R.drawable.home_n,"Biennale at a glance ");
        itemWhatsGoingOnToday  = new ResideMenuItem(this, R.drawable.today_n,"What's on today?");
        itemSpotBeak = new ResideMenuItem(this, R.drawable.walk_n,"Biennale Walkthrough®");
        itemEvents = new ResideMenuItem(this, R.drawable.events_n,"Programmes");
        itemNewsVideosAndUpdates  = new ResideMenuItem(this, R.drawable.new_n,"News & Updates");
        itemArtistsAndInstallations = new ResideMenuItem(this, R.drawable.art,"Artists");
        itemGallery = new ResideMenuItem(this, R.drawable.gallery_n,"Gallery");
        itemLocationMap = new ResideMenuItem(this, R.drawable.location_n,"Location Map");
        itemAboutUs = new ResideMenuItem(this, R.drawable.abou_n,"About Us");
        
        resideMenu.setLogo();
        resideMenu.setSponserLogo();
        loginbtn= (ImageButton) resideMenu.findViewById(R.id.btnLogin);
        
        itemHome.setOnClickListener(this);
        itemWhatsGoingOnToday.setOnClickListener(this);
        itemSpotBeak.setOnClickListener(this);
        itemEvents.setOnClickListener(this);
        itemNewsVideosAndUpdates.setOnClickListener(this);
        itemArtistsAndInstallations.setOnClickListener(this);
        itemGallery.setOnClickListener(this);
        itemLocationMap.setOnClickListener(this);
        itemAboutUs.setOnClickListener(this);



        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        addSeparatorAfter(itemHome);
        resideMenu.addMenuItem(itemArtistsAndInstallations, ResideMenu.DIRECTION_LEFT);
        addSeparatorAfter(itemArtistsAndInstallations);
        resideMenu.addMenuItem(itemSpotBeak, ResideMenu.DIRECTION_LEFT);
        addSeparatorAfter(itemSpotBeak);
        resideMenu.addMenuItem(itemWhatsGoingOnToday, ResideMenu.DIRECTION_LEFT);
        addSeparatorAfter(itemWhatsGoingOnToday);
        resideMenu.addMenuItem(itemEvents, ResideMenu.DIRECTION_LEFT);
        addSeparatorAfter(itemEvents);
        resideMenu.addMenuItem(itemNewsVideosAndUpdates, ResideMenu.DIRECTION_LEFT);
        addSeparatorAfter(itemNewsVideosAndUpdates);
        resideMenu.addMenuItem(itemLocationMap, ResideMenu.DIRECTION_LEFT);
        addSeparatorAfter(itemLocationMap);
        resideMenu.addMenuItem(itemGallery, ResideMenu.DIRECTION_LEFT);
        addSeparatorAfter(itemGallery);
        resideMenu.addMenuItem(itemAboutUs, ResideMenu.DIRECTION_LEFT);
        addSeparatorAfter(itemAboutUs);
        
        // You can disable a direction by setting ->
        // resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
        findViewById(R.id.title_bar_right_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
            }
        });
    }

	private void addSeparatorAfter(ResideMenuItem menuItem)
    {
		LinearLayout parent = (LinearLayout) menuItem.getParent();
        View separator = new View(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dpToPx(1));
        params.setMargins(dpToPx(0), dpToPx(0), dpToPx(0), dpToPx(0));
        separator.setLayoutParams(params);
        separator.setBackgroundColor(0xff888888);
        parent.addView(separator);
    }
	
    
    
     public int dpToPx(int dp) 
      {
        
    	 DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));       
        return px;
        
    }
    
	@Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View view) {
    	wv.loadUrl("");
    	//System.gc();

        if (view == itemHome){
            changeFragment(new Fragment());
            title.setText("Biennale at a Glance");
            wv.loadUrl(HTMLpath+"/"+zipname+"/"+"home.html");
        }
        else if (view == itemWhatsGoingOnToday){
            changeFragment(new Fragment());
            title.setText("What's On Today?");
            wv.loadUrl(HTMLpath+"/"+zipname+"/"+"whatsontoday.html");
            }
        
        else if (view == itemSpotBeak){
        	
        	/*Intent intent = new Intent(MainActivity.this, ListBeaconsActivity.class);
 	        intent.putExtra(ListBeaconsActivity.EXTRAS_TARGET_ACTIVITY, DistanceBeaconActivity.class.getName());
 	        startActivity(intent);*/
 	       // changeFragment(new SpotBeak());
        	 changeFragment(new SpotBeak());
            title.setText("Biennale Walkthrough®");
        }
        else if (view == itemEvents){
            changeFragment(new Fragment());
            title.setText("Programmes");
            wv.loadUrl(HTMLpath+"/"+zipname+"/"+"events.html");
            }
        else if (view == itemNewsVideosAndUpdates){
            changeFragment(new Fragment());
            title.setText("News & Updates");
            wv.loadUrl(HTMLpath+"/"+zipname+"/"+"newsnupdates.html");
        }
        else if (view == itemArtistsAndInstallations){
            changeFragment(new Artists());
            title.setText("Artists");
            //wv.loadUrl(HTMLpath+"/"+zipname+"/"+"artistsninstallations.html");
            }
        else if (view == itemGallery){
            changeFragment(new Gallery());
            title.setText("Gallery");
        }
        else if (view == itemLocationMap){
            changeFragment(new LocationMap());
            title.setText("Location Map");
        }
        else if (view == itemAboutUs){
        	 changeFragment(new Fragment());
            title.setText("About Us");
            wv.loadUrl(HTMLpath+"/"+zipname+"/"+"aboutus.html");
        }
        resideMenu.closeMenu();
    }

    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
           // Toast.makeText(mContext, "Menu is opened!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void closeMenu() {
            //Toast.makeText(mContext, "Menu is closed!", Toast.LENGTH_SHORT).show();
        }
    };

    private void changeFragment(Fragment targetFragment){
    	
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    // What good method is to access resideMenuÃ¯Â¼Å¸
    public ResideMenu getResideMenu(){
        return resideMenu;
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
		
		//Log.i("outside map cliked at", venueno+"");
		DirectionInGoogleMap map=new DirectionInGoogleMap(venueno,this);
		map.getdirection();
		}
		else
			Toast.makeText(getApplicationContext(), "Check Network Connection!!", Toast.LENGTH_SHORT).show();
		
	}
	LocationMap map=new LocationMap();


	/**
	 * Add notification to sidemenu
	 * 
	 */
	public void addNotificationToRightMenu(String message)
	{
		resideMenu.addNotifications(message);
	}
	
	
	 /**
	  * 
	  * Setting profile picture
	 * @param result 
	  * 
	  */
	 
	 public static void dispatchPicInformations(Bitmap result)
		{
	    resideMenu.getPic(result);
	    resideMenu.loginButtonVis();
		}
		
	 /**
	  * 
	  * Setting user name
	 * @param dispname 
	  */
	 public static void dispatchInformations(String dispname) {
			
		 resideMenu.getName(dispname);
		 resideMenu.loginButtonVis();
	 		
	 }
		
		public void onBtnClicked(View v){
		    if(v.getId() == R.id.btnLogin)
		    {
		    	
		    	Internet net = new Internet(this);
				boolean netstatus = net.isAvailable();
				
				if (netstatus != false) {
			
					 FacebookClass fb= new FacebookClass(mContext);
		                fb.login();
					
				}
				else
					Toast.makeText(getApplicationContext(), "Please check your network connection!!!", Toast.LENGTH_SHORT).show();
				
		    }
		    
		}
		
		 public void BeacondetailViewclick(View v)
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
			  //Toast.makeText(getApplicationContext(), "detaiil clicked", Toast.LENGTH_SHORT).show();
		  }
		 
		 public void Beaconshareclick(View v)
		 {
			 //Toast.mak eText(getApplicationContext(), "Please login to Facebook!!!", Toast.LENGTH_LONG).show();
				
			 String []details=v.getTag().toString().split("#share#");
			 myAccessToken = PreferenceManager.getDefaultSharedPreferences(context).getString("Access token",null);
		     accesstoken = myAccessToken;
		        
			 if (accesstoken== null) 
		     {
				 Toast.makeText(getApplicationContext(), "Please login to Facebook!!!", Toast.LENGTH_LONG).show();
			 }
			 else
			 {
				 FacebookClass fb= new FacebookClass(mContext);
				 fb.publishFeedDialog(details[0], details[1], details[2], details[3]);
			 }
		 } 
		 
		 public void BeaconVideoclick(View v)
		 {
			/* Intent i=new Intent(this, BeaconVideoViewActivity.class);
			 i.putExtra("url", v.getTag().toString());
			 Log.i("video url",  v.getTag().toString());
			 startActivity(i);*/
			 
			 Intent intent=new Intent(this, Videoview.class);
			 intent.putExtra("url", v.getTag().toString());
			 startActivity(intent);
			/* Intent i = new Intent(Intent.ACTION_VIEW);
			 
		        i.setData(Uri.parse(v.getTag().toString()));
		        MainActivity.this.startActivity(i);*/
		 }
		 
		 private void getDeviceData() {
				// TODO Auto-generated method stub
				PublicValues.os=Build.VERSION.RELEASE;
				PublicValues.brand=android.os.Build.BRAND;
				PublicValues.device=android.os.Build.DEVICE;
				PublicValues.display=android.os.Build.DISPLAY;
				PublicValues.model=android.os.Build.MODEL;
				//String type=android.os.Build.TYPE;
				//String m=System.getProperty(android.os.Build.BRAND);
				
				
				String serviceName = Context.TELEPHONY_SERVICE;
				TelephonyManager m_telephonyManager = (TelephonyManager) getSystemService(serviceName);
				PublicValues.number=m_telephonyManager.getLine1Number();
				PublicValues.operator=m_telephonyManager.getSimOperatorName();
				//String tel=m_telephonyManager.getLine1Number();
				//String myDeviceModel = android.os.Build.MODEL;
				
				
			/*	
				Log.e("phone details", "os: "+PublicValues.os+"\n"+
						"brand: "+PublicValues.brand+"\n"+
						"device: "+PublicValues.device+"\n"+
						"display: "+PublicValues.display+"\n"+
						"model: "+PublicValues.model+"\n"+
						"number: "+PublicValues.number+"\n"+
						"operatorname: "+PublicValues.operator);*/
				
				
			}
		 
		public void displayvenuedetails(View v)
		 {
			Intent loc=new Intent(getApplicationContext(), LocationDetailView.class);
			loc.putExtra("position", Integer.parseInt(v.getTag().toString()));
			startActivity(loc);
			 
		 }
		
		public void addCalendarEvent() throws ParseException
		{
			
			Calendar cal = Calendar.getInstance();
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss' 'Z");
		    cal.setTime(sdf.parse(strDate));// all done
		    
			Intent intent = new Intent(Intent.ACTION_INSERT)
			    .setData(Events.CONTENT_URI)
			    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.getTimeInMillis())
			    .putExtra(Events.TITLE, eventName)
			    .putExtra(Events.EVENT_LOCATION, eventLocation);
			startActivity(intent);
			
		    
		
		  }
/*
		private boolean isMyServiceRunning(Class<?> serviceClass) {
		    ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
		        if (serviceClass.getName().equals(service.service.getClassName())) {
		            return true;
		        }
		    }
		    return false;
		}*/
		
		@Override
		public void onBackPressed()
		{
			if (this.lastBackPressTime < System.currentTimeMillis() - 4000) {
				System.gc();
		    toast = Toast.makeText(this, "Press again to Exit", 5000);
		    toast.show();
		    this.lastBackPressTime = System.currentTimeMillis();
		  } else {
		    if (toast != null) {
		    toast.cancel();
		  }
		  super.onBackPressed();
		 }
		}
		@Override
		protected void onResume() {
		   super.onResume();
		   this.wv.onResume();
		}

		@Override
		protected void onPause() {
		   super.onPause();
		   this.wv.onPause();
		}
		private UiLifecycleHelper uiHelper;
		
		@Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
                
                uiHelper.onActivityResult(requestCode, resultCode, data);
                Log.i("On activity Result...", "OnActivityResult...");
        }
		
	
	
		
		
}
