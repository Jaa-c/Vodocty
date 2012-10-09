package com.vodocty.update;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.actionbarsherlock.R;
import com.vodocty.data.River;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xmlpull.v1.XmlPullParserException;

public class Update {
    SQLiteDatabase db;
    Context c;
    
    public Update(SQLiteDatabase db, Context c) {
	this.db = db;
	this.c = c;
    }
    
    
    public void doUpdate() {
	Resources res = c.getResources();
	TypedArray urls = res.obtainTypedArray(R.array.urls);
	
	InputStream xml;
	List<River> data = null;
	for(int i = 0; i < urls.length(); i++) {
	    try {
		xml = HttpReader.load(urls.getString(i));
	    } catch (IOException ex) {
		Log.e(Update.class.getName(), ex.getLocalizedMessage());
		continue;
	    }
	    try {
		data = XMLParser.parse(xml);
	    } catch (IOException ex) {
		Log.e(Update.class.getName(), ex.getLocalizedMessage());
	    } catch (XmlPullParserException ex) {
		Log.e(Update.class.getName(), ex.getLocalizedMessage());
	    }
	    Log.d(Update.class.getName(), data.size() + " " + data.get(0).getName());
	}
    
    }
    
    

}
