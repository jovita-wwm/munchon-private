package com.simelabs.kmb.socialmedia;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;
import com.simelabs.kmb.activities.MainActivity;
import com.simelabs.kmb.socialmedia.FActivity.DownloadImageTask;
import com.special.ResideMenu.ResideMenu;

public class FacebookClass 
{

	private Activity mContext;

	String dispname;
    String disploc;
    String  strLocation;
    Editor prefsEditor;
    Boolean sessionState = false;
    
    private static final List<String> PERMISSIONS = Arrays.asList("user_location");
	Boolean pendingPublishReauthorization;
	String id ;
    Boolean ProfilePic=false;
    String access_token;
    SharedPreferences pref;
    SharedPreferences.Editor edt;
    
    public FacebookClass(Activity context) 
    {
		 mContext = context;
	}

   
	/**
	 * Facebook login method
	 */
	public void login() 
	{
		// start Facebook Login
	    Session.openActiveSession(mContext, true, new Session.StatusCallback() {

			@Override
			public void call(Session session, SessionState state,
					Exception exception) {
				 if (session.isOpened()) {
					 
					 
					    	sessionState = true;
					   
					   
				 // Toast.makeText(mContext, "Access token :" + session.getAccessToken() + "\n" +"Expires at "+session.getExpirationDate().toLocaleString(), Toast.LENGTH_LONG).show();
				       
				  String access_token = session.getAccessToken();
			      PreferenceManager.getDefaultSharedPreferences(mContext).edit().putString("Access token",access_token).commit();
			      
					  	 
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
		             
		              PreferenceManager.getDefaultSharedPreferences(mContext).edit().putString("Name",dispname).commit();
					  
		              MainActivity.dispatchInformations(dispname);


		           }
			}
          }).executeAsync();
	}
	

	public void getPic()
	{

    	  /**
    	   * Getting user's profile picture
    	   */
		 Session.openActiveSession(mContext, true, new Session.StatusCallback() {

				@Override
				public void call(Session session, SessionState state,
						Exception exception) {
					
			
					 if (session.isOpened()) {
					
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
				}
		 });
		 getName();
	 
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
		 Session.openActiveSession(mContext, true, new Session.StatusCallback() {

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
	        new WebDialog.FeedDialogBuilder(mContext,
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
		                        Toast.makeText(mContext,
		                            "Shared to wall",
		                            Toast.LENGTH_SHORT).show();
		                    } else {
		                        // User clicked the Cancel button
		                        Toast.makeText(mContext.getApplicationContext(), 
		                            "Publish cancelled", 
		                            Toast.LENGTH_SHORT).show();
		                    }
		                } else if (error instanceof FacebookOperationCanceledException) {
		                    // User clicked the "x" button
		                    Toast.makeText(mContext.getApplicationContext(), 
		                        "Publish cancelled", 
		                        Toast.LENGTH_SHORT).show();
		                } else {
		                    // Generic, ex: network error
		                    Toast.makeText(mContext.getApplicationContext(), 
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
				  ProfilePic=true;
				  ((MainActivity)mContext).dispatchPicInformations(result);
				 
			  }
			  else
			  {
				  ProfilePic=false;
			  }
			  
		 
		  }
	 }
}
