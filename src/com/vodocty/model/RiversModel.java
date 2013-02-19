package com.vodocty.model;

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
    
    DBOpenHelper db;
    List<River> rivers;
    
    public RiversModel(DBOpenHelper db) {
	this.db = db;
	
	rivers = null;
	
    }
    
    
    
    public List<River> getRivers() {
	try {    
	    rivers = this.db.getRiverDao().queryForAll();
	} catch (SQLException ex) {}
	
	return rivers;
    }

}
