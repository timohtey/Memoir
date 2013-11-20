package com.example.memoir;

import Model.GameModel;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.View;
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
	ImageButton skipButton;
	ImageButton goButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz_phase);
        
        Intent i = getIntent();
        gm =  (GameModel)i.getSerializableExtra("gameModel");
        quizTimeTextView = (TextView) findViewById(R.id.quizTimeLabel);
        quizProgressTextView = (TextView) findViewById(R.id.quizProgressTextView);
        quizFirstWordTextView = (TextView) findViewById(R.id.quizFirstWordTextView);
        quizSecondWordTextField = (EditText)findViewById(R.id.quizSecondWordTextField);
        skipButton = (ImageButton)findViewById(R.id.skipButton); 
        goButton = (ImageButton)findViewById(R.id.goButton);
        
        gm.startQuizPhase();
        
        int progress = gm.getCurrentWordIndex()+1;
        quizProgressTextView.setText(progress+"/"+gm.getWordCount());
        quizFirstWordTextView.setText(gm.getWordOne());
	}
	
	public void onGo(View v){
		String answer = quizSecondWordTextField.getText().toString();
		if(gm.answerQuiz(answer)){
			int progress = gm.getCurrentWordIndex()+1;
			quizProgressTextView.setText(progress+"/"+gm.getWordCount());
	        quizFirstWordTextView.setText(gm.getWordOne());
	        
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
	
	

}
