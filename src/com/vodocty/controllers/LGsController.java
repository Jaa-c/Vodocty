package com.vodocty.controllers;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.vodocty.LGsActivtiy;
import com.vodocty.R;
import com.vodocty.data.LG;
import com.vodocty.data.River;
import com.vodocty.model.LGsModel;
import com.vodocty.view.adapters.LGsAdapter;
import java.util.List;

/**
 *
 * @author Dan Princ
 * @since 20.2.2013
 */
public class LGsController {
    
    private Activity activity;
    private LGsModel model;
    private LGsAdapter adapter;
    
    public LGsController(Activity activity, LGsModel model) {
	
	this.activity = activity;
	this.model = model;
	
	List<LG> data = model.getLGs();
	for(LG lg : data) {
	    Log.d(LGsController.class.getName(), lg.getName());
	
	}
	
	
        ListView list = (ListView) activity.findViewById(R.id.listview);
	adapter = new LGsAdapter(activity, R.layout.list_lg_row, model.getLGs());
	list.setAdapter(adapter);
        list.setOnItemClickListener(listClickHandler);
	
    }
    
    private AdapterView.OnItemClickListener listClickHandler = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            
	    //Intent intent = new Intent(activity, LGsActivtiy.class);
            //predame seznam, ktery chceme zobrazit
            LG lg = (LG) adapter.getItem(position);
	    Log.d(LGsController.class.getName(), "click: " + lg.getName());
	    
	    //intent.putExtra(RIVER_ID, lg.getId());
	    //activity.startActivity(intent);

        }
    };

}
