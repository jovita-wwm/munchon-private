package com.simelabs.munchon.Fragments;


import com.simelabs.munchon.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentBlank extends Fragment{
	Context context;
	 private View mContentView;

	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		context=getActivity().getApplicationContext();
		
		 mContentView = inflater.inflate(R.layout.fragment_blank, container, false);
		  return mContentView;
		  
	}
}
