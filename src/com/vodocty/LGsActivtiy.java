package com.vodocty;

import android.app.Activity;
import android.os.Bundle;
import com.vodocty.controllers.LGsController;
import com.vodocty.controllers.RiversController;
import com.vodocty.database.DBOpenHelper;
import com.vodocty.model.LGsModel;

/**
 *
 * @author Dan Princ
 * @since 20.2.2013
 */
public class LGsActivtiy extends Activity {
    
    private DBOpenHelper db; //save in sth like global context
    private LGsController controller;
    private LGsModel model;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);
	
	db = DBOpenHelper.getInstance(this);
	model = new LGsModel(db);
	
	int riverId = getIntent().getIntExtra(RiversController.RIVER_ID, -1);
	model.setRiverId(riverId);
	
	controller = new LGsController(this, model);
	
	       
    }

    @Override
    protected void onResume() {
	super.onResume();
	
	db = DBOpenHelper.getInstance(this);
    }

    @Override
    protected void onPause() {
	super.onPause();
	
	db.close();
    }
    
    
    
}

