package com.example.memoir;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class GameListScreen extends Activity {
	
	private MediaPlayer buttonSound;
	
	private ImageButton beginLinkingButton;
	private ImageButton customGameButton;
	private ImageButton tutorialButton;
	private ImageButton tutorialButton2;
	private ImageButton startButton;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamelist_screen);
        
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        
        buttonSound = MediaPlayer.create(GameListScreen.this, R.raw.button);
        
        beginLinkingButton = (ImageButton) findViewById(R.id.beginLinkingButton);
        customGameButton = (ImageButton) findViewById(R.id.customGameButton);
        tutorialButton = (ImageButton) findViewById(R.id.tutorialButton);
        tutorialButton2= (ImageButton) findViewById(R.id.tutorialButton2);
        startButton = (ImageButton) findViewById(R.id.startBtn);
        
        addListeners();
    }

	private void addListeners(){
		 beginLinkingButton.setOnClickListener(new OnClickListener(){    
	            @Override
				public void onClick(View v) {
	            	buttonSound.start();
	            	Intent myIntent = new Intent(GameListScreen.this, LoadingScreen.class);
	                startActivity(myIntent);
				}
	        }); 
	        
	        customGameButton.setOnClickListener(new OnClickListener(){    
	            @Override
				public void onClick(View v) {
	            	buttonSound.start();
	            	Intent myIntent = new Intent(GameListScreen.this, LinkPhaseUtility_Start.class);
	            	startActivity(myIntent);
				}
	        }); 
	        
	        tutorialButton.setOnClickListener(new OnClickListener(){    
	            @Override
				public void onClick(View v) {
	            	buttonSound.start();
//	            	Intent myIntent = new Intent(GameListScreen.this, StatisticsScreen.class);
//	                startActivity(myIntent);
				}
	        });
	        tutorialButton2.setOnClickListener(new OnClickListener(){    
	            @Override
				public void onClick(View v) {
	            	buttonSound.start();
	            	Intent myIntent = new Intent(GameListScreen.this, AddRouteScreen.class);
	                startActivity(myIntent);
				}
	        });
	        
	        startButton.setOnClickListener(new OnClickListener(){    
	            @Override
				public void onClick(View v) {
	            	buttonSound.start();
	            	Intent myIntent = new Intent(GameListScreen.this, RouteMain.class);
	                startActivity(myIntent);
				}
	        });
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void startLinkMethodGame(){
    	Intent intent = new Intent(this,LinkPhaseActivity.class);
    	startActivity(intent);
    }

}
