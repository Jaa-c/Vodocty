package com.vodocty.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.vodocty.Vodocty;
import com.vodocty.data.Data;
import com.vodocty.data.LG;
import com.vodocty.data.River;
import com.vodocty.data.Settings;
import java.sql.SQLException;

/**
 * Handles database conection, hanles DAO instances.
 *
 *
 * @author Dan Princ
 * @since long time ago
 */
public class DBOpenHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "vodocty.db";
	private static final int DATABASE_VERSION = 6;
	private static Dao<River, Integer> riverDao = null;
	private static Dao<LG, Integer> lgDao = null;
	private static Dao<Data, Integer> dataDao = null;
	private static Dao<Settings, Integer> settDao = null;

	//private static DBOpenHelper instance = null;
	public DBOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

//    public static synchronized DBOpenHelper getInstance(Context context) {
//	if(instance == null) {
//	    instance = new DBOpenHelper(context);
//	}
//	return instance;
//    }
	@Override
	public void onCreate(SQLiteDatabase sqld, ConnectionSource cs) {
		try {
			TableUtils.createTable(connectionSource, River.class);
			TableUtils.createTable(connectionSource, LG.class);
			TableUtils.createTable(connectionSource, Data.class);
			TableUtils.createTable(connectionSource, Settings.class);

			initDatabase();
		} catch (SQLException e) {
			Log.e(DBOpenHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase sqld, ConnectionSource cs, int oldVersion, int newVersion) {
		if (oldVersion < DATABASE_VERSION) {
			try {
				Dao<Data, Integer> dao = getDataDao();
				dao.executeRaw("CREATE INDEX lg_id_index ON data(lg_id);");
				Log.d(this.getClass().getName(), "Table updated!");
			} catch (SQLException e) {
				Log.e(this.getClass().getName(), e.getMessage());
				Log.d(this.getClass().getName(), "failed to update database. That would be BAD I guess.");
			}
		}
	}

	public synchronized Dao<River, Integer> getRiverDao() throws SQLException {
		if (riverDao == null) {
			riverDao = getDao(River.class);
		}
		return riverDao;
	}

	public synchronized Dao<LG, Integer> getLgDao() throws SQLException {
		if (lgDao == null) {
			lgDao = getDao(LG.class);
		}
		return lgDao;
	}

	public synchronized Dao<Data, Integer> getDataDao() throws SQLException {
		if (dataDao == null) {
			dataDao = getDao(Data.class);
		}
		return dataDao;
	}

	public synchronized Dao<Settings, Integer> getSettingsDao() throws SQLException {
		if (settDao == null) {
			settDao = getDao(Settings.class);
		}
		return settDao;
	}

	@Override
	public void close() {
		super.close();
		riverDao = null;
		lgDao = null;
		dataDao = null;
		settDao = null;

		//instance = null;
	}

	private void initDatabase() throws SQLException {
		settDao.create(new Settings(Settings.SETTINGS_FAVORITES, "0"));
		settDao.create(new Settings(Settings.SETTINGS_LAST_UPDATE, "0"));
	}
}