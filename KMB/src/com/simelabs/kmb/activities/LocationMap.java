package com.simelabs.kmb.activities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.simelabs.kmb.domain.LocationDetails;
import com.simelabs.kmb.launch.R;
import com.simelabs.kmb.locationmap.LocationMapAdapter;
import com.simelabs.kmb.locationmap.MapManagerInside;
import com.simelabs.kmb.locationmap.MapManagerOutside;
import com.simelabs.kmb.managers.JsonManager;
import com.simelabs.kmb.managers.JsonReadFeedback;
import com.simelabs.kmb.network.DownloadFiles;
import com.simelabs.kmb.network.Internet;
import com.simelabs.kmb.service.PublicValues;
import com.simelabs.kmb.spotbeak.Beacondetails;

/**
 * @author JOVITA P J
 * 
 * 
 */
public class LocationMap extends Fragment implements OnClickListener,
		JsonReadFeedback {
	RelativeLayout allvenuesLocation;
	ListView venuslist;
	private View mContentView;
	ArrayList<String> locationdata;
	ArrayList<String> venuesbg;
	JsonManager Jmanager;
	boolean netstatus;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		context = getActivity().getApplicationContext();

		mContentView = inflater.inflate(R.layout.gallerymain, container, false);

		Jmanager = new JsonManager(getActivity().getApplicationContext());

		/* getting data from location json */
		locationdata = Jmanager.getDatafromLocation(getActivity(),
				"activityLocation");
		if (locationdata != null) {

			mContentView = inflater.inflate(R.layout.activity_location_map,
					container, false);
		} else
			mContentView = inflater.inflate(R.layout.networkerrormsg,
					container, false);

		DownloadFiles.setcallback(this);
		return mContentView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
		refreshlocation();

	}

	private void refreshlocation() {
		// TODO Auto-generated method stub
		context = getActivity().getApplicationContext();

		// get the current view....
		View v = getView();

		RelativeLayout bgpic = (RelativeLayout) v.findViewById(R.id.bgpic);

		locationdata = Jmanager.getDatafromLocation(getActivity(),
				"activityLocation");
		if (locationdata != null && locationdata.size() > 0) {

			// declaring venues list
			venuslist = (ListView) v.findViewById(R.id.venuesList);

			venuesbg = new ArrayList<String>();
			PublicValues.alllocationdetails.clear();
			PublicValues.venueTitle.clear();

			for (String l : locationdata) {
				LocationDetails loc = new LocationDetails();

				String[] venue = l.split("#location#");

				PublicValues.venueTitle.add(venue[0]);
				String bg = venue[1].toString();
				if (bg == null || bg.length() <= 0) {
					bg = "no";
				}
				venuesbg.add(bg);
				double lat = Double.parseDouble(venue[2]);
				double lng = Double.parseDouble(venue[3]);
				PublicValues.venuesLatlng.add(new LatLng(lat, lng));

				loc.setName(venue[0]);
				loc.setVenueimageurl(venue[1].toString());
				loc.setLatlng(new LatLng(lat, lng));

				loc.setDetails(venue[4]);
				loc.setId(Integer.parseInt(venue[5]));
				loc.setFloorname(venue[6]);
				PublicValues.alllocationdetails.add(loc);
			}

			displayitems();

			// declaring and seting onclick listner to show all venues
			allvenuesLocation = (RelativeLayout) v
					.findViewById(R.id.allvenueslocation);
			allvenuesLocation.setOnClickListener(this);
			RelativeLayout findme = (RelativeLayout) v
					.findViewById(R.id.findme);
			findme.setOnClickListener(this);

		} else {

			locationdata = Jmanager.getDatafromLocation(getActivity(),
					"activityLocation");
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();

		switch (id) {
		case R.id.allvenueslocation:

			Intent intent = new Intent(getActivity().getApplicationContext(),
					MapManagerOutside.class);
			intent.putExtra("venue", "all");

			startActivity(intent);

			break;

		case R.id.findme:
			findmeinmap();
			break;

		default:
			break;
		}
	}

	private void findmeinmap() {
		// TODO Auto-generated method stub

		Beacondetails lastbeaconfound = PublicValues.lastbeaconfound;
		if (lastbeaconfound != null) {
			Intent intent = new Intent(getActivity().getApplicationContext(),
					MapManagerInside.class);

			intent.putExtra("position", 9999);
			startActivity(intent);
		} else {
			Toast.makeText(
					getActivity(),
					"You are not near to any of the Biennale Installations \n OR \n "
							+ "Your Bluetooth is not working!!",
					Toast.LENGTH_SHORT).show();

		}
	}

	public void manageinside(View v) {

	}

	Context context;

	private class DownloadImageTask extends AsyncTask<String, Void, String> {

		RelativeLayout bmImage;
		ArrayList<Drawable> drawablelist;
		ArrayList<String> vb;

		public DownloadImageTask(ArrayList<String> bac) {

			vb = bac;
		}

		protected String doInBackground(String... urls) {
			int i = 0;

			// create a File object for the parent directory
			File biennalevenues = new File(Environment
					.getExternalStorageDirectory().toString()
					+ "/.biennale/venues/");
			// have the object build the directory structure, if needed.
			biennalevenues.mkdirs();

			for (String s : vb) {
				if (s != null && (!s.equalsIgnoreCase("no"))) {
					try {

						URL url = new URL(s);
						InputStream input = url.openStream();
						try {
							File file = new File(biennalevenues, "venue" + i
									+ ".png");

							OutputStream output = new FileOutputStream(file);
							try {
								byte[] buffer = new byte[512];
								int bytesRead = 0;
								while ((bytesRead = input.read(buffer, 0,
										buffer.length)) >= 0) {
									output.write(buffer, 0, bytesRead);
								}
							} finally {
								output.close();
							}
						}

						finally {
							input.close();
						}

					} catch (Exception e) {
						Log.e("Error", e.getMessage());
						e.printStackTrace();
					}
				} else {
					Bitmap bitmap = BitmapFactory.decodeResource(
							getResources(), R.drawable.word);

					File dest = new File(biennalevenues, "venue" + i + ".png");
					try {
						FileOutputStream out;
						out = new FileOutputStream(dest);
						bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
						out.flush();
						out.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				i = i + 1;
			}

			return "Done";
		}

		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = ProgressDialog.show(getActivity(), "",
					"Loading. Please wait...", true);
		}

		protected void onPostExecute(String s) {

			// set adapter tp venues list
			LocationMapAdapter adapter = new LocationMapAdapter(getActivity(),
					PublicValues.venueTitle, PublicValues.venuesbackgroung);
			dialog.dismiss();
			displayitems();

		}
	}

	public void displayitems() {

		File biennalevenues = new File(Environment
				.getExternalStorageDirectory().toString()
				+ "/.biennale/venues/");
		File[] contents = biennalevenues.listFiles();
		// the directory file is not really a directory..
		if (contents == null || contents.length == 0) {
			// Folder is empty

			Internet net = new Internet(getActivity());
			boolean netstatus = net.isAvailable();
			if (netstatus != false) {
				new DownloadImageTask(venuesbg).execute("");
			} else {
				PublicValues.venuesbackgroung.clear();
				for (String i : venuesbg) {
					Drawable d = getResources().getDrawable(R.drawable.word);
					PublicValues.venuesbackgroung.add(d);
				}

				LocationMapAdapter adapter = new LocationMapAdapter(
						getActivity(), PublicValues.venueTitle,
						PublicValues.venuesbackgroung);
				venuslist.setAdapter(adapter);
			}

		}
		// Folder contains files
		else {
			PublicValues.venuesbackgroung.clear();
			for (File f : contents) {
				Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
				Drawable d = new BitmapDrawable(getResources(), bitmap);
				PublicValues.venuesbackgroung.add(d);

			}

			LocationMapAdapter adapter = new LocationMapAdapter(getActivity(),
					PublicValues.venueTitle, PublicValues.venuesbackgroung);
			venuslist.setAdapter(adapter);

		}

	}

	@Override
	public void oncomplete(String name) {
		// TODO Auto-generated method stub
		if (name.equalsIgnoreCase("location")) {
			refreshlocation();
		}
	}

}