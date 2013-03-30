package com.vodocty.controllers;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import com.vodocty.R;
import com.vodocty.Vodocty;
import com.vodocty.activities.DataActivity;
import com.vodocty.activities.MainActivity;
import com.vodocty.data.LG;
import com.vodocty.model.FavoritesModel;
import com.vodocty.view.FavoritesView;
import com.vodocty.view.adapters.LGsAdapter;

/**
 *
 * @author Dan Princ
 * @since 11.3.2013
 */
public class FavoritesController extends AbstractMessageReceiver {
    
    private final Activity activity;
    private final FavoritesModel model;
    private final LGsAdapter adapter; 
    private final FavoritesView view;
    
    
    public FavoritesController(Activity activity, FavoritesModel model) {
	
	this.activity = activity;
	this.model = model;
	this.adapter = new LGsAdapter(activity, R.layout.list_lg_row, model.getFavoriteLGs());
	adapter.setDisplayFavorites(true);
	
	this.view = new  FavoritesView(activity, adapter);	
	view.setFavButtonListener(favButtonListener);
	view.setFlagButtonListener(flagButtonListener);
	view.setListClickListener(listClickHandler);
    }
    
    @Override
    public void updateData() {
	model.invalidate();
	adapter.setData(model.getFavoriteLGs());
	adapter.notifyDataSetChanged();
    }
    
    private final AdapterView.OnItemClickListener listClickHandler = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            
	    Intent intent = new Intent(activity, DataActivity.class);
	    
            LG lg = (LG) adapter.getItem(position-1);
	    intent.putExtra(Vodocty.EXTRA_LG_ID, lg.getId());
	    activity.startActivity(intent);

        }
    };
    
    private final View.OnClickListener favButtonListener = new View.OnClickListener() {
	public void onClick(View arg0) {
	    Vodocty vodocty = (Vodocty) activity.getApplicationContext();
	    vodocty.setDisplayFavorites(false);
	    vodocty.setChangeDispFavorites(true);
	    ((MainActivity) activity).checkFavoritesView();
	}
    };
    
    private final View.OnClickListener flagButtonListener = new View.OnClickListener() {
	public void onClick(View arg0) {
	    //Vodocty vodocty = (Vodocty) activity.getApplicationContext();
	    Toast.makeText(activity, "Zatím jen ČR", Toast.LENGTH_LONG).show();
	    
	}
    };

}
