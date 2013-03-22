package com.vodocty.controllers;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import com.vodocty.activities.LGsActivtiy;
import com.vodocty.R;
import com.vodocty.Vodocty;
import com.vodocty.activities.MainActivity;
import com.vodocty.data.River;
import com.vodocty.model.RiversModel;
import com.vodocty.view.RiversView;
import com.vodocty.view.adapters.RiversAdapter;

/**
 *
 * @author Dan Princ
 * @since 17.2.2013
 */
public class RiversController extends AbstractMessageReceiver {
    
    private Activity activity;
    private RiversModel model;
    private RiversAdapter adapter;
    private RiversView view;
    
    
    public RiversController(Activity activity, RiversModel model) {
	
	this.activity = activity;
	this.model = model;
        this.adapter = new RiversAdapter(activity, R.layout.list_river_row, model.getRivers());
	this.view = new RiversView(activity, adapter);
	view.setFavButtonListener(favButtonListener);
	view.setListClickListener(childListClickHandler);
    
    }

    @Override
    public void updateData() {
	model.invalidateData();
	adapter.setData(model.getRivers());
	adapter.notifyDataSetChanged();
    }
    
    /**
     * anonymní listener na všechny položky v seznamu
     */
    private OnChildClickListener childListClickHandler = new OnChildClickListener() {
	
	public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
	    Intent intent = new Intent(activity, LGsActivtiy.class);
            //predame seznam, ktery chceme zobrazit
            River river = (River) adapter.getChild(groupPosition, childPosition);
	    
	    intent.putExtra(Vodocty.EXTRA_RIVER_ID, river.getId());
	    activity.startActivity(intent);
	    return true;
	}
    };
    
    private View.OnClickListener favButtonListener = new View.OnClickListener() {
	public void onClick(View arg0) {
	    Vodocty vodocty = (Vodocty) activity.getApplicationContext();
	    vodocty.setDisplayFavorites(true);
	    vodocty.setChangeDispFavorites(true);
	    ((MainActivity) activity).checkFavoritesView();
	}
    };
    

}
