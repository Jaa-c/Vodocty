package com.vodocty.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@DatabaseTable(tableName = Data.TABLE_NAME)
public class Data {

	public static final String TABLE_NAME = "data";
	public static final String COLUMN_DATE = "date";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_LG = "lg_id";
	public static final String COLUMN_HEIGHT = "height";
	public static final String COLUMN_VOLUME = "volume";
	public static final String COLUMN_FLOOD = "flood";
	@DatabaseField(generatedId = true, columnName = COLUMN_ID)
	private int id;
	@DatabaseField(foreign = true,
			foreignAutoRefresh = true,
			maxForeignAutoRefreshLevel = 2,
			index = true,
			canBeNull = false,
			columnName = COLUMN_LG)
	private LG lg;
	@DatabaseField(index = true, canBeNull = false, columnName = COLUMN_DATE)
	private Date date;
	@DatabaseField(defaultValue = "-1", columnName = COLUMN_HEIGHT)
	private int height;
	@DatabaseField(defaultValue = "-1", columnName = COLUMN_VOLUME)
	private float volume;
	@DatabaseField(defaultValue = "0", columnName = COLUMN_FLOOD)
	private int flood;
	
	private String rawDate;

	Data() {
	}

	public Data(LG lg) {
		this.lg = lg;
	}

	public int getID() {
		return id;
	}

	public LG getLg() {
		return lg;
	}

	public void setLg(LG lg) {
		this.lg = lg;
	}

	public Date getDate() {
		//TODO: test this
		if(date == null) { //parse date only if actually used 
			try {
				this.date = new SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.ENGLISH).parse(rawDate);
			} catch (ParseException ex) {
			   this.date = null;
		   }
		}
		return date;
	}

	public void setDate(String date) {
		rawDate = date;
//		try {
//			this.date = new SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.ENGLISH).parse(date);
//		} catch (ParseException ex) {
//			this.date = null;
//		}
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getVolume() {
		return volume;
	}

	public void setVolume(float volume) {
		this.volume = volume;
	}

	public int getFlood() {
		return flood;
	}

	public void setFlood(int flood) {
		this.flood = flood;
	}

	@Override
	public String toString() {
		return "Data{" + "id=" + id + ", date=" + rawDate + ", height=" + height + ", volume=" + volume + ", flood=" + flood + '}';
	}
}
