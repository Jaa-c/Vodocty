package com.vodocty.controllers;

import android.app.Activity;
import android.widget.TextView;
import com.vodocty.R;
import com.vodocty.data.Data;
import com.vodocty.model.DataModel;

/**
 *
 * @author Dan Princ
 * @since 21.2.2013
 */
public class DataController {
    
    private Activity activity;
    private DataModel model;
    
    public DataController(Activity activity, DataModel model) {
	
	this.activity = activity;
	this.model = model;
	
	Data data = model.getLastData();
	
        TextView text = (TextView) activity.findViewById(R.id.data_page_heading);
	text.setText(data.getLg().getRiver().getName() + "\n" + 
		data.getLg().getName() );
	
	text = (TextView) activity.findViewById(R.id.data_page_date);
	text.setText(data.getDate().toLocaleString());
	
	text = (TextView) activity.findViewById(R.id.data_page_height);
	text.setText(data.getHeight() + "cm");
	
	text = (TextView) activity.findViewById(R.id.data_page_volume);
	text.setText(data.getVolume() + "m^3/s");
	
    }

}
