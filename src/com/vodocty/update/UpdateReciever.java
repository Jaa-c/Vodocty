package com.vodocty.update;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 *
 * @author Dan Princ
 * @since 24.2.2013
 */
public class UpdateReciever extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent i) {
		Intent intent = new Intent(context, Update.class);
		context.startService(intent);
	}
}
