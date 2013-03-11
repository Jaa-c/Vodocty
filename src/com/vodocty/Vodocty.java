package com.vodocty;

import android.app.Application;
import android.util.Log;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.vodocty.data.Data;
import com.vodocty.data.Settings;
import com.vodocty.database.DBOpenHelper;
import java.sql.SQLException;

/**
 * Global Context stuff
 * @author Dan Princ
 * @since 11.3.2013
 */
public class Vodocty extends Application {
    
    private DBOpenHelper database;
    
    private int favorites;

    @Override
    public void onCreate() {
	super.onCreate();
	
	database = new DBOpenHelper(this);
	Dao<Settings, Integer> settingsDao;
	
	try {	
	    settingsDao = database.getSettingsDao();
	    
	    QueryBuilder<Settings, Integer> sQuery = settingsDao.queryBuilder();
	    sQuery.where().in(Settings.KEY, Settings.SETTINGS_FAVORITES);
	    favorites = Integer.parseInt(sQuery.queryForFirst().getValue());
	    assert(favorites >= 0);
	}
	catch(SQLException e) {
	    Log.d(this.getClass().getName(), "Unable to load initial settings.");
	    Log.e(this.getClass().getName(), e.getLocalizedMessage());
	}
	
    }

    @Override
    public void onTerminate() {
	super.onTerminate();
	
	database = null;
    }
    
    public DBOpenHelper getDatabase() {
	return database;
    }

    public int getFavorites() {
	return favorites;
    }

    public void setFavorites(int favorites) {
	this.favorites = favorites;
    }
    
    
    

}
