package com.simelabs.kmb.spotbeak;

import java.io.IOException;
import java.io.InputStream;

import com.google.android.gms.internal.di;
import com.simelabs.kmb.launch.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.PluginState;

public class BeaconDetailViewActivity extends Activity{
	 ProgressDialog dialog ;
	 WebView wv;
	 String desImgUrl,artNameEng,artDesEng,instEng,instDesEng,desEng,artNameMal,artDescMal,descMal;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_beacon_detailview);
		
		    wv = (WebView) findViewById(R.id.beacondetailwebview);
		    
		    Bundle extras = getIntent().getExtras();
		    if(extras !=null)
		    {
		       desImgUrl = extras.getString("desImgUrl");
		       artNameEng = extras.getString("artNameEng");
		       artDesEng = extras.getString("artDesEng");
		       instEng = extras.getString("instEng");
		       instDesEng = extras.getString("instDesEng");
		       desEng = extras.getString("desEng");
		       artNameMal = extras.getString("artNameMal");
		       artDescMal = extras.getString("artDescMal");
		       descMal = extras.getString("descMal");
		      
				 
		    }
		   
		    wv.getSettings().setPluginState(PluginState.ON);
	        wv.getSettings().setJavaScriptEnabled(true);
	        wv.getSettings().setAllowFileAccess(true);
	        wv.getSettings().setAllowFileAccessFromFileURLs(true);
	        wv.getSettings().setAllowUniversalAccessFromFileURLs(true);
	        wv.setWebChromeClient(new WebChromeClient());
	        wv.getSettings().setDefaultTextEncodingName("utf-8");
	        wv.setWebViewClient(new WebViewClient()
	        {
	        	@Override
	        	public void onPageFinished(WebView view, String url) {
	        		// TODO Auto-generated method stub
	        		super.onPageFinished(view, url);
	        		dialog.dismiss();
	        	}
	        });
	        
		InputStream is;
		try {
			is = getAssets().open("detailTemplate.html");
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			String detailHtmlStr = new String(buffer);
			String  detailStr= detailHtmlStr.
					replace("$$$$$$", desImgUrl).
					replace("******", artNameEng).
					replace("&&&&&&", artDesEng).
					replace("%%%%%%", instEng).
					replace("^^^^^^", instDesEng).
					replace("######", desEng).
					replace("!!!!!!", artNameMal).
					replace("((((((", artDescMal).
					replace("@@@@@@", descMal);
			
			
		      
		       
			wv.loadDataWithBaseURL(null, detailStr, "text/html", "utf-8", null);
		    //wv.loadData(detailStr,"text/html","utf-8");
		        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
		dialog = ProgressDialog.show(this, "", 
		        "Loading details...", true);
		
		dialog.setCancelable(true);
	        dialog.setCanceledOnTouchOutside(true);
	        
		
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		dialog.dismiss();
		this.finish();
	}
}
