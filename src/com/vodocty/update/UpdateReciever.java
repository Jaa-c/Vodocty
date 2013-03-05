package com.vodocty.update;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.vodocty.MainActivity;
import com.vodocty.R;
import com.vodocty.database.DBOpenHelper;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 *
 * @author Dan Princ
 * @since 24.2.2013
 */
public class UpdateReciever extends BroadcastReceiver {
    
    private DBOpenHelper db;
    public static final int NOTI_ID = 1;
    
 
    @Override
    public void onReceive(Context context, Intent intent) {

	NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    
	Intent notificationIntent = new Intent(context, MainActivity.class);
	PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
	Notification notify = new Notification(R.drawable.ic_launcher,
						    "Vodocty: updating data",
						    System.currentTimeMillis());
	notify.setLatestEventInfo(context, "Vodocty", "Update in progress", contentIntent);
	
	//Set default vibration
	notify.defaults |= Notification.FLAG_ONLY_ALERT_ONCE;
	notify.defaults |= Notification.FLAG_ONGOING_EVENT;
	notify.defaults |= Notification.DEFAULT_LIGHTS;
	
	mNotificationManager.notify(NOTI_ID, notify);
		
	db = DBOpenHelper.getInstance(context);
	
	
	Thread updateThread = new Thread(new Update(db, context, mNotificationManager));
	if(!Update.running) {
	    updateThread.start();
	}
    }

}
