package com.simelabs.munchon.Adapters;

import java.util.ArrayList;

import com.simelabs.munchon.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class AdapterPlaceorderItemList extends BaseAdapter
{
	private final Activity context;
	ArrayList<ArrayList> item;
	Integer count;
	ArrayList la;
	ArrayList bd;

	
	public AdapterPlaceorderItemList(Activity context,ArrayList<ArrayList> names) 
	{
	this.context = context;
	this.item = names;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return item.get(0).size();
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
		View rowView= inflater.inflate(R.layout.activity_placeorder_items_listitem, null, true);
		
		TextView txtTitle = (TextView) rowView.findViewById(R.id.txt_item);
		TextView quantity=(TextView)rowView.findViewById(R.id.txt_qty);
		TextView rate=(TextView)rowView.findViewById(R.id.txt_price);
		TextView total=(TextView)rowView.findViewById(R.id.txt_total);
    
     
    	  txtTitle.setText(item.get(0).get(position).toString());
    	  quantity.setText(item.get(1).get(position).toString());
    	  rate.setText("$"+item.get(2).get(position).toString());
    	  
    	  float r=Float.parseFloat(item.get(1).get(position).toString())*Float.parseFloat(item.get(2).get(position).toString());
    	  total.setText("$"+r);
    	  return rowView;
	}

	

	
	
	}