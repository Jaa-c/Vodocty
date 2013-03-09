package com.vodocty.controllers;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import com.vodocty.update.Update;

/**
 * Handles message receiving in controlers.
 * 
 * @author Dan Princ
 * @since 8.3.2013
 */
public abstract class AbstractMessageReceiver {
    
    /** Target we publish for clients to send messages to IncomingHandler. */
    private final Messenger mMessenger  = new Messenger(new IncomingHandler());

    private final ServiceConnection mConnection = new MyServiceConnection();
    
    public abstract void updateData();
    
    public final ServiceConnection getServiceConnection() {
	return mConnection;
    }
    
    /**
    * Handler of incoming messages from service.
    */
    private final class IncomingHandler extends Handler {

	@Override
	public void handleMessage(Message msg) {
	    switch (msg.what) {
		case Update.MSG_UPDATE:
		    updateData();
		    //Log.i(this.getClass().getName(), "Recieved message from Update!");
		default:
		    super.handleMessage(msg);
	    }
	}

    }
   
    /**
     * Class for interacting with the main interface of the service.
     */
    private final class MyServiceConnection implements ServiceConnection {

	protected Messenger m;

	@Override
	public void onServiceConnected(ComponentName arg0, IBinder service) {
	     m = new Messenger(service);
	     try {
		 Message msg = Message.obtain(null, Update.MSG_REGISTER);
		 msg.replyTo = mMessenger;
		 m.send(msg);
	     }
	     catch(RemoteException e) {
		 Log.e(AbstractMessageReceiver.class.getName(), e.getLocalizedMessage());
	    }
	}

	@Override
	 public void onServiceDisconnected(ComponentName arg0) {
	     m = null;
	 }

    }


}
