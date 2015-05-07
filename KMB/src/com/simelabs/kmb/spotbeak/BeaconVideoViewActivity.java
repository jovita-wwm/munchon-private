package com.simelabs.kmb.spotbeak;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;

import com.simelabs.kmb.launch.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.webkit.URLUtil;
import android.widget.Toast;
import android.widget.VideoView;

public class BeaconVideoViewActivity extends Activity{

	String mPath;
	VideoView video;
	  String current;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 video=(VideoView)findViewById(R.id.beaconvideo);
		mPath=getIntent().getExtras().getString("url");
		
		playVideo();
		
	}

	  private void playVideo() {
	        try {
	            final String path = mPath;
	            Log.v("video view", "path: " + path);
	            if (path == null || path.length() == 0) {
	                Toast.makeText(BeaconVideoViewActivity.this, "File URL/path is empty",
	                        Toast.LENGTH_LONG).show();
	 
	            } else {
	                // If the path has not changed, just start the media player
	                if ( video != null) {
	                	video.start();
	                	video.requestFocus();
	                    return;
	                }
	                 current = path;
	                 new videodownload(path);
	                /* video.setVideoPath(getDataSource(path));
	                 video.start();
	                 video.requestFocus();*/
	 
	            }
	        } catch (Exception e) {
	            Log.e("video exception", "error: " + e.getMessage(), e);
	            if (video != null) {
	            	video.stopPlayback();
	            }
	        }
	    }
	  
	  
	 /* private String getDataSource(String path) throws IOException {
	        if (!URLUtil.isNetworkUrl(path)) {
	            return path;
	        } else {
	            URL url = new URL(path);
	            URLConnection cn = url.openConnection();
	            cn.connect();
	            InputStream stream = cn.getInputStream();
	            if (stream == null)
	                throw new RuntimeException("stream is null");
	            File temp = File.createTempFile("mediaplayertmp", "dat");
	            temp.deleteOnExit();
	            String tempPath = temp.getAbsolutePath();
	            FileOutputStream out = new FileOutputStream(temp);
	            byte buf[] = new byte[128];
	            do {
	                int numread = stream.read(buf);
	                if (numread <= 0)
	                    break;
	                out.write(buf, 0, numread);
	            } while (true);
	            try {
	                stream.close();
	            } catch (IOException ex) {
	                Log.e("video excepttion2", "error: " + ex.getMessage(), ex);
	            }
	            return tempPath;
	        }
	  }*/

	  
	  private class videodownload extends AsyncTask<String, Void, String> {
			String path;
			 String tempPath ;

			public videodownload(String p) {
				path=p;
			}

			protected String doInBackground(String... urls) {
			
				 if (!URLUtil.isNetworkUrl(path)) {
			            return path;
			        } else {
			            URL url;
						try {
							url = new URL(path);
						
			            URLConnection cn = url.openConnection();
			            cn.connect();
			            InputStream stream = cn.getInputStream();
			            if (stream == null)
			                throw new RuntimeException("stream is null");
			            File temp = File.createTempFile("mediaplayertmp", "dat");
			            temp.deleteOnExit();
			             tempPath = temp.getAbsolutePath();
			            FileOutputStream out = new FileOutputStream(temp);
			            byte buf[] = new byte[128];
			            do {
			                int numread = stream.read(buf);
			                if (numread <= 0)
			                    break;
			                out.write(buf, 0, numread);
			            } while (true);
			           
			                stream.close();
			            } catch (IOException ex) {
			                Log.e("video excepttion2", "error: " + ex.getMessage(), ex);
			            }
			            return tempPath;
			        }
			}
			ProgressDialog dialog ;
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				 dialog = ProgressDialog.show(getApplicationContext(), "", 
					        "Loading. Please wait...", true);
			}

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				dialog.dismiss();
				
				 video.setVideoPath(result);
                 video.start();
                 video.requestFocus();
				
			}
		
		}
	  
}
