package com.bc.navweightwatchers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View.OnTouchListener;
import android.widget.PopupWindow;
import android.view.ViewGroup.LayoutParams;
import android.view.Gravity;

/**
 * Created by brendan on 29/12/2015.
 */
public class SPFragment extends Fragment implements OnTouchListener {
    //Setup GUI Features variables
    private EditText protein, sugars, satFat, calories, valuePer, servings;
    private ImageButton helpB;
    private TextView pointsHeading, points;
    private Button calculate;
    private String hidden;
    private PopupWindow mPopupWindow;
    SharedPreferences pref;
    SetPrefActivity spf;

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
        spf = new SetPrefActivity();
        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());

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

        helpB = (ImageButton) rootView.findViewById(R.id.helpButton);
        helpB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //cf.displayErrorMessage(getActivity(), "Pressing the Help Icon! :)");


                // Initialize a new instance of LayoutInflater service
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                // Inflate the custom layout/view
                View customView = inflater.inflate(R.layout.help_layout,null);

                /*
                    public PopupWindow (View contentView, int width, int height)
                        Create a new non focusable popup window which can display the contentView.
                        The dimension of the window must be passed to this constructor.

                        The popup does not provide any background. This should be handled by
                        the content view.

                    Parameters
                        contentView : the popup's content
                        width : the popup's width
                        height : the popup's height
                */
                // Initialize a new instance of popup window
                mPopupWindow = new PopupWindow(
                        customView,
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT
                );

                // Set an elevation value for popup window
                // Call requires API level 21
                /*if(Build.VERSION.SDK_INT>=21){
                    mPopupWindow.setElevation(5.0f);
                }*/

                // Get a reference for the custom view close button
                Button closeButton = (Button) customView.findViewById(R.id.close);

                // Set a click listener for the popup window close button
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Dismiss the popup window
                        mPopupWindow.dismiss();
                    }
                });

                /*
                    public void showAtLocation (View parent, int gravity, int x, int y)
                        Display the content view in a popup window at the specified location. If the
                        popup window cannot fit on screen, it will be clipped.
                        Learn WindowManager.LayoutParams for more information on how gravity and the x
                        and y parameters are related. Specifying a gravity of NO_GRAVITY is similar
                        to specifying Gravity.LEFT | Gravity.TOP.

                    Parameters
                        parent : a parent view to get the getWindowToken() token from
                        gravity : the gravity which controls the placement of the popup window
                        x : the popup's x location offset
                        y : the popup's y location offset
                */
                // Finally, show the popup window at the center location of root relative layout
                mPopupWindow.showAtLocation(rootView, Gravity.CENTER,0,0);
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


        if (    calories.getText().toString().equals("") &&
                satFat.getText().toString().equals("") &&
                sugars.getText().toString().equals("") &&
                protein.getText().toString().equals("23669") ) {
            //This is a hidden menu
            hidden = pref.getString(SetPrefActivity.KEY_HIDDEN_EGG_VALUE, "");
            SharedPreferences.Editor editor = pref.edit();

            if ( hidden.equals("0") ) {
                System.out.println("hidden is 0, changing to 1");
                editor.putString("hiddenMenu", "1").apply();
                cf.displayErrorMessage(getActivity(), "Restart the App to ADD the extra treats! :)");
            } else if ( hidden.equals("1") ) {
                System.out.println("hidden is 1, changing to 0");
                editor.putString("hiddenMenu", "0").apply();
                cf.displayErrorMessage(getActivity(), "Restart the App to HIDE the extra treats! :)");
            } else {
                System.out.println("This shouldn't happen");
            }

            Intent i = getActivity().getPackageManager().getLaunchIntentForPackage( getActivity().getPackageName() );
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

        }else if ( calories.getText().toString().equals("") || calories.getText().toString().equals(".")) {
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
                            if ( Integer.parseInt(pointsRounded) < 0 ) {
                                pointsRounded = "0";
                            }
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
