package com.simelabs.kmb.activities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.simelabs.kmb.domain.GalleryDomain;
import com.simelabs.kmb.gallery.GalleryFullImageDialog;
import com.simelabs.kmb.gallery.UploadActivity;
import com.simelabs.kmb.launch.R;
import com.simelabs.kmb.managers.GalleryDialog;
import com.simelabs.kmb.managers.JsonManager;
import com.simelabs.kmb.managers.JsonReadFeedback;
import com.simelabs.kmb.managers.LazyAdapter;
import com.simelabs.kmb.network.DownloadFiles;
import com.simelabs.kmb.network.Internet;
import com.simelabs.kmb.service.PublicValues;

public class Gallery extends Fragment implements JsonReadFeedback
{
	  ListView list;
	    GridView grid1,grid2,grid3;
	    LazyAdapter adapter;
	    boolean netstatus;
	    Context context;
	  ArrayList<String> ImgUrlss=new ArrayList<String>();
	  ArrayList<String> FullImgUrls=new ArrayList<String>();
	  ArrayList<String> fullgrid1=new ArrayList<String>();
	  ArrayList<String> imagedetails1=new ArrayList<String>();
	  ArrayList<String> fullgrid2=new ArrayList<String>();
	  ArrayList<String> imagedetails2=new ArrayList<String>();
	  ArrayList<String> fullgrid3=new ArrayList<String>();
	  ArrayList<String> imagedetails3=new ArrayList<String>();
	  ArrayList<GalleryDomain> galleryfulldeails=new ArrayList<GalleryDomain>();
	    JsonManager Jmanager;
	    TextView heading1,heading2,heading3;
	    
	 private View mContentView;
	     
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	    {
	    	context=getActivity().getApplicationContext();
	    	Internet net = new Internet(getActivity());
			boolean netstatus = net.isAvailable();
			if (netstatus != false) {
				 mContentView = inflater.inflate(R.layout.gallerymain, container, false);
			}
			else
				 mContentView = inflater.inflate(R.layout.networkerrormsg, container, false);
	       

			DownloadFiles.setcallback(this);
	        return mContentView;
	    }
	    

	    @Override
	    public void onActivityCreated(Bundle savedInstanceState) {
	       

	    	 super.onActivityCreated(savedInstanceState);

	    	refreshtoload();
	       
	    }
	    
