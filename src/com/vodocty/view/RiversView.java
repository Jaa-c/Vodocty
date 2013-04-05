package com.vodocty.view;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;
import com.vodocty.R;
import com.vodocty.model.RiversModel;
import com.vodocty.view.adapters.RiversAdapter;

/**
 *
 * @author Dan Princ
 * @since 23.3.2013
 */
public class RiversView {
    
    private Activity activity;
    private RiversAdapter adapter; 
        
    private Button favButton;
    private ExpandableListView list;
    private View header;
    private TextView head;

    public RiversView(Activity activity, RiversAdapter adapter) {
	this.activity = activity;
	this.adapter = adapter;
		
	favButton = (Button) activity.findViewById(R.id.button_fav);
	list = (ExpandableListView) activity.findViewById(R.id.river_listview);
	header = (View) activity.getLayoutInflater().inflate(R.layout.list_header, null);
	head = (TextView) header.findViewById(R.id.header_row);
	
	head.setText("Všechny řeky:");
	list.addHeaderView(header, null, false);
	list.setAdapter(adapter);
	
	favButton.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.star_simple));
    }
            
    public void setFavButtonListener(OnClickListener listener) {
	favButton.setOnClickListener(listener);
    }
    
    public void setListClickListener(OnChildClickListener listener) {
	list.setOnChildClickListener(listener);
    }
         
}
