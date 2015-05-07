package com.simelabs.kmb.gallery;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.simelabs.kmb.service.PublicValues;
import com.simelabs.kmb.launch.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager.OnCancelListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class UploadActivity extends Activity implements OnClickListener{
	
	private static int RESULT_LOAD_IMAGE = 1;
	 private static final int CAMERA_REQUEST = 1888; 
	 
		/*********  work only for Dedicated IP ***********/
		//static final String FTP_HOST= "http://jadoomovies.in/app/gallery/";
		static final String FTP_HOST= "jadoomovies.in";
		
		/*********  FTP USERNAME ***********/
		static final String FTP_USER = "jadoomovies@agentjadoo.co.uk";
		
		/*********  FTP PASSWORD ***********/
		static final String FTP_PASS  ="jadoo123";
		
		Button btn;
		ImageView imageView;
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.gallerytakepic);

	        imageView=(ImageView)findViewById(R.id.cameraimage);
	        btn=(Button)findViewById(R.id.imgupload);
	        btn.setOnClickListener(this);
				
			String action=getIntent().getExtras().getString("Action");	
			if(action.equalsIgnoreCase("take"))
			{

		        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
		        startActivityForResult(cameraIntent, CAMERA_REQUEST); 
			}
			if(action.equalsIgnoreCase("open"))
			{
				Intent i = new Intent(
						Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
						 
						startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
	        
	        
	    }
	    public void onClick(View v) {
			
	    	
	    	new RetrieveFeedTask(imagepath).execute("");
	    	
		}
	  
	    
	    
	    /*******  Used to file upload and show progress  **********/
	    
	    public class MyTransferListener implements FTPDataTransferListener {

	    	public void started() {
	    	
	    		// Transfer started
	    		//Toast.makeText(getBaseContext(), " Upload Started ...", Toast.LENGTH_SHORT).show();
	    		System.out.println(" Upload Started ...");
	    	}

	    	public void transferred(int length) {
	    		
	    		
	    		//Toast.makeText(getBaseContext(), " transferred ..." + length, Toast.LENGTH_SHORT).show();
	    		System.out.println(" transferred ..." + length);
	    	}

	    	public void completed() {
	    		
	    		Handler handler = new Handler(Looper.getMainLooper());

	    		handler.post(new Runnable() {

	    		        public void run() {
	    		            //Your UI code here
	    		        	Toast.makeText(getBaseContext(), " Uploaded ...", Toast.LENGTH_SHORT).show();
	    		        	
	    		       finish();
	    		        }
	    		    });
	    		
	    	System.out.println(" completed ..." );
	    	}

	    	public void aborted() {
	    		
	    		
	    		System.out.println(" aborted ..." );
	    	}

	    	public void failed() {
	    		
	    	
	    		System.out.println(" failed ..." );
	    	}

	    }
		
	   public class RetrieveFeedTask extends AsyncTask<String, String, String> {

	        private Exception exception;
	        public File f;
	        
	        public RetrieveFeedTask(File fileName) {
				// TODO Auto-generated constructor stub
	        	f=fileName;
			}

	        protected String doInBackground(String... urls) {
	            try {
/*
	       		 FTPClient client = new FTPClient();
	       		 
	       		try {
	       			
	       			client.connect(FTP_HOST);
	       			client.login(FTP_USER, FTP_PASS);
	       			client.setType(FTPClient.TYPE_BINARY);
	       			
	       			String[] s=PublicValues.GallerysavePath.split("jadoomovies.in");  
	       			client.changeDirectory(s[1]);
	       			
	       			
	       			
	       			client.upload(f, new MyTransferListener());*/
	            	
	            	AmazonS3Client s3Client = new AmazonS3Client( new BasicAWSCredentials( "AKIAJ3WOON2AHPXDZWVQ", "bNgeEAEWUYVwjoKOQ6OOUEOKEz3rsJtS2PyIrrMf" ) );
	            	
	            	String[] s=f.getAbsolutePath().split("/");
	            	String name=s[s.length-1];
	            	PutObjectRequest por = new PutObjectRequest("biennaleimageupload", name, f );  
	            	s3Client.putObject( por );
	       			
	       		} catch (Exception e) {
	       			e.printStackTrace();
	       			
	       		}
	       		
	            	
	            	
	            
				return "";
	        }
	        
	        
	        ProgressDialog dialog ;
	        
	        @Override
	        protected void onPreExecute() {
	        // TODO Auto-generated method stub
	        super.onPreExecute();
	         dialog = ProgressDialog.show(UploadActivity.this, "", 
                    "Loading. Please wait...", true);
	        }

	        protected void onPostExecute(String feed) {
	            // TODO: check this.exception 
	            // TODO: do something with the feed
	        	dialog.dismiss();
	        	Toast.makeText(getApplicationContext(), "Uploading Done", Toast.LENGTH_SHORT).show();
	        	finish();
	        }
	    }
	   
	   File imagepath;
	   protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
	       if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {  
	           Bitmap photo = (Bitmap) data.getExtras().get("data"); 
	           imageView.setImageBitmap(photo);
	           
	           //Bitmap b = pagesView.getDrawingCache();
	         
				btn.setVisibility(View.VISIBLE);
			
				
	           File myDirectory = new File(Environment.getExternalStorageDirectory()
						.toString() + "/biennale/", "camera");

	           if(!myDirectory.exists()) {                                 
	             myDirectory.mkdirs();
	           }
			   
			    try {

			    	SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
			    	String date = dateFormat.format(new Date());
			    	
			    	imagepath=new File(Environment.getExternalStorageDirectory()
							.toString() + "/biennale/camera/"
	    					+ "Camera"+date+".jpg");
			    	
			    	
			    	photo.compress(CompressFormat.PNG, 100, new FileOutputStream(imagepath));

			       Log.i("image", "addede to sd card");
			      
			       
			    } catch (FileNotFoundException e) {
			        e.printStackTrace();
			    } catch (IOException e) {
			        e.printStackTrace();
			    }
	    	
	           
	           btn.setVisibility(View.VISIBLE);
	       }  
	       if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
	            Uri selectedImage = data.getData();
	            String[] filePathColumn = { MediaStore.Images.Media.DATA };
	 
	            Cursor cursor = getContentResolver().query(selectedImage,
	                    filePathColumn, null, null, null);
	            cursor.moveToFirst();
	 
	            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	            String picturePath = cursor.getString(columnIndex);
	            cursor.close();
	             
	           Log.e("picture path", picturePath);
	            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
	            imagepath=new File(picturePath);
	            btn.setVisibility(View.VISIBLE);
	         
	        }
	     
	       
	       
	   } 
	    

}
