package com.simelabs.kmb.managers;

import java.util.ArrayList;

import com.simelabs.kmb.launch.R;
import com.simelabs.kmb.domain.GalleryDomain;
import com.simelabs.kmb.gallery.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LazyAdapter extends BaseAdapter {
    
    private Activity activity;
   // private ArrayList<String> data;
    ArrayList<GalleryDomain> galleryd;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    
    public LazyAdapter(Activity a, ArrayList<String> d) {
        activity = a;
       // data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public LazyAdapter(Activity c, ArrayList<GalleryDomain> gd,Context cx) {
        activity = c;
       /* for(GalleryDomain gdm:gd)
        {
        	data.add(gdm.getImagethumurl());
        }*/
        galleryd=gd;
        //data=gd;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }
    
    public int getCount() {
        return galleryd.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.galleryitem, null);

        //TextView text=(TextView)vi.findViewById(R.id.text);;
        ImageView image=(ImageView)vi.findViewById(R.id.image);
      //  text.setText("item "+position);
        imageLoader.GalleryDisplayImage(galleryd.get(position), image);
        return vi;
    }
}