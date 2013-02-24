package com.vodocty.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@DatabaseTable(tableName = "data")
public class Data {  
    
    @DatabaseField(generatedId = true)
    private int id;
    
    @DatabaseField(foreign = true,
	    foreignAutoRefresh=true,
	    maxForeignAutoRefreshLevel=2,
	    canBeNull = false)
    private LG lg;
    
    @DatabaseField(index = true, canBeNull = false)
    private Date date;
    
    @DatabaseField(defaultValue="-1")
    private int height;
    
    @DatabaseField(defaultValue="-1")
    private float volume;
    
    @DatabaseField(defaultValue="0")
    private int flood;
    
    Data() {}

    public Data(LG lg) {
	this.lg = lg;
    }
    
    public int getID() {
	return id;
    }

    public LG getLg() {
	return lg;
    }

    public void setLg(LG lg) {
	this.lg = lg;
    }

    public Date getDate() {
	return date;
    }
    
    public void setDate(String date) {
	try {
	    this.date = new SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.ENGLISH).parse(date);
	} catch (ParseException ex) {
	    this.date = null;
	}
    }

    public int getHeight() {
	return height;
    }

    public void setHeight(int height) {
	this.height = height;
    }

    public float getVolume() {
	return volume;
    }

    public void setVolume(float volume) {
	this.volume = volume;
    }

    public int getFlood() {
	return flood;
    }

    public void setFlood(int flood) {
	this.flood = flood;
    }

    @Override
    public String toString() {
	return "Data{" + "id=" + id + ", date=" + date + ", height=" + height + ", volume=" + volume + ", flood=" + flood + '}';
    }
    
    

}
