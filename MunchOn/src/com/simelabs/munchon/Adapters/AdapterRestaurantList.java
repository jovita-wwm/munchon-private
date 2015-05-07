

package com.simelabs.munchon.Adapters;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.simelabs.munchon.R;
import com.simelabs.munchon.DB.Restaurants;
import com.simelabs.munchon.Domain.RestaurantDomain;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources.Theme;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AdapterRestaurantList extends BaseAdapter

{
	private final Activity context;
	//ArrayList<ArrayList<String>> names;
	ArrayList<RestaurantDomain> restaurants=new ArrayList<RestaurantDomain>();
	Integer count;
	ArrayList la;
	ArrayList bd;
	public ImageLoader imageLoader; 

	
	public AdapterRestaurantList(Activity context,ArrayList<RestaurantDomain> res) 
	{
	this.context = context;
	this.restaurants = res;
	 imageLoader=new ImageLoader(context.getApplicationContext());
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return restaurants.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView= inflater.inflate(R.layout.fragment_restaurantlist_listitem, null, true);
		
		 
		TextView txtTitle = (TextView) rowView.findViewById(R.id.txt_fragment_restaurant_address);
		 ImageView img=(ImageView)rowView.findViewById(R.id.img_fragment_restaurents_restaurantimage);
		 ImageView logo=(ImageView)rowView.findViewById(R.id.img_restaurantLogo);
		TextView status=(TextView)rowView.findViewById(R.id.txt_fragment_restaurant_status);
		TextView rate=(TextView)rowView.findViewById(R.id.txt_fragment_restaurant_rate);
		  
/*		BitmapFactory.Options options=new BitmapFactory.Options();
		options.inSampleSize = 2;
		Bitmap bitmap = BitmapFactory.decodeResource(context.getApplicationContext().getResources(), R.drawable.restaurent1,options);
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = 30;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

      img.setImageBitmap(output);*/
      String imageurl="https://s3-us-west-2.amazonaws.com/elasticbeanstalk-us-west-2-060425629624/munchon/gallery/restaurant/";
      String logourl="https://s3-us-west-2.amazonaws.com/elasticbeanstalk-us-west-2-060425629624/munchon/gallery/restaurantlogo/";
      
     /* new DownloadImageTask(img)
      .execute(imageurl+restaurants.get(position).getRestaurantImage());
    */
     imageLoader.GalleryDisplayImage(img,imageurl+restaurants.get(position).getRestaurantImage());
      
     imageLoader.GalleryDisplayImage(logo, logourl+restaurants.get(position).getRestaurantlogo());
      
    	txtTitle.setText(Html.fromHtml("<p><b>"+restaurants.get(position).getName()+"</b></p>"+restaurants.get(position).getShortAddress()));
		Typeface font = Typeface.createFromAsset(context.getAssets(), "LaoUIb.ttf");
		
		Calendar c = Calendar.getInstance(); 
		String time=c.get(Calendar.HOUR)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND);
		SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss"); 
		Date currenttime = null,activefrom,activetill;
		try {
			 currenttime = dateFormat.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		activefrom=restaurants.get(position).getActivefrom();
		activetill=restaurants.get(position).getActivetill();
		//checking  restaurant open or closed
		if(activefrom.before(currenttime)  )
		{
			if(activetill.after(currenttime) || activefrom.after(activetill))
			{
			status.setTypeface(font, Typeface.BOLD);
			status.setText(Html.fromHtml("<font color=#36CE14>"+"OPEN"+"</font>"));
			}
		}
		else{
			status.setTypeface(font, Typeface.BOLD);
			status.setText(Html.fromHtml("<font color=#FF0000>"+"CLOSED"+"</font>"));
		}
			
		
		int r=restaurants.get(position).getRate();
		if(r<3)
		{
			if(r<2)
			{
				rate.setText(Html.fromHtml("<font color=#000000><b>$ $ </b></font>$ $"));
			}
			else
				rate.setText(Html.fromHtml("<font color=#000000><b>$ </b></font>$ $ $"));
		}
		else
		{
			if(r<4)
			{
				rate.setText(Html.fromHtml("<font color=#000000><b>$ $ $ </b></font>$"));
			}
			else
				rate.setText(Html.fromHtml("<font color=#000000><b>$ $ $ $</b></font>"));
		}
		
		return rowView;
	}
	   
	   

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		  ImageView bmImage;

		  public DownloadImageTask(ImageView bmImage) {
		      this.bmImage = bmImage;
		  }

		  protected Bitmap doInBackground(String... urls) {
		      String urldisplay = urls[0];
		      Bitmap mIcon11 = null;
		      try {
		        InputStream in = new java.net.URL(urldisplay).openStream();
		        mIcon11 = BitmapFactory.decodeStream(in);
		      } catch (Exception e) {
		          Log.e("Error", e.getMessage());
		          e.printStackTrace();
		      }
		      return mIcon11;
		  }

		  //
		  
		  protected void onPostExecute(Bitmap result) {
		      bmImage.setImageBitmap(result);
		  }
		}

	
	
	}