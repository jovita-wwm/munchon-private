package com.simelabs.kmb.activities;

import java.util.ArrayList;

import com.simelabs.kmb.domain.LocationDetails;
import com.simelabs.kmb.launch.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DialogAdapter extends ArrayAdapter<LocationDetails>
{
	ArrayList<LocationDetails> venuenames;
	private final Activity context;
	
	public DialogAdapter(Activity context,ArrayList<LocationDetails> venuenames) 
	{
	super(context, R.layout.artistdialogitem,venuenames);
	this.context = context;
	this.venuenames = venuenames;
	}
	

	@Override
	public View getView(final int position, View view, ViewGroup parent)
	{
	LayoutInflater inflater = context.getLayoutInflater();
	View rowView= inflater.inflate(R.layout.artistdialogitem, null, true);
	
	TextView txtVenuename = (TextView) rowView.findViewById(R.id.artistdialogtxt);
	txtVenuename.setText(venuenames.get(position).getName());
	
	
	return rowView;
	}
	
}
