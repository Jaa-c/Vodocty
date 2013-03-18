package com.vodocty.controllers;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.vodocty.R;
import com.vodocty.Vodocty;
import com.vodocty.activities.DataActivity;
import com.vodocty.activities.MainActivity;
import com.vodocty.data.LG;
import com.vodocty.model.FavoritesModel;
import com.vodocty.view.adapters.LGsAdapter;

/**
 *
 * @author Dan Princ
 * @since 11.3.2013
 */
public class FavoritesController extends AbstractMessageReceiver {
    
    private Activity activity;
    private FavoritesModel model;
    private LGsAdapter adapter; 
    
    private Button favButton;
    
    public FavoritesController(Activity activity, FavoritesModel model) {
	
	this.activity = activity;
	this.model = model;
	
        ListView list = (ListView) activity.findViewById(R.id.lg_listview);
	adapter = new LGsAdapter(activity, R.layout.list_lg_row, model.getFavoriteLGs());
	adapter.setDisplayFavorites(true);
	
	View header = (View) activity.getLayoutInflater().inflate(R.layout.list_header, null);
	TextView head = (TextView) header.findViewById(R.id.header_row);
	head.setText("Oblíbené vodočty:");
	list.addHeaderView(header, null, false);
	
	list.setAdapter(adapter);
        list.setOnItemClickListener(listClickHandler);
	
	favButton = (Button) activity.findViewById(R.id.button_fav);
	favButton.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.star_simple));
	favButton.setOnClickListener(favButtonListener);
    
    }
    
    private AdapterView.OnItemClickListener listClickHandler = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            
	    Intent intent = new Intent(activity, DataActivity.class);
            //predame seznam, ktery chceme zobrazit
            LG lg = (LG) adapter.getItem(position-1);
	    Log.d("lgcontroller", "position: " + position);
	    Log.d("lgcontroller", lg.toString());
	    
	    intent.putExtra(Vodocty.EXTRA_LG_ID, lg.getId());
	    activity.startActivity(intent);

        }
    };
    

    @Override
    public void updateData() {
	model.invalidate();
    }
    
    private View.OnClickListener favButtonListener = new View.OnClickListener() {
	public void onClick(View arg0) {
	    Vodocty vodocty = (Vodocty) activity.getApplicationContext();
	    vodocty.setDisplayFavorites(false);
	    vodocty.setChangeDispFavorites(true);
	    ((MainActivity) activity).checkFavoritesView();
	}
    };

}
