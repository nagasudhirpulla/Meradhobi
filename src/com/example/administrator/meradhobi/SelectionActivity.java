package com.example.administrator.meradhobi;

import java.io.InputStream;

import org.json.JSONObject;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;


public class SelectionActivity extends Activity implements OnClickListener, ConnectionCallbacks, OnConnectionFailedListener,SelectionFragment.OnFragmentListener, AddressFragment.OnAddressFragmentListener, OrderFragment.OnOrderFragmentListener{

	/* Track whether the sign-in button has been clicked so that we know to resolve
	 * all issues preventing sign-in without waiting.
	 */
	private boolean mSignInClicked;
	private boolean mIntentInProgress;
	private boolean SignedIn;
	private static final int RC_SIGN_IN = 0;
	private static final String TAG = "GoogleLogin";
	private String Id;
	private String personName;
	private String email;
	String personPhotoUrl;
	String id,username,site;
	// Google client to interact with Google API
	private GoogleApiClient mGoogleApiClient;

	/* Store the connection result from onConnectionFailed callbacks so that we can
	 * resolve them when the user clicks sign-in.
	 */
	private ConnectionResult mConnectionResult;

	private boolean isNewUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setIcon(R.drawable.ic_launcher);

		// Check that the activity is using the layout version with
		// the fragment_container FrameLayout
		if (findViewById(R.id.fragment_container) != null) {

			// However, if we're being restored from a previous state,
			// then we don't need to do anything and should return or else
			// we could end up with overlapping fragments.
			if (savedInstanceState != null) {
				return;
			}

			// Create a new Fragment to be placed in the activity layout
			SelectionFragment firstFragment = new SelectionFragment();

			// Add the fragment to the 'fragment_container' FrameLayout
			getFragmentManager().beginTransaction()
			.add(R.id.fragment_container, firstFragment).commit();
		}

		SignedIn = false;
		
		mGoogleApiClient = new GoogleApiClient.Builder(this)
		.addConnectionCallbacks(this)
		.addOnConnectionFailedListener(this).addApi(Plus.API)
		.addScope(Plus.SCOPE_PLUS_LOGIN).build();

		findViewById(R.id.btn_sign_in).setOnClickListener(this);

		SharedPreferences storage = getSharedPreferences("USERBASE", 0);
		id = storage.getString("u_id", "");
		username = storage.getString("u_name", "");
		site = storage.getString("u_site", "");

