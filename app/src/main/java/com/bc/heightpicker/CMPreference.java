package com.bc.heightpicker;

import com.bc.navweightwatchers.CommonFunctions;
import com.bc.navweightwatchers.R;
import com.bc.navweightwatchers.SetPrefActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class CMPreference extends DialogPreference {

	CommonFunctions cf;
	private Context context;
	private Spinner cm;
	private ArrayAdapter<CharSequence> cmValue;
	
	public CMPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		setPersistent(false);
		setDialogLayoutResource(R.layout.cmpreflayout);
	}
	
	@Override
    protected void onBindDialogView(View view) {
		//Assign spinner to xml spinner
        cm = (Spinner) view.findViewById(R.id.cmSpinner);
        cmValue = ArrayAdapter.createFromResource(context, R.array.pref_cm_values, android.R.layout.simple_spinner_dropdown_item);
        cmValue.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
        cm.setAdapter(cmValue);
        
        String defaultSetting = getDefaultValue();
        if (defaultSetting.equals("")) {
            cm.setSelection(75);
        } else {
        	String parts[] = defaultSetting.split("-");
        	cm.setSelection(Integer.parseInt( parts[0]) - 100 );
        }

        super.onBindDialogView(view);
    }
	
	public String getDefaultValue() {		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
		String s = prefs.getString(SetPrefActivity.KEY_CM_PICKER_VALUE, "");
		return s;
	}

    @Override
    public void onDismiss(DialogInterface dialog) {
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.cmpreflayout , null);
        linearLayout.removeView(cm);
        super.onDismiss(dialog);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        if (positiveResult) {
        	cf = new CommonFunctions();
        	String returnValue = cm.getSelectedItem().toString();
            SharedPreferences.Editor editor = getEditor();
            editor.putString(getKey(), returnValue).apply();
            
            //Update the corresponding Feet/Inches Value with the new height.
            String FI = cf.cmToFeet(Integer.parseInt(cm.getSelectedItem().toString()));
            editor.putString("FIpicker", FI).apply();
        }
    } 
}
