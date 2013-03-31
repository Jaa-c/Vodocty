package com.vodocty.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.vodocty.R;
import com.vodocty.Vodocty;
import com.vodocty.controllers.DataController;
import com.vodocty.database.DBOpenHelper;
import com.vodocty.model.DataModel;

/**
 *
 * @author Dan Princ
 * @since 31.3.2013
 */
public class DataFragment extends Fragment {
    
    private FragmentActivity activity;
    private DBOpenHelper db; //save in sth like global context
    private DataController controller;
    private DataModel model;
    
    /**
     * Create a new instance of DetailsFragment, initialized to
     * show the text at 'index'.
     */
    public DataFragment(FragmentActivity activity) {
	this.activity = activity;
	Context c = activity.getApplicationContext();
	
	Vodocty v = (Vodocty) c;
	
        db = v.getDatabase();
	model = new DataModel(v);
	
	int lgId = activity.getIntent().getIntExtra(Vodocty.EXTRA_LG_ID, -1);
	Log.d("dataFragment", "lg:" +lgId);
	model.setLGId(12);//todo
	
	
    }

 
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
        return inflater.inflate(R.layout.data_page, container, false);
    }

    @Override
    public void onStart() {
	super.onStart();
	controller = new DataController(activity, model);
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
