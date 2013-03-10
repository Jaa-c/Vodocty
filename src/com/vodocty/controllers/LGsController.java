package com.vodocty.controllers;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.vodocty.DataActivity;
import com.vodocty.R;
import com.vodocty.data.LG;
import com.vodocty.model.LGsModel;
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
    
    public static final String LG_ID = "lgId";
    
    public LGsController(Activity activity, LGsModel model) {
	
	this.activity = activity;
	this.model = model;
	
        ListView list = (ListView) activity.findViewById(R.id.listview);
	adapter = new LGsAdapter(activity, R.layout.list_lg_row, model.getLGs());
	
	View header = (View) activity.getLayoutInflater().inflate(R.layout.list_header, null);
	TextView head = (TextView) header.findViewById(R.id.header_row);
	head.setText(model.getRiver().getName() + ":");
	list.addHeaderView(header, null, false);
	
	list.setAdapter(adapter);
        list.setOnItemClickListener(listClickHandler);
	
    }
    
    private AdapterView.OnItemClickListener listClickHandler = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            
	    Intent intent = new Intent(activity, DataActivity.class);
            //predame seznam, ktery chceme zobrazit
            LG lg = (LG) adapter.getItem(position-1);
	    Log.d("lgcontroller", "position: " + position);
	    Log.d("lgcontroller", lg.toString());
	    
	    intent.putExtra(LG_ID, lg.getId());
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
