package com.example.memoir;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class RouteStart extends Activity {
	TextView progressLabel;
	ImageView firstImgRoute;
	TextView secondWordLabel;
	TextView timeLabel;
	Button nextBtn;
	Button prevBtn;
	ProgressBar progressBar;
	ProgressBar timeBar;
	CountDownTimer timer;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_start);
        
        ActionBar actionBar = getActionBar();
        actionBar.hide();
       
        progressLabel = (TextView) findViewById(R.id.progressRouteLbl2);
        firstImgRoute = (ImageView) findViewById(R.id.routeImg);
    	secondWordLabel = (TextView) findViewById(R.id.firstRouteWord);
    	timeLabel = (TextView) findViewById(R.id.timeRouteLabel2);
    	nextBtn = (Button) findViewById(R.id.nextRouteBtn);
    	prevBtn = (Button) findViewById(R.id.prevRouteBtn);
    	progressBar = (ProgressBar) findViewById(R.id.progressRouteBar2);
    	timeBar = (ProgressBar)findViewById(R.id.timeRouteBar2);
    	
    	CountDownTimer timer;
	}
}
