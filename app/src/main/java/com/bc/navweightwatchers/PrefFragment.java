package com.bc.navweightwatchers;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class PrefFragment extends PreferenceFragment {
	
	public static final String KEY_CM_PREF = "CMpicker";
	public static final String KEY_FEET_PREF = "FIpicker";
	SharedPreferences prefs;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}
	
	public void changeEnabled(String unit) {
		if ( unit.equals("0") ) {
			findPreference(KEY_CM_PREF).setEnabled(true);
			findPreference(KEY_FEET_PREF).setEnabled(false);
		} else if ( unit.equals("1") ) {
			findPreference(KEY_CM_PREF).setEnabled(false);
			findPreference(KEY_FEET_PREF).setEnabled(true);
		} else {
			findPreference(KEY_CM_PREF).setEnabled(false);
			findPreference(KEY_FEET_PREF).setEnabled(false);
		}	
	}
	
	public void checkHeightDefaultsSet() {
		prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		SharedPreferences.Editor editor = prefs.edit();
		if(prefs.getString(SetPrefActivity.KEY_FEET_HEIGHT_PICKER_VALUE, "").equals("")) {
			//set default feet/height
			editor.putString("FIpicker", "5-8.5").apply();
		}
		if(prefs.getString(SetPrefActivity.KEY_CM_PICKER_VALUE, "").equals("")) {
			//Set default cm
			editor.putString("CMpicker", "174").apply();
		}
	}

	public void ChangeHidden() {
		prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
	}
}