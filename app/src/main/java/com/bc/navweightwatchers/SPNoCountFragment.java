package com.bc.navweightwatchers;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SPNoCountFragment extends ListFragment {
	ArrayList<String> fAndHList;
	
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
			createArrayList(activity);
		} catch (Exception e){
			System.out.println("Exception is: " + e);
		}
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.spnocountmenu, container, false);
    	
    	ListView list = (ListView) rootView.findViewById(R.id.list);
    	list.setFastScrollEnabled(true);
        ArrayAdapter<String> fAndHAdapter = new ArrayAdapter<String>(getActivity(), R.layout.fillingandhealthymenu, R.id.fandhitem, fAndHList);
        
        try {
        	list.setAdapter(fAndHAdapter);
	    } catch (Exception e) {
	    	System.out.println("Exception is: " + e);
        }
	    return rootView;    
	}
	
	public void createArrayList(Activity c) throws IOException{
    	fAndHList = new ArrayList<String>();
    	InputStream is = c.getAssets().open("nocount.bc");
    	BufferedReader in = new BufferedReader(new InputStreamReader(is));
    	
    	String line = "";
        while ((line = in.readLine()) != null) {
            fAndHList.add(line);
        }
        in.close();
	}
}