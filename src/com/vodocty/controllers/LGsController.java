package com.vodocty.controllers;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import com.vodocty.activities.DataActivity;
import com.vodocty.R;
import com.vodocty.Vodocty;
import com.vodocty.activities.MainActivity;
import com.vodocty.data.LG;
import com.vodocty.model.LGsModel;
import com.vodocty.view.LGsView;
import com.vodocty.view.adapters.LGsAdapter;

/**
 *
 * @author Dan Princ
 * @since 20.2.2013
 */
public class LGsController extends AbstractHeaderController {
    
    private final LGsModel model;
    private final LGsAdapter adapter;
    private final LGsView view;
    
    public LGsController(Activity activity, LGsModel model) {
	super(activity);
	
	this.model = model;
	this.adapter = new LGsAdapter(activity, R.layout.list_lg_row, model.getLGs());
	this.view = new LGsView(activity, adapter);
	view.setContent(model.getRiver());
	view.setListClickListener(listClickHandler);
	view.setFavButtonListener(favButtonListener);
	view.setFlagButtonListener(flagButtonListener);
	
    }
    
    private final AdapterView.OnItemClickListener listClickHandler = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            
	    Intent intent = new Intent(activity, DataActivity.class);
            //predame seznam, ktery chceme zobrazit
            LG lg = (LG) adapter.getItem(position-1);	    
	    intent.putExtra(Vodocty.EXTRA_LG_ID, lg.getId());
	    activity.startActivity(intent);

        }
    };

    @Override
    public void updateData() {
	model.invalidate();
	adapter.setData(model.getLGs());
	adapter.notifyDataSetChanged();
    }
    
    private final View.OnClickListener favButtonListener = new View.OnClickListener() {
	public void onClick(View arg0) {
	    Vodocty vodocty = (Vodocty) activity.getApplicationContext();
	    vodocty.setDisplayFavorites(true);
	    vodocty.setChangeDispFavorites(true);
	    activity.finish(); //return back to main, i'll switch to view favs
	}
    };

}
