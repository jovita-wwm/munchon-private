package com.simelabs.munchon.Activities;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.simelabs.munchon.R;
import com.simelabs.munchon.Adapters.AdapterDishesTab;
import com.simelabs.munchon.Adapters.RestaurantSubMenuListAdapter;
import com.simelabs.munchon.DB.AppController;
import com.simelabs.munchon.DB.Const;
import com.simelabs.munchon.Domain.DishCategoriesDomain;
import com.simelabs.munchon.Domain.DishesDomain;
import com.simelabs.munchon.Domain.ImageHelper;
import com.simelabs.munchon.Domain.PublicValues;
import com.simelabs.munchon.Network.Internet;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ActivityDishCategory extends ListActivity {
	Integer[] DishImageArray = { R.drawable.dish, R.drawable.dishone,
			R.drawable.dishtwo, R.drawable.dishthree };
	String[] DishNameArray = { "Mini Mac & Cheese Bites", "Pizza Italian",
			"Burger & Fries", "Schezuan fish dry" };
	String[] DishDescriptionArray = {
			"Made a first batch substituting gruyere for the American cheese and doubled the paprika and still found them to be pretty bland.",
			"Made a first batch substituting gruyere for the American cheese and doubled the paprika and still found them to be pretty bland.",
			"Made a first batch substituting gruyere for the American cheese and doubled the paprika and still found them to be pretty bland.",
			"Made a first batch substituting gruyere for the American cheese and doubled the paprika and still found them to be pretty bland." };
	String[] DishPriceArray = { "8.00", "1.00", "2.50", "1.50" };

	ArrayList<Bitmap> DishImagesBitmapArray;
	Bitmap DishImagesRounded;
	public static Integer addToBasketCount = 0;
	TextView DishCount;
	ListView list;
	int totalcount;
	ArrayList<Integer> counter;
	ActivityRestaurantMenuListTab obj;
	int clickCount;
	Typeface tf, tfb;

	int userType, Itemposition;
	String restId;
	public static ArrayList<DishesDomain> DishList = new ArrayList<DishesDomain>();
	ArrayList<DishCategoriesDomain> DishCategories;
	ArrayList<DishesDomain> allDishes;
	ArrayList<DishesDomain> Dishes;
	String URL;
	
	private String tag_string_categories_req = "tag_string_dish_req";
	String ParameterOne,ParameterTwo,ParameterThree,ParameterFour,ParameterFive;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.restaurant_submenu);

		String fontPath = "fonts/LaoUI.ttf";
		String fontPathBold = "fonts/LaoUIb.ttf";

		Intent intent = getIntent();
		userType = intent.getIntExtra("userType", 0);
		Itemposition = intent.getIntExtra("position", 0);
		restId = intent.getStringExtra("restId");

		DishCategories = PublicValues.allDishCategories;

		String n = String.valueOf(DishCategories.get(Itemposition).getID());
		// URL = Const.URL_DISH_LIST +
		// String.valueOf(DishCategories.get(position).getID()) +
		// String.valueOf(restId);
		URL = Const.URL_DISH_LIST + restId + "/" + n;

		Internet net = new Internet(getApplicationContext());
		boolean netstatus = net.isAvailable();
		if (netstatus != false) {

			makeStringReq(URL);
		} else {
			Toast.makeText(getApplicationContext(),
					"Please check network connection!", Toast.LENGTH_SHORT)
					.show();

		}

		Dishes = PublicValues.allDishes;

		// Loading Font Face
		tf = Typeface.createFromAsset(getAssets(), fontPath);
		tfb = Typeface.createFromAsset(getAssets(), fontPathBold);

		counter = new ArrayList<Integer>();

		obj = new ActivityRestaurantMenuListTab();

		DishImagesBitmapArray = new ArrayList<Bitmap>();
		for (Integer i = 0; i < DishImageArray.length; i++) {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 2;

			Bitmap icon = BitmapFactory.decodeResource(getApplicationContext()
					.getResources(), DishImageArray[i], options);
			ImageHelper image = new ImageHelper();
			DishImagesRounded = image.getRoundedCornerBitmap(icon, 50);

			DishImagesBitmapArray.add(DishImagesRounded);

			/*
			 * RestaurantSubMenuListAdapter adapter = new
			 * RestaurantSubMenuListAdapter( this, DishImagesBitmapArray,
			 * DishNameArray, DishDescriptionArray, DishPriceArray, tf, tfb);
			 * setListAdapter(adapter);
			 */

			list = getListView();
			list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						final int position, long id) {
					// TODO Auto-generated method stub

					// parent.getItemAtPosition(position);

					final TextView add = (TextView) view
							.findViewById(R.id.btn_add);

					DishCount = (TextView) view
							.findViewById(R.id.textDishCount);

					((TextView) view.findViewById(R.id.btn_add))
							.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {

									addToBasketCount++;
									
									Toast.makeText(getApplicationContext(), String.valueOf(DishList.get(position)
											.getDishParameterCount()), Toast.LENGTH_SHORT).show();
									
									if (DishList.get(position)
											.getDishParameterCount() > 0) {
										
										Intent myIntent = new Intent(getApplicationContext(),
											ActivitySelectIngredients.class);
										myIntent.putExtra("userType", 0);
										myIntent.putExtra("BasketCount",addToBasketCount);
										myIntent.putExtra("position", Itemposition);
										startActivity(myIntent);

									} else
									{
										Object cc = add.getTag();
										int id = Integer.parseInt(cc.toString());
										clickCount = id + 1;
										add.setTag(clickCount);

										updateView(position);

										Toast.makeText(
												ActivityDishCategory.this,
												"Added to Basket "
														+ String.valueOf(clickCount)
														+ " at "
														+ String.valueOf(position),
												Toast.LENGTH_SHORT).show();

										ActivityRestaurantMenuListTab
												.addBasketItems(addToBasketCount);


									}

									
									
								}

							});

				}

			});

		}

	}

	/**
	 * Making json object request
	 * */
	private void makeStringReq(String url) {

		StringRequest strReq = new StringRequest(Method.GET, url,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						Log.d("Retrieving dish list", response.toString());
						PublicValues.dishListResponse = response;
						try {
							readCategoriesJson(response.toString());

						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d("Retrieving dish list error", "Error: "
								+ error.getMessage());

					}
				});

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(strReq,
				tag_string_categories_req);

	}

	protected void readCategoriesJson(String response) throws ParseException {

		try {
			JSONObject jsonObj = new JSONObject(response);

			JSONArray itemarray = jsonObj.getJSONArray("dishes");
			Log.d("Inside item json", "" + itemarray);

			for (int i = 0; i < itemarray.length(); i++) {

				JSONObject Jsonitem = itemarray.getJSONObject(i);

				DishesDomain dishes = new DishesDomain();

				dishes.setDishName(Jsonitem.getString("dishName"));
				dishes.setDishImage(Jsonitem.getString("dishImage"));
				dishes.setDishDescription(Jsonitem.getString("description"));
				dishes.setCategoryName(Jsonitem.getString("categoryName"));
				dishes.setUnit(Jsonitem.getString("unit"));

				dishes.setPrice(Jsonitem.getDouble("price"));
				dishes.setDiscount(Jsonitem.getDouble("discount"));
				dishes.setRating(Jsonitem.getString("rating"));

				dishes.setCategoryId(Jsonitem.getInt("categoryId"));
				dishes.setDishID(Jsonitem.getInt("dishId"));
				dishes.setRestaurantId(Jsonitem.getInt("restaurantId"));
				dishes.setCalorieAmount(Jsonitem.getInt("calorieAmount"));
				dishes.setCookingTime(Jsonitem.getInt("cookingTime"));
				dishes.setStatus(Jsonitem.getInt("status"));
				dishes.setDishParameterCount(Jsonitem.getInt("dishParameterCount"));
				dishes.setDishPropertyChilly(Jsonitem
						.getInt("dishPropertyChilly"));
				dishes.setDishPropertyVegan(Jsonitem
						.getInt("dishPropertyVegan"));
				dishes.setDishPropertyglutenFree(Jsonitem
						.getInt("dishPropertyglutenFree"));
				dishes.setDishPropertySeaFood(Jsonitem
						.getInt("dishPropertyglutenFree"));
				dishes.setDishPropertyVeg(Jsonitem.getInt("dishPropertyVeg"));
				dishes.setDishPropertyNutFree(Jsonitem
						.getInt("dishPropertyNutFree"));

				
				JSONObject dishParam = Jsonitem.getJSONObject("dishParameters");
				
				String parameters1 = dishParam.getString("dishParameter1");
				String parameters2 = dishParam.getString("dishParameter2");
				String parameters3 = dishParam.getString("dishParameter3");
				String parameters4 = dishParam.getString("dishParameter4");
				String parameters5 = dishParam.getString("dishParameter5");
				
				Log.d("Parameters 1.....", "" + parameters1);
				Log.d("Parameters 2.....", "" + parameters2);
				Log.d("Parameters 3 .....", "" + parameters3);
				Log.d("Parameters 4 .....", "" + parameters4);
				Log.d("Parameters 5 .....", "" + parameters5);
				
				JSONObject jsonObjp1 = new JSONObject(parameters1);
				JSONObject jsonObjp2 = new JSONObject(parameters2);
				JSONObject jsonObjp3 = new JSONObject(parameters3);
				JSONObject jsonObjp4= new JSONObject(parameters4);
				JSONObject jsonObjp5 = new JSONObject(parameters5);
				
				Toast.makeText(getApplicationContext(),jsonObjp1.getString("parameter1")
						+jsonObjp2.getString("parameter2")
						+jsonObjp3.getString("parameter3")
						+jsonObjp4.getString("parameter4")
						+jsonObjp5.getString("parameter5") , Toast.LENGTH_SHORT).show();
				
				
				dishes.setParameterName(jsonObjp1.getString("parameter1"));
				dishes.setParameterName(jsonObjp2.getString("parameter2"));
				dishes.setParameterName(jsonObjp3.getString("parameter3"));
				dishes.setParameterName(jsonObjp4.getString("parameter4"));
				dishes.setParameterName(jsonObjp5.getString("parameter5"));
				
				/*JSONObject jsonObjp1 = new JSONObject(parameters1);
				JSONObject jsonObjp2 = new JSONObject(parameters2);
				JSONObject jsonObjp3 = new JSONObject(parameters3);
				JSONObject jsonObjp4 = new JSONObject(parameters4);
				JSONObject jsonObjp5 = new JSONObject(parameters5);
				
				 
				dishes.setParameterOneName(jsonObjp1.getString("parameter1"));
				dishes.setParameterOneName(jsonObjp2.getString("parameter2"));
				dishes.setParameterOneName(jsonObjp3.getString("parameter3"));
				dishes.setParameterOneName(jsonObjp4.getString("parameter4"));
				dishes.setParameterOneName(jsonObjp5.getString("parameter5"));
				*/
				/*dishes.setParameterOneName(jsonObjp2.getString("parameter2"));
				dishes.setParameterOneName(jsonObjp3.getString("parameter3"));
				dishes.setParameterOneName(jsonObjp4.getString("parameter4"));
				dishes.setParameterOneName(jsonObjp5.getString("parameter5"));
				*/
				DishList.add(dishes);
				
				/*Toast.makeText(getApplicationContext(), parameters1+"............"+
						parameters2
						+"............"+parameters3
						+"............"+parameters4
						+"............"+parameters5, Toast.LENGTH_SHORT).show();*/
				
				/*String ParameterOneOption1 = jsonObjp1.getString("parameter1_option1");
				String ParameterOneOption2 = jsonObjp1.getString("parameter1_option2");
				String ParameterOneOption3 = jsonObjp1.getString("parameter1_option3");
				String ParameterOneOption4 = jsonObjp1.getString("parameter1_option4");
				String ParameterOneOption5 = jsonObjp1.getString("parameter1_option5");
				
				int ParameterOneOption1Price = jsonObjp1.getInt("parameter1_option1_price");
				int ParameterOneOption2Price = jsonObjp1.getInt("parameter1_option2_price");
				int ParameterOneOption3Price = jsonObjp1.getInt("parameter1_option3_price");
				int ParameterOneOption4Price = jsonObjp1.getInt("parameter1_option4_price");
				int ParameterOneOption5Price = jsonObjp1.getInt("parameter1_option5_price");
				
				
				
				ParameterTwo = jsonObjp2.getString("parameter2");
				
				String ParameterTwoOption1 = jsonObjp2.getString("parameter2_option1");
				String ParameterTwoOption2 = jsonObjp2.getString("parameter2_option2");
				String ParameterTwoOption3 = jsonObjp2.getString("parameter2_option3");
				String ParameterTwoOption4 = jsonObjp2.getString("parameter2_option4");
				String ParameterTwoOption5 = jsonObjp2.getString("parameter2_option5");
				
				int ParameterTwoOption1Price = jsonObjp2.getInt("parameter2_option1_price");
				int ParameterTwoOption2Price = jsonObjp2.getInt("parameter2_option2_price");
				int ParameterTwoOption3Price = jsonObjp2.getInt("parameter2_option3_price");
				int ParameterTwoOption4Price = jsonObjp2.getInt("parameter2_option4_price");
				int ParameterTwoOption5Price = jsonObjp2.getInt("parameter2_option5_price");
				
				
				
				
				ParameterThree = jsonObjp3.getString("parameter3");
				
				String ParameterThreeOption1 = jsonObjp3.getString("parameter3_option1");
				String ParameterThreeOption2 = jsonObjp3.getString("parameter3_option2");
				String ParameterThreeOption3 = jsonObjp3.getString("parameter3_option3");
				String ParameterThreeOption4 = jsonObjp3.getString("parameter3_option4");
				String ParameterThreeOption5 = jsonObjp3.getString("parameter3_option5");
				
				int ParameterThreeOption1Price = jsonObjp3.getInt("parameter3_option1_price");
				int ParameterThreeOption2Price = jsonObjp3.getInt("parameter3_option2_price");
				int ParameterThreeOption3Price = jsonObjp3.getInt("parameter3_option3_price");
				int ParameterThreeOption4Price = jsonObjp3.getInt("parameter3_option4_price");
				int ParameterThreeOption5Price = jsonObjp3.getInt("parameter3_option5_price");
				
				
				ParameterFour = jsonObjp4.getString("parameter4");
				
				String ParameterFourOption1 = jsonObjp4.getString("parameter4_option1");
				String ParameterFourOption2 = jsonObjp4.getString("parameter4_option2");
				String ParameterFourOption3 = jsonObjp4.getString("parameter4_option3");
				String ParameterFourOption4 = jsonObjp4.getString("parameter4_option4");
				String ParameterFourOption5 = jsonObjp4.getString("parameter4_option5");
				
				int ParameterFourOption1Price = jsonObjp4.getInt("parameter4_option1_price");
				int ParameterFourOption2Price = jsonObjp4.getInt("parameter4_option2_price");
				int ParameterFourOption3Price = jsonObjp4.getInt("parameter4_option3_price");
				int ParameterFourOption4Price = jsonObjp4.getInt("parameter4_option4_price");
				int ParameterFourOption5Price = jsonObjp4.getInt("parameter4_option5_price");
				
				
				ParameterFive = jsonObjp5.getString("parameter5");
				
				String ParameterFiveOption1 = jsonObjp5.getString("parameter5_option1");
				String ParameterFiveOption2 = jsonObjp5.getString("parameter5_option2");
				String ParameterFiveOption3 = jsonObjp5.getString("parameter5_option3");
				String ParameterFiveOption4 = jsonObjp5.getString("parameter5_option4");
				String ParameterFiveOption5 = jsonObjp5.getString("parameter5_option5");
				
				int ParameterFiveOption1Price = jsonObjp5.getInt("parameter5_option1_price");
				int ParameterFiveOption2Price = jsonObjp5.getInt("parameter5_option2_price");
				int ParameterFiveOption3Price = jsonObjp5.getInt("parameter5_option3_price");
				int ParameterFiveOption4Price = jsonObjp5.getInt("parameter5_option4_price");
				int ParameterFiveOption5Price = jsonObjp5.getInt("parameter5_option5_price");
				*/
				
				
				
				/*for (int j = 0; j < parameters.length(); j++) 
				{
					JSONObject parameterItems = parameters.getJSONObject(j);
					
					JSONArray arr = parameterItems.getJSONArray("dishParameter1");
					Log.d("Parameters 1.....", "" + arr);
					for (int k = 0; k < arr.length(); k++) 
					{

						JSONObject item = arr.getJSONObject(k);
						
						String ParameterOne = item.getString("parameter1");
						String ParameterOptionsOne = item.getString("parameter1_option1");
						String ParameterOptionsTwo = item.getString("parameter1_option2");
						String ParameterOptionsThree = item.getString("parameter1_option3");
						String ParameterOptionsFour = item.getString("parameter1_option4");
						String ParameterOptionsFive = item.getString("parameter1_option5");
						
						Double ParameterOptionsOnePrice = item.getDouble("parameter1_option1_price");
						Double ParameterOptionsTwoPrice = item.getDouble("parameter1_option2_price");
						Double ParameterOptionsThreePrice = item.getDouble("parameter1_option3_price");
						Double ParameterOptionsFourPrice = item.getDouble("parameter1_option4_price");
						Double ParameterOptionsFivePrice = item.getDouble("parameter1_option5_price");
						
						
					}
					
					String one = parameterItems.getString("dishParameter1");
					String two = parameterItems.getString("dishParameter2");
					String three = parameterItems.getString("dishParameter3");
					String four = parameterItems.getString("dishParameter4");
					String five = parameterItems.getString("dishParameter5");
					
				}*/
				
				
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PublicValues.allDishes = DishList;
		// Toast.makeText(getApplicationContext(), "no:"+DishCategories.size(),
		// Toast.LENGTH_SHORT).show();
		Log.i("table data", DishList + "");

		AdapterDishesTab dishCategoriesAdapter = new AdapterDishesTab(
				ActivityDishCategory.this, DishList);
		setListAdapter(dishCategoriesAdapter);

	}

	private void updateView(int index) {

		View v = list.getChildAt(index - list.getFirstVisiblePosition());

		if (v == null)
			return;

		TextView dishCount = (TextView) v.findViewById(R.id.textDishCount);
		dishCount.setVisibility(View.VISIBLE);
		dishCount.setTypeface(tf);
		dishCount.setText(String.valueOf(clickCount));
	}

}
