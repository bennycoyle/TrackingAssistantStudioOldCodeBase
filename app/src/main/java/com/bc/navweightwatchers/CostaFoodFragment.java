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

public class CostaFoodFragment extends ListFragment {
	
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
	        
    	View rootView = inflater.inflate(R.layout.treatmenu, container, false);
    	ListView list = (ListView) rootView.findViewById(R.id.list);
    	list.setFastScrollEnabled(true);
        SimpleAdapter pointsListAdapter = new SimpleAdapter(getActivity(), myList, R.layout.treatmenu, new String[] {"Item", "Amount"}, new int[] {R.id.treatItem, R.id.treatAmount});
        try {
        	list.setAdapter(pointsListAdapter);
        } catch (Exception e) {
        	System.out.println("Exception is: " + e);
        }
        ListFragmentLayout.setupTreatIds(rootView);
	    return rootView;    
	}

	public void createHashMap(Activity c) throws IOException{
    	myList = new ArrayList<HashMap<String, String>>();
    	HashMap<String, String> map = new HashMap<String, String>();
    	
    	InputStream is = c.getAssets().open("costafood.bc");
    	BufferedReader in = new BufferedReader(new InputStreamReader(is));
    	String line = "";

    	while ((line = in.readLine()) != null) {
            String parts[] = line.split("-");
            map.put("Item", parts[0]);
            map.put("Amount", parts[1]);
            myList.add(map);
            map = new HashMap<String, String>();
        }
        in.close();   
	}
}