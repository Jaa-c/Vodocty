package com.vodocty.data;

import java.util.ArrayList;
import java.util.List;

public class River {
    private String name;
    private List<LG> lg;
    
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
