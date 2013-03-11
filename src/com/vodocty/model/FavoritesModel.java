package com.vodocty.model;

import android.util.Log;
import com.j256.ormlite.stmt.QueryBuilder;
import com.vodocty.data.LG;
import com.vodocty.database.DBOpenHelper;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Dan Princ
 * @since 11.3.2013
 */
public class FavoritesModel {
    
    private DBOpenHelper db;
    private List<LG> data;
    
    public FavoritesModel(DBOpenHelper db) {
	this.db = db;
	
    }
    
    public List<LG> getFavoriteLGs() {	
	if(data != null) {
	    return data;
	}
	
	try {
	    QueryBuilder<LG,Integer> lgQb = this.db.getLgDao().queryBuilder();
	    lgQb.where().in(LG.COLUMN_FAVORITE, true);
	    lgQb.orderBy(LG.COLUMN_NAME, true); //which way?? or optional?
	    data = lgQb.query();
	} catch (SQLException ex) {
	    Log.e(LGsModel.class.getName(), ex.getLocalizedMessage());
	    return null;
	}
	
	return data;
    }
    
    public void invalidate() {
	data = null;
    }

}
