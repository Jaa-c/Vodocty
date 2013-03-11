package com.vodocty.activities;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.WindowManager;
import com.vodocty.R;
import com.vodocty.Vodocty;
import com.vodocty.controllers.DataController;
import com.vodocty.controllers.LGsController;
import com.vodocty.database.DBOpenHelper;
import com.vodocty.model.DataModel;

/**
 *
 * @author Dan Princ
 * @since 21.2.2013
 */
public class DataActivity extends Activity {
    private DBOpenHelper db; //save in sth like global context
    private DataController controller;
    private DataModel model;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	getWindow().setFormat(PixelFormat.RGBA_8888);
	getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
	
	setContentView(R.layout.data_page);
	
	db = ((Vodocty) getApplicationContext()).getDatabase();
	model = new DataModel(db);
	
	int lgId = getIntent().getIntExtra(LGsController.LG_ID, -1);
	model.setLGId(lgId);
	
	controller = new DataController(this, model);
	controller.execute();
	       
    }

    @Override
    protected void onResume() {
	super.onResume();
	
    }

    @Override
    protected void onPause() {
	super.onPause();
	
    }

}
