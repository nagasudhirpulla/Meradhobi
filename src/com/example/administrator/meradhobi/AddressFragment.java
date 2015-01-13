package com.example.administrator.meradhobi;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;

public class AddressFragment extends Fragment implements OnClickListener{

	public static String ARG_NAME = "PERSONNAME";
	private String name = ""; 
	OnAddressFragmentListener mCallback;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
			Bundle savedInstanceState) {

		// If activity recreated (such as from screen rotate), restore
		// the previous article selection set by onSaveInstanceState().
		// This is primarily necessary when in the two-pane layout.
		if (savedInstanceState != null) {

		}
		name = getArguments().getString(AddressFragment.ARG_NAME);
		if(name == null)
		{
			name = "";
		}		
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.frag_city_select, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle bundle) {
		((EditText)view.findViewById(R.id.editTextName)).setText(name);
		view.findViewById(R.id.address_proceed).setOnClickListener(this);
		//Add a Popup memu list for State
		final EditText state = (EditText) view.findViewById(R.id.EditTextState);
		PopUpFactory.createPopUp(getActivity(), state, new String[]{"Chandigarh","Haryana"});
		PopUpFactory.createPopUp(getActivity(), (EditText) view.findViewById(R.id.EditTextCity), new String[]{"City1","City2","City3","City4","City5","City6","City7","City8"});
	}

	// Container Activity must implement this interface
	public interface OnAddressFragmentListener {
		public void FragmentCall(int callcode);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception
		try {
			mCallback = (OnAddressFragmentListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentListener");
		}
	}

	@Override
	public void onStart() {
		super.onStart();

		// During startup, check if there are arguments passed to the fragment.
		// onStart is a good place to do this because the layout has already been
		// applied to the fragment at this point so we can safely call the method
		// below that sets the article text.
		Bundle args = getArguments();
		if (args != null) {
			// Set article based on argument passed in

		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// Save the current article selection in case we need to recreate the fragment
		//outState.putInt(ARG_POSITION, mCurrentPosition);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(validateForm())
			mCallback.FragmentCall(3);//goto next screen i.e., Order Detail

	}

	private boolean validateForm() {
		// TODO Auto-generated method stub
		return false;
	}

}
