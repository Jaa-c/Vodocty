package com.vodocty.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DatabaseTable(tableName = "river")
public class River {
    
    @DatabaseField(generatedId = true)
    private int id;
    
    @DatabaseField(index=true, uniqueCombo=true, canBeNull=false)
    private String name;
    
    @DatabaseField(uniqueCombo=true, canBeNull=false)
    private Country country;
    
    private Map<String, LG> lg;
    
    private long lastUpdate;
    
    River() {}
    
    public River(String name, Country country) {
	this.id = -1;
	this.name = name;    
	this.country = country;
	lg = new HashMap<String, LG>();
    }
    
    public void add(LG lg) {
	this.lg.put(lg.getName(), lg);
    }
    

    public String getName() {
	return name;
    }

    public Map<String, LG> getLg() {
	return lg;
    }
    
    public LG getLgItem(String s) {
	return lg.get(s);
    }
    
    public void emptyLGs()  {
	lg = new HashMap<String, LG>();
    }

    public int getId() {
	return id;
    }
    
    public void setId(int id) {
	this.id = id;
    }

    public Country getCountry() {
	return country;
    }

    public void setCountry(Country country) {
	this.country = country;
    }

    public long getLastUpdate() {
	return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
	this.lastUpdate = lastUpdate;
    }
    
    
    @Override
    public String toString() {
	return "River{" + "id=" + id + ", name=" + name + ", country=" + country + '}';
    }


}
