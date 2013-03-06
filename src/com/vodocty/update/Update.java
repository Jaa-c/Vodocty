package com.vodocty.update;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.PowerManager;
import android.util.Log;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.vodocty.R;
import com.vodocty.data.Country;
import com.vodocty.data.Data;
import com.vodocty.data.LG;
import com.vodocty.data.River;
import com.vodocty.data.Settings;
import com.vodocty.database.DBOpenHelper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Handles the regular data update.
 * 
 * @author Dan Princ
 * @since long time ago
 */
public class Update implements Runnable {
    private DBOpenHelper db;
    private Context context;
    private NotificationManager notifM;
    private Settings lastUpdate;
    
    private static final String GZ = ".xml.gz";
    
    public static boolean running = false;
    
    public Update(DBOpenHelper db, Context c, NotificationManager mNotificationManager) {
	this.db = db;
	this.context = c;
	this.notifM = mNotificationManager;
		
	try {
	    Dao<Settings, Integer> sdao = db.getSettingsDao();
	    QueryBuilder<Settings, Integer> query = sdao.queryBuilder();
	    query.where().eq(Settings.KEY, Settings.LAST_UPDATE);
	    Settings s = query.queryForFirst();
	    if(s == null) {
		lastUpdate = new Settings(Settings.LAST_UPDATE, "0");
	    }
	    else {
		lastUpdate = s;
	    }
	}
	catch(SQLException e) {
	    //better do nothing
	    lastUpdate = new Settings(Settings.LAST_UPDATE, "" + (int) (Calendar.getInstance().getTimeInMillis() / 1000));
	    Log.e(Update.class.getName(), e.getLocalizedMessage());
	}
	
	Log.i(Update.class.getName(),"lastUpdate="+  lastUpdate.getValue());
	
    }
    
    
    public void run() {
	if(isOnline() && !running) {
	    running = true;
	    doUpdate();

	    PowerManager powerManager = (PowerManager) context.getSystemService(Activity.POWER_SERVICE);
	    if(!powerManager.isScreenOn()) {
		db.close(); //TODO
	    }
	}
	else {
	    Log.i(Update.class.getName(), "Not connected to the internet or update already running. Update cancelled.");
	}
	running = false;
	this.notifM.cancel(UpdateReciever.NOTI_ID);
	Log.i(Update.class.getName(), "Update thread finished!");
    }
    
    
    private void doUpdate()  {
	
	Resources res = context.getResources();
	
	TypedArray urls = res.obtainTypedArray(R.array.urls);
	String path = res.getString(R.string.path);
	
	Map<String, River> data = null;
	//foreach feeds for all countries
	for(int i = 0; i < urls.length(); i++) {
	    List<String> files;
	    try {
		files = this.loadInfo(urls.getString(i));
	    }
	    catch(NullPointerException e) {
		Log.e(Update.class.getName(), "download error: " + e.getLocalizedMessage());
		continue;
	    }
	    
	    if(files == null || files.isEmpty()) {
		Log.i(Update.class.getName(), "Nothing to do.");
		return;
	    }
	    
	    //get all new files and parse them
	    for(int j = files.size()-1; j >= 0; j--) {
		InputStream xml = HttpReader.loadGz(path + files.get(j) + GZ);
		
		if(xml == null) {
		    Log.e(Update.class.getName(), "feed offline: " + urls.getString(i));
		    continue;
		}
		try {
		    data = XMLParser.parse(xml, Country.cze, data, Integer.parseInt(files.get(j)));
		} catch (Exception ex) {
		    Log.e(Update.class.getName(), ex.getLocalizedMessage());
		}
		finally {
		    if(data == null) {
			continue;
		    }
		}
		
		//finally update the data in database
		Log.i(Update.class.getName(), "used file: " + files.get(j) + ", files remaining: " + j);
		try {
		    this.updateDatabase(data, files.get(j));
		}
		catch(SQLException e) {
		    Log.e(Update.class.getName(), e.getLocalizedMessage());
		}
		
	    }
	}
    
    }
    
    private void updateDatabase(Map<String, River> data, String time) throws SQLException {
	//update the database:
	Dao<Data, Integer> dataDao = db.getDataDao();
	Dao<River, Integer> riverDao = db.getRiverDao();
	Dao<LG, Integer> lgDao = db.getLgDao();

	int updateTime = Integer.parseInt(time);
	
	SelectArg nameArg = new SelectArg();
	SelectArg riverIdArg = new SelectArg();
	QueryBuilder<LG, Integer> query = lgDao.queryBuilder();
	query.where().eq("name", nameArg).and().eq("river_id", riverIdArg);
	PreparedQuery<LG> preparedQLG = query.prepare();
	
	SelectArg riverArg = new SelectArg();
	SelectArg countryArg = new SelectArg();
	QueryBuilder<River, Integer> riverQuery = riverDao.queryBuilder();
	riverQuery.where().eq("name", riverArg).and().eq("country", countryArg);
	PreparedQuery<River> preparedQRiver = riverQuery.prepare();
	
	for(River r : data.values()) {
	    if(r.getLastUpdate() != updateTime) { //some old entry
		continue;
	    }
	    
	    if(r.getId() == -1) {//only if river is not reused
		riverArg.setValue(r.getName());
		countryArg.setValue(r.getCountry());
		River river = riverDao.queryForFirst(preparedQRiver);
		if (river == null) {
		    riverDao.create(r);
		}
		else {
		    r.setId(river.getId());
		}
	    }

	    
	    for(LG lg : r.getLg().values()) {
		try {
		    if(lg.getId() == -1) {
			nameArg.setValue(lg.getName());
			riverIdArg.setValue(r);
			LG l = lgDao.queryForFirst(preparedQLG);
			if(l != null) {
			    lg.setId(l.getId());
			}
			lg.setRiver(r);
		    }
		    
		    if(lg.getId() == -1) { //non existent LG
			lgDao.create(lg);
			Log.i("Added LG: ", lg.getName());
		    }
		    else {
			lgDao.update(lg);
		    }
		    
		    dataDao.create(lg.getData());
		}
		catch(SQLException ex) {
		    Log.e(Update.class.getName(), ex.getLocalizedMessage());
		    Log.d(Update.class.getName(), lg.toString());
		    Log.d(Update.class.getName(), lg.getData().toString());
		}
	    }
	}
	
	
	lastUpdate.setValue(time);
	if(lastUpdate.getId() == 0) {
	    db.getSettingsDao().create(lastUpdate);
	    lastUpdate.setId(db.getSettingsDao().extractId(lastUpdate));
	}
	else {
	    db.getSettingsDao().update(lastUpdate);
	}
    }
    
    
    private List<String> loadInfo(String url) {
	BufferedReader raw = new BufferedReader(new InputStreamReader(HttpReader.load(url)));
	
	String[] s;
	try {
	    s = raw.readLine().split(" ");
	} catch (IOException ex) {
	    return null;
	}
	
	List<String> data = new ArrayList<String>();
	int l = Integer.parseInt(lastUpdate.getValue());
	for(int i = 0; i < s.length; i++) {
	    if(Integer.parseInt(s[i]) > l) {
		data.add(s[i]);
	    }
	    else {
		break;
	    }
	}
	return data;
    }
    
    public boolean isOnline() {
	ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	NetworkInfo netInfo = cm.getActiveNetworkInfo();
	return (netInfo != null && netInfo.isConnectedOrConnecting());
    }

}
