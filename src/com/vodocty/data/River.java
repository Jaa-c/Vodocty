package com.vodocty.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DatabaseTable(tableName = River.TABLE_NAME)
public class River {

	public static final String TABLE_NAME = "river";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_COUNTRY = "country";
	@DatabaseField(generatedId = true, columnName = COLUMN_ID)
	private int id;
	@DatabaseField(
			index = true,
			uniqueCombo = true,
			canBeNull = false,
			columnName = COLUMN_NAME)
	private String name;
	@DatabaseField(
			index = true,
			uniqueCombo = true,
			canBeNull = false,
			columnName = COLUMN_COUNTRY)
	private Country country;
	private Map<String, LG> lg;
	private long lastUpdate;

	River() {
	}

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

	public void emptyLGs() {
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
