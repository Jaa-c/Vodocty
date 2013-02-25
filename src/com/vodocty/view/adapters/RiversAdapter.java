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
    
    private Map<String, List<River>> groups;
    private List<String> indices;
    
    public RiversAdapter(Activity activity, int layoutResourceId, List<River> data) {
        super();
   
	this.activity = activity;
	this.layoutResourceId = layoutResourceId;
	
	groups = new HashMap<String, List<River>>();
	indices = new ArrayList<String>();
	char letter = '.';
	List<River> current = null;
	for(River river : data) {
	    char first = river.getName().charAt(0);
	    if(first != letter) {
		letter = first;
		current = new ArrayList<River>();
		groups.put(letter + "", current);
		indices.add(letter + "");
	    }
	    current.add(river);
	}
    
    }
    
    public View getView(String text, View convertView, ViewGroup parent) {
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
	
	content.name.setText(text);
	return row;
    }
    
    
    public View getGroupView (int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
	return getView("     " + indices.get(groupPosition), convertView, parent);
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
	River river = groups.get(indices.get(groupPosition)).get(childPosition);
	return getView(river.getName(), convertView, parent);
    }

    public int getGroupCount() {
	return groups.size();
    }

    public int getChildrenCount(int i) {
	return groups.get(indices.get(i)).size();
    }

    public Object getGroup(int i) {
	return groups.get(indices.get(i));
    }

    public Object getChild(int i, int j) {
	return groups.get(indices.get(i)).get(j);
    }
    
    public long getGroupId(int i) {
	return i;
    }

    public long getChildId(int i, int j) {
	return ((River) getChild(i,j)).getId();
    }

    public boolean hasStableIds() {
	return true;//not rly for groups
    }

    public boolean isChildSelectable(int arg0, int arg1) {
	return true;
    }
    
    
    static class RiverData {
	protected TextView name;
    }

}
