package com.example.memoir;

import Model.GameModel;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultScreen extends Activity {
	Button playAgain;
	Button exitToMainMenu;
	TextView accuracy;
	TextView wordsPM;
	GameModel gm;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_screen_link);
        
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        Intent i = getIntent();
        gm =  (GameModel)i.getSerializableExtra("gameModel");
        
        playAgain = (Button) findViewById(R.id.playAgain);
        exitToMainMenu = (Button) findViewById(R.id.exitToMainMenu);
        accuracy = (TextView)findViewById(R.id.accuracyTxt);
        wordsPM  = (TextView)findViewById(R.id.wordsGuessedpm);
        
        int accuracyComputed = gm.computeAccuracy();
        //int wordsGuessed = gm.
        accuracy.setText(""+accuracyComputed);
        
        playAgain.setOnClickListener(new OnClickListener(){    
            @Override
			public void onClick(View v) {
            	
            }
        });
        
        exitToMainMenu.setOnClickListener(new OnClickListener(){    
            @Override
			public void onClick(View v) {
            	
            }
        });
	}
}
