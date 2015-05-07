package com.simelabs.kmb.spotbeak;

import com.simelabs.kmb.launch.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * 
 * @author Jovita
 *
 */
public class AllDemosActivity extends Activity {

	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    setContentView(R.layout.all_demos);
	    
	    Intent intent = new Intent(AllDemosActivity.this, ListBeaconsActivity.class);
        intent.putExtra(ListBeaconsActivity.EXTRAS_TARGET_ACTIVITY, DistanceBeaconActivity.class.getName());
        startActivity(intent);
        
	  }
}
