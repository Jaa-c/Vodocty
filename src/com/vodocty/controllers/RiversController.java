package com.vodocty.controllers;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;
import com.vodocty.activities.LGsActivtiy;
import com.vodocty.R;
import com.vodocty.Vodocty;
import com.vodocty.activities.MainActivity;
import com.vodocty.data.River;
import com.vodocty.model.RiversModel;
import com.vodocty.view.adapters.RiversAdapter;

/**
 *
 * @author Dan Princ
 * @since 17.2.2013
 */
public class RiversController extends AbstractMessageReceiver {
    
    private Activity activity;
    private RiversModel model;
    private RiversAdapter adapter; 
    
    private Button favButton;
    
    public RiversController(Activity activity, RiversModel model) {
	
	this.activity = activity;
	this.model = model;
	
        ExpandableListView list = (ExpandableListView) activity.findViewById(R.id.river_listview);
	
	View header = (View) activity.getLayoutInflater().inflate(R.layout.list_header, null);
	TextView head = (TextView) header.findViewById(R.id.header_row);
	head.setText("Všechny řeky:");
	list.addHeaderView(header, null, false);
	
	adapter = new RiversAdapter(activity, R.layout.list_river_row, model.getRivers());
	
	list.setAdapter(adapter);
        //list.setOnItemClickListener(listClickHandler);
	list.setOnChildClickListener(childListClickHandler);
	
	favButton = (Button) activity.findViewById(R.id.button_fav);
	favButton.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.star_simple));
	favButton.setOnClickListener(favButtonListener);
	
    
    }
    
    /**
     * anonymní listener na všechny položky v seznamu
     */
    private OnChildClickListener childListClickHandler = new OnChildClickListener() {
	
	public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
	    Intent intent = new Intent(activity, LGsActivtiy.class);
            //predame seznam, ktery chceme zobrazit
            River river = (River) adapter.getChild(groupPosition, childPosition);
	    
	    intent.putExtra(Vodocty.EXTRA_RIVER_ID, river.getId());
	    activity.startActivity(intent);
	    return true;
	}
    };

    @Override
    public void updateData() {
	model.invalidateData();
	adapter.setData(model.getRivers());
	adapter.notifyDataSetChanged();
    }
    
    private View.OnClickListener favButtonListener = new View.OnClickListener() {
	public void onClick(View arg0) {
	    Vodocty vodocty = (Vodocty) activity.getApplicationContext();
	    vodocty.setDisplayFavorites(true);
	    vodocty.setChangeDispFavorites(true);
	    ((MainActivity) activity).checkFavoritesView();
	}
    };
    

}
