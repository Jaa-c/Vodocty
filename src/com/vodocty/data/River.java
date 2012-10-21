package com.vodocty.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.ArrayList;
import java.util.List;

@DatabaseTable(tableName = "river")
public class River {
    
    @DatabaseField(generatedId = true)
    private int id;
    
    @DatabaseField(index=true, canBeNull=false)
    private String name;
    
    @DatabaseField(foreign=true)
    private Country country;
    
    private List<LG> lg;
    
    River() {}
    
    public River(String name) {
	this.name = name;    
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
    

}
