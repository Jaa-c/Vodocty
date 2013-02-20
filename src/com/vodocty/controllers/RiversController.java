package com.vodocty.controllers;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.vodocty.R;
import com.vodocty.data.River;
import com.vodocty.database.DBOpenHelper;
import com.vodocty.model.RiversModel;
import com.vodocty.view.adapters.RiversAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dan Princ
 * @since 17.2.2013
 */
public class RiversController {
    
    private Activity activity;
    RiversModel model;
    private ArrayAdapter adapter;
    
    public RiversController(Activity activity, RiversModel model) {
	
	this.activity = activity;
	this.model = model;
	//adapter = new MyListAdapter(context, R.layout.list_item_row, this.data.getData());
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
            //Intent intent = new Intent(context, DetailListActivity.class);
            //predame seznam, ktery chceme zobrazit
            River river = (River) adapter.getItem(position);

	    
	    Log.d(RiversController.class.getName(), river.getName() + "");

        }
    };

}
