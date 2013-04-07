package com.vodocty.view.preferences;

import android.content.Context;
import android.preference.ListPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.vodocty.Vodocty;
import com.vodocty.data.Country;

/**
 *
 * @author Dan Princ
 * @since 7.4.2013
 */
public class CountryListPreference  extends ListPreference {

    private Vodocty vodocty;

    public CountryListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
	//vodocty = (Vodocty) context.getApplicationContext();
        setEntries(Country.getStringNames());
        setEntryValues(Country.getStringValues());
	
    }

    public CountryListPreference(Context context) {
        super(context);
	//vodocty = (Vodocty) context.getApplicationContext();
    }
    
    

}