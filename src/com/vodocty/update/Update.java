package com.vodocty.update;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.Log;
import com.j256.ormlite.dao.Dao;
import com.vodocty.R;
import com.vodocty.data.Country;
import com.vodocty.data.Data;
import com.vodocty.data.LG;
import com.vodocty.data.River;
import com.vodocty.database.DBOpenHelper;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 * Handles the regular data update.
 * 
 * @author Dan Princ
 * @since long time ago
 */
public class Update {
    DBOpenHelper db;
    Context c;
    
    public Update(DBOpenHelper db, Context c) {
	this.db = db;
	this.c = c;
    }
    
    
    public void doUpdate() throws SQLException {
	
	Resources res = c.getResources();
	
	TypedArray urls = res.obtainTypedArray(R.array.urls);
	
	InputStream xml;
	List<River> data = null;
	for(int i = 0; i < urls.length(); i++) {
	    
	    xml = HttpReader.load(urls.getString(i));
	    if(xml == null) {
		Log.e(Update.class.getName(), "feed offline: " + urls.getString(i));
		continue;
	    }
	    try {
		data = XMLParser.parse(xml, Country.cze);
	    } catch (IOException ex) {
		Log.e("io: " + Update.class.getName(), ex.getLocalizedMessage());
	    } catch (ParserConfigurationException ex) {
		Log.e("parser: "+Update.class.getName(), ex.getLocalizedMessage());
	    } catch (SAXException ex) {
		Log.e("sax: "+Update.class.getName(), ex.getLocalizedMessage());
	    }
	    finally {
		if(data == null) {
		    continue;
		}
	    }
	    
	    //update the database:
	    Dao<Data, Integer> dataDao = db.getDataDao();
	    Dao<River, Integer> riverDao = db.getRiverDao();
	    Dao<LG, Integer> lgDao = db.getLgDao();
	    
	    for(River r : data) {
		riverDao.createIfNotExists(r);
		for(LG lg : r.getLg()) {
		    lgDao.createIfNotExists(lg);
		    for(Data d : lg.getData()) {
			dataDao.create(d);
		    }
		}
	    }
	}
    
    }    

}