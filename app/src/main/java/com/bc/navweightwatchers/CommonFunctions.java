package com.bc.navweightwatchers;

import java.util.Calendar;

import com.bc.datepreference.DatePreference;

import android.app.Activity;
import android.content.Intent;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class CommonFunctions extends PreferenceActivity {
	
	public int calculateAge( Activity a) {
		Calendar c = Calendar.getInstance();
		Calendar dob = DatePreference.getDateFor(PreferenceManager.getDefaultSharedPreferences(a),"dob");
		int AGE = c.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
		if(dob.get(Calendar.MONTH) > c.get(Calendar.MONTH) || dob.get(Calendar.MONTH) == c.get(Calendar.MONTH) 
				&& dob.get(Calendar.DATE) > c.get(Calendar.DATE)) {
			AGE--;
		}
		
		return AGE;
	}
	
	public String feetToCM(double feet, double inches) {
		//1inch = 2.54cm
		inches = ( ( feet * 12 ) + inches);
		double cm = inches * 2.54;
		cm = Double.parseDouble(roundIt(cm));
		return Integer.toString((int)cm);
	}
	
	public String cmToFeet(int cm) {
		//1cm = 0.39370079 inch
		double inches = cm * 0.39370079;
		double feet = ( ( inches / 12 ));
		
		String[] splitFeet = TextUtils.split(Double.toString( feet ), "\\.");
		String roundedInches = roundItToHalf(inches % 12);
		String FI = splitFeet[0] + "-" + roundedInches;
		
		return FI;
	}
		
	public double stoneToKG(double stones, double lbs) {
		//1lb = 0.45359237
		lbs = ( ( stones * 14 ) + lbs );
		double kg = lbs * 0.45359237;
				
		return kg;
	}
	
	public String roundIt(double x) {
		
	    String tempString = String.valueOf(x);
    	String[] splitString = TextUtils.split(tempString, "\\.");
    	int firstPart = Integer.parseInt(splitString[0]);
    	String remainder = "0." + splitString[1];
    	double tmpRemainder = Double.parseDouble(remainder);
    	int finalRemainder = 0, rounded;
    	if ( tmpRemainder >= 0 && tmpRemainder < 0.5 ) {
    		finalRemainder = 0;
    	} else if ( tmpRemainder >= 0.5 && tmpRemainder < 1 ) {
    		finalRemainder = 1;
    	} else {
    		System.out.println("Huh?");
    	}
    	rounded = firstPart + finalRemainder;
    	String fPoints = String.valueOf(rounded);
    	
    	return fPoints;
	}

	public String roundItToHalf(double x) {
		
	    String tempString = String.valueOf(x);
    	String[] splitString = TextUtils.split(tempString, "\\.");
    	double firstPart = Double.parseDouble(splitString[0]);
    	String remainder = "0." + splitString[1];
    	double tmpRemainder = Double.parseDouble(remainder);
    	double finalRemainder = 0, rounded;
    	if ( tmpRemainder >= 0 && tmpRemainder < 0.5 ) {
    		finalRemainder = 0;
    	} else if ( tmpRemainder >= 0.25 && tmpRemainder < 0.5 ) {
    		finalRemainder = 0.5;
    	} else if ( tmpRemainder >= 0.5 && tmpRemainder < 0.75 ) {
    		finalRemainder = 0.5;
    	} else if ( tmpRemainder >= 0.75 && tmpRemainder < 1 ) {
    		finalRemainder = 1;
    	} else {
    		System.out.println("Huh?!");
    	}
    	rounded = firstPart + finalRemainder;
    	String fPoints = String.valueOf(rounded);
    	
    	return fPoints;
	}
	
	public void displayErrorMessage(Activity a, String message) {
		Toast toast = Toast.makeText(a, message, Toast.LENGTH_LONG);
		toast.show();
	}
	public void setPointsText(TextView heading, TextView pointField, String points) {
		heading.setText("Points");
		pointField.setText(points);
	}
	
	public String getSelectedRadioButton(Activity a, RadioGroup RG) {
		int radioId = RG.getCheckedRadioButtonId();
		RadioButton rb = (RadioButton) a.findViewById(radioId);
		String radioText = (String) rb.getText();
		
		return radioText;
	}
}
