package com.simelabs.kmb.webview;


import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

import com.simelabs.kmb.launch.R;
import com.simelabs.kmb.activities.MainActivity;

public class HtmlRendering
{
	
	String file_name;
	String[] files={"aboutus.html","home.html","events.html","newsandupdates.html","whatsontoday.html"};
	   
   	public String load()
			   	{
			   		for(int i=0;i<files.length;i++)
			   		{
			   			file_name="file:///android_asset/"+files[i];
			   		}
			   		return file_name;
			   		
			   	}
				

}
