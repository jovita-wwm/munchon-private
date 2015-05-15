package com.simelabs.munchon.Adapters;

import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.simelabs.munchon.R;

import com.simelabs.munchon.Domain.DishCategoriesDomain;
import com.simelabs.munchon.Domain.DishesDomain;
import com.simelabs.munchon.Domain.ImageHelper;

public class AdapterDishesTab extends BaseAdapter

{
	private final Activity context;
	ArrayList<DishesDomain> dishes = new ArrayList<DishesDomain>();
	ViewHolder h;
	
	public AdapterDishesTab(Activity context, ArrayList<DishesDomain> dishList) {

		this.context = context;
		this.dishes = dishList;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dishes.size();
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
		Typeface fontBold = Typeface.createFromAsset(context.getAssets(), "LaoUIb.ttf");
		
		View v = convertView;
		
		if (v == null) {
			v = inflater.inflate(R.layout.restaurant_menu_list_item, parent,
					false);
			h = new ViewHolder();
			h.DishName = (TextView) v.findViewById(R.id.textDishName);
			h.DishDescription = (TextView) v
					.findViewById(R.id.textDishDescription);
			h.DishPrice = (TextView) v.findViewById(R.id.textDishPrice);
			h.AddButton = (TextView) v.findViewById(R.id.btn_add);

			h.ImageDish = (ImageView) v.findViewById(R.id.imageDish);
			h.DishCount = (TextView) v.findViewById(R.id.textDishCount);
			h.AddButton = (TextView) v.findViewById(R.id.btn_add);
			h.PropertyChilly = (ImageView) v.findViewById(R.id.img_Prop_Chilly);
			h.PropertyVegan = (ImageView) v.findViewById(R.id.img_Prop_Vegan);
			h.PropertySeafood = (ImageView) v.findViewById(R.id.img_Prop_Seafood);
			h.PropertyVeg = (ImageView) v.findViewById(R.id.img_Prop_Veg);
			h.PropertyNutFree = (ImageView) v.findViewById(R.id.img_Prop_Nutfree);
			h.PropertyGlutenFree = (ImageView) v.findViewById(R.id.img_Prop_Glutenfree);

			v.setTag(h);
		} else {
			h = (ViewHolder) v.getTag();
		}

		h.DishName.setTypeface(fontBold);
		h.DishDescription.setTypeface(font);
		h.DishPrice.setTypeface(fontBold);
		h.AddButton.setTypeface(font);
		h.DishCount.setTypeface(font);
		
		
		
		if((dishes.get(position).getDishPropertyChilly())==1)
		{
			h.PropertyChilly.setVisibility(View.VISIBLE);
			h.PropertyChilly.setImageResource(R.drawable.icon_chilly);
		}
		if((dishes.get(position).getDishPropertyglutenFree())==1)
		{
			h.PropertyGlutenFree.setVisibility(View.VISIBLE);
			h.PropertyGlutenFree.setImageResource(R.drawable.icon_glutenfree);
		}
		if((dishes.get(position).getDishPropertySeaFood())==1)
		{
			h.PropertySeafood.setVisibility(View.VISIBLE);
			h.PropertySeafood.setImageResource(R.drawable.icon_seafood);
		}
		if((dishes.get(position).getDishPropertyVegan())==1)
		{
			h.PropertyVegan.setVisibility(View.VISIBLE);
			h.PropertyVegan.setImageResource(R.drawable.icon_vegan);
		}
		if((dishes.get(position).getDishPropertyVeg())==1)
		{
			h.PropertyVeg.setVisibility(View.VISIBLE);
			h.PropertyVeg.setImageResource(R.drawable.icon_veg);
		}
		if((dishes.get(position).getDishPropertyNutFree())==1)
		{
			h.PropertyNutFree.setVisibility(View.VISIBLE);
			h.PropertyNutFree.setImageResource(R.drawable.icon_nutfree);
		}
		
		h.DishName.setText(dishes.get(position).getDishName());
		h.DishDescription.setText(dishes.get(position).getDishDescription());
		h.DishPrice.setText("$" + dishes.get(position).getPrice());
		//h.ImageDish.setImageBitmap(dish);

		if (h.AddButton.getTag() == null) {
			h.AddButton.setTag(0);
		}

		String imageurl = "https://s3-us-west-2.amazonaws.com/elasticbeanstalk-us-west-2-060425629624/munchon/gallery/dishes/";

		new DownloadImageTask(h.ImageDish).execute(imageurl
				+ dishes.get(position).getDishImage());

		return v;
	}
	
	/** 
	 * to stop recycling of listview items
	 */
	@Override
	public int getViewTypeCount() {
		return getCount();
	}

	/** 
	 * to stop recycling of listview items
	 */
	@Override
	public int getItemViewType(int position) {
		return position;
	}
	
	private static class ViewHolder{
		TextView DishName,DishDescription,DishPrice,AddButton,DishCount;
		ImageView ImageDish,PropertyChilly,PropertyGlutenFree,PropertyNutFree,PropertyVegan,PropertySeafood,PropertyVeg;
		
		}
	
	public ImageLoader imageLoader;

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;


		
		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
			
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			
			ImageHelper i = new ImageHelper();
			Bitmap b = i.getRoundedCornerBitmap(result, 50);
			
			bmImage.setImageBitmap(b);
			
		}
	}

}