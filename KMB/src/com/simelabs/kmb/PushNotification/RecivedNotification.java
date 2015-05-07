package com.simelabs.kmb.PushNotification;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import com.simelabs.kmb.activities.Splash;
import com.simelabs.kmb.launch.R;
import com.simelabs.kmb.locationmap.LocationMapAdapter;
import com.simelabs.kmb.network.Internet;
import com.simelabs.kmb.service.PublicValues;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RecivedNotification extends Activity{
	JSONObject json = null ;
	String message = null,imgmessage = null,date = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_push);
		Bundle extras=getIntent().getExtras();
		TextView text=(TextView)findViewById(R.id.message_txt);
		//ImageView background=(ImageView)findViewById(R.id.background);
		WebView web=(WebView)findViewById(R.id.web);
		
		String s=extras.getString("alert");
		
		try {
			 json = new JSONObject( extras.getString("com.parse.Data") );
			 message=json.getString("message");
		
			 imgmessage=json.getString("img");
			 date=json.getString("time");
			// Toast.makeText(getApplicationContext(), ""+date, Toast.LENGTH_SHORT).show();
			 
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		web.setVisibility(View.VISIBLE);
		
		//text.setText(""+extras+"\n"+message);
		//String data[]=message.split("@@@message@@@");
		
		text.setText(Html.fromHtml(message));
		
		Internet net=new Internet(getApplicationContext());
		boolean status=net.isAvailable();
		if(imgmessage!=null && status==true)
		{
		web.loadUrl(imgmessage);
		}
		else
		{
			web.setVisibility(View.GONE);
		}
		/*if(date==null)
		{
			ImageView calimg=(ImageView)findViewById(R.id.calender);
			calimg.setVisibility(View.INVISIBLE);
		}*/
	
		
		web.setScrollContainer(false);
		web.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		
			
	
		
	}
	
	public void close(View v)
	{
		this.finish();
	}
	public void app(View v)
	{
		Intent i=new Intent(this, Splash.class);
		startActivity(i);
	}
	public void calender(View v)
	{
		if(date!=null)
		{
			//Toast.makeText(getApplicationContext(), date, Toast.LENGTH_SHORT).show();
			try {
				addCalendarEvent(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void addCalendarEvent(String date ) throws ParseException
	{
		
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss' 'Z");
	    cal.setTime(sdf.parse(date));// all done
	    
		Intent intent = new Intent(Intent.ACTION_INSERT)
		    .setData(Events.CONTENT_URI)
		    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.getTimeInMillis())
		    .putExtra(Events.TITLE, message);
		startActivity(intent);
		
	    
	
	  }
}
