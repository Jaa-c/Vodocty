package com.vodocty.view.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.vodocty.R;
import com.vodocty.data.LG;
import java.util.List;

/**
 *
 * @author Dan Princ
 * @since 20.2.2013
 */
public class LGsAdapter extends ArrayAdapter {
    
    private Activity activity;
    private int layoutResourceId;
    private List<LG> data;
    
    public LGsAdapter(Activity activity, int layoutResourceId, List<LG> data) {
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
	LGData content;
	
	if(row == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
	    
	    content = new LGData();
	    content.name = (TextView) row.findViewById(R.id.lg_row);
        }
	else {
	    content = (LGData) row.getTag();
	}
	row.setTag(content);
	LG lg = data.get(position);
	content.name.setText(lg.getName());
	
	return row;
    }
    
    
    
    
    static class LGData {
	protected TextView name;
	
    }

}
