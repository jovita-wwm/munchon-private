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

import com.simelabs.munchon.Domain.DealsDomain;
import com.simelabs.munchon.Domain.DishCategoriesDomain;
import com.simelabs.munchon.Domain.DishesDomain;
import com.simelabs.munchon.Domain.ImageHelper;

public class DealsListAdapter extends BaseAdapter

{
	private final Activity context;
	ArrayList<DealsDomain> deals = new ArrayList<DealsDomain>();
	ViewHolder h;
	ImageHelper img;
	
	public DealsListAdapter(Activity context, ArrayList<DealsDomain> deals) {

		this.context = context;
		this.deals = deals;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return deals.size();
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

		img = new ImageHelper();
		
		Typeface font = Typeface.createFromAsset(context.getAssets(),
				"LaoUI.ttf");
		Typeface fontBold = Typeface.createFromAsset(context.getAssets(),
				"LaoUIb.ttf");

		View v = convertView;

		if (v == null) {
			v = inflater.inflate(R.layout.deals_list_item, parent, false);
			h = new ViewHolder();
			h.RestaurantName = (TextView) v.findViewById(R.id.txt_rest_name);
			h.CouponNumber = (TextView) v.findViewById(R.id.txt_coupon_num);
			h.DealValidity = (TextView) v.findViewById(R.id.txt_validity);

			h.ImageDeal = (ImageView) v.findViewById(R.id.imageDeal);

			v.setTag(h);
		} else {
			h = (ViewHolder) v.getTag();
		}

		h.RestaurantName.setTypeface(fontBold);
		h.CouponNumber.setTypeface(font);
		h.DealValidity.setTypeface(font);
		
		String imageurl = "https://s3-us-west-2.amazonaws.com/elasticbeanstalk-us-west-2-060425629624/munchon/gallery/deals/";

		new DownloadImageTask().execute(imageurl
				+ deals.get(position).getImage());
		
		h.RestaurantName.setText(deals.get(position).getRestaurantName());
		h.CouponNumber.setText("Coupon Code: "+deals.get(position).getCouponCode());
		h.DealValidity.setText("Valid till: "+deals.get(position).getDealValidityDate());

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

	private static class ViewHolder {

		TextView RestaurantName, CouponNumber, DealValidity;
		ImageView ImageDeal;

	}

	public ImageLoader imageLoader;

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		

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

			
			Bitmap b = img.getRoundedCornerBitmap(result, 50);
			h.ImageDeal.setImageBitmap(b);
		

		}
	}

}