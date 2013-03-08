package com.vodocty.update;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.PowerManager;
import android.os.RemoteException;
import android.util.Log;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.vodocty.MainActivity;
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
 * It's too big and does too many thinks. I don't like that.
 * 
 * @author Dan Princ
 * @since long time ago
 */
public class Update extends Service implements Runnable {
    
    private static final String GZ = ".xml.gz";
    private static final int NOTI_ID = 1;
    
    public static final int MSG_REGISTER = 1;
    public static final int MSG_UNREGISTER = -1;
    public static final int MSG_UPDATE = 2;
    
    private static boolean RUNNING = false;
    
    private DBOpenHelper db;
    private NotificationManager notifM;
    private Settings lastUpdate;
    
    /** Target we publish for clients to send messages to IncomingHandler. */
    private final Messenger mMessenger = new Messenger(new IncomingHandler());
    
    private ArrayList<Messenger> mClients = new ArrayList<Messenger>();


    @Override
    public void onCreate() {
	super.onCreate();
	
	this.db = DBOpenHelper.getInstance(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
	this.createNotification();
		
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
	
	
	Thread updateThread = new Thread(this);
	if(!RUNNING) {
	    updateThread.start();
	}
	return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
	super.onDestroy();
    }
    
    @Override
    public IBinder onBind(Intent i) {
	return mMessenger.getBinder();
    }
    
    
    public void run() {
	if(isOnline() && !RUNNING) {
	    RUNNING = true;
	    doUpdate();

	    PowerManager powerManager = (PowerManager) this.getSystemService(Activity.POWER_SERVICE);
	    if(!powerManager.isScreenOn()) {
		db.close(); //TODO
	    }
	}
	else {
	    Log.i(Update.class.getName(), "Not connected to the internet or update already running. Update cancelled.");
	}
	RUNNING = false;
	this.notifM.cancel(NOTI_ID);
	
	Log.i(Update.class.getName(), "Update thread finished!");
    }
    
    public void notifyReceivers() {
	for(Messenger m : mClients) {
	    try {
		m.send(Message.obtain(null, MSG_UPDATE, 0, 0));
	    } catch (RemoteException ex) {
		Log.e(Update.class.getName(), ex.getLocalizedMessage());
	    }
	}
    }
    
    
    private void doUpdate()  {
	
	Resources res = getResources();
	
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
		
		notifyReceivers(); //notify controllers that the data changed
		
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
    
    
    private boolean isOnline() {
	ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	NetworkInfo netInfo = cm.getActiveNetworkInfo();
	return (netInfo != null && netInfo.isConnectedOrConnecting());
    }
    
    
    private void createNotification() {
	this.notifM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    
	Intent notificationIntent = new Intent(this, MainActivity.class);
	PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
	Notification notify = new Notification(R.drawable.ic_launcher,
						    "Vodocty: updating data",
						    System.currentTimeMillis());
	notify.setLatestEventInfo(this, "Vodocty", "Update in progress", contentIntent);
	
	//Set default vibration
	notify.defaults |= Notification.FLAG_ONLY_ALERT_ONCE;
	notify.defaults |= Notification.FLAG_ONGOING_EVENT;
	notify.defaults |= Notification.DEFAULT_LIGHTS;
	
	this.notifM.notify(NOTI_ID, notify);
    }
    
    /**
     * Handler of incoming messages from clients.
     */
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
		case MSG_REGISTER:
                    mClients.add(msg.replyTo);
                    break;
                case MSG_UNREGISTER:
                    mClients.remove(msg.replyTo);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

}
