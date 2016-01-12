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
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class SPExerFragment extends Fragment implements OnTouchListener {
	//Setup Metric GUI Features variables
	private EditText ME_kilo, ME_time;
	private TextView ME_pointsHeading, ME_points;
	private RadioGroup ME_intensity;
	private Button ME_calculate;	
	
	//Setup Imperial GUI Features variables
	private EditText IM_stones, IM_lbs, IM_time;
	private TextView IM_pointsHeading, IM_points;
	private RadioGroup IM_intensity;
	private Button IM_calculate;
	
	//Other Variables
	SharedPreferences pref;
	private String unit = "2";
	private int resumeCount = 0;
	View rootView;
	CommonFunctions cf;
	private double low = 0.109479270;
	private double med = 0.152862160;
	private double high = 0.383441654;

	public static Fragment newInstance(Context context) {
    	SPExerFragment f = new SPExerFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
    	pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
    	cf = new CommonFunctions();
    	setUnitValue();
    	
        if (unit.equals("0")) {
        	rootView = inflater.inflate(R.layout.spexercisemenu_kgs, container, false);
        	setupMetricGUIIds();
        	//Listen for Metric button clicks
            ME_calculate.setOnClickListener(new OnClickListener() {
            	public void onClick(View v) {
            		checkMetricFieldValues();
            	}
            });
        } else if (unit.equals("1")) {
        	rootView = inflater.inflate(R.layout.spexercisemenu_lbs, container, false);
        	setupImperialGUIIds();
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
    	unit = pref.getString(SetPrefActivity.KEY_UNIT_VALUE, "");
    	//Now check that it's not empty. It might be empty if setting menu has not been opened
    	//If it's empty set to default value of 0
    	if (unit.equals("")) {
    		unit = "0";
    	}
    }
 
    public void onResume() {
    	super.onResume();
    	if ( resumeCount == 0 ) {
    		resumeCount++;
    	} else {
        	Fragment frag = new SPExerFragment();
        	FragmentTransaction transaction = getFragmentManager().beginTransaction();
        	transaction.replace(this.getId(), frag);
        	transaction.commit();
    	}
    }

    public void setupMetricGUIIds() {
    	ME_kilo = (EditText) rootView.findViewById(R.id.E_ME_kilograms);
    	ME_kilo.setOnTouchListener(this);
    	ME_time = (EditText) rootView.findViewById(R.id.E_ME_time);
    	ME_time.setOnTouchListener(this);
    	ME_pointsHeading = (TextView) rootView.findViewById(R.id.E_ME_pointsheading);
    	ME_points = (TextView) rootView.findViewById(R.id.E_ME_points);
    	ME_intensity = (RadioGroup) rootView.findViewById(R.id.E_ME_intensity_RG);
    	ME_calculate = (Button) rootView.findViewById(R.id.E_ME_calc);
    }
    
    public void setupImperialGUIIds() {
    	IM_stones = (EditText) rootView.findViewById(R.id.E_IM_stones);
    	IM_stones.setOnTouchListener(this);
    	IM_lbs = (EditText) rootView.findViewById(R.id.E_IM_pounds);
    	IM_lbs.setOnTouchListener(this);
    	IM_time = (EditText) rootView.findViewById(R.id.E_IM_time);
    	IM_time.setOnTouchListener(this);
    	IM_pointsHeading = (TextView) rootView.findViewById(R.id.E_IM_pointsheading);
    	IM_points = (TextView) rootView.findViewById(R.id.E_IM_points);
    	IM_intensity = (RadioGroup) rootView.findViewById(R.id.E_IM_intensity_RG);
    	IM_calculate = (Button) rootView.findViewById(R.id.E_IM_calc);
    }
	
	public void checkMetricFieldValues() {
		double Dkg, Dtime, pointsUnrounded;
		String pointsRounded;
		if( ME_kilo.getText().toString().equals("") || ME_kilo.getText().toString().equals(".")) {
			cf.displayErrorMessage(getActivity(), "Enter Weight in KG's");
		} else {
			if( ME_time.getText().toString().equals("") || ME_time.getText().toString().equals(".")) {
				cf.displayErrorMessage(getActivity(), "Enter Time Value");
			} else {
				Dkg = Double.parseDouble( ME_kilo.getText().toString());
				Dtime = Double.parseDouble( ME_time.getText().toString());
				if(cf.getSelectedRadioButton(getActivity(), ME_intensity).equals("Low")) {
					pointsUnrounded = calcIntensityPoints(low, Dkg, Dtime );
					pointsRounded = cf.roundIt(pointsUnrounded);
					cf.setPointsText(ME_pointsHeading, ME_points, pointsRounded);
				} else if ( cf.getSelectedRadioButton(getActivity(), ME_intensity).equals("Med") ) {
					pointsUnrounded = calcIntensityPoints(med, Dkg, Dtime );
					pointsRounded = cf.roundIt(pointsUnrounded);
					cf.setPointsText(ME_pointsHeading, ME_points, pointsRounded);
				} else if ( cf.getSelectedRadioButton(getActivity(), ME_intensity).equals("High") ) {
					pointsUnrounded = calcIntensityPoints(high, Dkg, Dtime );
					pointsRounded = cf.roundIt(pointsUnrounded);
					cf.setPointsText(ME_pointsHeading, ME_points, pointsRounded);
				} else {
					System.out.println("What Intensity??");
				}
			}
		}
	}
	
	public void checkImperialFieldValues() {
		double Dtime, lbsToKG, pointsUnrounded;
		String pointsRounded;
		
		if(IM_stones.getText().toString().equals("") || IM_stones.getText().toString().equals(".")) {
			cf.displayErrorMessage(getActivity(), "Enter Stones Weight Value");
		} else {
			if(IM_lbs.getText().toString().equals("") || IM_lbs.getText().toString().equals(".")) {
				cf.displayErrorMessage(getActivity(), "Enter Pounds Weight Value");
			} else {
				if(IM_time.getText().toString().equals("") || IM_time.getText().toString().equals(".")) {
					cf.displayErrorMessage(getActivity(), "Enter Time Value");
				} else {
					Dtime = Double.parseDouble(IM_time.getText().toString());
					lbsToKG = cf.stoneToKG( Double.parseDouble(IM_stones.getText().toString()), Double.parseDouble(IM_lbs.getText().toString()) );
					if(cf.getSelectedRadioButton(getActivity(), IM_intensity).equals("Low")) {
						pointsUnrounded = calcIntensityPoints(low, lbsToKG, Dtime );
						pointsRounded = cf.roundIt(pointsUnrounded);
						cf.setPointsText(IM_pointsHeading, IM_points, pointsRounded);
					} else if ( cf.getSelectedRadioButton(getActivity(), IM_intensity).equals("Med") ) {
						pointsUnrounded = calcIntensityPoints(med, lbsToKG, Dtime );
						pointsRounded = cf.roundIt(pointsUnrounded);
						cf.setPointsText(IM_pointsHeading, IM_points, pointsRounded);
					} else if ( cf.getSelectedRadioButton(getActivity(), IM_intensity).equals("High") ) {
						pointsUnrounded = calcIntensityPoints(high, lbsToKG, Dtime );
						pointsRounded = cf.roundIt(pointsUnrounded);
						cf.setPointsText(IM_pointsHeading, IM_points, pointsRounded);
					} else {
						System.out.println("What Intensity??");
					}
				}
			}
		}
	}
	
	public double calcIntensityPoints( double a, double b, double c ) {
    	double points = (a * b * c) / 100;
    	return points;
    }
	@Override
	public boolean onTouch(View v, MotionEvent arg1) {
		// TODO Auto-generated method stub
		EditText e = (EditText) rootView.findViewById(v.getId());
		e.setText("");
		return false;
	}
}