package com.vodocty.model;

import android.util.Log;
import com.j256.ormlite.stmt.QueryBuilder;
import com.vodocty.data.Data;
import com.vodocty.data.LG;
import com.vodocty.database.DBOpenHelper;
import java.sql.SQLException;

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
	    dataQb.orderBy("id", false);
	    data = dataQb.queryForFirst();
	} catch (SQLException ex) {
	    Log.e(DataModel.class.getName(), "SQLException: " + ex.getLocalizedMessage());
	    return null;
	}
	
	return data;
    
    
    }

}
