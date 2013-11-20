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
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class QuizPhaseActivity extends Activity {

	GameModel gm;
	TextView quizTimeTextView;
	TextView quizProgressTextView;
	TextView quizFirstWordTextView;
	EditText quizSecondWordTextField;
	ImageButton goButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz_phase);
        
		ActionBar actionBar = getActionBar();
	    actionBar.hide();
		
        Intent i = getIntent();
        gm =  (GameModel)i.getSerializableExtra("gameModel");
        
        quizTimeTextView = (TextView) findViewById(R.id.quizTimeLabel);
        quizProgressTextView = (TextView) findViewById(R.id.quizProgressTextView);
        quizFirstWordTextView = (TextView) findViewById(R.id.quizFirstWordTextView);
        quizSecondWordTextField = (EditText)findViewById(R.id.quizSecondWordTextField);
        goButton = (ImageButton)findViewById(R.id.goButton);
        
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        gm.startQuizPhase();
        
        new CountDownTimer(gm.getTimeLimit(), 1000) {
			int i = 60;
		     public void onTick(long millisUntilFinished) {
		    	 i--;
		    	 if(i<10){
		    		 quizTimeTextView.setText((millisUntilFinished/1000)/60 + ":0"+ i);
		    	 }
		    	 else quizTimeTextView.setText((millisUntilFinished/1000)/60 + ":"+ i);
		    	 if(i == 0){
		    		 i = 60;
		    	 }
		    	 if (i ==1 && (millisUntilFinished/1000)/60 == 0 ){
		    		 i = 0;
		    	 }
		     }

		     public void onFinish() {
		    	 //TODO: goto result screen
		    	 gm.endQuizPhase(false);
		    	 Intent intent = new Intent(QuizPhaseActivity.this, AboutScreen.class);
		    	 intent.putExtra("gameModel",gm);
		    	 startActivity(intent);
		     }
		     
		     
		  }.start();
		  
        updateLabels();
        
	}
	
	
	public void onGo(View v){
		String answer = quizSecondWordTextField.getText().toString();
		
		if(gm.answerQuiz(answer)){
			updateLabels();
			quizSecondWordTextField.setText("");
	        if(gm.getCurrentWordIndex()==gm.getWordCount()-1){
	        	gm.endQuizPhase(true);
	        	//TODO: goto result screen
	        }
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.quiz_phase, menu);
		return true;
	}
	
	public void updateLabels(){
		int progress = gm.getCurrentWordIndex()+1;
        quizProgressTextView.setText(progress+"/"+gm.getWordCount());
        quizFirstWordTextView.setText(gm.getWordOne());
	}
	
	

}
