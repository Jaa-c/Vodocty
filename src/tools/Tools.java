package tools;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Dan Princ
 * @since 22.2.2013
 */
public class Tools {
    
    public static boolean isToday(Calendar date) {
	Calendar c = Calendar.getInstance();
	c.set(Calendar.HOUR, 0);
	c.set(Calendar.MINUTE, 0);
	c.set(Calendar.SECOND, 0);
	return date.after(c.getTime());
    }
}
