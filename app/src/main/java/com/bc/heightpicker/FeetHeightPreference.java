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

public class FeetHeightPreference extends DialogPreference {

	CommonFunctions cf;
	private Context context;
	private Spinner feet, inches;
	private ArrayAdapter<CharSequence> feetValue, inchesValue;
	
	public FeetHeightPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		setPersistent(false);
		setDialogLayoutResource(R.layout.feetheightpreflayout);
	}
	
	@Override
    protected void onBindDialogView(View view) {
		//Assign spinners to xml spinners
        feet = (Spinner) view.findViewById(R.id.feetSpinner);
        inches = (Spinner) view.findViewById(R.id.inchesSpinner);
        feetValue = ArrayAdapter.createFromResource(context, R.array.pref_feet_values, android.R.layout.simple_spinner_dropdown_item);
        feetValue.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
        feet.setAdapter(feetValue);
        feet.setSelection(0);     
        inchesValue = ArrayAdapter.createFromResource(context, R.array.pref_inches_values, android.R.layout.simple_spinner_dropdown_item);
        inchesValue.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inches.setAdapter(inchesValue); 
        
        String defaultSetting = getDefaultValue();
        if (defaultSetting.equals("")) {
        	//Empty
        } else {
        	String parts[] = defaultSetting.split("-");
        	Double tmp = (Double.parseDouble(parts[1]) * 2);
        	feet.setSelection(Integer.parseInt( parts[0]) - 1 );
        	inches.setSelection( (int)Math.round(tmp)  );
        }
        super.onBindDialogView(view);
    }
	
	public String getDefaultValue() {		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
		String s = prefs.getString(SetPrefActivity.KEY_FEET_HEIGHT_PICKER_VALUE, "");
		return s;
	}

    @Override
    public void onDismiss(DialogInterface dialog) {
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.feetheightpreflayout , null);
        linearLayout.removeView(feet);
        linearLayout.removeView(inches);
        super.onDismiss(dialog);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        if (positiveResult) {
        	cf = new CommonFunctions();
        	String returnValue = feet.getSelectedItem().toString() + "-" + inches.getSelectedItem().toString();
            SharedPreferences.Editor editor = getEditor();
            editor.putString(getKey(), returnValue).apply();
            
            //Update the corresponding CM Value with the new height.
            String cm = cf.feetToCM(Double.parseDouble(feet.getSelectedItem().toString()), Double.parseDouble(inches.getSelectedItem().toString()));
            editor.putString("CMpicker", cm).apply();
        }
    } 
}