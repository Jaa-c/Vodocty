package com.vodocty.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LG {
    private String name;
    private Date date;
    private int height, flood;
    private float volume;
    
    public LG(String name) {
	this.name = name;
    }

    public Date getDate() {
	return date;
    }

    public void setDate(Date date) {
	this.date = date;
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

    public String getName() {
	return name;
    }
    
    
}
