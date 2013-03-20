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
    
    public static final String EXTRA_LG_ID = "lgId";
    public static final String EXTRA_RIVER_ID = "riverId";
    
    private DBOpenHelper database;
    
    private int favorites;
    private boolean displayFavorites;
    private boolean changeDispFavorites;

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
	
	Log.d("fav", "favorites: " + favorites);
	
    }

    @Override
    public void onTerminate() {
	super.onTerminate();
	database.close();
	database = null;
    }
    
    public DBOpenHelper getDatabase() {
	return database;
    }

    public int getFavorites() {
	return favorites;
    }

    public void setFavorites(int favorites) {
	if(this.favorites == 0 && favorites > 0) {
	    this.displayFavorites = true;
	    this.changeDispFavorites = true;
	}
	if(this.favorites > 0 && favorites == 0 ) {
	    this.displayFavorites = false;
	    this.changeDispFavorites = true;
	}
	
	this.favorites = favorites;
    }
    
    public void adToFavorites(int i) {
	this.setFavorites(favorites + i);
    }

    public boolean isDisplayFavorites() {
	return displayFavorites;
    }

    public void setDisplayFavorites(boolean displayFavorites) {
	this.displayFavorites = displayFavorites;
    }

    public boolean isChangeDispFavorites() {
	return changeDispFavorites;
    }

    public void setChangeDispFavorites(boolean changeDispFavorites) {
	this.changeDispFavorites = changeDispFavorites;
    }
    
    
}
