package com.vodocty.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.ArrayList;
import java.util.List;

@DatabaseTable(tableName = "river")
public class River {
    
    @DatabaseField(generatedId = true)
    private int id;
    
    @DatabaseField(index=true, uniqueCombo=true, canBeNull=false)
    private String name;
    
    @DatabaseField(uniqueCombo=true, canBeNull=false)
    private Country country;
    
    private List<LG> lg;
    
    River() {}
    
    public River(String name, Country country) {
	this.name = name;    
	this.country = country;
	lg = new ArrayList<LG>();
    }
    
    public void add(LG lg) {
	this.lg.add(lg);
    }
    
    public void addAll(List<LG> lg) {
	this.lg.addAll(lg);
    }

    public String getName() {
	return name;
    }

    public List<LG> getLg() {
	return lg;
    }
    
    public LG getLgItem(int i) {
	return lg.get(i);
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

    @Override
    public String toString() {
	return "River{" + "id=" + id + ", name=" + name + ", country=" + country + '}';
    }

    
    
    
    

}
