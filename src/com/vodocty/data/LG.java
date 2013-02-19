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
    
    @DatabaseField(foreign=true, uniqueCombo=true, canBeNull=false)
    private River river;
    
    @DatabaseField(defaultValue="0")
    private boolean favourite;
    
    @DatabaseField(defaultValue="0")
    private boolean notify;
    
    @DatabaseField(defaultValue="0")
    private int notifyHeight;
    
    @DatabaseField(defaultValue="0")
    private float notifyVolume;
    
    private List<Data> data = new ArrayList<Data>();
    //private Data data; //tbh do I really need List??
    
    LG() {}
    
    public LG(String name) {
	this.name = name;
    }

    public int getId() {
	return id;
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

    public int getNotifyHeight() {
	return notifyHeight;
    }

    public void setNotifyHeight(int notifyHeight) {
	this.notifyHeight = notifyHeight;
    }

    public float getNotifyVolume() {
	return notifyVolume;
    }

    public void setNotifyVolume(float notifyVolume) {
	this.notifyVolume = notifyVolume;
    }
    
    public void addData(Data d) {
	data.add(d);
    }
    
    public List<Data> getData() {
	return data;
    }

    @Override
    public String toString() {
	return "LG{" + "id=" + id + ", name=" + name + ", favourite=" + favourite + ", notify=" + notify + ", notifyHeight=" + notifyHeight + ", notifyVolume=" + notifyVolume + ", data=" + data + '}';
    }

    
    
    
}