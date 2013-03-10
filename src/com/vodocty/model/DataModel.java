package com.vodocty.model;

import android.util.Log;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.vodocty.data.Data;
import com.vodocty.data.LG;
import com.vodocty.database.DBOpenHelper;
import java.sql.SQLException;
import java.util.List;
import org.achartengine.model.TimeSeries;

/**
 *
 * @author Dan Princ
 * @since 21.2.2013
 */
public class DataModel {
    
    private DBOpenHelper db;
    private Data data;
    
    private int lgId;
    
    public DataModel(DBOpenHelper db) {
	this.db = db;
	data = null;
	lgId = -1;
    }

    public void setLGId(int lgId) {
	this.lgId = lgId;
    }
    
    public Data getLastData() {
	if(lgId == -1) {
	    return null;
	}
	
	//if(data != null) {
	//    return data;
	//}
	
	try {
	    QueryBuilder<Data, Integer> dataQb = this.db.getDataDao().queryBuilder();
	    dataQb.where().in("lg_id", lgId);
	    dataQb.orderBy("date", false);
	    data = dataQb.queryForFirst();
	} catch (SQLException ex) {
	    Log.e(DataModel.class.getName(), "SQLException: " + ex.getLocalizedMessage());
	    return null;
	}
	
	return data;
    }

    public TimeSeries getVolumeSeries(String title) {
	return getVolumeSeries(title, true);
    }
    
    public TimeSeries getVolumeSeries(String title, boolean volume) {
	if(lgId == -1) {
	    return null;
	}
	//potreba nejak cachovat data!!!! TODO
	List<Data > d = null;
	try {
	    QueryBuilder<Data, Integer> dataQb = this.db.getDataDao().queryBuilder();
	    dataQb.where().in("lg_id", lgId);
	    //dataQb.orderBy("date", false);
	    d = dataQb.query();
	} catch (SQLException ex) {
	    Log.e(DataModel.class.getName(), "SQLException: " + ex.getLocalizedMessage());
	}
	if(d == null) {
	    return null;
	}
	
	TimeSeries series = new TimeSeries(title);
	for(Data curr : d) {	    
	    if(volume) {
		series.add(curr.getDate(), curr.getVolume());
	    }
	    else {
		series.add(curr.getDate(), curr.getHeight());
	    }
	}	
	
	return series;
    }
    
    
    public boolean updateLG(LG lg) {
	try {
	    Dao<LG, Integer> lgDao = db.getLgDao();
	    return lgDao.update(lg) == 1;
	}
	catch(SQLException e) {
	    Log.e(this.getClass().getName(), e.getMessage());
	    return false;
	}
    }
    
}
