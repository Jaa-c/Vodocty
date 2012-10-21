package com.vodocty.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.vodocty.data.Country;
import com.vodocty.data.Data;
import com.vodocty.data.LG;
import com.vodocty.data.River;
import java.sql.SQLException;

public class DBOpenHelper extends OrmLiteSqliteOpenHelper {
    
    private static final String DATABASE_NAME = "vodocty.db";
    private static final int DATABASE_VERSION = 1;
    
    private Dao<Country, Integer> countryDao = null;
    
  
    
    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqld, ConnectionSource cs) {
	try {
	    TableUtils.createTable(connectionSource, Country.class);
	    TableUtils.createTable(connectionSource, River.class);
	    TableUtils.createTable(connectionSource, LG.class);
	    TableUtils.createTable(connectionSource, Data.class);
	}
	catch(SQLException e) {
	    Log.e(DBOpenHelper.class.getName(), "Can't create database", e);
	    throw new RuntimeException(e);
	}
	
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqld, ConnectionSource cs, int i, int i1) {
	throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public Dao<Country, Integer> getCountryDao() throws SQLException {
	if (countryDao == null) {
	    countryDao = getDao(Country.class);
	}
	return countryDao;
    }
    
    @Override
    public void close() {
	super.close();
	countryDao = null;
    }
}