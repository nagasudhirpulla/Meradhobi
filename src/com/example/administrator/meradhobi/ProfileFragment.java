package com.example.administrator.meradhobi;

import java.io.InputStream;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;

public class ProfileFragment extends Fragment{

	public static final String ARG_EMAIL = "PERSONEMAIL";
	public static final String ARG_NAME = "PERSONNAME";
	public static final String ARG_PHONE = "PERSONPHONE";
	public static final String ARG_P_URL = "PERSONPICURL";
	private String name;
	private String email;
	private String Phone;
	private String picURL;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {

		// If activity recreated (such as from screen rotate), restore
		// the previous article selection set by onSaveInstanceState().
		// This is primarily necessary when in the two-pane layout.
		if (savedInstanceState != null) {

		}

		Bundle args = getArguments();
		if (args != null) {
			// Set article based on argument passed in
			name = args.getString(ARG_NAME);
			if(name == null)
				name = "";
			email = args.getString(ARG_EMAIL);
			if(email == null)
				email = "";
			Phone = args.getString(ARG_PHONE);
			if(Phone == null)
				Phone = "----------";
			picURL = args.getString(ARG_P_URL);
			if(picURL == null)
				picURL = "";

		}

		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.frag_profile, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle bundle) {
		((TextView)view.findViewById(R.id.TextViewProfile)).setText(name);
		((TextView)view.findViewById(R.id.TextViewEmail)).setText(email);
		((TextView)view.findViewById(R.id.TextViewPhone)).setText(Phone);
		new LoadProfileImage((ImageView) view.findViewById(R.id.imageView1)).execute(picURL);

	}

	@Override
	public void onStart() {
		super.onStart();
		// During startup, check if there are arguments passed to the fragment.
		// onStart is a good place to do this because the layout has already been
		// applied to the fragment at this point so we can safely call the method
		// below that sets the article text.

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

}

