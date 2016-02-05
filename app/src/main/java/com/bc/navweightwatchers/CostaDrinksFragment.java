package com.bc.navweightwatchers;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.ListFragmentLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class CostaDrinksFragment extends ListFragment {
	
	ArrayList<HashMap<String, String>> myList;
	
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			createHashMap(activity);
		} catch (Exception e){
			System.out.println("Exception is: " + e);
		}
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
	        
    	//View rootView = inflater.inflate(R.layout.spfoodlistmenu, container, false);
    	View rootView = inflater.inflate(R.layout.costalist, container, false);
		ListView list = (ListView) rootView.findViewById(R.id.list);
    	list.setFastScrollEnabled(true);
        SimpleAdapter pointsListAdapter = new SimpleAdapter(getActivity(), myList, R.layout.costalist, new String[] {"Drink", "Milk", "Small", "Med", "Large"}, new int[] {R.id.cdrink, R.id.cmilk, R.id.csmall, R.id.cmed, R.id.clarge});
        try {
        	list.setAdapter(pointsListAdapter);
        } catch (Exception e) {
        	System.out.println("Exception is: " + e);
        }
        ListFragmentLayout.setupCostaDrinksIds(rootView);
	    return rootView;
	}

	public void createHashMap(Activity c) throws IOException{
    	myList = new ArrayList<HashMap<String, String>>();
    	HashMap<String, String> map = new HashMap<String, String>();
    	
    	InputStream is = c.getAssets().open("costadrinks.bc");
    	BufferedReader in = new BufferedReader(new InputStreamReader(is));
    	String line = "";

    	while ((line = in.readLine()) != null) {
            String parts[] = line.split("-");
            map.put("Drink", parts[0]);
			map.put("Milk", parts[1]);
            map.put("Small", parts[2]);
            map.put("Med", parts[3]);
			map.put("Large", parts[4]);
            myList.add(map);
            map = new HashMap<String, String>();
        }
        in.close();   
	}
}