	    public void refreshtoload()
	    {
	    	 Internet net = new Internet(getActivity());
	    	 if(net.isAvailable()== true)
	    	 {
	        View v = getView();

	         Jmanager=new JsonManager(getActivity().getApplicationContext());
	   
	      ImgUrlss=Jmanager.getfromGallery(getActivity());
	      
	  
	      galleryfulldeails=PublicValues.gallerydomain;
	      FullImgUrls=PublicValues.Imagefullviewurls;
	     
	        grid1=(GridView)v.findViewById(R.id.grid1);
	        grid2=(GridView)v.findViewById(R.id.grid2);
	        grid3=(GridView)v.findViewById(R.id.grid3);
	        heading1=(TextView)v.findViewById(R.id.txt_gridname1);
	        heading2=(TextView)v.findViewById(R.id.txt_gridname2);
	        heading3=(TextView)v.findViewById(R.id.txt_gridname3);
	        
	        
	        if(galleryfulldeails.size()>0)
	        {
	        
	        ArrayList<String> categorydetails=PublicValues.gallerycategorydetails;
	       
	        for(String category:categorydetails)
	        {
	        	// ArrayList<String> categoryurls=new ArrayList<String>();
	        	ArrayList<GalleryDomain> selectedgalleryitems=new ArrayList<GalleryDomain>();
	        	
	        	 ArrayList<String> categoryfullurls=new ArrayList<String>();
	        	 ArrayList<String> imagedetails=new ArrayList<String>();
	        	String details[]=category.split("##categoryid##");
	        	int catid=Integer.parseInt(details[1]);
	        	
	        /*	for(String img:ImgUrls)*/
	        	for(int j=0;j<galleryfulldeails.size();j++)
	        	{
	        		String img=galleryfulldeails.get(j).getImagethumurl();
	        		//String image[]=img.split("##category##");
	        		//int imagecatid=Integer.parseInt(image[1]);
	        		int imagecatid=galleryfulldeails.get(j).getCatid();
	        		if(imagecatid==catid)
	        		{
	        			selectedgalleryitems.add(galleryfulldeails.get(j));
	        			categoryfullurls.add(galleryfulldeails.get(j).getImageurl());
	        			imagedetails.add(galleryfulldeails.get(j).getName()+"@@@galllery@@@"+galleryfulldeails.get(j).getDescription());
	        		}
	        	}
	        	
	        	if(selectedgalleryitems.size()>0)
	        	{
	        	if(catid==1)
	        	{
	        		heading1.setVisibility(View.VISIBLE);
	        		heading1.setText(details[0]);
	        		displayimages(selectedgalleryitems,grid1);
	        		fullgrid1=categoryfullurls;
	        		imagedetails1=imagedetails;
	        		
	        		  grid1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	  		            @Override
	  		            public void onItemClick(AdapterView<?> parent, View view,
	  		                                    int position, long id) {
	  		               // Toast.makeText(getActivity(), "You Clicked at " +position, Toast.LENGTH_SHORT).show();
	  		            	String details=imagedetails1.get(position);
	  		            	String s=fullgrid1.get(position);
	  		            	 new DownloadImageTask(details)
	  		            	 .execute(s);
	  		            	 
	  		             
	  		                
	  		            }
	  		        });
	        		
	        	}
	        	else if(catid==2)
	        	{
	        		heading2.setVisibility(View.VISIBLE);
	        		heading2.setText(details[0]);
	        		displayimages(selectedgalleryitems,grid2);
	        		fullgrid2=categoryfullurls;
	        		imagedetails2=imagedetails;
	        		 grid2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

		  		            @Override
		  		            public void onItemClick(AdapterView<?> parent, View view,
		  		                                    int position, long id) {
		  		               // Toast.makeText(getActivity(), "You Clicked at " +position, Toast.LENGTH_SHORT).show();
		  		            	String details=imagedetails2.get(position);
		  		            	String s=fullgrid2.get(position);
		  		            	 new DownloadImageTask(details)
		  		            	 .execute(s);
		  		            	 
		  		             
		  		                
		  		            }
		  		        });
	        	}
	        	else
	        	{
	        		heading3.setVisibility(View.VISIBLE);
	        		heading3.setText(details[0]);
	        		displayimages(selectedgalleryitems,grid3);
	        		fullgrid3=categoryfullurls;
	        		imagedetails3=imagedetails;
	        		
	        		 grid3.setOnItemClickListener(new AdapterView.OnItemClickListener() {

		  		            @Override
		  		            public void onItemClick(AdapterView<?> parent, View view,
		  		                                    int position, long id) {
		  		               // Toast.makeText(getActivity(), "You Clicked at " +position, Toast.LENGTH_SHORT).show();
		  		            	
		  		            	String details=imagedetails3.get(position);
		  		            	String s=fullgrid3.get(position);
		  		            	 new DownloadImageTask(details)
		  		            	 .execute(s);
		  		            	 
		  		             
		  		                
		  		            }
		  		        });
	        	}
	        	 
	        	}
	        	
	        }
	       
	     
	       /* displayimages(ImgUrls,grid1);
	       
			  
			  grid1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

		            @Override
		            public void onItemClick(AdapterView<?> parent, View view,
		                                    int position, long id) {
		               // Toast.makeText(getActivity(), "You Clicked at " +position, Toast.LENGTH_SHORT).show();
		            	
		            	String s=PublicValues.Imagefullviewurls.get(position);
		            	 new DownloadImageTask()
		            	 .execute(s);
		            	 
		             
		                
		            }
		        });*/
	        }
	        else{
	        	//Toast.makeText(getActivity(), "No Internet Connection!!", Toast.LENGTH_SHORT).show();
	        	}
		        
		        RelativeLayout b=(
		        		RelativeLayout)v.findViewById(R.id.upload);
		        b.setOnClickListener(listener);
	    	 }
	    	 else
	    	 {
	    		 Toast.makeText(getActivity(), "No Internet Connection!!", Toast.LENGTH_SHORT).show();
	    	 }
	    }
	    
	    private void displayimages( ArrayList<GalleryDomain> ImgUrl,GridView gridview) {
			// TODO Auto-generated method stub
	    	 adapter=new LazyAdapter(getActivity(), ImgUrl,getActivity().getApplicationContext());
	    	 gridview.setAdapter(adapter);
		}
		public OnClickListener listener=new OnClickListener(){
	        @Override
	        public void onClick(View arg0) {
	        	
	        	GalleryDialog cdd=new GalleryDialog(getActivity());
	        	cdd.show(); 
	        	
	        }
	    };
	    
	    

private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
ImageView bmImage;
String imagedetails;

public DownloadImageTask(String details) {
	imagedetails=details;
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

ProgressDialog dialog ;
@Override
protected void onPreExecute() {
// TODO Auto-generated method stub
super.onPreExecute();
  dialog = ProgressDialog.show(getActivity(), "", 
        "Loading. Please wait...", true);
}


protected void onPostExecute(Bitmap result) {
   
	dialog.dismiss();
   GalleryFullImageDialog GFID= new GalleryFullImageDialog(getActivity(),result,imagedetails);
   GFID.show();
}
}




@Override
public void oncomplete(String name) {
	// TODO Auto-generated method stub
	
	//Toast.makeText(getActivity(), "download completee", Toast.LENGTH_SHORT).show();
if(name.equalsIgnoreCase("gallery"))
{
	refreshtoload();}
}

}
