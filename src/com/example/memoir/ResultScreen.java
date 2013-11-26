package com.example.memoir;

import Model.GameModel;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
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
	TextView goodJob;
	GameModel gm;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_screen_link);
        
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        Intent i = getIntent();
        gm =  (GameModel)i.getSerializableExtra("gameModel");
        
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/Canter/Canter Bold.otf");
        
        playAgain = (Button) findViewById(R.id.playAgain);
        exitToMainMenu = (Button) findViewById(R.id.exitToMainMenu);
        accuracy = (TextView)findViewById(R.id.accuracyTxt);
        wordsPM  = (TextView)findViewById(R.id.wordsGuessedpm);
        goodJob= (TextView)findViewById(R.id.goodJob);
        goodJob.setTypeface(myTypeface);
        int accuracyComputed = gm.computeAccuracy();
        float wordsGuessed = gm.computeWordPerMin();
        accuracy.setText(""+accuracyComputed +"%");
        wordsPM.setText(""+wordsGuessed);
        
        playAgain.setOnClickListener(new OnClickListener(){    
            @Override
			public void onClick(View v) {
            	Intent i = new Intent(ResultScreen.this, LinkPhaseActivity.class);
            	startActivity(i);
            	finish();
            }
        });
        
        exitToMainMenu.setOnClickListener(new OnClickListener(){    
            @Override
			public void onClick(View v) {
            	finish();
            }
        });
	}
}
