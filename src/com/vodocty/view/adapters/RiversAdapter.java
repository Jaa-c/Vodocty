package com.vodocty.view.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.vodocty.R;
import com.vodocty.data.River;
import java.util.List;

/**
 * Adapter for the river list.
 * 
 * For now, one row contains only one String with name,
 * but I expect to add some more data some time...
 * 
 * @author Dan Princ
 * @since 20.2.2013
 */
public class RiversAdapter extends ArrayAdapter {
    
    private Activity activity;
    private int layoutResourceId;
    private List<River> data;
    
    public RiversAdapter(Activity activity, int layoutResourceId, List<River> data) {
        super(activity, layoutResourceId, data);
    
	this.activity = activity;
	this.layoutResourceId = layoutResourceId;
	this.data = data;
    
    }
    
    /**
     * Called for each row
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	View row = convertView;
	RiverData content;
	
	if(row == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
	    
	    content = new RiverData();
	    content.name = (TextView) row.findViewById(R.id.river_row);
        }
	else {
	    content = (RiverData) row.getTag();
	}
	row.setTag(content);
	
	River river = data.get(position);
	content.name.setText(river.getName());
	
	return row;
    }
    
    
    static class RiverData {
	protected TextView name;
    }

}
