package com.vodocty.tools;

import java.util.Calendar;

/**
 *
 * @author Dan Princ
 * @since 22.2.2013
 */
public class Tools {

	public static final int DAY_SECONDS = 86400;

	public static boolean isToday(Calendar date) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 1);
		return date.after(c);
	}
}
