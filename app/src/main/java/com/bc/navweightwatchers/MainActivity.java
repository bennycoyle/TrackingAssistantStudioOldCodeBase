package com.bc.navweightwatchers;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	public String[] menuEntries, fragments;
    private CharSequence mTitle;
    CheckBox prefCheckBox;
    TextView prefEditText;
    public static int settingsMenu = 0;
    SharedPreferences pref;
    String plan, hidden;


    final String[] PPFragments = {
            "com.bc.navweightwatchers.FoodFragment",
            "com.bc.navweightwatchers.ExerFragment",
            "com.bc.navweightwatchers.DailyFragment",
            "com.bc.navweightwatchers.FoodListFragment",
            "com.bc.navweightwatchers.FillingAndHealthyFragment",
            "com.bc.navweightwatchers.BoostersAndDrinksFragment",
            "com.bc.navweightwatchers.TreatsListFragment",
            "com.bc.navweightwatchers.AboutFragment",
    };
    final String [] SPFragments = {
            "com.bc.navweightwatchers.SPFragment",
            "com.bc.navweightwatchers.SPExerFragment",
            "com.bc.navweightwatchers.SPFoodListFragment",
            "com.bc.navweightwatchers.SPNoCountFragment",
            "com.bc.navweightwatchers.SPAlcoholFragment",
            "com.bc.navweightwatchers.AboutFragment"

    };
    final String[] HiddenPPFragments = {
            "com.bc.navweightwatchers.FoodFragment",
            "com.bc.navweightwatchers.ExerFragment",
            "com.bc.navweightwatchers.DailyFragment",
            "com.bc.navweightwatchers.FoodListFragment",
            "com.bc.navweightwatchers.FillingAndHealthyFragment",
            "com.bc.navweightwatchers.BoostersAndDrinksFragment",
            "com.bc.navweightwatchers.TreatsListFragment",
            "com.bc.navweightwatchers.CostaDrinksFragment",
            "com.bc.navweightwatchers.CostaFoodFragment",
            "com.bc.navweightwatchers.SubwayFragment",
            "com.bc.navweightwatchers.NandosFragment",
            "com.bc.navweightwatchers.AboutFragment"
    };
    final String [] HiddenSPFragments = {
            "com.bc.navweightwatchers.SPFragment",
            "com.bc.navweightwatchers.SPExerFragment",
            "com.bc.navweightwatchers.SPFoodListFragment",
            "com.bc.navweightwatchers.SPNoCountFragment",
            "com.bc.navweightwatchers.SPAlcoholFragment",
            "com.bc.navweightwatchers.CostaDrinksFragment",
            "com.bc.navweightwatchers.CostaFoodFragment",
            "com.bc.navweightwatchers.SubwayFragment",
            "com.bc.navweightwatchers.NandosFragment",
            "com.bc.navweightwatchers.AboutFragment"
    };

    private ActionBarDrawerToggle navDrawerToggle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		PreferenceManager.setDefaultValues(getBaseContext(), R.xml.preferences, false);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        plan = pref.getString(SetPrefActivity.KEY_PLAN_VALUE, "");
        hidden = pref.getString(SetPrefActivity.KEY_HIDDEN_EGG_VALUE, "");

		prefCheckBox = (CheckBox)findViewById(R.id.prefCheckBox);
		prefEditText = (TextView)findViewById(R.id.prefEditText);

        //Lets make sure that Hidden and plan are setup
        if (hidden.equals("") ) {
            //Default it to 0.
            System.out.println("Setting Hidden to 0");
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("hiddenMenu", "0").apply();
        }
        if (plan.equals("") ) {
            //Default it to 0.
            System.out.println("Setting PLAN to 0");
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("planType", "0").apply();
        }
		
		//Setup the Entries in the list from an array in strings.xml
        if ( hidden.equals("") || hidden.equals("0") ) {
            System.out.println("Hidden is 0!");

            if ( plan.equals("") ) {
                //No value for plan yet... Lets assume people want SmartPoints
                System.out.println("Plan is NOT SET!");
                menuEntries = getResources().getStringArray(R.array.menuItemsSP);
                fragments = SPFragments;
            }
            if ( plan.equals("0") ) {
                //SmartPoints
                System.out.println("PLAN is 0!");
                menuEntries = getResources().getStringArray(R.array.menuItemsSP);
                fragments = SPFragments;
            }
            if ( plan.equals("1") ) {
                //ProPoints
                System.out.println("PLAN is 1!");
                menuEntries = getResources().getStringArray(R.array.menuItemsPP);
                fragments = PPFragments;
            }
        }
        if ( hidden.equals("1") ) {
            //hidden is set.. Lets set this up.
            System.out.println("Hidden is 1!");
            if ( plan.equals("") || plan.equals("0") ) {
                System.out.println("Plan is NOT SET! or is 0..." + plan);
                menuEntries = getResources().getStringArray(R.array.hiddenMenuItemsSP);
                fragments = HiddenSPFragments;
            }
            if ( plan.equals("1") ) {
                System.out.println("PLAN is 1!");
                menuEntries = getResources().getStringArray(R.array.hiddenMenuItemsPP);
                fragments = HiddenPPFragments;
            }
        }



		//Populate the Navigation Drawer
		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActionBar().getThemedContext(), android.R.layout.simple_list_item_1, menuEntries);
		
		final DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawerLayout);
		final ListView navigationList = (ListView) findViewById(R.id.drawerList);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		navDrawerToggle = new ActionBarDrawerToggle(
			this,
			drawer,
			R.drawable.ic_drawer,
			R.string.drawerOpen,
			R.string.drawerClose
		) {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                System.out.println("onDrawerClosed");
            }
            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {

            }
            public void onDrawerSlide() {
                // TODO Auto-generated method stub
                System.out.println("onDrawerSlide");
            }
        };


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActionBar().getThemedContext(), android.R.layout.simple_list_item_1, menuEntries);
        drawer.setDrawerListener(navDrawerToggle);
        navigationList.setAdapter(adapter);

        navigationList.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int pos,long id){
                drawer.setDrawerListener( new DrawerLayout.SimpleDrawerListener(){
                    @Override
                    public void onDrawerClosed(View drawerView){
                        super.onDrawerClosed(drawerView);
                        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
                        tx.replace(R.id.main, Fragment.instantiate(MainActivity.this, fragments[pos]));
                        tx.commit();
                        setTitle(menuEntries[pos]);
                    }
                });
                drawer.closeDrawer(navigationList);
            }
        });
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.main,Fragment.instantiate(MainActivity.this, fragments[0]));
        tx.commit();
	}

    public void setTitle(CharSequence title) {
    	mTitle = title;
        getActionBar().setTitle(mTitle);
    }
	
	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        navDrawerToggle.syncState();
    }
	
	@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        navDrawerToggle.onConfigurationChanged(newConfig);
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
		InputMethodManager IM = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
    	IM.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		if ( item.getItemId() == R.id.action_settings ) {
			Intent intent = new Intent();
	        settingsMenu = 1;
			intent.setClass(MainActivity.this, SetPrefActivity.class);
	        startActivityForResult(intent, 0); 
		}else{
		    if (navDrawerToggle.onOptionsItemSelected(item)) {
		        return true;
		    }
		}       
        return super.onOptionsItemSelected(item);
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}