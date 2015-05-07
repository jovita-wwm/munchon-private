package com.simelabs.kmb.socialmedia;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.android.Facebook;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;
import com.simelabs.kmb.launch.R;
import com.simelabs.kmb.activities.MainActivity;
import com.simelabs.kmb.socialmedia.FacebookClass.DownloadImageTask;

public class FActivity extends Activity 
{

	private static final List<String> PERMISSIONS = Arrays.asList("user_location");
	MainActivity main;
	MainActivity ma;
	String access_token,access_expiry;
    String dispname;
    String id ;
    ProgressDialog pd;
    SharedPreferences pref;
    SharedPreferences.Editor edt;
   
    
    @Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_notifications);
		
		ma = new MainActivity();
		
		pd = new ProgressDialog(FActivity.this);
		pd.setMessage("Please wait while logging into Facebook...");
		pd.setCancelable(false);
       // pd.setCanceledOnTouchOutside(false);
		pd.show();
		 
		login();
	}
	
	   
		/**
		 * Facebook login method
		 */
		public void login() 
		{
			// start Facebook Login
		    Session.openActiveSession(this, true, new Session.StatusCallback() {

				@Override
				public void call(Session session, SessionState state,
						Exception exception) {
					 if (session.isOpened()) {
						   	
					//	 Toast.makeText(mContext, "Access token :" + session.getAccessToken() + "\n" +"Expires at "+session.getExpirationDate().toLocaleString(), Toast.LENGTH_LONG).show();
					       
						      Log.i("sessionToken", session.getAccessToken());
						      Log.i("sessionTokenDueDate", session.getExpirationDate().toLocaleString());
						
						      String access_token = session.getAccessToken();
						      PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("Access token",access_token).commit();
						     
					getName();
				    getPic();  
				          
				      }
				
		   
		}
		
		    private boolean isSubsetOf(Collection<String> subset,
			        Collection<String> superset) {
			    for (String string : subset) {
			        if (!superset.contains(string)) {
			            return false;
			        }
			    }
			    return true;
			
		 }
		 
		 });

		}

		public void getPic()
		{

	    	  /**
	    	   * Getting user's profile picture
	    	   */
      	  Bundle params = new Bundle();
      	  params.putBoolean("redirect", false);
      	  params.putString("type", "normal");
      	  params.putString("height", "200");
      	  params.putString("width", "200");
      	  new Request(
      			  Session.getActiveSession(),
      			    "/me/picture",
      			    params,
      			    HttpMethod.GET,
      			    new Request.Callback() {
      			        public void onCompleted(Response response) {
      			            /* handle the result */
      			        	Log.i("Profile pic", ""+response);
      			        	GraphObject graph=response.getGraphObject();
      			        	JSONObject jsonObj=graph.getInnerJSONObject();
      			        	JSONObject object;
      			        	String imageURL;
      			        	
      			        	  
      			        	  
      			        	try {
									 object = jsonObj.getJSONObject("data");
									  imageURL=object.getString("url");
									  
									  new DownloadImageTask().execute(imageURL);
									  
									
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
      			        	
      			        }
      			    }
      			).executeAsync();
	  
  	 
		}
		public void getName()
		{
			/**
			 * getting user's name and location
			 */
			 
			 // make request to the /me API
	          Request.newMeRequest(Session.getActiveSession(), new Request.GraphUserCallback() {

	            // callback after Graph API response with user object
	            @Override
				public void onCompleted(GraphUser user, Response response) {
					 if (user != null)
					 {
			              String dispname = user.getName();
			                
			              Log.i("Profile information", ""+response);
			                
			              MainActivity.facebook_profilename = dispname;
			             
			              PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("Name",dispname).commit();
						  
			           //   MainActivity.dispatchInformations();


			           }
				}
	          }).executeAsync();
		}
		
		/**
		 * Share to Facebook Wall
		 * 
		 * @param postTitle
		 * @param postDescription
		 * @param detUrl
		 * @param picUrl
		 */
		public void publishFeedDialog(final String postTitle,final String postDescription,final String detUrl,final String picUrl) 
		{
			 Session.openActiveSession(this, true, new Session.StatusCallback() {

					@Override
					public void call(Session session, SessionState state,
							Exception exception) {
						
				
						 if (session.isOpened()) {
						
			Bundle params = new Bundle();
		    params.putString("name", postTitle);
		    params.putString("caption", "Shared via Kochi Muziris Biennale app for Android");
		    params.putString("description", postDescription);
		    params.putString("link", detUrl);
		    params.putString("picture", picUrl);

		    WebDialog feedDialog = (
		        new WebDialog.FeedDialogBuilder(main,
		            Session.getActiveSession(),
		            params))
		        .setOnCompleteListener(new OnCompleteListener() {

		           
					@Override
					public void onComplete(Bundle values, FacebookException error) {
						  if (error == null) {
			                    // When the story is posted, echo the success
			                    // and the post Id.
			                    final String postId = values.getString("post_id");
			                    if (postId != null) {
			                        Toast.makeText(main,
			                            "Shared to wall",
			                            Toast.LENGTH_SHORT).show();
			                    } else {
			                        // User clicked the Cancel button
			                        Toast.makeText(main, 
			                            "Publish cancelled", 
			                            Toast.LENGTH_SHORT).show();
			                    }
			                } else if (error instanceof FacebookOperationCanceledException) {
			                    // User clicked the "x" button
			                    Toast.makeText(main, 
			                        "Publish cancelled", 
			                        Toast.LENGTH_SHORT).show();
			                } else {
			                    // Generic, ex: network error
			                    Toast.makeText(main, 
			                        "Error posting story", 
			                        Toast.LENGTH_SHORT).show();
			                }
						
					}

				

		        })
		        .build();
		    feedDialog.show();
		}
					}
			 });
		}

		/**
		 * AsyncTask for downloading user's profile picture
		 */
		 public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
			 //ImageView bmImage;
			

			  public DownloadImageTask() {
			      //this.bmImage = bmImage;
			     
			  }

			  protected Bitmap doInBackground(String... urls) {
			      String urldisplay = urls[0];
			      Bitmap mIcon11 = null;
			      try {
			        InputStream in = new java.net.URL(urldisplay).openStream();
			        mIcon11 = BitmapFactory.decodeStream(in);
			      
			      } catch (Exception e) {
			      
			          e.printStackTrace();
			      }
			      return mIcon11;
			  }

			  protected void onPostExecute(Bitmap result) {
			   
				  if(result!=null)
				  {
					 
					  String extr = Environment.getExternalStorageDirectory().toString();
					  File mFolder = new File(extr + "/biennale/facebook/");

					  if (!mFolder.exists()) {
					      mFolder.mkdir();
					  }
					  String s = "Facebook_profile_pic.png";
					  File f = new File(mFolder.getAbsolutePath(),s);

					  ByteArrayOutputStream bytes = new ByteArrayOutputStream();
					  result.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
                     
					  PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("Profile picture path", extr + "/biennale/facebook/Facebook_profile_pic.png").commit();
					     
					  try {
						  
						f.createNewFile();
						 FileOutputStream fo = new FileOutputStream(f);
						  fo.write(bytes.toByteArray());

						  // remember close de FileOutput
						  fo.close();
						  
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 
		              MainActivity.facebookimage = result;
					//  MainActivity.dispatchPicInformations();
					  
					  pd.dismiss();
					  finish();
					
				  }
				
			 
			  }
		 }
	
	 @Override
	  public void onActivityResult(int requestCode, int resultCode, Intent data)
   {
		  super.onActivityResult(requestCode, resultCode, data);
	      Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	    
		}
	  }
	


