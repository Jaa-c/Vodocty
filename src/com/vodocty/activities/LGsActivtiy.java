package com.vodocty.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import com.vodocty.R;
import com.vodocty.Vodocty;
import com.vodocty.controllers.LGsController;
import com.vodocty.controllers.RiversController;
import com.vodocty.database.DBOpenHelper;
import com.vodocty.model.LGsModel;
import com.vodocty.update.Update;

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
	setContentView(R.layout.lgs);
	
	db = ((Vodocty) getApplicationContext()).getDatabase();
	model = new LGsModel(db);
	
	int riverId = getIntent().getIntExtra(RiversController.RIVER_ID, -1);
	model.setRiverId(riverId);
	
	controller = new LGsController(this, model);
	
	bindService();
	       
    }

    @Override
    protected void onResume() {
	super.onResume();
	bindService();
	
    }

    @Override
    protected void onPause() {
	super.onPause();
	unbindService(sConn);
	//db.close();
    }
    
    
    private ServiceConnection sConn;
    
    private void bindService() {
	if(sConn == null) {
	    sConn = controller.getServiceConnection();
	}
	
	Intent i = new Intent(this, Update.class);
	bindService(i, sConn, Context.BIND_AUTO_CREATE);
    }
    
    
}

