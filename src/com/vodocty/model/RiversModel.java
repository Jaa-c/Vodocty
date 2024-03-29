package com.vodocty.model;

import android.util.Log;
import com.j256.ormlite.stmt.QueryBuilder;
import com.vodocty.Vodocty;
import com.vodocty.data.Country;
import com.vodocty.data.River;
import com.vodocty.database.DBOpenHelper;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Dan Princ
 * @since 19.2.2013
 */
public class RiversModel {

	private DBOpenHelper db;
	private Vodocty context;
	private List<River> rivers;

	public RiversModel(Vodocty v) {
		this.context = v;
		this.db = v.getDatabase();
		rivers = null;

	}

	public List<River> getRivers() {
		if (rivers != null) {
			return rivers;
		}

		try {
			QueryBuilder<River, Integer> lgQb = this.db.getRiverDao().queryBuilder();
			lgQb.where().in("country", context.getDisplayedCountry());
			//lgQb.orderByRaw("name COLLATE UNICODE"); //which way?? or optional?
			lgQb.orderBy(River.COLUMN_NAME, true); //which way?? or optional?
			rivers = lgQb.query();
		} catch (SQLException ex) {
			Log.e(RiversModel.class.getName(), ex.getLocalizedMessage());
			return null;
		}

		return rivers;
	}

	public void invalidateData() {
		rivers = null;
	}
}
