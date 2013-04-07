package com.vodocty;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.vodocty.activities.SettingsActivity;
import com.vodocty.data.Country;
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
    
    private Country displayedCountry;

    @Override
    public void onCreate() {
	super.onCreate();
	
	database = new DBOpenHelper(this);
	Dao<Settings, Integer> settingsDao;
	
	try {	
	    settingsDao = database.getSettingsDao();
	    
	    QueryBuilder<Settings, Integer> sQuery = settingsDao.queryBuilder();
	    sQuery.where().in(Settings.COLUMN_KEY, Settings.SETTINGS_FAVORITES);
	    favorites = Integer.parseInt(sQuery.queryForFirst().getValue());
	    assert(favorites >= 0);
	}
	catch(SQLException e) {
	    Log.d(this.getClass().getName(), "Unable to load initial settings.");
	    Log.e(this.getClass().getName(), e.getLocalizedMessage());
	}
	
	
	SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
	String syncConnPref = sharedPref.getString(SettingsActivity.COUNTRY_DEFAULT, "fail");
	
	displayedCountry = Country.valueOf(syncConnPref); //todo osetrit fail
	
	Log.d("vodocty", "country: " + syncConnPref);
	Log.d("vodocty", "favorites: " + favorites);
	
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

    public Country getDisplayedCountry() {
	return displayedCountry;
    }

    public void setDisplayedCountry(Country displayedCountry) {
	this.displayedCountry = displayedCountry;
    }
    
    
    
}
