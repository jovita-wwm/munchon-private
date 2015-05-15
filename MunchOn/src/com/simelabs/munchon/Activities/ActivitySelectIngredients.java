package com.simelabs.munchon.Activities;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.SyncStateContract.Helpers;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockListActivity;
import com.simelabs.munchon.R;
import com.simelabs.munchon.Adapters.AdapterSetDishQuantity;
import com.simelabs.munchon.Adapters.CustomListAdapterDialog;
import com.simelabs.munchon.Domain.DishesDomain;
import com.simelabs.munchon.Domain.ImageHelper;
import com.simelabs.munchon.Domain.PublicValues;
import com.simelabs.munchon.Domain.RestaurantDomain;

public class ActivitySelectIngredients extends SherlockActivity {

	EditText editTextInstructions;
	ListView list;
	Typeface tf, tfb;

	ImageView DishImage, quantityDropDown;

	TextView DishName, LabelSelectQuantity, SelectQuantity, LabelPrice,
			ShowPrice, buttonAddToBasket, buttonCheckOut;

	TextView tableno, basketitems, actionBarTitle; // action bar
	ImageView back, table, basket; // action bar
	Bitmap DishImageRounded;

	Dialog changeIngredient, changeQuantity;
	double IngredientPriceSum = 0;
	double ParameterPrice = 0;
	double total = 0;
	double totalPrice = 0;
	Integer Quantity=0;
	Integer Items=0;
	
	String nos;
	ArrayList<String> DishQuantityArray;
	Integer image = R.drawable.dishone;
	String RestaurantName = "Slide Lounge";
	String Dish_Name = "Mini Mac & Cheese Bites";

	ArrayList<String> ParameterNameArray, ParameterTypeNameArray,
			IngredientNameArray;
	ArrayList<Double> IngredientPriceArray;
	
