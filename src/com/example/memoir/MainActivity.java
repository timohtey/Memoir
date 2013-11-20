package com.example.memoir;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ImageButton beginButton;
	private ImageButton statisticsButton;
	private ImageButton aboutButton;
	private TextView nameText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        
        beginButton = (ImageButton) findViewById(R.id.beginButton);
        statisticsButton = (ImageButton) findViewById(R.id.statisticsButton);
        nameText = (TextView) findViewById(R.id.nameText);
        aboutButton = (ImageButton) findViewById(R.id.aboutButton);
        
        addListeners();
        
    }

    private void addListeners(){

        beginButton.setOnClickListener(new OnClickListener(){    
            @Override
			public void onClick(View v) {
            	Intent myIntent = new Intent(MainActivity.this, GameListScreen.class);
                startActivity(myIntent);
			}
        }); 
        
        statisticsButton.setOnClickListener(new OnClickListener(){    
            @Override
			public void onClick(View v) {
            	Intent myIntent = new Intent(MainActivity.this, StatisticsScreen.class);
                startActivity(myIntent);
			}
        }); 
        
        aboutButton.setOnClickListener(new OnClickListener(){    
            @Override
			public void onClick(View v) {
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
