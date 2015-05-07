package com.simelabs.kmb.network;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.http.util.ByteArrayBuffer;

import com.simelabs.kmb.launch.R;
import com.simelabs.kmb.activities.MainActivity;
import com.simelabs.kmb.managers.Decompress;
import com.simelabs.kmb.managers.JsonManager;
import com.simelabs.kmb.managers.JsonReadFeedback;
import com.simelabs.kmb.service.PublicValues;
import com.simelabs.kmb.spotbeak.MyService;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * 
 * @author Jovita
 * 
 */
public class DownloadFiles {

	File biennale;

	Context context;
	Activity activity;
	String zipfilename;
	FileOutputStream fos;
	private static JsonReadFeedback feedback;

	public DownloadFiles(Context c, Activity a) {
		// TODO Auto-generated constructor stub
		this.context = c;
		this.activity = a;
	}

	public boolean Download() {

		GetFile getfile = new GetFile();

		getfile.execute("fcgdf");

		do {
			/**
			 * checking whether async completed
			 */

		} while (getfile.getStatus() == AsyncTask.Status.FINISHED);

		return true;

	}

	private class GetFile extends AsyncTask<String, Void, String> {

		public GetFile() {

		}

		protected String doInBackground(String... urls) {

			try {

				/**
				 * creating app folder
				 * 
				 */

				// create a File object for the parent directory
				File biennale = new File(Environment
						.getExternalStorageDirectory().toString()
						+ "/.biennale/");
				// have the object build the directory structure, if needed.
				biennale.mkdirs();
				// create a File object for the output file
				File outputFile = new File(biennale, "config.json");
				// now attach the OutputStream to the file object, instead of a
				// String representation
				fos = new FileOutputStream(outputFile);

				/**
				 * download single file
				 */
				String remoteFilePath = "https://s3-us-west-2.amazonaws.com/elasticbeanstalk-us-west-2-060425629624/biennale/service/config.json.php";

				URL address = new URL(remoteFilePath);
				URLConnection conn = address.openConnection();
				InputStream is = conn.getInputStream();
				BufferedInputStream bis = new BufferedInputStream(is);
				ByteArrayBuffer bab = new ByteArrayBuffer(64);
				int current = 0;

				while ((current = bis.read()) != -1) {
					bab.append((byte) current);
				}

				fos.write(bab.toByteArray());
				fos.close();

				JsonManager json = new JsonManager(context);

				// checking beacon timestamp
				String beacontimestamp = PreferenceManager
						.getDefaultSharedPreferences(context).getString(
								"beacon version", null);

				Map<String, String> allversions = json.getfromConfig(context);
				String beaconzipversion = allversions.get("Beacon Version");
				String[] beacontime = null;
				if (beaconzipversion != null) {

					beacontime = beaconzipversion.split("#version#");

				}
				File beacon = new File(Environment
						.getExternalStorageDirectory().toString()
						+ "/.biennale/beacon.json");

				if (!(beacontime[0].equalsIgnoreCase(beacontimestamp))
						|| beacontimestamp == null || (!beacon.isDirectory())) {
					JsonManager beakjson = new JsonManager(context);
					beakjson.getDatafromBeacon(activity);

				}

				// checking zip timestamp
				String ziptimestamp = PreferenceManager
						.getDefaultSharedPreferences(context).getString(
								"zip version", null);

				String zipversion = allversions.get("Zip Version");
				String[] s = zipversion.split("#version#");

				/**
				 * download zip only if version changed
				 */
				File f = new File(Environment.getExternalStorageDirectory()
						.toString() + "/.biennale/unzipped/");
				if (!s[0].equalsIgnoreCase(ziptimestamp)
						|| ziptimestamp == null || (!f.isDirectory())) {

					String remoteDirPath = s[1];

					String[] zipfilpath = remoteDirPath.split("/");
					zipfilename = zipfilpath[zipfilpath.length - 1].substring(
							0, zipfilpath[zipfilpath.length - 1].length() - 4);


					File zipoutputFile = null;

					try {

						URL zipurl = new URL(remoteDirPath); // Your given URL.

						HttpURLConnection c = (HttpURLConnection) zipurl
								.openConnection();
						c.setRequestMethod("GET");
						c.connect(); // Connection Complete here.!

						int lenghtOfFile = c.getContentLength();

						Log.d("Downloading Updates", "Lenght of file: "
								+ lenghtOfFile);

						File file = biennale;

						if (!file.exists()) {
							file.mkdirs();
						}
						zipoutputFile = new File(file, "download.zip");

						FileOutputStream zipfos = new FileOutputStream(
								zipoutputFile);

						InputStream zipis = c.getInputStream();

						byte[] buffer = new byte[1024];
						int len1 = 0;
						long total = 0;
						while ((len1 = zipis.read(buffer)) != -1) {
							total += len1;

							zipfos.write(buffer, 0, len1); // Write In
															// FileOutputStream.
						}
						zipfos.flush();
						zipfos.close();
						zipis.close();
					} catch (Exception e) {
						e.printStackTrace();
					}

					PreferenceManager.getDefaultSharedPreferences(context)
							.edit().putString("zip version", s[0]).commit();


					// fast unzip
					String unzipLocation = Environment
							.getExternalStorageDirectory().toString()
							+ "/.biennale/unzipped/";
					String inputZipFile = Environment
							.getExternalStorageDirectory().toString()
							+ "/.biennale/download.zip";
					FastUnzip(inputZipFile, unzipLocation);

				}

			} catch (IOException ex) {
				ex.printStackTrace();
			}

			return "Done";
		}


