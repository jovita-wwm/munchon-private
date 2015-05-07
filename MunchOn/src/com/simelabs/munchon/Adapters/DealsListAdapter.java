package com.simelabs.munchon.Adapters;

import java.util.ArrayList;

import com.simelabs.munchon.R;



import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DealsListAdapter extends ArrayAdapter<String> {

	private final Activity context;
	
	private final ArrayList<Bitmap> restImagesRounded;
	private final ArrayList<String> RestName;
	private final ArrayList<String> CouponCode;
	private final ArrayList<String> DealVal;
	Typeface tf,tfb;
	
	public DealsListAdapter(Activity context,ArrayList<Bitmap> restImagesRounded,
			ArrayList<String> restaurantNameArray
			,ArrayList<String> couponCode, ArrayList<String> validityDate, Typeface tf, Typeface tfb) {
		
		super(context, R.layout.deals_list_item, restaurantNameArray);
		// TODO Auto-generated constructor stub
		this.context=context;
		this.restImagesRounded=restImagesRounded;
		this.RestName=restaurantNameArray;
		this.CouponCode=couponCode;
		this.DealVal=validityDate;
		this.tf = tf;
		this.tfb = tfb;
	}
	

	
	public View getView(int position,View view,ViewGroup parent) {
		LayoutInflater inflater=context.getLayoutInflater();
		View rowView=inflater.inflate(R.layout.deals_list_item, null,true);
		
		ImageView RestImageImg = (ImageView) rowView.findViewById(R.id.imageRest);
		TextView RestNameTxt  = (TextView) rowView.findViewById(R.id.txt_rest_name);
		TextView CoupCodeTxt = (TextView) rowView.findViewById(R.id.txt_coupon_num);
		TextView ValidityTxt = (TextView) rowView.findViewById(R.id.txt_validity);
		
		/*TextView DealDesc = (TextView) rowView.findViewById(R.id.textDealDesc);
		TextView DealValidity = (TextView) rowView.findViewById(R.id.txtDealValidity);
		TextView Distance = (TextView) rowView.findViewById(R.id.textDistance);
	    TextView Order = (TextView) rowView.findViewById(R.id.btnOrder);*/
	    
		RestImageImg.setImageBitmap(restImagesRounded.get(position));
		RestNameTxt.setText(RestName.get(position));
		RestNameTxt.setTypeface(tfb);
		CoupCodeTxt.setText("Coupon code: "+CouponCode.get(position));
		CoupCodeTxt.setTypeface(tf);
		ValidityTxt.setText("Valid till: "+DealVal.get(position));
		ValidityTxt.setTypeface(tf);
		
		/*RestTitle.setText(RestName[position]);
		DealDesc.setText(DealDes[position]);
		DealValidity.setText(DealVal[position]);
		Distance.setText(RestDistance[position]+" KM");
		DealPerc.setText(DealPercentage[position]+"%"+"\n OFF");*/
		return rowView;
		
	};
	@Override
	public boolean isEnabled(int position) {
	    return false;
	}
}
