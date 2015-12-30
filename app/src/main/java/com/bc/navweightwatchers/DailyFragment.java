package com.bc.navweightwatchers;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;


public class DailyFragment extends Fragment implements OnTouchListener {
	
	//Setup GUI Features variables
	//Metric var's
	private EditText ME_kilo, ME_age, ME_height;
	private TextView ME_pointsHeading, ME_points;
	private RadioGroup ME_gender;
	private Button ME_calculate;
	
	//Imperial Var's
	private EditText IM_stones, IM_lbs, IM_age, IM_feet, IM_inches;
	private TextView IM_pointsHeading, IM_points;
	private RadioGroup IM_gender;
	private Button IM_calculate;

	SharedPreferences pref;
	private String unit = "2";
	private int resumeCount = 0;
	View rootView;
	CommonFunctions cf;
	
    public static Fragment newInstance(Context context) {
    	DailyFragment f = new DailyFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
    	pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
    	cf = new CommonFunctions();
    	setUnitValue();
    	
        if (unit.equals("0")) {
        	rootView = inflater.inflate(R.layout.dailymenu_cm, container, false);
        	setupMetricGUIIds();
        	if(pref.getBoolean("promptAge", true)) {
    			ME_age.setText( Integer.toString(cf.calculateAge( getActivity() )));
    			ME_height.setText(pref.getString(SetPrefActivity.KEY_CM_PICKER_VALUE, ""));
    		}
        	
        	//Listen for Metric button clicks
            ME_calculate.setOnClickListener(new OnClickListener() {
            	public void onClick(View v) {
            		checkMetricFieldValues();
            	}
            });
        } else if (unit.equals("1")) {
        	rootView = inflater.inflate(R.layout.dailymenu_feet, container, false);
        	setupImperialGUIIds();
        	if(pref.getBoolean("promptAge", true)) {
    			IM_age.setText( Integer.toString(cf.calculateAge( getActivity() ) ) );
    			String FI = pref.getString(SetPrefActivity.KEY_FEET_HEIGHT_PICKER_VALUE, "");
    			String parts[] = FI.split("-");
    			IM_feet.setText(parts[0]);
    			IM_inches.setText(parts[1]);
    		}
        	
        	//Listen for Imperial button clicks
            IM_calculate.setOnClickListener(new OnClickListener() {
            	public void onClick(View v) {
            		checkImperialFieldValues();
            	}
            });
        } else {
        	System.out.println("Not Attached yet maybe??");
        }
        return rootView;
    }
    
    public void setUnitValue() {
    	//SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
    	unit = pref.getString(SetPrefActivity.KEY_UNIT_VALUE, "");
    }
    public void onResume() {
    	super.onResume();
    	if ( resumeCount == 0 ) {
    		resumeCount++;
    	} else {
        	Fragment frag = new DailyFragment();
        	FragmentTransaction transaction = getFragmentManager().beginTransaction();
        	transaction.replace(this.getId(), frag);
        	transaction.commit();
    	}
    }
    
    public void setupMetricGUIIds() {
    	ME_kilo = (EditText) rootView.findViewById(R.id.D_ME_kilograms);
    	ME_kilo.setOnTouchListener(this);
    	ME_age = (EditText) rootView.findViewById(R.id.D_ME_age);
    	ME_age.setOnTouchListener(this);
    	ME_height = (EditText) rootView.findViewById(R.id.D_ME_height);
    	ME_height.setOnTouchListener(this);
    	ME_pointsHeading = (TextView) rootView.findViewById(R.id.D_ME_pointsheading);
    	ME_points = (TextView) rootView.findViewById(R.id.D_ME_points);
    	ME_gender = (RadioGroup) rootView.findViewById(R.id.D_ME_gender_RG);
    	ME_calculate = (Button) rootView.findViewById(R.id.D_ME_calc);
    }
    
    public void setupImperialGUIIds() {
    	IM_stones = (EditText) rootView.findViewById(R.id.D_IM_stones);
    	IM_stones.setOnTouchListener(this);
    	IM_lbs = (EditText) rootView.findViewById(R.id.D_IM_pounds);
    	IM_lbs.setOnTouchListener(this);
    	IM_age = (EditText) rootView.findViewById(R.id.D_IM_age);
    	IM_age.setOnTouchListener(this);
    	IM_feet = (EditText) rootView.findViewById(R.id.D_IM_feet);
    	IM_feet.setOnTouchListener(this);
    	IM_inches = (EditText) rootView.findViewById(R.id.D_IM_inches);
    	IM_inches.setOnTouchListener(this);
    	IM_pointsHeading = (TextView) rootView.findViewById(R.id.D_IM_pointsheading);
    	IM_points = (TextView) rootView.findViewById(R.id.D_IM_points);
    	IM_gender = (RadioGroup) rootView.findViewById(R.id.D_IM_gender_RG);
    	IM_calculate = (Button) rootView.findViewById(R.id.D_IM_calc);
    }
    
