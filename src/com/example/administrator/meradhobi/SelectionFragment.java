package com.example.administrator.meradhobi;

import com.google.android.gms.common.SignInButton;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class SelectionFragment extends Fragment implements OnClickListener {
	
	OnFragmentListener mCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {

        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if (savedInstanceState != null) {
        
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frag_selection, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        SignInButton signInButton = (SignInButton) view.findViewById(R.id.btn_sign_in);
        view.findViewById(R.id.guest_button).setOnClickListener(this);
        signInButton.setOnClickListener(this);
    }
    
    // Container Activity must implement this interface
    public interface OnFragmentListener {
        public void FragmentCall(int callcode);
    }
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnFragmentListener) activity;
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
		if(v.getId() == R.id.btn_sign_in)
		{
			mCallback.FragmentCall(1);			
		}
		else if(v.getId() == R.id.guest_button)
		{
			mCallback.FragmentCall(2);
		}
	}
}
