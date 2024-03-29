package com.vodocty.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class MainActivity extends FragmentActivity {

	private DBOpenHelper db; //save in sth like global context
	private AbstractMessageReceiver controller;
	private Vodocty context;
	private static final long ALARM_TIME = 1000 * 60 * 30;//30 min

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		context = (Vodocty) getApplicationContext();
		db = context.getDatabase();

		AlarmManager mgr = (AlarmManager) getSystemService(ALARM_SERVICE);
		Intent intent = new Intent(this, UpdateReciever.class);
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		mgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), ALARM_TIME, pi);

		if (context.getFavorites() > 0) {
			setContentView(R.layout.lgs);
			FavoritesModel model = new FavoritesModel(context);
			controller = new FavoritesController(this, model);
			context.setDisplayFavorites(true);
		} else {
			setContentView(R.layout.rivers);
			RiversModel model = new RiversModel(context);
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
		bindService(i, controller.getServiceConnection(), Context.BIND_AUTO_CREATE);
		//Log.d(this.getClass().getName(), "binding service: " + b);
	}

	public void checkFavoritesView() {

		if (context.isChangeDispFavorites()) {

			if (context.isDisplayFavorites()) {
				setContentView(R.layout.lgs);
				FavoritesModel model = new FavoritesModel(context);
				controller = new FavoritesController(this, model);
			} else {
				setContentView(R.layout.rivers);
				RiversModel model = new RiversModel(context);
				controller = new RiversController(this, model);
			}
			bindService();
			context.setChangeDispFavorites(false);
		} else {
			controller.updateData();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case R.id.menu_info:
				Intent i = new Intent(this, InfoActivity.class);
				startActivity(i);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}