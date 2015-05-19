package com.simelabs.munchon.Activities;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockListActivity;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingListActivity;
import com.simelabs.munchon.R;
import com.simelabs.munchon.Adapters.AdapterDishCategories;
import com.simelabs.munchon.DB.AppController;
import com.simelabs.munchon.DB.Const;
import com.simelabs.munchon.Domain.DishCategoriesDomain;
import com.simelabs.munchon.Domain.ImageHelper;
import com.simelabs.munchon.Domain.PublicValues;
import com.simelabs.munchon.Domain.RestaurantDomain;
import com.simelabs.munchon.Network.Internet;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityRestaurantMenu extends BaseClassActionBar {
	ListView list;
	String RestaurantName = "Slide Lounge";
	String[] menuCategories = { "Breakfast", "Coffee", "Appetizers", "Desserts" };
	ArrayList<String> dishCategories;
	ImageView imageRest, imageRestLogo;
	TextView restaurantName, actionBarTitle, tableno;
	Bitmap RestImageRounded;
	Integer image = R.drawable.rest_two;
	Integer selectedCategory;
	ImageView basket, table;
	int userType,pos;
	ListView menuList;
	Bitmap resultImage;
	private String tag_string_categories_req = "string_categories_req";
	ArrayList<RestaurantDomain> restaurantDetails;
	ArrayList<DishCategoriesDomain> DishCategories= new ArrayList<DishCategoriesDomain>();
	ArrayList<DishCategoriesDomain> categoriesList;
	String restURL;
	public String ns,RestaurantID;
	int restId;
	
	public ActivityRestaurantMenu() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.restaurant_main_menu);

		Intent intent = getIntent();
		userType = intent.getIntExtra("userType", 0);
		pos = intent.getIntExtra("position",0);

		restaurantDetails = PublicValues.allnearbyRestaurants;
		
		RestaurantID = String.valueOf(restaurantDetails.get(pos).getId());
		//Toast.makeText(getApplicationContext(),"Id: "+n,
				//Toast.LENGTH_LONG).show();
		restId = restaurantDetails.get(pos).getId();
		
        ns = Const.URL_DISH_CATEGORIES+String.valueOf(restaurantDetails.get(pos).getId());
		
	/*	Toast.makeText(getApplicationContext(),"Url: "+ns,
				Toast.LENGTH_LONG).show();
		*/
		
		restaurantDetails.get(pos).getId();
		
		Internet net = new Internet(getApplicationContext());
		boolean netstatus = net.isAvailable();
		if (netstatus != false) {
			
			makeStringReq(ns);
		}
		 else {
				Toast.makeText(getApplicationContext(), "Please check network connection!",
						Toast.LENGTH_SHORT).show();
			
		 		}
		
		menuList = (ListView) findViewById(R.id.listview);
        
		String fontPath = "fonts/LaoUI.ttf";
		String fontPathBold = "fonts/LaoUIb.ttf";

		// Loading Font Face
		Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
		Typeface tfb = Typeface.createFromAsset(getAssets(), fontPathBold);

		
		setBasketVisibility();
		
		/*
		if (userType == 0) {
			updateTableNumber("23");

		}*/

	    imageRest = (ImageView) findViewById(R.id.imageRest);
		imageRestLogo = (ImageView) findViewById(R.id.img_RestaurantLogo);

		setActionBarTitle(restaurantDetails.get(pos).getName());
		
        categoriesList =PublicValues.allDishCategories;
		
		
		new DownloadImage(imageRestLogo).execute(Const.RestLogoImageUrl
				+ restaurantDetails.get(pos).getRestaurantlogo());

		new DownloadImage(imageRest).execute(Const.RestImageUrl
				+ restaurantDetails.get(pos).getRestaurantImage());

		
		Bitmap icon = BitmapFactory.decodeResource(getApplicationContext()
				.getResources(), image);
		ImageHelper img = new ImageHelper();

		
		
		menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				String selection = menuList.getItemAtPosition(position)
						.toString();
				/*Toast.makeText(getApplicationContext(), selection,
						Toast.LENGTH_LONG).show();
*/
				Intent myIntent = new Intent(getApplicationContext(),
						ActivityRestaurantMenuListTab.class);
				myIntent.putStringArrayListExtra("categoryArray",
						dishCategories);
				myIntent.putExtra("category", selectedCategory);
				myIntent.putExtra("position", position);
				myIntent.putExtra("Restposition", pos);
				myIntent.putExtra("userType", userType);
				myIntent.putExtra("restId", RestaurantID);
				
				startActivity(myIntent);
			}

		});

	}
	
	
	/**
	 * Making json object request
	 * */
	private void makeStringReq(String url) {
		
		StringRequest strReq = new StringRequest(Method.GET,
				ns, new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						Log.d("Retrieving dish categories", response.toString());
						PublicValues.dishResponse=response;
						try {
							readCategoriesJson(response.toString());
							/*pDialog.dismiss();*/
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d("Retrieving dish categories error", "Error: " + error.getMessage());
						/*pDialog.dismiss();*/
					}
				});

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(strReq, tag_string_categories_req);

	}


	protected void readCategoriesJson(String response) throws ParseException {
		
		try 
		 {
			JSONObject jsonObj = new JSONObject(response);
			
			JSONArray itemarray=jsonObj.getJSONArray("categories");
		    Log.d("Inside item json", ""+itemarray);
			
			for(int i=0;i<itemarray.length();i++)
			{
				
				JSONObject Jsonitem = itemarray.getJSONObject(i);
				
				DishCategoriesDomain categories=new DishCategoriesDomain();
				
				categories.setID(Jsonitem.getInt("id"));
				categories.setRestaurantName(Jsonitem.getString("categoryName"));
			    
				DishCategories.add(categories);
			}
			
		} 
		 catch (JSONException e) 
		 {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PublicValues.allDishCategories=DishCategories;
		//Toast.makeText(getApplicationContext(), "no:"+DishCategories.size(), Toast.LENGTH_SHORT).show();
		Log.i("table data", DishCategories+"");
		
		AdapterDishCategories dishCategoriesAdapter = new AdapterDishCategories(ActivityRestaurantMenu.this,DishCategories);
		menuList.setAdapter(dishCategoriesAdapter);
	
	}
	
	// DownloadImage AsyncTask
			private class DownloadImage extends AsyncTask<String, Void, Bitmap> {
		 
				//ProgressDialog mProgressDialog;
				ImageView bmImage;

				public DownloadImage(ImageView bmImage) {
					this.bmImage = bmImage;
				}

				
				public DownloadImage() {
					// TODO Auto-generated constructor stub
				}


				@Override
				protected void onPreExecute() {
					super.onPreExecute();
					// Create a progressdialog
					/*mProgressDialog = new ProgressDialog(ActivityRestaurantMenu.this);
					
					// Set progressdialog message
					mProgressDialog.setMessage("Please wait while loading...");
					mProgressDialog.setIcon(R.drawable.loading);
					mProgressDialog.setIndeterminate(false);
					// Show progressdialog
					mProgressDialog.show();*/
					
				}
		 
				@Override
				protected Bitmap doInBackground(String... URL) {
		 
					String imageURL = URL[0];
		 
					Bitmap bitmap = null;
					try {
						// Download Image from URL
						InputStream input = new java.net.URL(imageURL).openStream();
						// Decode Bitmap
						bitmap = BitmapFactory.decodeStream(input);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return bitmap;
				}
		 
				@Override
				protected void onPostExecute(Bitmap result) {
					// Set the bitmap into ImageView
					
					
					bmImage.setImageBitmap(result);
					resultImage = result;
					
					// Close progressdialog
				//	mProgressDialog.dismiss();
					
				}
			}
			
			
}
