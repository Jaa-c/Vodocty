package com.vodocty.activities;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Log;
import com.vodocty.R;
import com.vodocty.data.Country;

/**
 *
 * @author Dan Princ
 * @since 6.4.2013
 */
public class SettingsActivity extends PreferenceActivity
                              implements OnSharedPreferenceChangeListener {
    
    public static final String COUNTRY_DEFAULT = "pref_default_country";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	addPreferencesFromResource(R.xml.preferences_settings);
	
	SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
	String syncConnPref = sharedPref.getString(SettingsActivity.COUNTRY_DEFAULT, "fail");
	
	Log.d("fav", "country: " + syncConnPref);

	
    }
    
    

    public void onSharedPreferenceChanged(SharedPreferences arg0, String arg1) {
	
	
	
    }
    
    @Override
    protected void onResume() {
	super.onResume();
	getPreferenceScreen().getSharedPreferences()
		.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
	super.onPause();
	getPreferenceScreen().getSharedPreferences()
		.unregisterOnSharedPreferenceChangeListener(this);
    }


}