		if(id.length() != 0){
			if(true)//getProfileInformationFromServer()
			{
				Id = id;
				personName = username;
				// Update the UI after signin
				updateUI(true);			
			}
			else
			{
				Toast.makeText(this, "Unable to get User Info, Login once again...", Toast.LENGTH_LONG).show();
				signOutFromGplus();
			}
		}
		
		
		//In order to avoid network android.os.Network error for making connections from Main Activity
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

	}


	private boolean getProfileInformationFromServer() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(final Menu menu) {
		MenuItem menuItemLogOut = menu.findItem(R.id.log_out);
		MenuItem menuItemProfile = menu.findItem(R.id.user_profile);
		MenuItem menuItemOrders = menu.findItem(R.id.orderlog);
		if (SignedIn) {
			menuItemLogOut.setEnabled(true).setVisible(true);
			menuItemProfile.setEnabled(true).setVisible(true);
			menuItemProfile.setTitle(personName);
			menuItemOrders.setEnabled(true).setVisible(true);
		} else {
			menuItemLogOut.setEnabled(false).setVisible(false);
			menuItemProfile.setEnabled(false).setVisible(false);
			menuItemOrders.setEnabled(false).setVisible(false);
		}
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		else if (id == R.id.log_out) {
			signOut();
			return true;
		}
		else if (id == R.id.user_profile) {
			takeToProfile();
			return true;
		}
		else
			return super.onOptionsItemSelected(item);
	}

	private void signOut() {
		signOutFromGplus();
		SharedPreferences storage = getSharedPreferences("USERBASE", 0);
    	SharedPreferences.Editor editor = storage.edit();    	
    	editor.clear();
    	editor.commit();
		updateUI(false);
	}


	private void takeToProfile() {
		// TODO Auto-generated method stub
		ProfileFragment newFragment = new ProfileFragment();
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		Bundle args = new Bundle();
		args.putString(ProfileFragment.ARG_ID, Id);
		args.putString(ProfileFragment.ARG_NAME, personName);
		//args.putString(ProfileFragment.ARG_EMAIL, email);
		//args.putString(ProfileFragment.ARG_PHONE, "----------");
		//args.putString(ProfileFragment.ARG_P_URL, personPhotoUrl);
		newFragment.setArguments(args);
		// Replace whatever is in the fragment_container view with this fragment,
		// and add the transaction to the back stack so the user can navigate back
		transaction.add(R.id.fragment_container, newFragment);
		transaction.addToBackStack(null);
		// Commit the transaction
		transaction.commit();
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

	/**
	 * Method to resolve any signin errors
	 * */
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
	protected void onActivityResult(int requestCode, int responseCode,
			Intent intent) {
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
	public void onConnected(Bundle arg0) {
		mSignInClicked = false;
		Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();

		//If we are already connected before this ,just do nothing
		if(!SignedIn){		
			// Get user's information
			if(getProfileInformation())
			{
				// Save the userName and id, so user do not have to login every time he opens the app
				SharedPreferences u_id = getSharedPreferences("USERBASE", 0);
				SharedPreferences.Editor editor = u_id.edit();
				editor.putString("u_id", Id);
				editor.putString("u_name", personName);
				editor.putString("u_site", "google");
				editor.commit();
				// Update the UI after signin
				updateUI(true);			
			}
			else
			{
				Toast.makeText(this, "Unable to get User Info, Login once again...", Toast.LENGTH_LONG).show();
				signOutFromGplus();
			}
		}

	}


	/**
	 * Fetching user's information name, email, profile pic
	 * @return 
	 * */
	private boolean getProfileInformation() {
		try {
			if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
				Person currentPerson = Plus.PeopleApi
						.getCurrentPerson(mGoogleApiClient);
				personName = currentPerson.getDisplayName();
				personPhotoUrl = currentPerson.getImage().getUrl();
				String personGooglePlusProfile = currentPerson.getUrl();
				email = Plus.AccountApi.getAccountName(mGoogleApiClient);
				Id = currentPerson.getId();

				Log.e(TAG, "Name: " + personName + ", plusProfile: "
						+ personGooglePlusProfile + ", email: " + email
						+ ", Image: " + personPhotoUrl);
				/*txtName.setText(personName);
				txtEmail.setText(email);*/

				// by default the profile url gives 50x50 px image only
				// we can replace the value with whatever dimension we want by
				// replacing sz=X
				/*personPhotoUrl = personPhotoUrl.substring(0,
						personPhotoUrl.length() - 2)
						+ PROFILE_PIC_SIZE;

				new LoadProfileImage(imgProfilePic).execute(personPhotoUrl);*/
				return true;

			} else {

				Toast.makeText(getApplicationContext(),
						"Person information is null", Toast.LENGTH_LONG).show();
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}


	private void updateUI(boolean isSignedIn) {
		if (isSignedIn) {
			/*btnSignIn.setVisibility(View.GONE);
			btnSignOut.setVisibility(View.VISIBLE);
			btnRevokeAccess.setVisibility(View.VISIBLE);
			llProfileLayout.setVisibility(View.VISIBLE);*/
			SignedIn = true;
			this.invalidateOptionsMenu();			
			isNewUser = true;//not required

			LoadProfAsyncTask tsk = new LoadProfAsyncTask();
			tsk.execute(Id);

			//Send the User to the next screen with the apiclient
			/*Intent intent = new Intent(getApplicationContext(), UserActivity.class);
			Bundle b = new Bundle();
			b.putString("Id", Id);
			b.putString("Account", "Google");
			intent.putExtras(b); //Put your id to your next Intent
			startActivity(intent);
			finish();*/
		} else {
			/*btnSignIn.setVisibility(View.VISIBLE);
			btnSignOut.setVisibility(View.GONE);
			btnRevokeAccess.setVisibility(View.GONE);
			llProfileLayout.setVisibility(View.GONE);*/

			//Take to Login Screen
			// Create a new Fragment to be placed in the activity layout
			
			SelectionFragment firstFragment = new SelectionFragment();
			FragmentTransaction transaction = getFragmentManager().beginTransaction();

			// Replace whatever is in the fragment_container view with this fragment,
			// and add the transaction to the back stack so the user can navigate back
			transaction.replace(R.id.fragment_container, firstFragment);
			//transaction.addToBackStack(null);
			// Commit the transaction
			transaction.commit();
			SignedIn = false;
			this.invalidateOptionsMenu();
		}

	}


	@Override
	public void onConnectionSuspended(int arg0) {
		// mGoogleApiClient.connect();
		//updateUI(false);

	}

	public void FragmentCall(int callcode) {
		// The user selected the headline of an article from the HeadlinesFragment
		// Do something here to display that article
		switch(callcode)
		{
		case 1:
			signInWithGplus();
			break;
		case 2:
			signInGuest();
			break;
		case 3:
			takeToOrderDetail();
			break;
		default:
			break;
		}

	}


	private void takeToOrderDetail() {
		// TODO Auto-generated method stub
		OrderFragment newFragment = new OrderFragment();
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		// Replace whatever is in the fragment_container view with this fragment,
		// and add the transaction to the back stack so the user can navigate back
		transaction.add(R.id.fragment_container, newFragment);
		transaction.addToBackStack(null);
		// Commit the transaction
		transaction.commit();

	}


	private void signInGuest() {
		//Replace the current fragment with city Selection fragment
		takeToFillAddress();

	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_sign_in:
			// Signin button clicked
			signInWithGplus();
			break;
			/*case R.id.btn_sign_out:
			// Signout button clicked
			signOutFromGplus();
			break;
		case R.id.btn_revoke_access:
			// Revoke access button clicked
			revokeGplusAccess();
			break;*/
		}

	}

	/**
	 * Sign-in into google
	 * */
	private void signInWithGplus() {
		if (!mGoogleApiClient.isConnecting()) {
			mSignInClicked = true;
			resolveSignInError();
		}
	}

	/**
	 * Sign-out from google
	 * */
	private void signOutFromGplus() {
		if (mGoogleApiClient.isConnected()) {
			Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
			mGoogleApiClient.disconnect();
			mGoogleApiClient.connect();
		}
	}

	/**
	 * Revoking access from google
	 * */
	private void revokeGplusAccess() {
		if (mGoogleApiClient.isConnected()) {
			Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
			Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
			.setResultCallback(new ResultCallback<Status>() {
				@Override
				public void onResult(Status arg0) {
					Log.e(TAG, "User access revoked!");
					mGoogleApiClient.connect();
					updateUI(false);
				}

			});
		}
	}

	/**
	 * Background Async task to load user profile picture from url
	 * */
	private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public LoadProfileImage(ImageView bmImage) {
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
			bmImage.setImageBitmap(result);
		}
	}

	private void addNewUser() {
		new AddProfAsyncTask().execute(Id, email, personName);
	}

	public class LoadProfAsyncTask extends AsyncTask<String, Void, JSONObject>{

		@Override
		protected JSONObject doInBackground(String... arg0) {
			String Id = arg0[0];						
			/*MongoLabDB db = new MongoLabDB("dhobi_base_1", "nxslG9WNoFOHU38iB9OmGqxndQx7AfiL");
			return db.getCollection("userBase").findOne("q={\"Id\":\"113669339194963770872\"}");*/
			return DhobiDBHelper.lookForUser(new String[]{"Id",Id});
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result==null)
			{
				isNewUser = true;
				Toast.makeText(getApplicationContext(), "NewUser!!!", Toast.LENGTH_SHORT).show();
				//Take the Person to the Profile Fill Fragment
				//For now lets just add him
				addNewUser();
				//Now Can take him to his profile edit screen.
			}
			else
			{
				isNewUser = false;
				//Take to the next screen				
				Toast.makeText(getApplicationContext(), "ExistingUser!!!"+result.toString(), Toast.LENGTH_SHORT).show();
				takeToFillAddress();
			}
		}

	}

	public class AddProfAsyncTask extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... arg0) {
			String Id = arg0[0];
			String email = arg0[1];
			String personName = arg0[2];
			return DhobiDBHelper.signupUser(Id, email, personName,personPhotoUrl);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Toast.makeText(getApplicationContext(), "UserAdded!!!"+result, Toast.LENGTH_SHORT).show();
			takeToFillAddress();
		}

	}

	void takeToFillAddress(){

		AddressFragment newFragment = new AddressFragment();
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		Bundle args = new Bundle();
		args.putString(AddressFragment.ARG_NAME, personName);
		newFragment.setArguments(args);
		// Replace whatever is in the fragment_container view with this fragment,
		// and add the transaction to the back stack so the user can navigate back
		transaction.replace(R.id.fragment_container, newFragment);
		if(!SignedIn)
			transaction.addToBackStack(null);
		// Commit the transaction
		transaction.commit();
	}

}