package com.vodocty.controllers;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import com.vodocty.R;
import com.vodocty.data.Data;
import com.vodocty.data.LG;
import com.vodocty.model.DataModel;
import com.vodocty.view.DataView;

/**
 *
 * @author Dan Princ
 * @since 21.2.2013
 */
public class DataController extends AbstractMessageReceiver {
    
    private Activity activity;
    private DataModel model;
    private DataView view;
    
    //private Data data;
    
    public DataController(Activity activity, DataModel model) {
	this.activity = activity;
	this.model = model;
	
	view = new DataView(activity, model);
	
	//this.data = model.getLastData();
	//view.setContent(data);
	
	view.execute();
	view.setFavButtonListener(favButtonListener);
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

    @Override
    public void updateData() {
	
	
    }
    
    
    
}
