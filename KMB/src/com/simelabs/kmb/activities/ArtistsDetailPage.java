package com.simelabs.kmb.activities;

import java.util.ArrayList;

import com.simelabs.kmb.domain.ArtistDomain;
import com.simelabs.kmb.launch.R;
import com.simelabs.kmb.locationmap.ArtistMap;
import com.simelabs.kmb.locationmap.MapManagerInside;
import com.simelabs.kmb.managers.JsonManager;
import com.simelabs.kmb.service.PublicValues;
import com.simelabs.kmb.spotbeak.Beacon;
import com.simelabs.kmb.spotbeak.Beacondetails;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ArtistsDetailPage extends Activity{

	public static ArrayList<Beacondetails> s;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.activity_artistsdetailpage);
		 
		final int artistid=getIntent().getExtras().getInt("artistid");
		//Toast.makeText(getApplicationContext(), artistid, Toast.LENGTH_SHORT).show();
		
		TextView artistname=(TextView)findViewById(R.id.txt_artistnname);
		TextView artistdetails=(TextView)findViewById(R.id.txt_artistdetails);
		LinearLayout installations=(LinearLayout)findViewById(R.id.installattionlist);
		
		//TextView installationdetails=(TextView)findViewById(R.id.txt_installationdetails);
		//Button map=(Button)findViewById(R.id.artistdetailpagemap);
		
		ArrayList<Beacondetails> arayy=new ArrayList<Beacondetails>();
		
		
		
		
		JsonManager json=new JsonManager(getApplicationContext());
		json.getDatafromBeacon(this);
		
		ArrayList<Beacondetails> allbeacons=PublicValues.beacondetails;
		ArrayList<Beacondetails> artistsbeacons=new ArrayList<Beacondetails>();
		//artistsbeacons.clear();
	
		
		for(Beacondetails bec:allbeacons)
		{
			if(bec.getArtistid()==artistid)
			{
				
				artistsbeacons.add(bec);
				
			}
		}
		
		
		
		if(artistsbeacons.size()>0)
		{
			artistname.setText(artistsbeacons.get(0).getartistNameEnglish());
			artistdetails.setText(artistsbeacons.get(0).getartistDescriptionEnglish());
			artistdetails.setLineSpacing(2, 2);
			
			String s=" ";
			for(Beacondetails b:artistsbeacons)
			{
				if(s.equalsIgnoreCase(b.getName()))
				{
					break;
				}
				TextView installationtitletext=new TextView(getApplicationContext());
				installationtitletext.setText(b.getName());
				installationtitletext.setTypeface(Typeface.DEFAULT_BOLD);
				installationtitletext.setPadding(0, 5, 0, 5);
				installationtitletext.setTextSize(16);
				installationtitletext.setTextColor(Color.BLACK);
				//installationtitletext.setTypeface(Typeface.DEFAULT_BOLD);
				s=b.getName();
				TextView installationtext=new TextView(getApplicationContext());
				installationtext.setText(b.getDescriptionenglish());
				installationtext.setLineSpacing(2, 2);
				installationtext.setTextColor(Color.BLACK);
				//installationtext.setTextColor(R.color.black);
				
				installations.addView(installationtitletext);
				installations.addView(installationtext);
				
			//installationdetails.setText(artistsbeacons.get(0).getDescriptionenglish());
			}
			
		}
		else
		{
			artistname.setVisibility(View.INVISIBLE);
		artistdetails.setVisibility(View.INVISIBLE);
		//installationdetails.setVisibility(View.INVISIBLE);
			Toast.makeText(getApplicationContext(), "No data to show", Toast.LENGTH_SHORT).show();
			finish();
		}
		
		
		
		
/*map.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), ArtistMap.class);
				Integer venueno=(Integer)v.getTag();
				intent.putExtra("location", "aspin");
				//intent.putExtra("artistbeacons",arayy);
		        startActivity(intent);
			}
		});
		
	}
	
*/
	}}
