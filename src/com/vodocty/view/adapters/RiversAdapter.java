package com.vodocty.view.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.vodocty.R;
import com.vodocty.data.River;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Adapter for the river list.
 * 
 * For now, one row contains only one String with name,
 * but I expect to add some more data some time...
 * 
 * @author Dan Princ
 * @since 20.2.2013
 */
public class RiversAdapter extends BaseExpandableListAdapter {
    
    private Activity activity;
    private int layoutResourceId;
    private List<River> data;
    
    private Map<String, List<River>> groups;
    private List<String> indicies;
    
    public RiversAdapter(Activity activity, int layoutResourceId, List<River> data) {
        super();
   
	this.activity = activity;
	this.layoutResourceId = layoutResourceId;
	this.data = data;
	
	groups = new HashMap<String, List<River>>();
	indicies = new ArrayList<String>();
	char letter = '.';
	List<River> current = null;
	for(River river : data) {
	    char first = river.getName().charAt(0);
	    if(first != letter) {
		letter = first;
		current = new ArrayList<River>();
		groups.put(letter + "", current);
		indicies.add(letter + "");
	    }
	    current.add(river);
	}
    
    }
    
    
    public View getGroupView (int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
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
	
	content.name.setText("     " + indicies.get(groupPosition));
	return row;
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
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
	
	River river = groups.get(indicies.get(groupPosition)).get(childPosition);
	content.name.setText(river.getName());
	
	return row;
    }

    public int getGroupCount() {
	return groups.size();
    }

    public int getChildrenCount(int i) {
	return groups.get(indicies.get(i)).size();
    }

    public Object getGroup(int i) {
	return groups.get(indicies.get(i));
    }

    public Object getChild(int i, int j) {
	return groups.get(indicies.get(i)).get(j);
    }
    
    public long getGroupId(int i) {
	return i;
    }

    public long getChildId(int i, int j) {
	return ((River) getChild(i,j)).getId();
    }

    public boolean hasStableIds() {
	return true;
    }

    public boolean isChildSelectable(int arg0, int arg1) {
	return true;
    }
    
    
    static class RiverData {
	protected TextView name;
    }

}
