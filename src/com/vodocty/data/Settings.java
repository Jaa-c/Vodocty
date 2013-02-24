package com.vodocty.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *
 * @author Dan Princ
 * @since 24.2.2013
 */
@DatabaseTable(tableName = "settings")
public class Settings {
    
    
    public static final String KEY = "key";
    public static final String VALUE = "value";
    
    
    public static final String LAST_UPDATE = "lastUpdate";
    
    @DatabaseField(generatedId = true)
    private int id;
    
    @DatabaseField(index=true, unique=true, canBeNull=false, columnName=KEY)
    private String key;
    
    @DatabaseField(canBeNull=false, columnName=VALUE)
    private String value;
    
    Settings() {}

    public Settings(String key, String value) {
	this.key = key;
	this.value = value;
    }

    public String getKey() {
	return key;
    }

    public void setKey(String key) {
	this.key = key;
    }

    public String getValue() {
	return value;
    }

    public void setValue(String value) {
	this.value = value;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }
    

}
