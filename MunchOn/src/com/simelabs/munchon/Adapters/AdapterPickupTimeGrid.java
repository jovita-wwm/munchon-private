package com.simelabs.munchon.Adapters;

import java.util.ArrayList;

import com.simelabs.munchon.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AdapterPickupTimeGrid extends BaseAdapter {
Activity act;
ArrayList<String> times;

public AdapterPickupTimeGrid(Activity c, ArrayList<String> time) {
    act = c;
    times = time;
}



@Override
public int getCount() {
    // TODO Auto-generated method stub
    return times.size();
}

@Override
public Object getItem(int position) {
    // TODO Auto-generated method stub
    return times.get(position);
}

@Override
public long getItemId(int position) {
    // TODO Auto-generated method stub
    return 0;
}

@Override
public View getView(int position, View convertView, ViewGroup parent) {
	LayoutInflater inflater = act.getLayoutInflater();
	View rowView= inflater.inflate(R.layout.dialog_pickuptime_timeitem, null, true);
	TextView t=(TextView)rowView.findViewById(R.id.time);
	t.setText(times.get(position));
	rowView.setTag(times.get(position));

    return rowView;
}

}