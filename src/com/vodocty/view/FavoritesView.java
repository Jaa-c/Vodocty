package com.vodocty.view;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.vodocty.R;
import com.vodocty.view.adapters.LGsAdapter;

/**
 *
 * @author Dan Princ
 * @since 23.3.2013
 */
public class FavoritesView {
    
    private Activity activity;
    private LGsAdapter adapter; 
        
    private Button favButton;
    private ListView list;
    private View header;
    private TextView head;

    public FavoritesView(Activity activity, LGsAdapter adapter) {
	this.activity = activity;
	this.adapter = adapter;
	
	
	list = (ListView) activity.findViewById(R.id.lg_listview);
	header = (View) activity.getLayoutInflater().inflate(R.layout.list_header, null);
	head = (TextView) header.findViewById(R.id.header_row);
	favButton = (Button) activity.findViewById(R.id.button_fav);
		
	head.setText("Oblíbené vodočty:");
	list.addHeaderView(header, null, false);
	list.setAdapter(adapter);
	
	favButton.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.star_color));
	
    }
    
            
    public void setFavButtonListener(OnClickListener listener) {
	favButton.setOnClickListener(listener);
    }
             
    public void setListClickListener(OnItemClickListener listener) {
	list.setOnItemClickListener(listener);
    }
    
    
    

}
