package com.example.memoir;

import Model.GameModel;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

public class QuizPhaseActivity extends Activity {

	GameModel gm;
	
	TextView progressLabel;
	TextView firstWordLabel;
	TextView secondWordLabel;
	TextView timeLabel;
	TextView accuracyLbl;
	Button nextWordLabel;
	ProgressBar progressBar;
	ProgressBar timeBar;
	CountDownTimer timer;
	long timeRemaining=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz2_phase);
        
		ActionBar actionBar = getActionBar();
	    actionBar.hide();
		
        Intent i = getIntent();
        gm =  (GameModel)i.getSerializableExtra("gameModel");
        
        timeLabel = (TextView)findViewById(R.id.timeLabel2);
		timeBar = (ProgressBar) findViewById(R.id.timeBar2);
		
        progressLabel = (TextView)findViewById(R.id.progressLabel2);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        accuracyLbl = (TextView)findViewById(R.id.accuracyLbl2);
        
		firstWordLabel = (TextView)findViewById(R.id.firstWordLabel2);
		secondWordLabel = (EditText)findViewById(R.id.secondWordField2);
		
		timeBar.setMax(gm.getTimeLimit());
		progressBar.setMax(gm.getWordCount());
        gm.startQuizPhase();
        
        timer = new CountDownTimer(gm.getTimeLimit(), 1000) {
			int i = 60;
		     public void onTick(long millisUntilFinished) {
		    	 timeBar.setProgress(gm.getTimeLimit()-(int)millisUntilFinished);
		    	 timeRemaining =  millisUntilFinished;
		    	 i--;
		    	 if(i<10){
			    	 timeLabel.setText((millisUntilFinished/1000)/60 + ":0"+ i);
		    	 }
		    	 else timeLabel.setText((millisUntilFinished/1000)/60 + ":"+ i);
		    	 if(i == 0){
		    		 i = 60;
		    	 }
		    	 if (i ==1 && (millisUntilFinished/1000)/60 == 0 ){
		    		 i = 0;
		    	 }
		     }

		     public void onFinish() {
		    	 //TODO: Goto resultscreen
		    	 Intent intent = new Intent(QuizPhaseActivity.this, AboutScreen.class);
		    	 gm.endQuizPhase(false);
		    	 intent.putExtra("gameModel",gm);
		    	 startActivity(intent);
		     }
		     
		     
		  }.start();
		  
		  //OVERRIDE KEYBOARD DONE
		  secondWordLabel.setOnEditorActionListener(new TextView.OnEditorActionListener() {
		        @Override
		        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		            if (actionId == EditorInfo.IME_ACTION_DONE) {

		            	String answer = secondWordLabel.getText().toString();
		        		if(gm.answerQuiz(answer)){
		        			updateLabels();
		        			secondWordLabel.setText("");
		        	        if(gm.getCurrentWordIndex()==gm.getWordCount()-1){
		        	        	gm.endQuizPhase(true);
		        	        	finish();
		        	        	//TODO: goto result screen
		        	        }
		        		}

		                return true;
		            }
		            return false;
		        }
		    });
		 
        updateLabels();
        
	}
	
	public void updateLabels(){
		int progress = gm.getCurrentWordIndex()+1;
		
		accuracyLbl.setText(Integer.toString(gm.computeAccuracy()).concat("% Accuracy") );
		progressBar.setProgress(progress);
		progressLabel.setText(progress+"/"+gm.getWordCount() + " words");
		firstWordLabel.setText(gm.getWordOne());
		
	}
	
	

}
