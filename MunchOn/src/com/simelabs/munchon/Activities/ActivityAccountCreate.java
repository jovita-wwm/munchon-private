package com.simelabs.munchon.Activities;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.UserInfoChangedCallback;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.simelabs.munchon.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.IntentSender.SendIntentException;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityAccountCreate extends Activity implements OnClickListener,
		ConnectionCallbacks, OnConnectionFailedListener {

	TextView pageTitle, labelRegister, CreateAccountButton;
	EditText Email, Password, FirstName, LastName;
	CharSequence emailId;
	String emailValidate;
	String emailPattern;
	String userEmail, userFirstName, userLastName;
	private static final int RC_SIGN_IN = 0;
	private static final String TAG = "ActivityLoginScreen";
	private GoogleApiClient mGoogleApiClient;
	private boolean mIntentInProgress;
	private boolean mSignInClicked;
	private ConnectionResult mConnectionResult;
	private LoginButton loginBtn;
	private UiLifecycleHelper uiHelper;
	String Gemail, Gname, Femail, Fname;
	Typeface tf, tfb;
	EditText edtLoginEmail, edtLoginPwd;
	TextView btnlogin, btncreate, btnforgotpass, btnsignupdesclabel, pagetitle;
	ImageView btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_account);

		String fontPath = "fonts/LaoUI.ttf";
		String fontPathBold = "fonts/LaoUIb.ttf";

		// Loading Font Face
		tf = Typeface.createFromAsset(getAssets(), fontPath);
		tfb = Typeface.createFromAsset(getAssets(), fontPathBold);

		pageTitle = (TextView) findViewById(R.id.page_title);
		Email = (EditText) findViewById(R.id.edt_email);
		labelRegister = (TextView) findViewById(R.id.label_register);
		Password = (EditText) findViewById(R.id.edt_password);
		FirstName = (EditText) findViewById(R.id.edt_first_name);
		LastName = (EditText) findViewById(R.id.edt_last_name);
		CreateAccountButton = (TextView) findViewById(R.id.btn_create_new_account);

		pageTitle.setTypeface(tfb);
		Email.setTypeface(tf);
		labelRegister.setTypeface(tf);
		Password.setTypeface(tf);
		FirstName.setTypeface(tf);
		LastName.setTypeface(tf);
		CreateAccountButton.setTypeface(tfb);

		emailId = Email.getText();
		emailValidate = Email.getText().toString().trim();
		emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
		
		/*
		 * Facebook login
		 */
		uiHelper = new UiLifecycleHelper(this, statusCallback);
		uiHelper.onCreate(savedInstanceState);

		loginBtn = (LoginButton) findViewById(R.id.fb_login_button);
		//loginBtn.setBackgroundResource(R.drawable.icon_fb_login);
		// loginBtn.setBackgroundResource(R.drawable.icon_fb_login);
	    loginBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
		loginBtn.setReadPermissions(Arrays.asList("email"));
		loginBtn.setUserInfoChangedCallback(new UserInfoChangedCallback() {
			@Override
			public void onUserInfoFetched(GraphUser user) {
				if (user != null) {
					updateFacebookUI(true);
					String id = user.getId();
					Femail = user.getProperty("email").toString();
					Fname = user.getName();

					SharedPreferences.Editor editor = getSharedPreferences(
							"Facebook Credentials", MODE_PRIVATE).edit();
					editor.putInt("FB login status", 1);
					editor.putString("FEmail", Femail);
					editor.putString("FName", Fname);
					editor.commit();

					Toast.makeText(
							getApplicationContext(),
							user.getProperty("email").toString()
									+ user.getName(), Toast.LENGTH_SHORT)
							.show();

					// Toast.makeText(getApplicationContext(),
					// "Facebook already logged in...Redirecting to order page",
					// Toast.LENGTH_SHORT).show();

					Intent fIntent = new Intent(ActivityAccountCreate.this,
							ActivityPlaceOrder.class);

					fIntent.putExtra("FEMAIL", Femail);
					fIntent.putExtra("FNAME", Fname);
					startActivity(fIntent);
				} else {

				}
			}
		});

		/**
		 * Google login
		 */
		btn = (ImageView) findViewById(R.id.imageView1);
		//btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);
		//btnSignIn.setBackgroundResource(R.drawable.icon_google_login);
		btn.setOnClickListener(this);

		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this).addApi(Plus.API)
				.addScope(Plus.SCOPE_PLUS_LOGIN).build();
	}

	public void createAccount(View v) {
		if (emailValidator(Email.getText().toString())) {
			Toast.makeText(getApplicationContext(), "valid email address",
					Toast.LENGTH_SHORT).show();

		} else {
			Toast.makeText(getApplicationContext(), "invalid email address",
					Toast.LENGTH_SHORT).show();
		}

		String userEmail = Email.getText().toString();
		String userPassword = Password.getText().toString();
		String userFirstName = FirstName.getText().toString();
		String userLastName = LastName.getText().toString();

		Toast.makeText(
				getApplicationContext(),
				"Email " + userEmail + "\n" + "First name " + userFirstName
						+ "\n" + "Last name " + userLastName,
				Toast.LENGTH_SHORT).show();

		SharedPreferences.Editor editor = getSharedPreferences(
				"User signup Credentials", MODE_PRIVATE).edit();
		editor.putString("UserEmail", userEmail);
		editor.putString("UserPassword", userPassword);
		editor.putString("UserFirstName", userFirstName);
		editor.putString("UserLastName", userLastName);

		editor.commit();

		Toast.makeText(getApplicationContext(), "Added to Preferences",
				Toast.LENGTH_SHORT).show();

		SharedPreferences userCredentials = getSharedPreferences(
				"User signup Credentials", MODE_PRIVATE);

		String uemail = userCredentials.getString("UserEmail",
				"No email defined");// "No name defined" is the default value.
		String ufname = userCredentials.getString("UserFirstName",
				"No first name defined"); // 0 is the default value.
		String ulname = userCredentials.getString("UserLastName",
				"No last name defined");

		Toast.makeText(
				getApplicationContext(),
				"Email " + uemail + "\n" + "First name " + ufname + "\n"
						+ "Last name " + ulname, Toast.LENGTH_SHORT).show();

		Intent fIntent = new Intent(ActivityAccountCreate.this,
				ActivityLoginScreen.class);

		startActivity(fIntent);
	}

	public static boolean emailValidator(final String mailAddress) {

		Pattern pattern;
		Matcher matcher;

		final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(mailAddress);
		return matcher.matches();

	}

	protected void onStart() {
		super.onStart();
		mGoogleApiClient.connect();
	}

	protected void onStop() {
		super.onStop();
		if (mGoogleApiClient.isConnected()) {
			mGoogleApiClient.disconnect();
		}
	}

	// Method to resolve any signin errors

	private void resolveSignInError() {
		if (mConnectionResult.hasResolution()) {
			try {
				mIntentInProgress = true;
				mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
			} catch (SendIntentException e) {
				mIntentInProgress = false;
				mGoogleApiClient.connect();
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int responseCode,
			Intent intent) {

		super.onActivityResult(requestCode, responseCode, intent);
		uiHelper.onActivityResult(requestCode, responseCode, intent);

		if (requestCode == RC_SIGN_IN) {
			if (responseCode != RESULT_OK) {
				mSignInClicked = false;
			}

			mIntentInProgress = false;

			if (!mGoogleApiClient.isConnecting()) {
				mGoogleApiClient.connect();
			}
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (!result.hasResolution()) {
			GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
					0).show();
			return;
		}

		if (!mIntentInProgress) {
			// Store the ConnectionResult for later usage
			mConnectionResult = result;

			if (mSignInClicked) {
				// The user has already clicked 'sign-in' so we attempt to
				// resolve all
				// errors until the user is signed in, or they cancel.
				resolveSignInError();
			}
		}

	}

	@Override
	public void onConnected(Bundle arg0) {
		mSignInClicked = false;

		// Get user's information
		getProfileInformation();

		// Update the UI after signin
		updateGoogleUI(true);

	}

	// Updating the Google Login UI, showing/hiding buttons and profile layout

	private void updateGoogleUI(boolean isSignedIn) {
		if (isSignedIn) {

			btn.setVisibility(View.GONE);
			loginBtn.setVisibility(View.GONE);
			// btnSignOut.setVisibility(View.VISIBLE);
			// btnRevokeAccess.setVisibility(View.VISIBLE);
			// llProfileLayout.setVisibility(View.VISIBLE);
		} else {
			btn.setVisibility(View.VISIBLE);
			// btnSignOut.setVisibility(View.GONE);
			// btnRevokeAccess.setVisibility(View.GONE);
			// llProfileLayout.setVisibility(View.GONE);
		}
	}

	// Updating the Google Login UI, showing/hiding buttons and profile layout

	private void updateFacebookUI(boolean isSignedIn) {
		if (isSignedIn) {
			loginBtn.setVisibility(View.GONE);
			btn.setVisibility(View.GONE);
			// btnSignOut.setVisibility(View.VISIBLE);
			// btnRevokeAccess.setVisibility(View.VISIBLE);
			// llProfileLayout.setVisibility(View.VISIBLE);
		} else {
			loginBtn.setVisibility(View.VISIBLE);
			// btnSignOut.setVisibility(View.GONE);
			// btnRevokeAccess.setVisibility(View.GONE);
			// llProfileLayout.setVisibility(View.GONE);
		}
	}

	// Fetching user's information name, email, profile pic

	private void getProfileInformation() {
		try {
			if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
				Person currentPerson = Plus.PeopleApi
						.getCurrentPerson(mGoogleApiClient);
				Gname = currentPerson.getDisplayName();
				Gemail = Plus.AccountApi.getAccountName(mGoogleApiClient);

				Toast.makeText(getApplicationContext(),
						"Google already logged in...Redirecting to order page",
						Toast.LENGTH_SHORT).show();

				SharedPreferences.Editor editor = getSharedPreferences(
						"Google Credentials", MODE_PRIVATE).edit();
				editor.putInt("Google login status", 1);
				editor.putString("Gemail", Gemail);
				editor.putString("Gname", Gname);
				editor.commit();

				Intent gIntent = new Intent(ActivityAccountCreate.this,
						ActivityPlaceOrder.class);

				gIntent.putExtra("GEMAIL", Gemail);
				gIntent.putExtra("GNAME", Gname);
				startActivity(gIntent);

				Toast.makeText(
						getApplicationContext(),
						"Google name is " + Gname + "\n" + "Email is " + Gemail,
						Toast.LENGTH_SHORT).show();

				// by default the profile url gives 50x50 px image only
				// we can replace the value with whatever dimension we want by
				// replacing sz=X

			} else {
				Toast.makeText(getApplicationContext(),
						"Person information is null", Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		mGoogleApiClient.connect();
		updateGoogleUI(false);
	}

	// Button on click listener

	@Override
	public void onClick(View v) {
		if (!mGoogleApiClient.isConnecting()) {
			mSignInClicked = true;
			resolveSignInError();
		}

		}
	

	private void signInWithGplus() {
		
	}

	private void signOutFromGplus() {
		if (mGoogleApiClient.isConnected()) {
			Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
			mGoogleApiClient.disconnect();
			mGoogleApiClient.connect();
			updateGoogleUI(false);
		}
	}

	private void revokeGplusAccess() {
		if (mGoogleApiClient.isConnected()) {
			Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
			Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
					.setResultCallback(new ResultCallback<Status>() {
						@Override
						public void onResult(Status arg0) {
							Log.e(TAG, "User access revoked!");
							mGoogleApiClient.connect();
							updateGoogleUI(false);
						}

					});
		}
	}

	private Session.StatusCallback statusCallback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			if (state.isOpened()) {
				Log.d("MainActivity", "Facebook session opened.");
			} else if (state.isClosed()) {
				Log.d("MainActivity", "Facebook session closed.");
			}
		}
	};

	@Override
	public void onResume() {
		super.onResume();
		uiHelper.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle savedState) {
		super.onSaveInstanceState(savedState);
		uiHelper.onSaveInstanceState(savedState);
	}

	public void userLogin(View v) {
		String email = edtLoginEmail.getText().toString();
		String pass = edtLoginPwd.getText().toString();

		SharedPreferences userCredentials = getSharedPreferences(
				"User signup Credentials", MODE_PRIVATE);
		String uemail = userCredentials.getString("UserEmail",
				"No email defined");
		String upwd = userCredentials.getString("UserPassword",
				"No email defined");

		if ((email.equals(uemail)) && (pass.equals(upwd))) {
			Toast.makeText(getApplicationContext(), "Successful",
					Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(getApplicationContext(), "Invalid credentials",
					Toast.LENGTH_LONG).show();
		}

	}

	
}
