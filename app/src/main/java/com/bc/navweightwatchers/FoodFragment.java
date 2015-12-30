package com.bc.navweightwatchers;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FoodFragment extends Fragment implements OnTouchListener {
	//Setup GUI Features variables
	private EditText protein, carbs, fat, fibre, servings;
	private TextView pointsHeading, points;
	private Button calculate;
	CommonFunctions cf;
	View rootView;

    public static Fragment newInstance(Context context) {
    	FoodFragment f = new FoodFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.foodmenu, container, false);
        cf = new CommonFunctions();
        //Link GUI Var's to GUI Segments
        //EditText's
        protein = (EditText) rootView.findViewById(R.id.F_protein);
        protein.setOnTouchListener(this);
        carbs = (EditText) rootView.findViewById(R.id.F_carbs);
        carbs.setOnTouchListener(this);
        fat = (EditText) rootView.findViewById(R.id.F_fat);
        fat.setOnTouchListener(this);
        fibre = (EditText) rootView.findViewById(R.id.F_fibre);
        fibre.setOnTouchListener(this);
        servings = (EditText) rootView.findViewById(R.id.F_servings);
        servings.setOnTouchListener(this);
        
        //TextView's
        pointsHeading = (TextView) rootView.findViewById(R.id.F_pointsheading);
        points = (TextView) rootView.findViewById(R.id.F_points);
        
        //Button
        calculate = (Button) rootView.findViewById(R.id.F_calc);
        calculate.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		clearMessages();
        		checkFieldValues();	
        	}
        });
        return rootView;
    }
    
    public void clearMessages() {
    	pointsHeading.setText("");
    	points.setText("");
    }
    
    public void checkFieldValues() {
    	double Dprotein, Dcarbs, Dfat, Dfibre, Dservings, pointsUnrounded;
    	String pointsRounded;
    	
    	if( protein.getText().toString().equals("") || protein.getText().toString().equals(".")) {
    		cf.displayErrorMessage(getActivity(), "Enter Protein Value");
    	} else {
    		if ( carbs.getText().toString().equals("") || carbs.getText().toString().equals(".")) {
    			cf.displayErrorMessage(getActivity(), "Enter Carbohydrate Value");
    		} else {
    			if ( fat.getText().toString().equals("") || fat.getText().toString().equals(".")) {
        			cf.displayErrorMessage(getActivity(), "Enter Fat Value");
        		} else {
        			if ( fibre.getText().toString().equals("") || fibre.getText().toString().equals(".")) {
            			cf.displayErrorMessage(getActivity(), "Enter Fibre Value");
            		} else {
            			Dprotein = Double.parseDouble(protein.getText().toString());
            			Dcarbs = Double.parseDouble(carbs.getText().toString());
            			Dfat = Double.parseDouble(fat.getText().toString());
            			Dfibre = Double.parseDouble(fibre.getText().toString());
            			Dservings = getServingsValue();
            			pointsUnrounded = calcPoints(Dprotein, Dcarbs, Dfat, Dfibre, Dservings);
            			pointsRounded = cf.roundIt(pointsUnrounded);
            			cf.setPointsText(pointsHeading, points, pointsRounded);
            		}
        		}
    		}
    	}
    }
    
    public Double getServingsValue() {
    	double x;

		if ( servings.getText().toString().equals("") || servings.getText().toString().equals(".")) {
			x = 1;
		} else {
			x = Double.parseDouble(servings.getText().toString());
		}
    	return x;
    }
    
    public double calcPoints(double a, double b, double c, double d, double e) {
    	double pointsTemp = ( ( ( a / 10.9375 ) + ( b / 9.2105 ) + ( c / 3.8889 ) + ( d / 35 ) ) * e );
    	return pointsTemp;
    }
    @Override
	public boolean onTouch(View v, MotionEvent arg1) {
		// TODO Auto-generated method stub
		EditText e = (EditText) rootView.findViewById(v.getId());
		e.setText("");
		return false;
	}
}