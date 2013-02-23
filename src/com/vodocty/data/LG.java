package com.vodocty.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.ArrayList;
import java.util.List;

@DatabaseTable(tableName = "lg")
public class LG {
    
    @DatabaseField(generatedId = true)
    private int id;
    
    @DatabaseField(index=true, uniqueCombo=true, canBeNull=false)
    private String name;
    
    @DatabaseField(
	    foreign=true,
	    foreignAutoRefresh=true,
	    maxForeignAutoRefreshLevel=2,
	    uniqueCombo=true,
	    canBeNull=false)
    private River river;
    
    @DatabaseField(defaultValue="0")
    private boolean favourite;
    
    @DatabaseField(defaultValue="0")
    private boolean notify;
    
    @DatabaseField(defaultValue="0")
    private float notifyHeight;
    
    @DatabaseField(defaultValue="0")
    private float notifyVolume;
    
    @DatabaseField(defaultValue="0")
    private float currentHeight;
    
    @DatabaseField(defaultValue="0")
    private float currentVolume;
    
    @DatabaseField(defaultValue="0")
    private int currentFlood;
    
    //private List<Data> data = new ArrayList<Data>();
    private Data data; //tbh do I really need List??
    
    LG() {}
    
    public LG(String name) {
	this.name = name;
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

    public boolean isFavourite() {
	return favourite;
    }

    public void setFavourite(boolean favourite) {
	this.favourite = favourite;
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
    

    @Override
    public String toString() {
	return "LG{" + "id=" + id + ", name=" + name + ", river=" + river + ", favourite=" + favourite + ", notify=" + notify + ", notifyHeight=" + notifyHeight + ", notifyVolume=" + notifyVolume + ", currentHeight=" + currentHeight + ", currentVolume=" + currentVolume + '}';
    }
        
}
