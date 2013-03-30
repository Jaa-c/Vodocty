package com.vodocty.data;


public enum Country {
    
    cze("Česká republika");
    
    private final String name;
    
    Country(String s) {
	this.name = s;
    }
    
    public String getName() {
	return this.name;
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