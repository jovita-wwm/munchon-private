package com.simelabs.kmb.locationmap;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.google.android.gms.internal.bg;
import com.simelabs.kmb.launch.R;
import com.simelabs.kmb.activities.LocationMap;
import com.simelabs.kmb.gallery.GalleryFullImageDialog;

import android.app.Activity;
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
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * @author JOVITA P J
 *
 *
 */
public class LocationMapAdapter extends BaseAdapter   implements OnClickListener {

	 private Activity activity;
	    private ArrayList data,venuebg;
	    LayoutInflater inflater ;
	    
	public LocationMapAdapter(Activity a,ArrayList<String> d,ArrayList<Drawable> vb) {
		// TODO Auto-generated constructor stub
		
		 activity = a;
	        data=d;
	        venuebg=vb;
	        
	        /***********  Layout inflator to call external xml layout () **********************/
	        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	 /******** What is the size of Passed Arraylist Size ************/
    public int getCount() {
    	
    	if(data.size()<=0)
    		return 1;
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    /********* Create a holder to contain inflated xml file elements ***********/
    public static class ViewHolder{
    	
        public TextView venuetitle;
       RelativeLayout vb;
       ImageView inside,outside;

    }

    /*********** Depends upon data size called for each row , Create each ListView row ***********/
    public View getView(int position, View convertView, ViewGroup parent) {
    	
        View vi=convertView;
        ViewHolder holder;
        
        if(convertView==null){ 
        	
        	/********** Inflate tabitem.xml file for each row ( Defined below ) ************/
            vi = inflater.inflate(R.layout.location_map_list_item, null); 
            
            /******** View Holder Object to contain tabitem.xml file elements ************/
            holder=new ViewHolder();
            holder.venuetitle=(TextView)vi.findViewById(R.id.title);
           holder.vb=(RelativeLayout)vi.findViewById(R.id.bgpic);
            holder.inside=(ImageView)vi.findViewById(R.id.InsideNavigation);
            holder.outside=(ImageView)vi.findViewById(R.id.OusideNavigation);
            
            
            
           /************  Set holder with LayoutInflater ************/
            vi.setTag(holder);
        }
        else  
            holder=(ViewHolder)vi.getTag();
        
        if(data.size()<=0)
        {
        	holder.venuetitle.setText("No Data");
            
        }
        else
        {
       
	         holder.venuetitle.setText(data.get(position).toString());
	         Drawable id=(Drawable) venuebg.get(position);
	         holder.vb.setBackground(id);
	         holder.vb.setTag(position);
	        
	         /******** Set Item Click Listener for LayoutInflater for each row ***********/
	         vi.setOnClickListener(new OnItemClickListener(position));
	         
	         holder.inside.setTag(position);
	         holder.outside.setTag(position);
	         
        }
        return vi;
    }
    
    @Override
    public void onClick(View v) {
            Log.v("Notification list adapter", "=====Row button clicked");
    }
    
    /********* Called when Item click in ListView ************/
    private class OnItemClickListener  implements OnClickListener{           
        private int mPosition;
        
        OnItemClickListener(int position){
        	 mPosition = position;
        }
        
        @Override
        public void onClick(View arg0) {
        	
        	
    //     LocationMap sct = (LocationMap)activity;
    	//sct.onItemClick(mPosition);
        	
        	
        	
        }               
    }  
    
   
    
}
