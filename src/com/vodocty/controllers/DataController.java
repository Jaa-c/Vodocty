package com.vodocty.controllers;

import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.vodocty.data.LG;
import com.vodocty.model.DataModel;
import com.vodocty.view.DataView;

/**
 *
 * @author Dan Princ
 * @since 21.2.2013
 */
public class DataController extends AbstractMessageReceiver {
    
    private final FragmentActivity activity;
    private final DataModel model;
    private final DataView view;
    
    
    public DataController(FragmentActivity activity, DataModel model) {
	this.activity = activity;
	this.model = model;
	
	view = new DataView(activity, model);
	view.initDialog(okNotiDialogListener);
	
	view.execute();
	view.setFavButtonListener(favButtonListener);
	view.setNotifButtonListener(notifButtonListener);
    }

    
    private final View.OnClickListener favButtonListener = new View.OnClickListener() {
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
    
    private final View.OnClickListener notifButtonListener = new View.OnClickListener() {
	public void onClick(View arg0) {
	    view.showNotificationDialog();
	}
    
    };
    
    private final DialogInterface.OnClickListener okNotiDialogListener = new DialogInterface.OnClickListener() {
	public void onClick(DialogInterface dialog, int which) {
	    //TODO: nejlepe to udelat asynchrone v jinym vlakne
	    LG lg = model.getLastData().getLg();
	    
	    if(!view.isNotificationEnabled() && !lg.isNotify()) {
		return;
	    }
	    float height = view.getDialogHeight().length() > 0 
		    ? Float.parseFloat(view.getDialogHeight()) : 0;
	    float volume = view.getDialogVolume().length() > 0 
		    ? Float.parseFloat(view.getDialogVolume()) : 0;
	    
	    if(view.isNotificationEnabled() && lg.isNotify() && 
		    lg.getNotifyHeight() == height &&
		    lg.getNotifyVolume() == volume) {
		return; //nothing done
	    }
	    
	    lg.setNotify(view.isNotificationEnabled());
	    lg.setNotifyHeight(height);
	    lg.setNotifyVolume(volume);
	    if(!model.updateLG(lg)) {
		Toast.makeText(activity, "Nepodařilo se uložit upozornění", Toast.LENGTH_LONG);
	    }
	    else {
		view.notifyDataChanged();
	    }
	    
	}

    };
    
    @Override
    public void updateData() {
	
	
    }
    
    
    
}
