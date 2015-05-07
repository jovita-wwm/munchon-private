package com.simelabs.kmb.spotbeak;


import java.util.ArrayList;
import java.util.Collection;

import com.simelabs.kmb.launch.R;
import com.simelabs.kmb.gallery.ImageLoader;
import com.simelabs.kmb.service.PublicValues;
import com.simelabs.kmb.spotbeak.LeDeviceListAdapter.ViewHolder;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LazyBeaconAdapter extends BaseAdapter {
    
    private Context context;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    private ArrayList<Beacon> beacons;
    
    public LazyBeaconAdapter(Context con) {
        context = con;
        inflater =  LayoutInflater.from(context);
        imageLoader=new ImageLoader(con);
    }
    
    public void replaceWith(Collection<Beacon> newBeacons) {
        this.beacons.clear();
        this.beacons.addAll(newBeacons);
        notifyDataSetChanged();
      }


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
    
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=inflateIfRequired(convertView, position, parent);
        bind(getItem(position), vi);
        
       /* ImageView image=(ImageView)vi.findViewById(R.id.image);
      //  text.setText("item "+position);
        imageLoader.DisplayImage(data.get(position), image);*/
        return vi;
    }
    private View inflateIfRequired(View view, int position, ViewGroup parent) {
        if (view == null) {
          view = inflater.inflate(R.layout.list_item_handle_left, null);
          view.setTag(new ViewHolder(view));
        }
        return view;
      }
    private void bind(Beacon beacon, View view) {
        ViewHolder holder = (ViewHolder) view.getTag();
        
        //gettt data from beacon  json
        Beacondetails detail=getbeacondetail(beacon);
        
        
     
        holder.titleTextView.setText(detail.getName());
       // holder.titleTextView.setText(beacon.getProximityUUID());
        holder.descTextView.setText(detail.getDescription());
       // holder.beakimg.setBackground(background);
      //  new DownloadImageTask(holder.beakimg).execute(detail.getImageurl());
        imageLoader.DisplayImage(detail.getImageurl(), holder.beakimg);
        
        holder.beaconndetailIMG.setTag(detail.getDetailurl());
        holder.beaconvideo.setTag(detail.getVideourl());
        holder.beacondistance.setVisibility(View.INVISIBLE);
        holder.beaconshare.setTag(detail.getName()+"#share#"+detail.getDescription()+"#share#"+
        detail.getDetailurl()+"#share#"+detail.getImageurl());
        
       // holder.beacondistance.setText(beacon.getMeasuredPower());
       // holder.minorTextView.setText("Minor: " + beacon.getMinor());
       // holder.measuredPowerTextView.setText("MPower: " + beacon.getMeasuredPower());
       
        
        // holder.macTextView.setText(String.format("MAC: %s (%.2fm)", beacon.getMacAddress(), Utils.computeAccuracy(beacon)));
        
        
        
        
        
        
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
  		  String beaconori=b.getProximityUUID().toUpperCase();
  			String beaconlist=beak.getId().toUpperCase();
  			if(beaconori.equalsIgnoreCase(beaconlist))
  			{
  				detail=beak;
  			}
  	  }
  	return detail;
  	  
  	  
  	  
    }
    
}