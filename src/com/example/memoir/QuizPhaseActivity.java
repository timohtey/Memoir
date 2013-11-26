package com.example.memoir;

import DAO.MemoirDAO;
import DAO.StatisticsValues;
import Model.GameModel;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.Log;
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

	MediaPlayer correctSound;
	MediaPlayer wrongSound;
	MediaPlayer clockSound;
	
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
	long timeRemainingStatic=0;
	MemoirDAO DAO = new MemoirDAO(QuizPhaseActivity.this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz2_phase);
        
		ActionBar actionBar = getActionBar();
	    actionBar.hide();
		
	    correctSound = MediaPlayer.create(QuizPhaseActivity.this, R.raw.correct);
	    wrongSound = MediaPlayer.create(QuizPhaseActivity.this, R.raw.wrong);
	    clockSound = MediaPlayer.create(QuizPhaseActivity.this, R.raw.clock);
	    clockSound.setLooping(true);
	    clockSound.start();
	    
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
		    	 finishByTime();
		     }
		     
		     
		  }.start();
		  
		  //OVERRIDE KEYBOARD DONE
		  secondWordLabel.setOnEditorActionListener(new TextView.OnEditorActionListener() {
		        @Override
		        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		            if (actionId == EditorInfo.IME_ACTION_DONE) {

		            	String answer = secondWordLabel.getText().toString();
		        		if(gm.answerQuiz(answer)){
		        			
		        			correctSound.start();
		        			updateLabels();
		        			secondWordLabel.setText("");
		        	        if(gm.getCurrentWordIndex()==gm.getWordCount()-1){
		        	        	Intent intent = new Intent(QuizPhaseActivity.this, AboutScreen.class);
			       		    	 gm.endQuizPhase(true,timeRemaining);
			       		    	 {	//RECORD GAME
			       		    		 int id = DAO.getLastStatisticIndex()+1;
			       		    		 StatisticsValues result = new StatisticsValues(id,"link",gm.computeAccuracy(),gm.computeWordPerMin(),gm.getLinkLevel());;
			       		    		 DAO.updateStatistics(result);
			       		    	 }
			       		    	 intent.putExtra("gameModel",gm);
			       		    	 startActivity(intent);
			       		    	 finish();
		        	        	//TODO: goto result screen
		        	        }
		        		}
		        		else{
		        			wrongSound.start();
		        		}

		                return true;
		            }
		            return false;
		        }
		    });
		 
        updateLabels();
        
	}
	
	public void finishByTime(){
		Intent intent = new Intent(QuizPhaseActivity.this, AboutScreen.class);
	   	 gm.endQuizPhase(false,(long)gm.getTimeLimit());
	   	 {	//RECORD GAME
	   		 int id = DAO.getLastStatisticIndex()+1;
	   		 StatisticsValues result = new StatisticsValues(id,"link",gm.computeAccuracy(),gm.computeWordPerMin(),gm.getLinkLevel());;
	   		 DAO.updateStatistics(result);
	   	 }
	   	 intent.putExtra("gameModel",gm);
	   	 startActivity(intent);
	   	 finish();
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) { //Back key pressed
	       //Things to Do
	    	Intent i = new Intent(QuizPhaseActivity.this, PauseScreen.class);
	    	i.putExtra("timeRemaining", timeRemaining);
	    	i.putExtra("gameModel", gm);
	    	timer.cancel();
	    	startActivityForResult(i,0);
	        return true;
	    }else if((keyCode == KeyEvent.KEYCODE_MENU)){
	    	Intent i = new Intent(QuizPhaseActivity.this, PauseScreen.class);
	    	i.putExtra("timeRemaining", timeRemaining);
	    	i.putExtra("gameModel", gm);
	    	timer.cancel();
	    	startActivityForResult(i,0);
	    	return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		Log.d("pauseTest", "rCode :"+ requestCode+ "time rem: " + data.getLongExtra("timeRemaining", 0));
		
		if(requestCode ==0){
			if(resultCode == RESULT_OK){
				
				timeRemainingStatic = data.getLongExtra("timeRemaining", 300000);
				timeBar.setMax((int)timeRemaining);
				Log.d("pauseTest","timeRemaining global: "+ timeRemaining);
				timer = new CountDownTimer(timeRemainingStatic, 1000) {
					int i = (int)((timeRemainingStatic/1000)%60);
				     public void onTick(long millisUntilFinished) {
				    	 timeBar.setProgress((int)(timeRemainingStatic-millisUntilFinished));
				    	 Log.d("pauseTest","i: "+i+" progress:"+(timeRemainingStatic-millisUntilFinished) +"millis:"+millisUntilFinished);
				    	 timeRemaining =  millisUntilFinished;
				    	 i--;
				    	 if(i<10){
					    	 timeLabel.setText((millisUntilFinished/1000)/60 + ":0"+ i);
				    	 }
				    	 else timeLabel.setText((millisUntilFinished/1000)/60 + ":"+ i);
				    	 if(i == 0){
				    		 i = 60;
				    		 Log.d("pauseTest","RESET seconds to 60");
				    	 }
				    	 if (i ==1 && (millisUntilFinished/1000)/60 == 0 ){
				    		 i = 0;
				    	 }
				     }

				     public void onFinish() {
				    	 finishByTime();
				     }
				  }.start();
			}else if( resultCode == 2){
				//restart
				//TODO: restart
				finishByTime();
				Intent intent = new Intent(QuizPhaseActivity.this, LinkPhaseActivity.class);
		    	startActivity(intent);
		    	 finish();
			}else if(resultCode == 3){
				//settings
				//TODO: settings
			}else if( resultCode == 4){
				//EXITT
				finishByTime();
				finish();
			}
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	public void updateLabels(){
		int progress = gm.getCurrentWordIndex()+1;
		
		accuracyLbl.setText(Integer.toString(gm.computeAccuracy()).concat("% Accuracy") );
		progressBar.setProgress(progress);
		progressLabel.setText(progress+"/"+gm.getWordCount() + " words");
		firstWordLabel.setText(gm.getWordOne());
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		clockSound.release();
	}
	
	

}
