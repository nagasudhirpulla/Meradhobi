package com.example.administrator.meradhobi;

import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class AddressListFragment extends Fragment{	
	private String id;
	public static final String ARG_ID = "PERSONID";
	private ListView list;

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
			id = args.getString(ARG_ID);
			if(id == null)
				id = "";
		}

		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.frag_address_list, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle bundle) {		
		list = (ListView) view.findViewById(R.id.address_list);		

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
	private class LoadProfileAddresses extends AsyncTask<String, Void, JSONObject> {

		protected JSONObject doInBackground(String... id) {
			String iD = id[0];
			JSONObject json = DhobiDBHelper.lookForUser(new String[]{"Id",iD});
			return json;
		}

		protected void onPostExecute(JSONObject json) {
			setList(json);
		}
	}
	
	private void setList(JSONObject json) {
		try {
			ArrayList<AddressListItem> rowItems = new ArrayList<AddressListItem>();
			JSONArray array = json.getJSONArray("addresses");
			for (int i = 0; i < array.length(); i++) {
				JSONObject inditem = array.getJSONObject(i);  
				rowItems.add(new AddressListItem(inditem.getString("name"), inditem.getString("city"), inditem.getString("state"), inditem.getString("address"), inditem.getString("pin")));
			}
			list.setAdapter(new AddressAdapter(this.getActivity(), R.layout.adapter_adress_list, rowItems));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		
	}

}

