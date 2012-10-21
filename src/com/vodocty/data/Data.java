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
    
    @DatabaseField(foreign = true, canBeNull = false)
    private LG lg;
    
    @DatabaseField(index = true, canBeNull = false)
    private Date date;
    
    @DatabaseField(canBeNull = false)
    private int height;
    
    @DatabaseField
    private float volume;
    
    @DatabaseField
    private int flood;
    
    Data() {}

    public Data(Date date, int height, float volume, int flood) {
	this.date = date;
	this.height = height;
	this.volume = volume;
	this.flood = flood;
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
    
    
    
    

}
