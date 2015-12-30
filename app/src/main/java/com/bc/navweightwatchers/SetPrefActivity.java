package com.bc.navweightwatchers;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class SetPrefActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {

	public static final String KEY_UNIT_VALUE = "unitType";
	public static final String KEY_DOB_VALUE = "dob";
	public static final String KEY_PROMPTAGE_CHECKBOX_VALUE = "promptAge";
	public static final String KEY_FEET_HEIGHT_PICKER_VALUE = "FIpicker";
	public static final String KEY_CM_PICKER_VALUE = "CMpicker";
	PrefFragment pf;
	CheckBoxPreference promptAge;
	SharedPreferences prefs;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pf = new PrefFragment();
		getFragmentManager().beginTransaction().replace(android.R.id.content, pf).commit();
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
	}

	protected void onResume() {
		super.onResume();
		prefs.registerOnSharedPreferenceChangeListener(this);
		checkPreferences();
	}
	protected void onPause() {
		super.onPause();
		prefs.unregisterOnSharedPreferenceChangeListener(this);
	}
	
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreference, String key) {
		// TODO Auto-generated method stub
		if ( key.equals(KEY_UNIT_VALUE) ) {
			checkPreferences();
		} else if ( key.equals(KEY_DOB_VALUE) ) {
			//DOB Changed now..
		} else if ( key.equals(KEY_PROMPTAGE_CHECKBOX_VALUE) ) {
			//Check height defaults and set them if they are empty.
			pf.checkHeightDefaultsSet();
			checkPreferences();
		} else if ( key.equals(KEY_FEET_HEIGHT_PICKER_VALUE) ) {
			//Feet Changed
		} else if ( key.equals(KEY_CM_PICKER_VALUE) ) {
			//CM Height changed
		} else {
			//A another preference changed 
		}
	}
	
	public void checkPreferences() {		
		if(prefs.getBoolean("promptAge", true)) {
			pf.changeEnabled( prefs.getString(SetPrefActivity.KEY_UNIT_VALUE, "") );
		}
	}
}