package com.vodocty.controllers;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.vodocty.R;
import com.vodocty.Vodocty;
import com.vodocty.data.Country;
import com.vodocty.view.CommonHeaderView;
import com.vodocty.view.adapters.FlagAdapter;
import com.vodocty.view.dialogs.FlagDialog;

/**
 *
 * @author Dan Princ
 * @since 5.4.2013
 */
public abstract class AbstractHeaderController extends AbstractMessageReceiver {
    
    protected final FragmentActivity activity;
    protected FlagDialog flagDialog;
    protected ArrayAdapter adapter;
    protected CommonHeaderView headerView;
    
    protected AbstractHeaderController(FragmentActivity activity) {
	super();
	this.activity = activity;
	adapter = new FlagAdapter(activity, R.layout.list_flag_row, Country.values());
	flagDialog = new FlagDialog(activity, adapter, listClickHandler);
	
	headerView = new CommonHeaderView(activity);
	headerView.setFlagButtonListener(flagButtonListener);
	headerView.setMenuButtonListener(menuButtonListener);
	
    }
    
    protected final View.OnClickListener flagButtonListener = new View.OnClickListener() {
	public void onClick(View arg0) {
	    flagDialog.show(activity.getSupportFragmentManager(), FlagDialog.ID);
	}
    };
    
    
    private final AdapterView.OnItemClickListener listClickHandler = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            
	    Country chosen = (Country) parent.getItemAtPosition(position);
	    Vodocty vodocty = (Vodocty) activity.getApplicationContext();
	    if(vodocty.getDisplayedCountry() != chosen) {
		vodocty.setDisplayedCountry(chosen);
		headerView.flagButtonChanged();
	    }
	    flagDialog.getDialog().cancel();
        }
    };
    
    
    
    private final View.OnClickListener menuButtonListener = new View.OnClickListener() {
	public void onClick(View arg0) {
	    //Vodocty vodocty = (Vodocty) activity.getApplicationContext();
	    Toast.makeText(activity, "Menu pyčo", Toast.LENGTH_LONG).show();
	    
	}
    };
    

}
