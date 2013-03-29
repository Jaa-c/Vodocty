package com.vodocty.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.Date;

@DatabaseTable(tableName = LG.TABLE_NAME)
public class LG {
    
    public static final String TABLE_NAME = "lg";
    
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FAVORITE = "favourite";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_RIVER = "river_id";
    public static final String COLUMN_NOTIFY = "notify";
    public static final String COLUMN_LAST_NOTIFICATION = "lastNotification";
    public static final String COLUMN_NOTIFY_HEIGHT = "notifyHeight";
    public static final String COLUMN_NOTIFY_VOLUME = "notifyVolume";
    public static final String COLUMN_CURRENT_HEIGHT = "currentHeight";
    public static final String COLUMN_CURRENT_VOLUME = "currentVolume";
    public static final String COLUMN_CURRENT_FLOOD = "currentFlood";
    
    @DatabaseField(generatedId = true, columnName = COLUMN_ID)
    private int id;
    
    @DatabaseField(
	    index=true,
	    uniqueCombo=true,
	    canBeNull=false,
	    columnName=COLUMN_NAME)
    private String name;
    
    @DatabaseField(
	    foreign=true,
	    foreignAutoRefresh=true,
	    maxForeignAutoRefreshLevel=2,
	    uniqueCombo=true,
	    canBeNull=false,
	    columnName=COLUMN_RIVER)
    private River river;
    
    @DatabaseField(defaultValue="0", columnName=COLUMN_FAVORITE)
    private boolean favourite;
    
    @DatabaseField(defaultValue="0", columnName=COLUMN_NOTIFY)
    private boolean notify;
    
    @DatabaseField(columnName=COLUMN_LAST_NOTIFICATION)
    private Date lastNotification;
    
    @DatabaseField(defaultValue="0", columnName=COLUMN_NOTIFY_HEIGHT)
    private float notifyHeight;
    
    @DatabaseField(defaultValue="0", columnName=COLUMN_NOTIFY_VOLUME)
    private float notifyVolume;
    
    @DatabaseField(defaultValue="0", columnName=COLUMN_CURRENT_HEIGHT)
    private float currentHeight;
    
    @DatabaseField(defaultValue="0", columnName=COLUMN_CURRENT_VOLUME)
    private float currentVolume;
    
    @DatabaseField(defaultValue="0", columnName=COLUMN_CURRENT_FLOOD)
    private int currentFlood;
    
    private volatile Data data;
    
    LG() {}
    
    public LG(String name) {
	this.name = name;
	id = -1;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public River getRiver() {
	return river;
    }

    public void setRiver(River river) {
	this.river = river;
    }

    public boolean isFavorite() {
	return favourite;
    }

    public void setFavorite(boolean favorite) {
	this.favourite = favorite;
    }

    public boolean isNotify() {
	return notify;
    }

    public void setNotify(boolean notify) {
	this.notify = notify;
    }

    public float getNotifyHeight() {
	return notifyHeight;
    }

    public void setNotifyHeight(float notifyHeight) {
	this.notifyHeight = notifyHeight;
    }

    public float getNotifyVolume() {
	return notifyVolume;
    }

    public void setNotifyVolume(float notifyVolume) {
	this.notifyVolume = notifyVolume;
    }
    
    public void setData(Data d) {
	data = d;
    }
    
    public Data getData() {
	return data;
    }

    public float getCurrentHeight() {
	return currentHeight;
    }

    public void setCurrentHeight(float currentHeight) {
	this.currentHeight = currentHeight;
    }

    public float getCurrentVolume() {
	return currentVolume;
    }

    public void setCurrentVolume(float currentVolume) {
	this.currentVolume = currentVolume;
    }

    public int getCurrentFlood() {
	return currentFlood;
    }

    public void setCurrentFlood(int currentFlood) {
	this.currentFlood = currentFlood;
    }

    public Date getLastNotification() {
	return lastNotification;
    }

    public void setLastNotification(Date lastNotification) {
	this.lastNotification = lastNotification;
    }
    
    
    

    @Override
    public String toString() {
	return "LG{" + "id=" + id + ", name=" + name + ", river=" + river + ", favourite=" + favourite + ", notify=" + notify + ", notifyHeight=" + notifyHeight + ", notifyVolume=" + notifyVolume + ", currentHeight=" + currentHeight + ", currentVolume=" + currentVolume + '}';
    }
        
}
