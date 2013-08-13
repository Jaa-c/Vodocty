package com.vodocty.model;

import android.util.Log;
import com.j256.ormlite.stmt.QueryBuilder;
import com.vodocty.Vodocty;
import com.vodocty.data.LG;
import com.vodocty.data.River;
import com.vodocty.database.DBOpenHelper;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Dan Princ
 * @since 11.3.2013
 */
public class FavoritesModel {

	private Vodocty context;
	private DBOpenHelper db;
	private List<LG> data;

	public FavoritesModel(Vodocty v) {
		this.context = v;
		this.db = v.getDatabase();

	}

	public List<LG> getFavoriteLGs() {
		if (data != null) {
			return data;
		}

		try {
			QueryBuilder<River, Integer> riQb = this.db.getRiverDao().queryBuilder();
			riQb.selectColumns(River.COLUMN_ID);
			riQb.where().eq(River.COLUMN_COUNTRY, context.getDisplayedCountry());

			QueryBuilder<LG, Integer> lgQb = this.db.getLgDao().queryBuilder();
			lgQb.where().eq(LG.COLUMN_FAVORITE, true).and().in(LG.COLUMN_RIVER, riQb);
			lgQb.orderBy(LG.COLUMN_NAME, true); //which way?? or optional?
			data = lgQb.query();
		} catch (SQLException ex) {
			ex.printStackTrace();
			Log.e(LGsModel.class.getName(), ex.getLocalizedMessage());
			return null;
		}

		return data;
	}

	public void invalidate() {
		data = null;
	}
}
