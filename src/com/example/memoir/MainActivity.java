package com.example.memoir;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private MediaPlayer buttonSound;
	
	private ImageButton beginButton;
	private ImageButton statisticsButton;
	private ImageView aboutButton;
	private TextView nameText;
	private TextView memoir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        
        buttonSound = MediaPlayer.create(MainActivity.this, R.raw.button);
        
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/Canter/Canter Bold.otf");
        
        beginButton = (ImageButton) findViewById(R.id.beginButton);
        statisticsButton = (ImageButton) findViewById(R.id.statisticsButton);
        nameText = (TextView) findViewById(R.id.nameText);
        memoir = (TextView) findViewById(R.id.memoirTextView);
        aboutButton = (ImageView) findViewById(R.id.aboutButton);
        memoir.setTypeface(myTypeface);
        memoir.setTextSize(100);
        
        addListeners();
        
    }

    private void addListeners(){

        beginButton.setOnClickListener(new OnClickListener(){    
            @Override
			public void onClick(View v) {
            	buttonSound.start();
            	Intent myIntent = new Intent(MainActivity.this, GameListScreen.class);
                startActivity(myIntent);
			}
        }); 
        
        statisticsButton.setOnClickListener(new OnClickListener(){    
            @Override
			public void onClick(View v) {
            	buttonSound.start();
            	Intent myIntent = new Intent(MainActivity.this, StatisticsScreen.class);
                startActivity(myIntent);
			}
        }); 
        
        aboutButton.setOnClickListener(new OnClickListener(){    
            @Override
			public void onClick(View v) {
            	buttonSound.start();
            	Intent myIntent = new Intent(MainActivity.this, AboutScreen.class);
                startActivity(myIntent);
			}
        }); 
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
