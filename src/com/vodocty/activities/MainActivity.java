package com.vodocty.activities;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import com.vodocty.R;
import com.vodocty.Vodocty;
import com.vodocty.controllers.AbstractMessageReceiver;
import com.vodocty.controllers.FavoritesController;
import com.vodocty.controllers.RiversController;
import com.vodocty.database.DBOpenHelper;
import com.vodocty.model.FavoritesModel;
import com.vodocty.model.RiversModel;
import com.vodocty.update.Update;
import com.vodocty.update.UpdateReciever;

public class MainActivity extends Activity {
    
    private DBOpenHelper db; //save in sth like global context
    private AbstractMessageReceiver controller;
    private Vodocty context; 
    
    private static final long ALARM_TIME = 1000 * 60 * 30;//30 min
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	
	context = (Vodocty) getApplicationContext();
	db = context.getDatabase();
		
	AlarmManager mgr = (AlarmManager) getSystemService(ALARM_SERVICE);
	Intent intent = new Intent(this, UpdateReciever.class);
	PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	mgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), ALARM_TIME,  pi);
	
	if(context.getFavorites() > 0) {
	    setContentView(R.layout.lgs);
	    FavoritesModel model = new FavoritesModel(db);
	    controller = null;//new FavoritesController(this, model);
	    context.setDisplayFavorites(true);
	}
	else {
	    setContentView(R.layout.rivers);
	    RiversModel model = new RiversModel(db);
	    controller = new RiversController(this, model);
	    context.setDisplayFavorites(false);
	}
		
    }

    @Override
    protected void onResume() {
	super.onResume();
	
	this.checkFavoritesView();
	bindService();
    }

    @Override
    protected void onPause() {
	super.onPause();
	unbindService(controller.getServiceConnection());
    }
    
    
    private void bindService() {
	Intent i = new Intent(this, Update.class);
	boolean b = bindService(i, controller.getServiceConnection(), Context.BIND_AUTO_CREATE);
	//Log.d(this.getClass().getName(), "binding service: " + b);
    }
    
    
    public void checkFavoritesView() {
	
	if(context.isChangeDispFavorites()) {
	    //unbindService(controller.getServiceConnection());
	    
	    if(context.isDisplayFavorites()) {
		setContentView(R.layout.lgs);
		FavoritesModel model = new FavoritesModel(db);
		controller = null;//new FavoritesController(this, model);
	    }
	    else {
		setContentView(R.layout.rivers);
		RiversModel model = new RiversModel(db);
		controller = new RiversController(this, model);
	    }
	    bindService();
	    context.setChangeDispFavorites(false);
	}
	
    }
   


    
    
    
}