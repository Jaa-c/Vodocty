package com.vodocty.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {
    
    private static final String DATABASE_NAME = "vodocty";
    private static final int DATABASE_VERSION = 1;
    
    private static final String COUNTRY_TABLE_NAME = "country";
    private static final String COUNTRY_ID = "id";
    private static final String COUNTRY_NAME = "name";
    private static final String COUNTRY_TABLE_CREATE  =
	    "CREATE TABLE " + COUNTRY_TABLE_NAME + " (" +
	    COUNTRY_ID + " INTEGER PRIMARY KEY ASC, " +
	    COUNTRY_NAME + " TEXT);";
    
    private static final String RIVER_TABLE_NAME = "river";
    private static final String RIVER_ID = "id";
    private static final String RIVER_COUNTRY_ID = "countryId";
    private static final String RIVER_NAME = "name";
    private static final String RIVER_TABLE_CREATE =
	    "CREATE TABLE " + RIVER_TABLE_NAME + " (" +
	    RIVER_ID + " INTEGER PRIMARY KEY ASC, " +
	    RIVER_COUNTRY_ID + " INTEGER, "+
	    RIVER_NAME + " TEXT, " + 
	    "FOREIGN KEY(" + RIVER_COUNTRY_ID + ") REFERENCES "
	    + COUNTRY_TABLE_NAME + "(" + COUNTRY_ID + "));";
    
    private static final String LG_TABLE_NAME = "lg";
    private static final String LG_ID = "id";
    private static final String LG_RIVER_ID = "riverId";
    private static final String LG_NAME = "name";
    private static final String LG_FAV = "fav";
    private static final String LG_NOTIFY = "notify";
    private static final String LG_NOTIFY_HEIGHT = "notifyHeight";
    private static final String LG_NOTIFY_VOLUME = "notifyVolume";
    private static final String LG_TABLE_CREATE =
	    "CREATE TABLE " + LG_TABLE_NAME + " (" +
	    LG_ID + " INTEGER PRIMARY KEY ASC, " +
	    LG_RIVER_ID + " INTEGER, "+
	    LG_NAME + " TEXT, " + 
	    LG_FAV + " INTEGER, " + 
	    LG_NOTIFY + " INTEGER, " + 
	    LG_NOTIFY_HEIGHT + " INTEGER, " + 
	    LG_NOTIFY_VOLUME + " REAL, " + 
	    "FOREIGN KEY(" + LG_RIVER_ID + ") REFERENCES "
	    + RIVER_TABLE_NAME + "(" + RIVER_ID + "));";
    
    
    private static final String DATA_TABLE_NAME = "data";
    private static final String DATA_ID = "id";
    private static final String DATA_LG_ID = "lgId";
    private static final String DATA_TIME = "time";
    private static final String DATA_HEIGHT = "height";
    private static final String DATA_VOLUME = "volume";
    private static final String DATA_FLOOD = "flood";
    private static final String DATA_TABLE_CREATE =
	    "CREATE TABLE " + DATA_TABLE_NAME + " (" +
	    DATA_ID + " INTEGER PRIMARY KEY ASC, " +
	    DATA_LG_ID + " INTEGER, "+
	    DATA_TIME + " INTEER, " + 
	    DATA_HEIGHT + " INTEGER, " + 
	    DATA_VOLUME + " REAL, " + 
	    DATA_FLOOD + " INTEGER, " + 
	    "FOREIGN KEY(" + DATA_LG_ID + ") REFERENCES "
	    + LG_TABLE_NAME + "(" + LG_ID + "));";
    
    

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(COUNTRY_TABLE_CREATE);
        db.execSQL(RIVER_TABLE_CREATE);
        db.execSQL(LG_TABLE_CREATE);
        db.execSQL(DATA_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	throw new UnsupportedOperationException("Not supported yet.");
    }
}