	int userType,pos;
	String restId;
	ArrayList<RestaurantDomain> restaurantDetails;
	ArrayList<DishesDomain> dishDetails;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.restaurants_select_ingredients);

		Intent userIntent = getIntent();
		userType = userIntent.getIntExtra("userType", 0);
		pos = userIntent.getIntExtra("position", 0);
		restId = userIntent.getStringExtra("restId");
		
		restaurantDetails = PublicValues.allnearbyRestaurants;
		dishDetails = PublicValues.allDishes;
		
		ActionBar actionBar = getSupportActionBar();

		String fontPath = "fonts/LaoUI.ttf";
		String fontPathBold = "fonts/LaoUIb.ttf";

		// Loading Font Face
		tf = Typeface.createFromAsset(getAssets(), fontPath);
		tfb = Typeface.createFromAsset(getAssets(), fontPathBold);

		/*
		 * Actionbar
		 */

		View actionBarView = getLayoutInflater().inflate(
				R.layout.custom_actionbar_restaurant, null);

		back = (ImageView) actionBarView.findViewById(R.id.btn_back);
		table = (ImageView) actionBarView.findViewById(R.id.btn_table);
		basket = (ImageView) actionBarView.findViewById(R.id.btn_basket);
		tableno = (TextView) actionBarView.findViewById(R.id.txt_table_no);
		basketitems = (TextView) actionBarView
				.findViewById(R.id.txt_basket_items);
		actionBarTitle = (TextView) actionBarView.findViewById(R.id.txt_title);

		basket.setVisibility(View.VISIBLE);

		actionBarTitle.setText(restaurantDetails.get(pos).getName());
		actionBarTitle.setTypeface(tfb);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				finish();
			}
		});

		actionBar.setCustomView(actionBarView);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

		editTextInstructions = (EditText) findViewById(R.id.edt_instructions);
		DishImage = (ImageView) findViewById(R.id.img_dish);
		DishName = (TextView) findViewById(R.id.txt_dishName);
		LabelSelectQuantity = (TextView) findViewById(R.id.txt_label_selectQuantity);
		SelectQuantity = (TextView) findViewById(R.id.txt_quantity);
		LabelPrice = (TextView) findViewById(R.id.txt_label_price);
		ShowPrice = (TextView) findViewById(R.id.txt_price);
		buttonAddToBasket = (TextView) findViewById(R.id.btn_add_to_basket);
		buttonCheckOut = (TextView) findViewById(R.id.btn_checkout);
		quantityDropDown = (ImageView) findViewById(R.id.img_dropDown);

		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		Bitmap icon = BitmapFactory.decodeResource(getApplicationContext()
				.getResources(), image);
		ImageHelper image = new ImageHelper();

		DishImageRounded = image.getRoundedCornerBitmap(icon, 50);

		DishImage.setImageBitmap(DishImageRounded);

		DishName.setText(dishDetails.get(pos).getDishName());
		DishName.setTypeface(tfb);
		LabelSelectQuantity.setTypeface(tf);
		SelectQuantity.setTypeface(tf);
		LabelPrice.setTypeface(tf);
		ShowPrice.setTypeface(tf);
		editTextInstructions.setTypeface(tf);
		buttonAddToBasket.setTypeface(tfb);
		buttonCheckOut.setTypeface(tfb);

		ParameterNameArray = new ArrayList<String>();
		ParameterTypeNameArray = new ArrayList<String>();
		IngredientNameArray = new ArrayList<String>();
		IngredientPriceArray = new ArrayList<Double>();
		DishQuantityArray = new ArrayList<String>();

		ParameterNameArray.add("Parameter 1");
		ParameterNameArray.add("Parameter 2");
		ParameterNameArray.add("Parameter 3");
		ParameterNameArray.add("Parameter 4");

		ParameterTypeNameArray.add("Chilli");
		ParameterTypeNameArray.add("Tomato");
		ParameterTypeNameArray.add("Corn");
		ParameterTypeNameArray.add("Flakes");

		IngredientNameArray.add("Roquefort");
		IngredientNameArray.add("Camembert");
		IngredientNameArray.add("Cotija");
		IngredientNameArray.add("Feta");
		IngredientNameArray.add("Mozzarella");
		IngredientNameArray.add("Emmental");

		IngredientPriceArray.add(1.4);
		IngredientPriceArray.add(3.4);
		IngredientPriceArray.add(1.5);
		IngredientPriceArray.add(1.2);
		IngredientPriceArray.add(5.4);
		IngredientPriceArray.add(1.0);

		DishQuantityArray.add("1");
		DishQuantityArray.add("2");
		DishQuantityArray.add("3");
		DishQuantityArray.add("4");
		DishQuantityArray.add("5");
		DishQuantityArray.add("6");
		DishQuantityArray.add("7");
		DishQuantityArray.add("8");
		DishQuantityArray.add("9");

		int ParameterCount = dishDetails.get(pos).getDishParameterCount();
		
		
		
		/***
		 * adding item into listview
		 */
		for (int i = 0; i < ParameterCount; i++) {
			/**
			 * inflate items/ add items in linear layout instead of listview
			 */

			LinearLayout parametersLayout = (LinearLayout) findViewById(R.id.layout_parameters);

			LayoutInflater inflater = LayoutInflater
					.from(getApplicationContext());
			View inflatedLayout = inflater.inflate(
					R.layout.layout_parameter_listitem, null, false);

			/**
			 * getting id of row.xml
			 */
			TextView LabelParameter = (TextView) inflatedLayout
					.findViewById(R.id.txt_parameter);    //NAme
			LabelParameter.setTypeface(tf);
			final TextView ParameterName = (TextView) inflatedLayout
					.findViewById(R.id.txt_parameterName);   //Types
			ParameterName.setTypeface(tf);
			ImageView dropDown = (ImageView) inflatedLayout
					.findViewById(R.id.img_dropDown);

			/**
			 * set item into row
			 */
			
			
			
			final String ParTypeName = ParameterTypeNameArray.get(i);
			final String ParName = ParameterNameArray.get(i);

			ParameterName.setText(ParTypeName);
			LabelParameter.setText(dishDetails.get(i).getParameterName());

			/**
			 * add view in top linear
			 */

			parametersLayout.addView(inflatedLayout);

			dropDown.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					changeIngredient = new Dialog(
							ActivitySelectIngredients.this);
					changeIngredient
							.requestWindowFeature(Window.FEATURE_NO_TITLE);
					View view = getLayoutInflater().inflate(
							R.layout.dialog_select_ingredients, null);

					final ListView lv = (ListView) view
							.findViewById(R.id.listView1);

					TextView DishName = (TextView) view
							.findViewById(R.id.txt_DishName);
					DishName.setText(Dish_Name);
					TextView DishPrice = (TextView) view
							.findViewById(R.id.txt_ParameterName);
					DishPrice.setText(ParTypeName);
					DishName.setTypeface(tfb);
					DishPrice.setTypeface(tf);

					final CustomListAdapterDialog clad = new CustomListAdapterDialog(
							ActivitySelectIngredients.this,
							IngredientNameArray, IngredientPriceArray, tf, tfb,
							changeIngredient);

					lv.setAdapter(clad);

					lv.setOnItemClickListener(new OnItemClickListener() {
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {

							TextView txtIngredientName = (TextView) view
									.findViewById(R.id.txt_IngredientName);
							TextView txtIngredientPrice = (TextView) view
									.findViewById(R.id.txt_IngredientPrice);

							String IngredientName = txtIngredientName.getText()
									.toString();
							String IngredientPrice = txtIngredientPrice
									.getText().toString();
							ParameterName.setText(IngredientName);
							
							String price = IngredientPrice.replace("$", "");
							double value = Double.parseDouble(price);
							calculateIngredientPrice(value);

							changeIngredient.dismiss();

						}

					});

					changeIngredient.getWindow().setBackgroundDrawable(
							new ColorDrawable(Color.argb(0, 0, 0, 0)));
					changeIngredient.setContentView(view);

					changeIngredient.show();

					ImageView close_button = (ImageView) view
							.findViewById(R.id.button_close);
					close_button.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							changeIngredient.dismiss();
						}
					});

				}
			});

			quantityDropDown.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					changeQuantity = new Dialog(ActivitySelectIngredients.this);
					changeQuantity
							.requestWindowFeature(Window.FEATURE_NO_TITLE);
					View view = getLayoutInflater().inflate(
							R.layout.dialog_change_quantity, null);

					final ListView lv = (ListView) view
							.findViewById(R.id.QuantityList);

					TextView DishName = (TextView) view
							.findViewById(R.id.txtDishName);
					DishName.setText(Dish_Name);
					TextView QuantityLabel = (TextView) view
							.findViewById(R.id.txtQuantityLabel);
					QuantityLabel.setText("Quantity");
					DishName.setTypeface(tfb);
					QuantityLabel.setTypeface(tf);

					final AdapterSetDishQuantity clad = new AdapterSetDishQuantity(
							ActivitySelectIngredients.this, DishQuantityArray,
							tf, tfb);

					lv.setAdapter(clad);

					lv.setOnItemClickListener(new OnItemClickListener() {
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {

							TextView txtDishQuantity = (TextView) view
									.findViewById(R.id.txt_DishQuantity);

							nos = txtDishQuantity.getText().toString();
							Quantity = Integer.parseInt(nos);

							calculateTotalPrice(Quantity);
							SelectQuantity.setText(nos);

							changeQuantity.dismiss();

						}

					});

					changeQuantity.getWindow().setBackgroundDrawable(
							new ColorDrawable(Color.argb(0, 0, 0, 0)));
					changeQuantity.setContentView(view);

					changeQuantity.show();

					ImageView close_button = (ImageView) view
							.findViewById(R.id.button_close);
					close_button.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							changeQuantity.dismiss();
						}
					});

				}
			});

		}
		
		buttonCheckOut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(getApplicationContext(),
						ActivityLoginScreen.class);
				
				startActivity(myIntent);

			}
		});
		
		buttonAddToBasket.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				

				Editable instructions = editTextInstructions.getText();
				Toast.makeText(getApplicationContext(), instructions.toString(), Toast.LENGTH_SHORT).show();
				changeBasketNumber();
			}
		});

	}

	protected void changeBasketNumber() {
		// TODO Auto-generated method stub
		if(Quantity>0)
		{
			basketitems.setVisibility(View.VISIBLE);
			Items = Quantity;
			basketitems.setText(Items.toString());
		}
		
		
	}

	public void calculateIngredientPrice(double value) {
		// TODO Auto-generated method stub
		ParameterPrice = ParameterPrice + value;
		IngredientPriceSum = IngredientPriceSum + ParameterPrice;
		/*Toast.makeText(
				getApplicationContext(),
				"Total ingredient price is : "
						+ String.valueOf(IngredientPriceSum),
				Toast.LENGTH_SHORT).show();*/

	}

	public void calculateTotalPrice(int quantity) {
		// TODO Auto-generated method stub
		Double serviceCharge = 1.30;
		total = IngredientPriceSum * quantity;
		totalPrice = Math.round(total * 100.0) / 100.0;

		/*Toast.makeText(getApplicationContext(),
				"Total ingredient price is : " + String.valueOf(totalPrice),
				Toast.LENGTH_SHORT).show();*/

		ShowPrice.setText("$" + String.valueOf(totalPrice));
	}

	
}
