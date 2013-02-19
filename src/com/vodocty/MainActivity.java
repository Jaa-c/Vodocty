package com.vodocty;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.vodocty.controllers.RiversController;
import com.vodocty.database.DBOpenHelper;
import com.vodocty.model.RiversModel;
import com.vodocty.update.Update;
import java.sql.SQLException;

public class MainActivity extends Activity {
    
    private DBOpenHelper db; //save in sth like global context
    private RiversController controller;
    private RiversModel model;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);
	
	db = new DBOpenHelper(this);
	model = new RiversModel(db);
	controller = new RiversController(this, model);
	
	Update u = new Update(db, this); //temp
	try {
	    u.doUpdate();
	}
	catch(SQLException e) {
	    Log.e(MainActivity.class.getName(), e.getLocalizedMessage());
	    e.printStackTrace(); //debug
	    Toast.makeText(this, "Update error", Toast.LENGTH_LONG);
	}
	
	       
    }

    @Override
    protected void onResume() {
	super.onResume();
	
	db = new DBOpenHelper(this);
    }

    @Override
    protected void onPause() {
	super.onPause();
	
	db.close();
    }
    
    
    
}