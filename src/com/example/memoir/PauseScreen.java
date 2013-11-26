package com.example.memoir;

import Model.GameModel;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class PauseScreen extends Activity{
	
	private ImageButton resumeButton;
	private ImageButton restartButton;
	private ImageButton settingsButton;
	private ImageButton exitButton;
	private GameModel gm;
	private TextView gamePaused;
	private long timeRemaining;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pause_screen);
        
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        
        Intent i = getIntent();
        gm =  (GameModel)i.getSerializableExtra("gameModel");
        if(gm==null)
        	
        timeRemaining = i.getLongExtra("timeRemaining", 300000);
        Log.d("pauseTest", "time rem:"+timeRemaining);
        
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/Canter/Canter Bold.otf");
        
        resumeButton = (ImageButton) findViewById(R.id.resumeButton);
        restartButton = (ImageButton) findViewById(R.id.restartButton);
        settingsButton = (ImageButton) findViewById(R.id.settingsButton);
        exitButton = (ImageButton) findViewById(R.id.exitButton);
        gamePaused = (TextView) findViewById(R.id.gamePausedTxt);
        gamePaused.setTypeface(myTypeface);
        gamePaused.setTextSize(70);
        
        addListeners();
        Log.d("pauseTest", "timeRemaining on pause: " + i.getLongExtra("timeRemaining", 0) );
	}
	
	private void addListeners(){
		resumeButton.setOnClickListener(new OnClickListener(){    
            @Override
			public void onClick(View v) {
            	Intent intent = new Intent();
            	intent.putExtra("timeRemaining", timeRemaining);
            	setResult(RESULT_OK,intent);
            	finish();
			}
        }); 
		
		restartButton.setOnClickListener(new OnClickListener(){    
            @Override
			public void onClick(View v) {
                setResult(2);
                finish();
			}
        }); 
		
		settingsButton.setOnClickListener(new OnClickListener(){    
            @Override
			public void onClick(View v) {
            	Intent myIntent = new Intent(PauseScreen.this, PauseScreen.class);
            	//TODO: make options screen.
            	startActivity(myIntent);
			}
        }); 
		
		exitButton.setOnClickListener(new OnClickListener(){    
            @Override
			public void onClick(View v) {
            	//TODO: Record game
            	setResult(4);
                finish();
			}
        }); 
		
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) { //Back key pressed
			Intent intent = new Intent();
        	intent.putExtra("timeRemaining", timeRemaining);
        	setResult(RESULT_OK,intent);
        	finish();
	        return true;
		}
		return false;
	}
	public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
