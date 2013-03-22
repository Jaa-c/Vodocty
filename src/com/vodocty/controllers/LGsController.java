package com.vodocty.controllers;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import com.vodocty.activities.DataActivity;
import com.vodocty.R;
import com.vodocty.Vodocty;
import com.vodocty.data.LG;
import com.vodocty.model.LGsModel;
import com.vodocty.view.LGsView;
import com.vodocty.view.adapters.LGsAdapter;

/**
 *
 * @author Dan Princ
 * @since 20.2.2013
 */
public class LGsController extends AbstractMessageReceiver {
    
    private Activity activity;
    private LGsModel model;
    private LGsAdapter adapter;
    private LGsView view;
    
    public LGsController(Activity activity, LGsModel model) {
	
	this.activity = activity;
	this.model = model;
	this.adapter = new LGsAdapter(activity, R.layout.list_lg_row, model.getLGs());
	this.view = new LGsView(activity, adapter);
	view.setContent(model.getRiver());
	view.setListClickListener(listClickHandler);
	
	
    }
    
    private AdapterView.OnItemClickListener listClickHandler = new AdapterView.OnItemClickListener() {
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

}
