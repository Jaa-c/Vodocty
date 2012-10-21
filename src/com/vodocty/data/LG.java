package com.vodocty.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "lg")
public class LG {
    
    @DatabaseField(generatedId = true)
    private int id;
    
    @DatabaseField(index=true, canBeNull=false)
    private String name;
    
    @DatabaseField(foreign=true, canBeNull=false)
    private River river;
    
    @DatabaseField
    private boolean favourite;
    
    @DatabaseField
    private boolean notify;
    
    @DatabaseField
    private int notifyHeight;
    
    @DatabaseField
    private float notifyVolume;
    
    LG() {}
    
    public LG(String name) {
	this.name = name;
    }


    public String getName() {
	return name;
    }
    
    
}
