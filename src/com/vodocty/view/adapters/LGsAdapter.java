package com.vodocty.view.adapters;

import android.app.Activity;
import android.text.Html;
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
    private boolean displayFavorites;
    
    public LGsAdapter(Activity activity, int layoutResourceId, List<LG> data) {
        super(activity, layoutResourceId, data);
    
	this.activity = activity;
	this.layoutResourceId = layoutResourceId;
	this.data = data;
	this.displayFavorites = false;
    
    }
    
    public void setData(List<LG> data) {
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
	    content.data = (TextView) row.findViewById(R.id.lg_row_right);
        }
	else {
	    content = (LGData) row.getTag();
	}
	row.setTag(content);
	
	LG lg = data.get(position);
	if(displayFavorites) {
	    content.name.setText(lg.getRiver().getName() + " - " + lg.getName());
	}
	else {
	    content.name.setText(lg.getName());
	}
	String height = lg.getCurrentHeight() == 0 ? "" : lg.getCurrentHeight() + " cm";
	content.data.setText(Html.fromHtml(height + "<br>" 
		+ lg.getCurrentVolume() + " m<small><sup>3</sup></small>/s"));
		
	switch(lg.getCurrentFlood()) {
	    case 0:
		row.setBackgroundResource(R.drawable.selector_grey);
		content.data.setBackgroundResource(R.drawable.selector_grey);
		break;
	    case 1:
		row.setBackgroundResource(R.drawable.selector_flood_yellow);
		break;
	    case 2:
		row.setBackgroundResource(R.drawable.selector_flood_orange);
		break;
	    case 3:
		row.setBackgroundResource(R.drawable.selector_flood_red);
		break;
	    default:
		row.setBackgroundResource(R.drawable.selector_grey);
		break;
	    }
	
	
	return row;
    }

    @Override
    public Object getItem(int position) {
	return data.get(position);
    }
    
    
    
    public void setDisplayFavorites(boolean fav) {
	this.displayFavorites = fav;
    }

    
    
    static final class LGData {
	protected TextView name;
	protected TextView data;
	
    }

}
