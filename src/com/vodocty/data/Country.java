package com.vodocty.data;

import java.util.LinkedList;
import java.util.List;


public enum Country {
    
    cz("Česká republika"), at("Rakousko");
    
    private final String name;
    private boolean enabled;
    private int lastUpdate;
    
    Country(String s) {
	this.name = s;
	this.enabled = false;
    }
    
    public static String[] getStringNames() {
	List<String> ret = new LinkedList<String>();
	for(Country c : Country.values()) {
	    ret.add(c.getName());
	}
	return ret.toArray(new String[ret.size()]);
    }
    
    public static String[] getStringValues() {
	List<String> ret = new LinkedList<String>();
	for(Country c : Country.values()) {
	    ret.add(c + "");
	}
	return ret.toArray(new String[ret.size()]);
    }
    
    public String getName() {
	return this.name;
    }

    public int getLastUpdate() {
	return lastUpdate;
    }

    public void setLastUpdate(int lastUpdate) {
	this.lastUpdate = lastUpdate;
    }
    
    
    
    
};




/*@DatabaseTable(tableName = "country")
public class Country {
    
    @DatabaseField(generatedId = true)
    private int id;
    
    @DatabaseField(index=true, unique=true, canBeNull=false)
    private String code;
    
    Country() {}

    public Country(String name) {
	this.code = name;
    }

    public String getCode() {
	return code;
    }

    public void setCode(String name) {
	this.code = name;
    }

    public int getId() {
	return id;
    }
    
    

}
*/