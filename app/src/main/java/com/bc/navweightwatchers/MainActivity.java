package com.bc.navweightwatchers;

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

	private String[] menuEntries;
    private CharSequence mTitle;
    CheckBox prefCheckBox;
    TextView prefEditText;
    public static int settingsMenu = 0;

	final String[] fragments = {
            "com.bc.navweightwatchers.SPFragment",
            "com.bc.navweightwatchers.FoodFragment",
            "com.bc.navweightwatchers.ExerFragment",
            "com.bc.navweightwatchers.DailyFragment",
            "com.bc.navweightwatchers.FoodListFragment",
            "com.bc.navweightwatchers.FillingAndHealthyFragment",
            "com.bc.navweightwatchers.BoostersAndDrinksFragment",
            "com.bc.navweightwatchers.TreatsListFragment",
            "com.bc.navweightwatchers.AboutFragment",
    };

    private ActionBarDrawerToggle navDrawerToggle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		PreferenceManager.setDefaultValues(getBaseContext(), R.xml.preferences, false);
		
		prefCheckBox = (CheckBox)findViewById(R.id.prefCheckBox);
		prefEditText = (TextView)findViewById(R.id.prefEditText);
		
		//Setup the Entries in the list from an array in strings.xml
		menuEntries = getResources().getStringArray(R.array.menuItems);
		
		//Populate the Navigation Drawer
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActionBar().getThemedContext(), android.R.layout.simple_list_item_1, menuEntries);
		
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

            }
            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
	
            }
        };

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