package com.vodocty.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.vodocty.R;
import com.vodocty.Vodocty;
import com.vodocty.controllers.FavoritesController;
import com.vodocty.database.DBOpenHelper;
import com.vodocty.model.DataModel;
import com.vodocty.model.FavoritesModel;

/**
 *
 * @author Dan Princ
 * @since 31.3.2013
 */
public class FavoritesFragment extends Fragment {
    
    private FragmentActivity activity;
    private DBOpenHelper db; //save in sth like global context
    private FavoritesController controller;
    private DataModel model;
    
    public FavoritesFragment() {	
    } 
    
    boolean mDualPane;
    int mCurCheckPosition = 0;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

	this.activity = getActivity();
        // Check to see if we have a frame in which to embed the details
        // fragment directly in the containing UI.
        View detailsFrame = activity.findViewById(R.id.data_frame);
        
	mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;
	
	
	db = ((Vodocty) activity.getApplicationContext()).getDatabase();
	FavoritesModel model = new FavoritesModel(db);
	controller = new FavoritesController(this, model, mDualPane);
	((Vodocty) activity.getApplicationContext()).setDisplayFavorites(true);

    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
        return inflater.inflate(R.layout.lgs, container, false);

    }

    @Override
    public void onStart() {
	super.onStart();
    }

    @Override
    public void onPause() {
	super.onPause();
    }

    @Override
    public void onDestroyView() {
	super.onDestroyView();
    }

}