		ImageView ball1, ball2, ball3;
		final Animation zoomin = AnimationUtils.loadAnimation(context,
				R.anim.zoomin);

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			ball1 = (ImageView) activity.findViewById(R.id.ball1);
			ball2 = (ImageView) activity.findViewById(R.id.ball2);
			ball3 = (ImageView) activity.findViewById(R.id.ball3);
			final Animation b1ball = AnimationUtils.loadAnimation(context,
					R.anim.ballzoom);
			final Animation b2ball = AnimationUtils.loadAnimation(context,
					R.anim.ballzoom);
			final Animation b3ball = AnimationUtils.loadAnimation(context,
					R.anim.ballzoom);
			b1ball.setStartOffset(100);
			ball1.setAnimation(b1ball);

			b2ball.setStartOffset(200);
			ball2.setAnimation(b2ball);

			b3ball.setStartOffset(300);
			ball3.setAnimation(b3ball);

			final ImageView white = (ImageView) activity
					.findViewById(R.id.whiteimage);

			white.startAnimation(zoomin);
			zoomin.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationEnd(Animation animation) {
					// TODO Auto-generated method stub
				}
			});

		}

		protected void onPostExecute(String result) {

			ball1.clearAnimation();
			ball2.clearAnimation();
			ball3.clearAnimation();

			Intent i = new Intent(context, MainActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
			activity.finish();


			context.startService(new Intent(context, MyService.class));

		}
	}

	public void unzip(String zippath) {

		/**
		 * unzip
		 */

		String zipFile = zippath;
		String unzipLocation = Environment.getExternalStorageDirectory()
				.toString() + "/.biennale/unzipped/";

		Decompress d = new Decompress(zipFile, unzipLocation);
		d.unzip();
		PublicValues.unzipedpath = zipfilename;
		Log.i("Unzipfilename:", PublicValues.unzipedpath);
		PreferenceManager.getDefaultSharedPreferences(context).edit()
				.putString("UnzipFilePath", zipfilename).commit();

	}

	private class DownloadsingleJson extends AsyncTask<String, Void, String> {
		String remoteFilePath, filename, activityclass;

		public DownloadsingleJson(String remotf, String jsonfilename,
				String attr) {
			remoteFilePath = remotf;
			filename = jsonfilename;
			activityclass = attr;
		}

		protected String doInBackground(String... urls) {

			File biennale = new File(Environment.getExternalStorageDirectory()
					.toString() + "/.biennale/");

			// create a File object for the output file
			File outputFile = new File(biennale, filename);
			// now attach the OutputStream to the file object, instead of a
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(outputFile);

				URL address = new URL(remoteFilePath);
				URLConnection conn = address.openConnection();
				InputStream is = conn.getInputStream();
				BufferedInputStream bis = new BufferedInputStream(is);
				ByteArrayBuffer bab = new ByteArrayBuffer(64);
				int current = 0;

				while ((current = bis.read()) != -1) {
					bab.append((byte) current);
				}

				Log.i(" json", bab.toByteArray().toString());
				fos.write(bab.toByteArray());
				fos.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return "Done";

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if (!filename.equalsIgnoreCase("beacon.json")) {
				dialog = ProgressDialog.show(activity, "",
						"Loading. Please wait...", true);
			}
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			JsonManager json = new JsonManager(context);
			if (filename.equalsIgnoreCase("gallery.json")) {
				feedback.oncomplete("gallery");
			}

			else if (filename.equalsIgnoreCase("location.json")) {
				if (activityclass.equalsIgnoreCase("activityLocation")) {
					feedback.oncomplete("location");
				}

			}

			else if (filename.equalsIgnoreCase("beacon.json")) {
				json.getDatafromBeacon(activity);
			}

			if (!filename.equalsIgnoreCase("beacon.json")) {
				dialog.dismiss();
			}

		}

	}

	ProgressDialog dialog;

	public String jsondownload(String remotefilename, String jsonfilename,
			String attr) {
		DownloadsingleJson download = new DownloadsingleJson(remotefilename,
				jsonfilename, attr);
		download.execute("");

		String s = download.getStatus().toString();

		do {
			Log.e("background gallery download status", s);
		} while (!s.equalsIgnoreCase("Running"));

		return s;
	}

	public boolean FastUnzip(String inputZipFile, String destinationDirectory) {
		try {
			int BUFFER = 2048;
			List<String> zipFiles = new ArrayList<String>();
			File sourceZipFile = new File(inputZipFile);
			File unzipDestinationDirectory = new File(destinationDirectory);
			unzipDestinationDirectory.mkdir();
			ZipFile zipFile;
			zipFile = new ZipFile(sourceZipFile, ZipFile.OPEN_READ);
			Enumeration<?> zipFileEntries = zipFile.entries();
			while (zipFileEntries.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
				String currentEntry = entry.getName();
				File destFile = new File(unzipDestinationDirectory,
						currentEntry);
				if (currentEntry.endsWith(".zip")) {
					zipFiles.add(destFile.getAbsolutePath());
				}

				File destinationParent = destFile.getParentFile();

				destinationParent.mkdirs();

				try {
					if (!entry.isDirectory()) {
						BufferedInputStream is = new BufferedInputStream(
								zipFile.getInputStream(entry));
						int currentByte;
						byte data[] = new byte[BUFFER];

						FileOutputStream fos = new FileOutputStream(destFile);
						BufferedOutputStream dest = new BufferedOutputStream(
								fos, BUFFER);
						while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
							dest.write(data, 0, currentByte);
						}
						dest.flush();
						dest.close();
						is.close();
					}
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
			zipFile.close();

			for (Iterator<String> iter = zipFiles.iterator(); iter.hasNext();) {
				String zipName = (String) iter.next();
				FastUnzip(zipName, destinationDirectory + File.separatorChar
						+ zipName.substring(0, zipName.lastIndexOf(".zip")));
			}

			PublicValues.unzipedpath = zipfilename;
			Log.i("Unzipfilename:", PublicValues.unzipedpath);
			PreferenceManager.getDefaultSharedPreferences(context).edit()
					.putString("UnzipFilePath", zipfilename).commit();

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static void setcallback(JsonReadFeedback instance) {

		feedback = instance;

	}
}
