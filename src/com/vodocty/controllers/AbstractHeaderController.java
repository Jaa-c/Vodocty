package com.vodocty.controllers;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import com.vodocty.R;
import com.vodocty.data.Country;
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
    
    protected AbstractHeaderController(FragmentActivity activity) {
	super();
	this.activity = activity;
	adapter = new FlagAdapter(activity, R.layout.list_flag_row, Country.values());
	flagDialog = new FlagDialog(activity, null, adapter);
    }
    
    protected final View.OnClickListener flagButtonListener = new View.OnClickListener() {
	public void onClick(View arg0) {
	    //Vodocty vodocty = (Vodocty) activity.getApplicationContext();
	    //Toast.makeText(activity, "Zatím jen ČR", Toast.LENGTH_LONG).show();
	    flagDialog.show(activity.getSupportFragmentManager(), FlagDialog.ID);
	}
    };
    

}
