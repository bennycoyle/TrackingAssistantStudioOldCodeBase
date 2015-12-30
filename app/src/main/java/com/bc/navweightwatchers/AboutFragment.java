package com.bc.navweightwatchers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AboutFragment extends Fragment {

	private Button btkcFb, btkcTwit, btkcTube;
	private Button bcFb, bcTwit, bcTube, bcGoog, bcSound;
	private String appFbPage, appTwit, appTube, appGoog, appSound;
	private String webFb, webTwit, webTube, webGoog, webSound;
	CommonFunctions cf;
	//Use this if want to connect to a facebook profile
	//private String appFbProfile;
	
	public static Fragment newInstance(Context context) {
		AboutFragment f = new AboutFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.about, container, false);
        //Setup personal buttons
    	bcFb = (Button) rootView.findViewById(R.id.bcFacebook);
    	bcTwit = (Button) rootView.findViewById(R.id.bcTwitter);
    	bcTube = (Button) rootView.findViewById(R.id.bcYoutube);
    	bcGoog = (Button) rootView.findViewById(R.id.bcGoogle);
    	bcSound = (Button) rootView.findViewById(R.id.bcSoundcloud);
    	
    	//Setup band buttons
    	btkcFb = (Button) rootView.findViewById(R.id.btkcFacebook);
    	btkcTwit = (Button) rootView.findViewById(R.id.btkcTwitter);
    	btkcTube = (Button) rootView.findViewById(R.id.btkcYoutube);
    	
    	//Setup App Link URL's
    	appFbPage = "fb://page/";
    	//appFbProfile = "fb://profile/";
    	appTwit = "twitter://user?screen_name=";
    	appTube = "http://www.youtube.com/user/";
    	appGoog = "gplus://+";
    	appSound = "soundcloud:users:";
    	
    	//Setup Web Link URL's
    	webFb = "http://www.facebook.com/";
    	webTwit = "http://www.twitter.com/";
    	webTube = "http://www.youtube.com/user/";
    	webGoog = "http://plus.google.com/+";
    	webSound = "http://www.soundcloud.com/";
    	
    	cf = new CommonFunctions();
    	
    	//Setup person onClickListeners
    	bcFb.setOnClickListener(new OnClickListener() {
    		public void onClick(View v) {
    			String appID = appFbPage + "592183144144809";
    			String webID = webFb + "brendancoylemusic";
    			launchIntent(appID, webID);
    		}
    	});
    	
    	bcTwit.setOnClickListener(new OnClickListener() {
    		public void onClick(View v) {
    			String userID = "bennycoyle";
    			String appID = appTwit + userID;
    			String webID = webTwit + userID;
    			launchIntent(appID, webID);
    		}
    	});
    	
    	bcTube.setOnClickListener(new OnClickListener() {
    		public void onClick(View v) {
    			String userID = "brendanmjcoyle";
    			String appID = appTube + userID;
    			String webID = webTube + userID;
    			launchIntent(appID, webID);
    		}
    	});
    	
    	bcGoog.setOnClickListener(new OnClickListener() {
    		public void onClick(View v) {
    			String userID = "BrendanCoyleMusic";
    			String appID = appGoog + userID;
    			String webID = webGoog + userID;
    			launchIntent(appID, webID);
    		}
    	});
    	
    	bcSound.setOnClickListener(new OnClickListener() {
    		public void onClick(View v) {
    			String userID = "bennycoyle";
    			String appID = appSound + userID;
    			String webID = webSound + userID;
    			launchIntent(appID, webID);
    		}
    	});
    	
    	
    	//Setup Band Button Clicks
    	btkcFb.setOnClickListener(new OnClickListener() {
    		public void onClick(View v) {
    			String appID = appFbPage + "255608371117962";
    			String webID = webFb + "bitethekillercat";
    			launchIntent(appID, webID);
    		}
    	});
    	
    	btkcTwit.setOnClickListener(new OnClickListener() {
    		public void onClick(View v) {
    			String userID = "bitethekillerc";
    			String appID = appTwit + userID;
    			String webID = webTwit + userID;
    			launchIntent(appID, webID);
    		}
    	});
    	
    	btkcTube.setOnClickListener(new OnClickListener() {
    		public void onClick(View v) {
    			String userID = "bitethekillercat";
    			String appID = appTube + userID;
    			String webID = webTube + userID;
    			launchIntent(appID, webID);
    		}
    	});
                
        return rootView;
    }
    
    public void launchIntent(String app, String web) {
    	try {
    		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(app));
    		startActivity(intent);
    	} catch (Exception e) {
    		//Application is not installed!
    		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(web));
    		startActivity(intent);
    	}
    } 
}