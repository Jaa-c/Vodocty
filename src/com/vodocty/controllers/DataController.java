package com.vodocty.controllers;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import com.vodocty.data.LG;
import com.vodocty.model.DataModel;
import com.vodocty.view.DataView;
import com.vodocty.view.dialogs.NotificationDialog;

/**
 *
 * @author Dan Princ
 * @since 21.2.2013
 */
public class DataController extends AbstractMessageReceiver {
    
    private FragmentActivity activity;
    private DataModel model;
    private DataView view;
    
    //private Data data;
    
    public DataController(FragmentActivity activity, DataModel model) {
	this.activity = activity;
	this.model = model;
	
	view = new DataView(activity, model);
	
	//this.data = model.getLastData();
	//view.setContent(data);
	
	view.execute();
	view.setFavButtonListener(favButtonListener);
	view.setNotifButtonListener(notifButtonListener);
    }

    
    private OnClickListener favButtonListener = new OnClickListener() {
	public void onClick(View arg0) {
	    LG lg = model.getLastData().getLg();
	    if(lg.isFavorite()) {
		//dialog
		if(model.unsetFavorite(lg)) {
		    view.setStarIcon(true);
		}
		else {
		    Toast.makeText(activity, "Chyba, nepodarilo se odebrat z oblibenych", Toast.LENGTH_LONG);
		}
	    }
	    else {
		if(model.setFavorite(lg)) {
		    view.setStarIcon(false);
		}
		else {
		    Toast.makeText(activity, "Chyba, nepodarilo se prodat do oblibenych", Toast.LENGTH_LONG);
		}
	    }
	}
    };
    
    private OnClickListener notifButtonListener = new OnClickListener() {
	public void onClick(View arg0) {
	    DialogFragment d = new NotificationDialog(activity);
	    d.show(activity.getSupportFragmentManager(), NotificationDialog.ID);
	}
    
    };

    @Override
    public void updateData() {
	
	
    }
    
    
    
}
