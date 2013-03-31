package com.vodocty.view;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import com.vodocty.R;

/**
 *
 * @author Dan Princ
 * @since 31.3.2013
 */
public class CommonHeaderView {
    
    private final Activity activity;
    
    private Button flagButton;
    private Button menuButton;
    
    public CommonHeaderView(Activity activity) {
	this.activity = activity;
    
	
	flagButton = (Button) activity.findViewById(R.id.button_left);
	menuButton = (Button) activity.findViewById(R.id.button_right);
	
	flagButton.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.cz));
	menuButton.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.menu));
    }
    
    public void setFlagButtonListener(View.OnClickListener listener) {
	flagButton.setOnClickListener(listener);
    }
    
    public void setMenuButtonListener(View.OnClickListener listener) {
	menuButton.setOnClickListener(listener);
    }

}
