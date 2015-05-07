package com.simelabs.kmb.activities;

import org.apache.commons.net.ftp.FTPClient;

import com.simelabs.kmb.launch.R;
import com.simelabs.kmb.network.DownloadFiles;
import com.simelabs.kmb.network.Internet;
import com.simelabs.kmb.service.PublicValues;
import com.simelabs.kmb.spotbeak.MyService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 
 * @author Jovita
 * 
 *         Splash screen and checks internet connection ,downloads all files if
 *         net available
 */

public class Splash extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		 PublicValues.beaconsalreadyfound.clear();
		//long time = System.currentTimeMillis();

		Internet net = new Internet(getApplicationContext());
		boolean netstatus = net.isAvailable();
		if (netstatus != false) {
			/*Toast.makeText(getApplicationContext(), "Network is available",
					Toast.LENGTH_SHORT).show();*/
			
			

			DownloadFiles files = new DownloadFiles(getApplicationContext(),Splash.this);
		boolean filestatus = files.Download();

	
			
			/**
			 * temporary skip for testing remove its after testing
			 */
			//..........................................................................................
			/*Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
			    public void run() {
			    	Intent i = new Intent(getApplicationContext(), MainActivity.class);
					startActivity(i);
					Splash.this.finish();
			    }
			},3000);*/
			
			//..........................................................................................


			
		} else {
			Toast.makeText(getApplicationContext(), "No available Networks",
					Toast.LENGTH_SHORT).show();
			
			final Animation myRotation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotator);
			 final ImageView load;
			// load=(ImageView)findViewById(R.id.loading);
				
			// load.startAnimation(myRotation);

			 
			 final ImageView ball1 = (ImageView)findViewById(R.id.ball1);
				final ImageView ball2 = (ImageView)findViewById(R.id.ball2);
				final ImageView ball3 = (ImageView)findViewById(R.id.ball3);
				
				Animation ball=AnimationUtils.loadAnimation(this, R.anim.ballzoom);
				
				Handler handler = new Handler();


				final Animation b1ball=AnimationUtils.loadAnimation(this, R.anim.ballzoom);
				final Animation b2ball=AnimationUtils.loadAnimation(this, R.anim.ballzoom);
				final Animation b3ball=AnimationUtils.loadAnimation(this, R.anim.ballzoom);
				//ball2.setVisibility(View.INVISIBLE);
			//	ball3.setVisibility(View.INVISIBLE);
				b1ball.setStartOffset(100);
				ball1.setAnimation(b1ball);
				
				b2ball.setStartOffset(200);
				ball2.setAnimation(b2ball);
				
				b3ball.setStartOffset(300);
				ball3.setAnimation(b3ball);
				
				
				


			
			handler.postDelayed(new Runnable() {
			    public void run() {
			    	
			    	//load.clearAnimation();
			    	ball1.clearAnimation();
			    	ball2.clearAnimation();
			    	ball3.clearAnimation();
			    	
			    	Intent i = new Intent(getApplicationContext(), MainActivity.class);
					startActivity(i);
					Splash.this.finish();
			    }
			},3000);
			
			startService(new Intent(this, MyService.class));

		}
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
	}

}
