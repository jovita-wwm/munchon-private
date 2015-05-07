package com.simelabs.kmb.spotbeak;


	import java.security.Provider;

	import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.simelabs.kmb.launch.R;

	import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

	public class Youtube extends YouTubeBaseActivity implements 
		YouTubePlayer.OnInitializedListener{
		
		public static final String API_KEY = "AIzaSyBN0ZsSVHPNhFivihiW4eVJJO3WxK0JruY";
		public static final String VIDEO_ID = "o7VVHhK9zf0";
		
		private YouTubePlayer youTubePlayer;
		private YouTubePlayerFragment youTubePlayerFragment;
		//private TextView textVideoLog;
		//private Button btnViewFullScreen;
		
		private static final int RQS_ErrorDialog = 1;
		
		private MyPlayerStateChangeListener myPlayerStateChangeListener;
		private MyPlaybackEventListener myPlaybackEventListener;
		
		String log = "";

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.youtubeplayer_layout);
	        
	        youTubePlayerFragment = (YouTubePlayerFragment)getFragmentManager()
	        	    .findFragmentById(R.id.youtubeplayerfragment);
	        youTubePlayerFragment.initialize(API_KEY, Youtube.this);

	      //  textVideoLog = (TextView)findViewById(R.id.videolog);
	        
	        myPlayerStateChangeListener = new MyPlayerStateChangeListener();
	        myPlaybackEventListener = new MyPlaybackEventListener();
	    
	    }
	        
	      /*  btnViewFullScreen = (Button)findViewById(R.id.btnviewfullscreen);
	        btnViewFullScreen.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					youTubePlayer.setFullscreen(true);
				}});
	    }
*/
		@Override
		public void onInitializationFailure(com.google.android.youtube.player.YouTubePlayer.Provider provider,
				YouTubeInitializationResult result) {
			
			if (result.isUserRecoverableError()) {
				result.getErrorDialog(this, RQS_ErrorDialog).show();	
			} else {
				Toast.makeText(this, 
						"YouTubePlayer.onInitializationFailure(): " + result.toString(), 
						Toast.LENGTH_LONG).show();	
			}
		}

		@Override
		public void onInitializationSuccess(com.google.android.youtube.player.YouTubePlayer.Provider provider, YouTubePlayer player,
				boolean wasRestored) {
			
			youTubePlayer = player;
			
			/*Toast.makeText(getApplicationContext(), 
					"YouTubePlayer.onInitializationSuccess()", 
					Toast.LENGTH_LONG).show();*/
			
			youTubePlayer.setPlayerStateChangeListener(myPlayerStateChangeListener);
			youTubePlayer.setPlaybackEventListener(myPlaybackEventListener);
		    youTubePlayer.setFullscreen(true);
			
			if (!wasRestored) {
			      player.cueVideo(VIDEO_ID);
			    }

		}
		
		private final class MyPlayerStateChangeListener implements PlayerStateChangeListener {
			
			private void updateLog(String prompt){
				log += 	"MyPlayerStateChangeListener" + "\n" + 
						prompt + "\n\n=====";
				//textVideoLog.setText(log);
			};

			@Override
			public void onAdStarted() {
				updateLog("onAdStarted()");
			}

			@Override
			public void onError(
					com.google.android.youtube.player.YouTubePlayer.ErrorReason arg0) {
				updateLog("onError(): " + arg0.toString());
			}

			@Override
			public void onLoaded(String arg0) {
				updateLog("onLoaded(): " + arg0);
			}

			@Override
			public void onLoading() {
				updateLog("onLoading()");
			}

			@Override
			public void onVideoEnded() {
				updateLog("onVideoEnded()");
			}

			@Override
			public void onVideoStarted() {
				updateLog("onVideoStarted()");
			}
			
		}
		
		private final class MyPlaybackEventListener implements PlaybackEventListener {
			
			private void updateLog(String prompt){
				log += 	"MyPlaybackEventListener" + "\n-" + 
						prompt + "\n\n=====";
			//	textVideoLog.setText(log);
			};

			@Override
			public void onBuffering(boolean arg0) {
				updateLog("onBuffering(): " + String.valueOf(arg0));
			}

			@Override
			public void onPaused() {
				updateLog("onPaused()");
			}

			@Override
			public void onPlaying() {
				updateLog("onPlaying()");
			}

			@Override
			public void onSeekTo(int arg0) {
				updateLog("onSeekTo(): " + String.valueOf(arg0));
			}

			@Override
			public void onStopped() {
			
				updateLog("onStopped()");
			}
			
		}



		
	}