    public void checkMetricFieldValues() {
		double Dkg, Dage, Dheight, pointsUnrounded;
		String pointsRounded;
		if(ME_kilo.getText().toString().equals("") || ME_kilo.getText().toString().equals(".")) {
			cf.displayErrorMessage(getActivity(), "Enter Weight in KG's");
		} else {
			if(ME_age.getText().toString().equals("") || ME_age.getText().toString().equals(".")) {
				cf.displayErrorMessage(getActivity(), "Enter Age Value");
			} else {
				if(ME_height.getText().toString().equals("") || ME_height.getText().toString().equals(".")) {
					cf.displayErrorMessage(getActivity(), "Enter Height Value");
				} else {
					Dkg = Double.parseDouble(ME_kilo.getText().toString());
					Dage = Double.parseDouble(ME_age.getText().toString());
					Dheight = ((Double.parseDouble(ME_height.getText().toString()) ) /100);
					
					if(cf.getSelectedRadioButton(getActivity(), ME_gender).equals("Male") ) {
						pointsUnrounded = calcMalePoints(Dkg, Dage, Dheight);
		        		pointsRounded = cf.roundIt(pointsUnrounded);
		        		cf.setPointsText(ME_pointsHeading, ME_points, pointsRounded);
					} else if ( cf.getSelectedRadioButton(getActivity(), ME_gender).equals("Female") ) {
						pointsUnrounded = calcFemalePoints(Dkg, Dage, Dheight);
		        		pointsRounded = cf.roundIt(pointsUnrounded);
		        		cf.setPointsText(ME_pointsHeading, ME_points, pointsRounded);
					} else {
						System.out.println("Huh??");
					}
				}
			}
		}
	}
	
	public void checkImperialFieldValues() {
		double Dstones, Dlbs, Dage, Dfeet, Dinches, lbsToKG, Dcm, pointsUnrounded;
		String cm, pointsRounded;
		if(IM_stones.getText().toString().equals("") || IM_stones.getText().toString().equals(".")) {
			cf.displayErrorMessage(getActivity(), "Enter Stones Weight Value");
		} else {
			if(IM_lbs.getText().toString().equals("") || IM_lbs.getText().toString().equals(".")) {
				cf.displayErrorMessage(getActivity(), "Enter Pounds Weight Value");
			} else {
				if(IM_age.getText().toString().equals("") || IM_age.getText().toString().equals(".")) {
					cf.displayErrorMessage(getActivity(), "Enter Age Value");
				} else {
					if(IM_feet.getText().toString().equals("") || IM_feet.getText().toString().equals(".")) {
						cf.displayErrorMessage(getActivity(), "Enter Feet Height Value");
					} else {
						if(IM_inches.getText().toString().equals("") || IM_inches.getText().toString().equals(".")) {
							cf.displayErrorMessage(getActivity(), "Enter Inches Height Value");
						} else {
							Dstones = Double.parseDouble(IM_stones.getText().toString());
							Dlbs = Double.parseDouble(IM_lbs.getText().toString());
							Dage = Double.parseDouble(IM_age.getText().toString());
							Dfeet = Double.parseDouble(IM_feet.getText().toString());
							Dinches = Double.parseDouble(IM_inches.getText().toString());
							cm = cf.feetToCM(Dfeet, Dinches);
							Dcm = ((Double.parseDouble(cm)) / 100);
							lbsToKG = (cf.stoneToKG( Dstones, Dlbs ));
							if(cf.getSelectedRadioButton(getActivity(), IM_gender).equals("Male") ) {
								pointsUnrounded = calcMalePoints(lbsToKG, Dage, Dcm);
				        		pointsRounded = cf.roundIt(pointsUnrounded);
				        		cf.setPointsText(IM_pointsHeading, IM_points, pointsRounded);
							} else if ( cf.getSelectedRadioButton(getActivity(), IM_gender).equals("Female") ) {
								pointsUnrounded = calcFemalePoints(lbsToKG, Dage, Dcm);
				        		pointsRounded = cf.roundIt(pointsUnrounded);
				        		cf.setPointsText(IM_pointsHeading, IM_points, pointsRounded);
							} else {
								System.out.println("Huh??");
							}
						}
					}
				}
			}
		}
	}
	
	public Double calcMalePoints(double a, double b, double c) {
    	double ATEE = ( 0.9 * ( 864 - (9.72 * b) + 1.12 * ( 14.2 * a + 503 * c) ) + 200 );
		double pointsTmp = Math.max(ATEE - 1000, 1000.00);
    	//double pointsTmp1 = Math.round(pointsTmp) / 35;
    	double pointsTmp1 = pointsTmp / 35;
    	System.out.println("FEMALE: " + pointsTmp1);
    	double pointsTmp2 = Math.max(pointsTmp1 - 7 - 4, 29.00);
    	double pointsTmp3 = Math.min(pointsTmp2, 71);
    	return pointsTmp3;
    }
	
	public Double calcFemalePoints(double a, double b, double c) {
    	double ATEE = ( 0.9 * ( 387 - (7.31 * b) + 1.14 * ( 10.9 * a + 660.7 * c) ) + 200 );
		double pointsTmp = Math.max(ATEE - 1000, 1000.00);
    	//double pointsTmp1 = Math.round(pointsTmp) / 35;
		double pointsTmp1 = pointsTmp / 35;
    	System.out.println("FEMALE: " + pointsTmp1);
    	double pointsTmp2 = Math.max(pointsTmp1 - 7 - 4, 26.00);
    	double pointsTmp3 = Math.min(pointsTmp2, 71);
    	return pointsTmp3;
    }

	@Override
	public boolean onTouch(View v, MotionEvent arg1) {
		// TODO Auto-generated method stub
		EditText e = (EditText) rootView.findViewById(v.getId());
		e.setText("");
		return false;
	}
}