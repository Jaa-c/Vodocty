package com.vodocty.controllers;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.vodocty.LGsActivtiy;
import com.vodocty.R;
import com.vodocty.data.River;
import com.vodocty.model.RiversModel;
import com.vodocty.view.adapters.RiversAdapter;

/**
 *
 * @author Dan Princ
 * @since 17.2.2013
 */
public class RiversController {
    
    private Activity activity;
    RiversModel model;
    private ArrayAdapter adapter; 
    
    public static String RIVER_ID = "riverId";
    
    public RiversController(Activity activity, RiversModel model) {
	
	this.activity = activity;
	this.model = model;
	
        ListView list = (ListView) activity.findViewById(R.id.listview);
	
	adapter = new RiversAdapter(activity, R.layout.list_river_row, model.getRivers());
	
	list.setAdapter(adapter);
        list.setOnItemClickListener(listClickHandler);
	
    
    }
    
    /**
     * anonymní listener na všechny položky v seznamu
     */
    private OnItemClickListener listClickHandler = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            
	    Intent intent = new Intent(activity, LGsActivtiy.class);
            //predame seznam, ktery chceme zobrazit
            River river = (River) adapter.getItem(position);
	    
	    intent.putExtra(RIVER_ID, river.getId());
	    activity.startActivity(intent);

        }
    };

}
