package com.simelabs.kmb.managers;

import java.util.ArrayList;

import com.simelabs.kmb.launch.R;
import com.simelabs.kmb.domain.LocationDetails;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DialogArrayAdapter extends BaseAdapter{

	private final Activity context;
	ArrayList<LocationDetails> venueNames;

	public DialogArrayAdapter(Activity context,ArrayList<LocationDetails> la) 
	{
	//super(context, R.layout.artistdialogitem);
	this.context = context;
	this.venueNames = la;
	}
    public int getCount() {
        return venueNames.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
	@Override
	public View getView(final int position, View view, ViewGroup parent)
	{
	LayoutInflater inflater = context.getLayoutInflater();
	View rowView= inflater.inflate(R.layout.artistdialogitem, null, true);
	
	TextView txtvenueName = (TextView) rowView.findViewById(R.id.artistdialogtxt);
	txtvenueName.setText(venueNames.get(position).getName());
	return rowView;
	
	}
}
