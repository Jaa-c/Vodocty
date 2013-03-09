package tools;

import android.util.Log;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Dan Princ
 * @since 22.2.2013
 */
public class Tools {
    
    public static final int DAY_SECONDS = 81300000;
    
    public static boolean isToday(Calendar date) {
	Calendar c = Calendar.getInstance();
	c.set(Calendar.HOUR_OF_DAY, 0);
	c.set(Calendar.MINUTE, 0);
	c.set(Calendar.SECOND, 1);
	return date.after(c);
    }
    
}
