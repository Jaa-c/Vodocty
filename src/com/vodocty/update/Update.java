package com.vodocty.update;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
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

/**
 * Handles the regular data update.
 * 
 * @author Dan Princ
 * @since long time ago
 */
public class Update implements Runnable {
    private DBOpenHelper db;
    private Context c;
    private Settings lastUpdate;
    
    private static final String GZ = ".xml.gz";
    
    public Update(DBOpenHelper db, Context c) {
	this.db = db;
	this.c = c;
		
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
	try {
	    doUpdate();
	}
	catch(SQLException e) {
	    Log.e(Update.class.getName(), e.getLocalizedMessage());
	}
	finally {
	    db.close();
	}
    }
    
    
    private void doUpdate() throws SQLException {
	
	Resources res = c.getResources();
	
	TypedArray urls = res.obtainTypedArray(R.array.urls);
	String path = res.getString(R.string.path);
	
	List<InputStream> xmlList = new ArrayList<InputStream>();
	List<River> data = null;
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
	    
	    //get all new files
	    for(int j = files.size()-1; j >= 0; j--) {
		String file = files.get(i);
		xmlList.add(HttpReader.loadGz(path + file + GZ));
	    }
	    
	    //parse and save all new files
	    for (int k = 0; k < xmlList.size(); k++) {
		InputStream xml = xmlList.get(k);
		if(xml == null) {
		    Log.e(Update.class.getName(), "feed offline: " + urls.getString(i));
		    continue;
		}
		try {
		    data = XMLParser.parse(xml, Country.cze);
		} catch (Exception ex) {
		    Log.e(Update.class.getName(), ex.getLocalizedMessage());
		}
		finally {
		    if(data == null) {
			continue;
		    }
		}
		
		//finally update the data in database
		Log.i(Update.class.getName(), "used file: " + files.get(files.size() - 1 - k));
		this.updateDatabase(data, files.get(files.size() - 1 - k));
	    }
	}
    
    }
    
    private void updateDatabase(List<River> data, String time) throws SQLException {
	//update the database:
	Dao<Data, Integer> dataDao = db.getDataDao();
	Dao<River, Integer> riverDao = db.getRiverDao();
	Dao<LG, Integer> lgDao = db.getLgDao();

	for(River r : data) {
	    QueryBuilder<River, Integer> riverQuery = riverDao.queryBuilder();
	    riverQuery.where().eq("name", r.getName()).and().eq("country", r.getCountry());
	    if (riverQuery.queryForFirst() == null) {
		riverDao.create(r);
	    }
	    else {
		River river = riverQuery.queryForFirst();
		r.setId(river.getId());
	    }

	    SelectArg nameArg = new SelectArg();
	    SelectArg riverArg = new SelectArg();
	    QueryBuilder<LG, Integer> query = lgDao.queryBuilder();
	    query.where().eq("name", nameArg).and().eq("river_id", riverArg);
	    PreparedQuery<LG> preparedQ = query.prepare();
	    
	    for(LG lg : r.getLg()) {
		lg.setRiver(r);

		nameArg.setValue(lg.getName());
		riverArg.setValue(lg.getRiver());
		LG l = lgDao.queryForFirst(preparedQ);

		if (l == null) {
		    lgDao.create(lg);
		    Log.i("Added LG: ", lg.getName());
		}
		else {
		    lg.setId(l.getId());
		    lgDao.update(lg);
		}
		
		try {
		    dataDao.create(lg.getData());
		}
		catch(SQLException ex) {
		    Log.e(Update.class.getName(), ex.getLocalizedMessage());
		    Log.d(Update.class.getName(), lg.toString());
		    Log.d(Update.class.getName(), lg.getData().toString());
		}
	    }
	}
	
	Settings s = new Settings(Settings.LAST_UPDATE, time);
	if(lastUpdate.getId() == 0) {
	    db.getSettingsDao().create(s);
	}
	else {
	    s.setId(lastUpdate.getId());
	    db.getSettingsDao().update(s);
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
	for(int i = 0; i < s.length; i++) {
	    if(Integer.parseInt(s[i]) > Integer.parseInt(lastUpdate.getValue())) {
		data.add(s[i]);
	    }
	    else {
		break;
	    }
	}
	return data;
    }


}
