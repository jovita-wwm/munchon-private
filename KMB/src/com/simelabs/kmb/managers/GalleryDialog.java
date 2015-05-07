package com.simelabs.kmb.managers;

import com.simelabs.kmb.launch.R;
import com.simelabs.kmb.gallery.UploadActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class GalleryDialog extends Dialog implements
    android.view.View.OnClickListener {

  public Activity c;
  public Dialog d;
  public RelativeLayout yes, no;

  public GalleryDialog(Activity a) {
    super(a);
    // TODO Auto-generated constructor stub
    this.c = a;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.gallerydialog);
    yes = (RelativeLayout) findViewById(R.id.take);
    no = (RelativeLayout) findViewById(R.id.gallery);
    yes.setOnClickListener(this);
    no.setOnClickListener(this);

  }

  @Override
  public void onClick(View v) {
	  Intent i=new Intent(c, UploadActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
    switch (v.getId()) {
   case R.id.take:
       	i.putExtra("Action", "take");
		c.startActivity(i);
		
    	dismiss();
    	
      break;
    case R.id.gallery:
    	
    	i.putExtra("Action", "open");
		c.startActivity(i);
      dismiss();
      break;
    default:
      break;
    }
    dismiss();
  }
}