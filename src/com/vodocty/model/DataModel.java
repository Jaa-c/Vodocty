package com.vodocty.model;

import android.util.Log;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.DatabaseConnection;
import com.vodocty.Vodocty;
import com.vodocty.data.Data;
import com.vodocty.data.LG;
import com.vodocty.data.Settings;
import com.vodocty.database.DBOpenHelper;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;
import org.achartengine.model.TimeSeries;

/**
 *
 * @author Dan Princ
 * @since 21.2.2013
 */
public class DataModel {

	private DBOpenHelper db;
	private Vodocty context;
	private Data data;
	private TimeSeries series;
	private int lgId;

	public DataModel(Vodocty c) {
		this.context = c;
		this.db = c.getDatabase();
		this.data = null;
		this.series = null;
		this.lgId = -1;
	}

	public void setLGId(int lgId) {
		this.lgId = lgId;
	}

	public void invalidateData() {
		data = null;
		series = null;
	}

	public Data getLastData() {
		if (lgId == -1) {
			return null;
		}

		if (data != null) {
			return data;
		}

		Log.d("model", "loading LAST from db.");

		try {
			QueryBuilder<Data, Integer> dataQb = this.db.getDataDao().queryBuilder();
			dataQb.where().in(Data.COLUMN_LG, lgId);
			dataQb.orderBy(Data.COLUMN_DATE, false);
			data = dataQb.queryForFirst();
		} catch (SQLException ex) {
			Log.e(DataModel.class.getName(), "SQLException: " + ex.getLocalizedMessage());
			return null;
		}

		return data;
	}

	public TimeSeries getVolumeSeries() {
		return getVolumeSeries(true);
	}

	public TimeSeries getVolumeSeries(boolean volume) {
		if (lgId == -1) {
			return null;
		}

		if (series != null) {
			return series; //lame caching, todo better
		}


		Log.d("model", "loading all from db.");

		List<Data> d = null;
		try {
			QueryBuilder<Data, Integer> dataQb = this.db.getDataDao().queryBuilder();
			dataQb.where().in(Data.COLUMN_LG, lgId);
			dataQb.orderBy(Data.COLUMN_DATE, false); //is it helpful? todo
			d = dataQb.query();
		} catch (SQLException ex) {
			Log.e(DataModel.class.getName(), "SQLException: " + ex.getLocalizedMessage());
		}
		if (d == null) {
			return null;
		}

		series = new TimeSeries("");
		for (Data curr : d) {
			if (volume) {
				series.add(curr.getDate(), curr.getVolume());
			} else {
				series.add(curr.getDate(), curr.getHeight());
			}
		}

		return series;
	}

	public boolean switchFavorite() {
		if (data == null) {
			return false;
		}
		Dao<Settings, Integer> settDao;
		DatabaseConnection conn;
		Savepoint savePoint;
		LG lg = data.getLg();
		int value = lg.isFavorite() ? -1 : 1;
		try {
			settDao = db.getSettingsDao();
			conn = settDao.startThreadConnection();
			savePoint = conn.setSavePoint(null); //TODO use update from ormlite!!
//	    UpdateBuilder<Settings, Integer> settUpdate = settDao.updateBuilder();
//	    settUpdate.updateColumnValue(Settings.VALUE, Settings.VALUE + " + " + value); //does not work!!
//	    settUpdate.where().in(Settings.KEY, Settings.SETTINGS_FAVORITES);
//	    settUpdate.update();
			GenericRawResults<String[]> cursor = settDao.queryRaw("UPDATE " + Settings.TABLE_NAME
					+ " SET " + Settings.COLUMN_VALUE + " = " + Settings.COLUMN_VALUE + " + " + value
					+ " WHERE " + Settings.COLUMN_KEY + " = \"" + Settings.SETTINGS_FAVORITES + "\"");
			cursor.close();
		} catch (SQLException e) {
			Log.e(this.getClass().getName(), e.getLocalizedMessage());
			return false;
		}
		lg.setFavorite(!lg.isFavorite());
		context.adToFavorites(value);
		if (updateLG(lg)) {
			try {
				conn.commit(savePoint);
				settDao.endThreadConnection(conn);
			} catch (SQLException e) {
				Log.e(this.getClass().getName(), e.getLocalizedMessage());
			}
			return true;
		}
		return false;
	}

	public boolean updateLG(LG lg) {
		try {
			if (lg.getId() == this.lgId) {
				this.data.setLg(lg);
			}
			Dao<LG, Integer> lgDao = db.getLgDao();
			return lgDao.update(lg) == 1;
		} catch (SQLException e) {
			Log.e(this.getClass().getName(), e.getMessage());
			return false;
		}
	}
}
