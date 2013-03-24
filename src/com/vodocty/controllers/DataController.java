package com.vodocty.controllers;

import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
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
	view.initDialog(okNotiDialogListener, cancelNotiDialogListener, deleteNotiDialogListener);
	
	view.execute();
	view.setFavButtonListener(favButtonListener);
	view.setNotifButtonListener(notifButtonListener);
    }

    
    private final OnClickListener favButtonListener = new OnClickListener() {
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
    
    private final OnClickListener notifButtonListener = new OnClickListener() {
	public void onClick(View arg0) {
	    view.showNotificationDialog();
	}
    
    };
    
    private final DialogInterface.OnClickListener cancelNotiDialogListener = new DialogInterface.OnClickListener() {
	public void onClick(DialogInterface arg0, int arg1) {
	
	}

    };
    
    private final DialogInterface.OnClickListener okNotiDialogListener = new DialogInterface.OnClickListener() {
	public void onClick(DialogInterface arg0, int arg1) {
	
	}

    };
    
    private final DialogInterface.OnClickListener deleteNotiDialogListener = new DialogInterface.OnClickListener() {
	public void onClick(DialogInterface arg0, int arg1) {
	
	}

    };

    @Override
    public void updateData() {
	
	
    }
    
    
    
}
