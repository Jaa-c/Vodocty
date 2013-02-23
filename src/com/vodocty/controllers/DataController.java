package com.vodocty.controllers;

import android.app.Activity;
import android.text.Html;
import android.widget.TextView;
import com.vodocty.R;
import com.vodocty.data.Data;
import com.vodocty.model.DataModel;
import java.util.Calendar;
import java.util.Date;
import tools.Tools;

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
	
	Calendar c = Calendar.getInstance();
	c.setTime(data.getDate());
	
	
	String date = Tools.isToday(c) ? "dnes" : 
		c.get(Calendar.DAY_OF_MONTH) + "." + c.get(Calendar.MONTH) + "." + c.get(Calendar.YEAR);
	date += " " + (c.get(Calendar.HOUR_OF_DAY) < 10 ? "0" : "") + c.get(Calendar.HOUR_OF_DAY) + ":" + 
		(c.get(Calendar.MINUTE) < 10 ? "0" : "") + c.get(Calendar.MINUTE);
	
	text.setText(date);
	
	text = (TextView) activity.findViewById(R.id.data_page_height);
	text.setText(data.getHeight() + "cm");
	
	text = (TextView) activity.findViewById(R.id.data_page_volume);
	text.setText(Html.fromHtml(data.getVolume() + "m<small><sup>3</sup></small>/s"));
	
    }

}
