package com.vodocty;

import android.app.Activity;
import android.os.Bundle;
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
	setContentView(R.layout.data_page);
	
	db = DBOpenHelper.getInstance(this);
	model = new DataModel(db);
	
	int lgId = getIntent().getIntExtra(LGsController.LG_ID, -1);
	model.setLGId(lgId);
	
	controller = new DataController(this, model);
	
	       
    }

    @Override
    protected void onResume() {
	super.onResume();
	
	db = DBOpenHelper.getInstance(this);
    }

    @Override
    protected void onPause() {
	super.onPause();
	
	//db.close();
    }

}
