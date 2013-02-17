package com.vodocty;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.vodocty.controllers.MainListController;
import com.vodocty.data.River;
import com.vodocty.database.DBOpenHelper;
import com.vodocty.update.Update;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    
    DBOpenHelper db; //save in sth like global context
    MainListController controller;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	
	db = new DBOpenHelper(this);
	controller = new MainListController(this);
	
	Update u = new Update(db, this); //temp
	try {
	    u.doUpdate();
	}
	catch(SQLException e) {
	    Log.e(MainActivity.class.getName(), e.getLocalizedMessage());
	    e.printStackTrace(); //debug
	    Toast.makeText(this, "Update error", Toast.LENGTH_LONG);
	}
	
	       
        setContentView(R.layout.main);
	
	List<String> test = new ArrayList<String>(); 
	
	List<River> rivers = null;
	try {    
	    rivers = db.getRiverDao().queryForAll();
	} catch (SQLException ex) {}
	
	for(River r : rivers) {
	    test.add(r.getName());
	}
	
	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, test);
	
	
	ListView listView = (ListView) findViewById(R.id.listview);
	listView.setAdapter(adapter);
	
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