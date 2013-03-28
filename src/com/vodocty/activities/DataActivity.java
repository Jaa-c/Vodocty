package com.vodocty.activities;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.WindowManager;
import com.vodocty.R;
import com.vodocty.Vodocty;
import com.vodocty.controllers.DataController;
import com.vodocty.database.DBOpenHelper;
import com.vodocty.model.DataModel;

/**
 *
 * @author Dan Princ
 * @since 21.2.2013
 */
public class DataActivity extends FragmentActivity {
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
	model = new DataModel((Vodocty) getApplicationContext());
	
	int lgId = getIntent().getIntExtra(Vodocty.EXTRA_LG_ID, -1);
	Log.d("dataactivity", "lg:" +lgId);
	model.setLGId(lgId);
	
	controller = new DataController(this, model);	 
	
	
	Log.d("activity", "oncreate");
    }

    @Override
    protected void onResume() {
	super.onResume();
	
    }

    @Override
    protected void onPause() {
	super.onPause();
	Log.d("activity", "onPause");
	
    }
    
    @Override
    protected void onStop() {
        super.onStop();
	Log.d("activity", "onStop");
    }
    
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
	Log.d("activity", "onDestroy");
    }
    
//    @Override
//    public void onBackPressed () {
//	super.moveTaskToBack(true);
//	//moveTaskToBack(true);
//    }


}
