package com.vodocty.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.List;

@DatabaseTable(tableName = "country")
public class Country {
    
    @DatabaseField(generatedId = true)
    private int id;
    
    @DatabaseField(index=true, canBeNull=false)
    private String name;
        
    
    Country() {}

    public Country(String name) {
	this.name = name;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public int getId() {
	return id;
    }
    
    

}
