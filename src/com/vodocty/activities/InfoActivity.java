package com.vodocty.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import com.vodocty.R;

/**
 *
 * @author Dan Princ
 * @since 3.4.2013
 */
public class InfoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.info_page);
	TextView content = (TextView) findViewById(R.id.info_text);
	content.setText(Html.fromHtml(getString(R.string.about)));
	
	
    }
    
    
    
    

}
