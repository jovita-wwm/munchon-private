package com.simelabs.munchon.Adapters;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.simelabs.munchon.R;
import com.simelabs.munchon.DB.Restaurants;
import com.simelabs.munchon.Domain.DishCategoriesDomain;
import com.simelabs.munchon.Domain.RestaurantDomain;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources.Theme;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AdapterDishCategories extends BaseAdapter

{
	private final Activity context;
	ArrayList<DishCategoriesDomain> dishCategories = new ArrayList<DishCategoriesDomain>();
	Integer count;
	ArrayList la;
	ArrayList bd;
    //ProgressDialog progressDialog;

	public AdapterDishCategories(Activity context,
			ArrayList<DishCategoriesDomain> categoriesList) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.dishCategories = categoriesList;
		
		/* progressDialog = new ProgressDialog(context);
         progressDialog.setMessage("Saving...");
         progressDialog.setCancelable(false);
         progressDialog.show();*/
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dishCategories.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = context.getLayoutInflater();
		
		Typeface font = Typeface.createFromAsset(context.getAssets(), "LaoUI.ttf");
		
		View rowView = inflater.inflate(
				R.layout.restaurant_main_menu_item, null, true);

		TextView categoryName = (TextView) rowView.findViewById(R.id.txt_category_name);
		
		categoryName.setText(dishCategories.get(position).getCategoryName());
		categoryName.setTypeface(font);
		
		//progressDialog.dismiss();
		return rowView;

		
	}

}