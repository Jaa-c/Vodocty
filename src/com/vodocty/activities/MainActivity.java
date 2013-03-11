package com.vodocty.activities;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import com.vodocty.R;
import com.vodocty.Vodocty;
import com.vodocty.controllers.RiversController;
import com.vodocty.data.Settings;
import com.vodocty.database.DBOpenHelper;
import com.vodocty.model.RiversModel;
import com.vodocty.update.Update;
import com.vodocty.update.UpdateReciever;

public class MainActivity extends Activity {
    
    private DBOpenHelper db; //save in sth like global context
    private RiversController controller;
    private RiversModel model;
    
    private static final long ALARM_TIME = 1000 * 60 * 30;//30 min
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.rivers);
	
	db = ((Vodocty) getApplicationContext()).getDatabase();
	//if(there are favorites)
	//launch new favoritesActivity
	model = new RiversModel(db);
	controller = new RiversController(this, model);
	
	bindService();
	
	AlarmManager mgr = (AlarmManager) getSystemService(ALARM_SERVICE);
	Intent intent = new Intent(this, UpdateReciever.class);
	PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	mgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), ALARM_TIME,  pi);
	
    }

    @Override
    protected void onResume() {
	super.onResume();
	//if there are favorites display
	    //this.onBackPressed();
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