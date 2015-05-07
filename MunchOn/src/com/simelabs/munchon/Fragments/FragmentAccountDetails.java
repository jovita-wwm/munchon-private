package com.simelabs.munchon.Fragments;

import com.simelabs.munchon.R;
import com.simelabs.munchon.Activities.ActivityAccountAddLocation;
import com.simelabs.munchon.Domain.PublicValues;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class FragmentAccountDetails extends Fragment{
	Context context;
	 private View mContentView;
	 LinearLayout save,change,address;
	 EditText Fname,Lname,email;
	 boolean savestatus=false;

	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		context=getActivity().getApplicationContext();
		
		 mContentView = inflater.inflate(R.layout.fragment_account_details, container, false);
		  return mContentView;
		  
	}
	
@Override
public void onActivityCreated( Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onActivityCreated(savedInstanceState);
	  View v = getView();
	save=(LinearLayout)v.findViewById(R.id.lnr_save);
	change=(LinearLayout)v.findViewById(R.id.lnr_change);
	address=(LinearLayout)v.findViewById(R.id.lnr_address);
	
	
	Fname=(EditText)v.findViewById(R.id.edt_firstname);
	Lname=(EditText)v.findViewById(R.id.edt_lastname);
	email=(EditText)v.findViewById(R.id.edt_email);
	
	
	
	String AccountFirstname,Accountlastname,Accountemail,accountpassword;
	AccountFirstname=PublicValues.AccountFirstName;
	Accountlastname=PublicValues.AccountLastName;
	Accountemail=PublicValues.Email;
	accountpassword=PublicValues.Password;
	
	if(AccountFirstname.length()>3)
	{
		Fname.setText(AccountFirstname);
		Lname.setText(Accountlastname);
		email.setText(Accountemail);
	}
	
	save.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			if( Fname.getText().toString().length()>3 && Lname.getText().toString().length()>1)
			{
			if(Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches())
			{
				savestatus=true;
				PublicValues.AccountFirstName=Fname.getText().toString();
				PublicValues.AccountLastName=Lname.getText().toString();
				PublicValues.Email=email.getText().toString();
				Toast.makeText(getActivity(), "Account Details Updated successfully", Toast.LENGTH_SHORT).show();
			}
			else
				Toast.makeText(getActivity(), "Please enter Valid Email Id", Toast.LENGTH_SHORT).show();
			
			}
			else
			{
				Toast.makeText(getActivity(), "Please enter Valid Data in all fields", Toast.LENGTH_SHORT).show();
			}
			
		}
	});
	
	
	change.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			LinearLayout ok,cancel;
			final EditText CURENT;
			final EditText NEWpass;
			final EditText CONFIRM;
			
			final Dialog password=new Dialog(getActivity());
			password.requestWindowFeature(Window.FEATURE_NO_TITLE);
			password.setContentView(R.layout.dialog_changepassword);
			password.show();
			
			ok=(LinearLayout)password.findViewById(R.id.lnr_ok);
			cancel=(LinearLayout)password.findViewById(R.id.lnr_cancel);
			CURENT=(EditText)password.findViewById(R.id.edt_currentpassword);
			NEWpass=(EditText)password.findViewById(R.id.edt_newpassword);
			CONFIRM=(EditText)password.findViewById(R.id.edt_confirm);
			
			
			ok.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					if(CURENT.getText().toString().equalsIgnoreCase(PublicValues.Password))
					{
						if(NEWpass.getText().toString().length()>3)
						{
						if(NEWpass.getText().toString().equalsIgnoreCase(CONFIRM.getText().toString()))
						{
							
							PublicValues.Password=NEWpass.getText().toString();
							Toast.makeText(getActivity().getApplicationContext(), "Password successfully Updated", Toast.LENGTH_SHORT).show();
							password.dismiss();
						
						}
						else
						{
							Toast.makeText(getActivity().getApplicationContext(), "New password do not match!!", Toast.LENGTH_SHORT).show();
						}
					}
						else
						{
							Toast.makeText(getActivity().getApplicationContext(), "Please enter neew Password!!", Toast.LENGTH_SHORT).show();
						}
					}
					else
					{
						Toast.makeText(getActivity().getApplicationContext(), "Check your current password!!", Toast.LENGTH_SHORT).show();
					}
				}
			});
			cancel.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					password.dismiss();
				}
			});
			
			
			
		}
	});
	
	address.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent address=new Intent(getActivity(), ActivityAccountAddLocation.class);
			startActivity(address);
			
		}
	});
}

}
