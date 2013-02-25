package com.vodocty.model;

import android.util.Log;
import com.j256.ormlite.stmt.QueryBuilder;
import com.vodocty.data.LG;
import com.vodocty.data.River;
import com.vodocty.database.DBOpenHelper;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Dan Princ
 * @since 20.2.2013
 */
public class LGsModel {
    
    private DBOpenHelper db;
    private List<LG> data;
    
    private int riverId;
    
    public LGsModel(DBOpenHelper db) {
	this.db = db;
	data = null;
	riverId = -1;
    }
    
    public void setRiverId(int id) {
	this.riverId = id;
    }
    
    public River getRiver() {
	if(data != null) {
	    return data.get(0).getRiver();
	}
	else {
	    try {
		QueryBuilder<River,Integer> riv = this.db.getRiverDao().queryBuilder();
		riv.where().in("id", riverId);
		return riv.queryForFirst();
	    }
	    catch(SQLException e) {
		return null;
	    }
	}
    }
    
    public List<LG> getLGs() {	
	if(riverId == -1) {
	    return null;
	}
	if(data != null) {
	    return data;
	}
	
	try {
	    QueryBuilder<LG,Integer> lgQb = this.db.getLgDao().queryBuilder();
	    lgQb.where().in("river_id", riverId);
	    lgQb.orderBy("currentVolume", false); //which way?? or optional?
	    data = lgQb.query();
	} catch (SQLException ex) {
	    Log.e(LGsModel.class.getName(), ex.getLocalizedMessage());
	    return null;
	}
	
	return data;
    }

}
