package com.simelabs.kmb.gallery;

import java.io.InputStream;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simelabs.kmb.launch.R;
public class GalleryFullImageDialog extends Dialog{

public Activity c;
public Dialog d;
public Bitmap background;

public ImageView fullimage;
TextView name,desc;
String det;

public GalleryFullImageDialog(Activity a,Bitmap imgbit,String details) {
super(a);
// TODO Auto-generated constructor stub
this.c = a;
this.background=imgbit;
this.det=details;
}

@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
requestWindowFeature(Window.FEATURE_NO_TITLE);
setContentView(R.layout.gallery_fullsize_pic_dialog);

 fullimage=(ImageView)findViewById(R.id.fullimage);
 fullimage.setImageBitmap(background);
 
 name=(TextView)findViewById(R.id.imagename);
 desc=(TextView)findViewById(R.id.imagedesc);
 
 String s[]=det.split("@@@galllery@@@");
 
 try
 {
 name.setText(s[0]);
 desc.setText(s[1]);
 }
 catch(ArrayIndexOutOfBoundsException e)
 {
	 name.setVisibility(View.INVISIBLE);
	 desc.setVisibility(View.INVISIBLE);
 }
 



}


}