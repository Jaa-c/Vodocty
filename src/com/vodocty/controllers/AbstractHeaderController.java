package com.vodocty.controllers;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

/**
 *
 * @author Dan Princ
 * @since 5.4.2013
 */
public abstract class AbstractHeaderController extends AbstractMessageReceiver {
    
    protected final Activity activity;
    
    protected AbstractHeaderController(Activity activity) {
	super();
	this.activity = activity;
    }
    
    protected final View.OnClickListener flagButtonListener = new View.OnClickListener() {
	public void onClick(View arg0) {
	    //Vodocty vodocty = (Vodocty) activity.getApplicationContext();
	    Toast.makeText(activity, "Zatím jen ČR", Toast.LENGTH_LONG).show();
	    
	}
    };
    

}
