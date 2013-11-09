package com.example.memoir;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class PauseScreen extends Activity{
	
	private ImageButton resumeButton;
	private ImageButton restartButton;
	private ImageButton settingsButton;
	private ImageButton exitButton;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pause_screen);
        
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        
        resumeButton = (ImageButton) findViewById(R.id.resumeButton);
        restartButton = (ImageButton) findViewById(R.id.restartButton);
        settingsButton = (ImageButton) findViewById(R.id.settingsButton);
        exitButton = (ImageButton) findViewById(R.id.exitButton);
        
        addListeners();
        
	}
	
	private void addListeners(){
		resumeButton.setOnClickListener(new OnClickListener(){    
            @Override
			public void onClick(View v) {
            	Intent myIntent = new Intent(PauseScreen.this, LinkPhaseActivity.class);
                startActivity(myIntent);
			}
        }); 
		
		restartButton.setOnClickListener(new OnClickListener(){    
            @Override
			public void onClick(View v) {
            	Intent myIntent = new Intent(PauseScreen.this, PauseScreen.class);
                startActivity(myIntent);
			}
        }); 
		
		settingsButton.setOnClickListener(new OnClickListener(){    
            @Override
			public void onClick(View v) {
            	Intent myIntent = new Intent(PauseScreen.this, PauseScreen.class);
                startActivity(myIntent);
			}
        }); 
		
		exitButton.setOnClickListener(new OnClickListener(){    
            @Override
			public void onClick(View v) {
            	Intent myIntent = new Intent(PauseScreen.this, MainActivity.class);
                startActivity(myIntent);
			}
        }); 
		
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
