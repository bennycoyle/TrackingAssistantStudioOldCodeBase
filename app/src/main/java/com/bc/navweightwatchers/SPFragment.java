package com.bc.navweightwatchers;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnTouchListener;


/**
 * Created by brendan on 29/12/2015.
 */
public class SPFragment extends Fragment implements OnTouchListener {
    //Setup GUI Features variables
    private EditText protein, sugars, satFat, calories, valuePer, servings;
    private TextView pointsHeading, points;
    private Button calculate;
    CommonFunctions cf;
    View rootView;

    public static Fragment newInstance(Context context) {
        SPFragment f = new SPFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.spmenu, container, false);
        cf = new CommonFunctions();
        //Link GUI Var's to GUI Segments
        //EditText's
        protein = (EditText) rootView.findViewById(R.id.SP_protein);
        protein.setOnTouchListener(this);
        sugars = (EditText) rootView.findViewById(R.id.SP_sugars);
        sugars.setOnTouchListener(this);
        satFat = (EditText) rootView.findViewById(R.id.SP_satfat);
        satFat.setOnTouchListener(this);
        calories = (EditText) rootView.findViewById(R.id.SP_calories);
        calories.setOnTouchListener(this);
        valuePer = (EditText) rootView.findViewById(R.id.SP_valueper);
        valuePer.setOnTouchListener(this);
        servings = (EditText) rootView.findViewById(R.id.SP_servings);
        servings.setOnTouchListener(this);

        //TextView's
        pointsHeading = (TextView) rootView.findViewById(R.id.SP_pointsheading);
        points = (TextView) rootView.findViewById(R.id.SP_points);

        //Button
        calculate = (Button) rootView.findViewById(R.id.SP_calc);
        calculate.setOnClickListener(new View.OnClickListener() {
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
        double Dprotein, Dsugars, DsatFat, Dcalories, DvaluePer, Dservings, pointsUnrounded;
        String pointsRounded;

        if( calories.getText().toString().equals("") || calories.getText().toString().equals(".")) {
            cf.displayErrorMessage(getActivity(), "Enter Calories Value");
        } else {
            if ( satFat.getText().toString().equals("") || satFat.getText().toString().equals(".")) {
                cf.displayErrorMessage(getActivity(), "Enter Saturated Fats Value");
            } else {
                if ( sugars.getText().toString().equals("") || sugars.getText().toString().equals(".")) {
                    cf.displayErrorMessage(getActivity(), "Enter Sugars Value");
                } else {
                    if ( protein.getText().toString().equals("") || protein.getText().toString().equals(".")) {
                        cf.displayErrorMessage(getActivity(), "Enter Protein Value");
                    } else {
                        Dprotein = Double.parseDouble(protein.getText().toString());
                        Dsugars = Double.parseDouble(sugars.getText().toString());
                        DsatFat = Double.parseDouble(satFat.getText().toString());
                        Dcalories = Double.parseDouble(calories.getText().toString());
                        DvaluePer = getValuePer();
                        Dservings = getServings();

                        /*
                        Not using the 'getServingsValue() method at the moment. Might be a better way to do it in the future.
                        double[] valueOfServings = getServingsValue();
                        DvaluePer = valueOfServings[0];
                        Dservings = valueOfServings[1];
                        */

                        //System.out.println("DvaluePer: " + Double.toString(DvaluePer) + " Dservings: " + Double.toString(Dservings));

                        if ( DvaluePer > 0 && Dservings > 0 ) {
                            pointsUnrounded = calcPoints(Dprotein, Dsugars, DsatFat, Dcalories, DvaluePer, Dservings);
                            pointsRounded = cf.roundIt(pointsUnrounded);
                            cf.setPointsText(pointsHeading, points, pointsRounded);
                        } else if ( DvaluePer == 0 || Dservings == 0) {
                            cf.displayErrorMessage(getActivity(), "'Value Per' and 'Servings' must not be 0.");
                        }
                    }
                }
            }
        }
    }

    public Double getValuePer() {
        double x = -0.0099009900;
        if (valuePer.getText().toString().equals("") || valuePer.getText().toString().equals(".")) {
            if (servings.getText().toString().equals("") || servings.getText().toString().equals(".")) {
                x = 100;
            } else {
                cf.displayErrorMessage(getActivity(), "You must enter a 'Value Per' when using Servings.");
                x = -0.0099009900;
            }
        } else {
            x = Double.parseDouble(valuePer.getText().toString());
        }
        return x;
    }

    public Double getServings() {
        double x = -0.0099009900;
        if (servings.getText().toString().equals("") || servings.getText().toString().equals(".")) {
            if (valuePer.getText().toString().equals("") || valuePer.getText().toString().equals(".")) {
                x = 100;
            } else {
                cf.displayErrorMessage(getActivity(), "You must enter a 'Servings' when using 'Value Per'.");
                x = -0.0099009900;
            }
        } else {
            x = Double.parseDouble(servings.getText().toString());
        }
        return x;
    }

    public double[] getServingsValue() {
        //Not used at the moment. Might be a better way to do it in future.
        double[] y = {-0.0099009900, -0.0099009900};
        //double[] y = new double[2];
        Boolean servingsEmpty = false;
        Boolean valuePerEmpty = false;

        if ( servings.getText().toString().equals("") || servings.getText().toString().equals(".")) {
            servingsEmpty = true;
        }
        if ( valuePer.getText().toString().equals("") || valuePer.getText().toString().equals(".")) {
            valuePerEmpty = true;
        }

        if ( valuePerEmpty && servingsEmpty ) {
            y[0] = 100;
            y[1] = 100;
        }

        if ( valuePerEmpty == false && servingsEmpty == false ){
            y[0] = Double.parseDouble(valuePer.getText().toString());
            y[1] = Double.parseDouble(servings.getText().toString());
        }


        if ( valuePerEmpty == false) {
            if (servingsEmpty) {
                cf.displayErrorMessage(getActivity(), "Servings is Empty. Value is not");
            }
        }

        if ( valuePerEmpty == true ) {
            if( servingsEmpty == false ) {
                cf.displayErrorMessage(getActivity(), "Value is Empty. Servings is not");
            }
        }

        return y;
    }

    public double calcPoints(double a, double b, double c, double d, double e, double f) {
        double pointsTemp = ( ( d + ( b * 4 ) + ( c * 9 ) - ( a * 3.2 ) ) / 33 );
        double pointsOneGram = pointsTemp / e;
        double pointsServing = pointsOneGram * f;
        return pointsServing;
    }
    @Override
    public boolean onTouch(View v, MotionEvent arg1) {
        // TODO Auto-generated method stub
        EditText e = (EditText) rootView.findViewById(v.getId());
        e.setText("");
        return false;
    }


}
