package com.vodocty.view;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.vodocty.R;
import com.vodocty.data.River;
import com.vodocty.view.adapters.LGsAdapter;

/**
 *
 * @author Dan Princ
 * @since 23.3.2013
 */
public class LGsView {
    
    private Activity activity;
    private LGsAdapter adapter;
    
    private ListView list;
    private TextView head;
    private View header;
    private Button favButton;

    public LGsView(Activity activity, LGsAdapter adapter) {
	this.activity = activity;
	this.adapter = adapter;
	
	list = (ListView) activity.findViewById(R.id.lg_listview);
	favButton = (Button) activity.findViewById(R.id.button_fav);
	
	header = (View) activity.getLayoutInflater().inflate(R.layout.list_header, null);
	head = (TextView) header.findViewById(R.id.header_row);
	
	list.addHeaderView(header, null, false);
	list.setAdapter(adapter);
	
	favButton.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.star_simple));
    }
    
    public void setContent(River river) {
	head.setText(river.getName() + ":");
    }
    
       
    public void setListClickListener(OnItemClickListener listener) {
	list.setOnItemClickListener(listener);
    }
            
    public void setFavButtonListener(OnClickListener listener) {
	favButton.setOnClickListener(listener);
    }
    
}
