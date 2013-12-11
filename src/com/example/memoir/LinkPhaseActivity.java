package com.example.memoir;

import java.util.ArrayList;

import DAO.MemoirDAO;
import Model.GameModel;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LinkPhaseActivity extends Activity {

	MediaPlayer clockSound;
	MediaPlayer buttonSound;
	
	GameModel gm;
	TextView progressLabel;
	TextView firstWordLabel;
	TextView secondWordLabel;
	TextView timeLabel;
	Button nextWordLabel;
	Button prevWordLabel;
	ProgressBar progressBar;
	ProgressBar timeBar;
	CountDownTimer timer;
	long timeRemaining=0;
	long timeRemainingStatic=0;
	MemoirDAO DAO = new MemoirDAO(LinkPhaseActivity.this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_link_phase);
				
		buttonSound = MediaPlayer.create(LinkPhaseActivity.this, R.raw.button);
		clockSound = MediaPlayer.create(LinkPhaseActivity.this, R.raw.clock);
		clockSound.setLooping(true);
		clockSound.start();
		
		DAO.open();
		ActionBar actionBar = getActionBar();
	    actionBar.hide();
	    Bundle extras = getIntent().getExtras();
	    if(extras != null){
	    	ArrayList<String> arrayB = extras.getStringArrayList("customWords");
	    	gm = new GameModel(GameModel.FIXED_TIME_MODE, arrayB);
	    }
	    else gm = new GameModel(GameModel.FIXED_TIME_MODE, DAO);
		
		
		progressLabel = (TextView)findViewById(R.id.progressRouteLbl2);
		firstWordLabel = (TextView)findViewById(R.id.firstWordLabel2);
		secondWordLabel = (TextView)findViewById(R.id.secondWordField2);
		timeLabel = (TextView)findViewById(R.id.timeRouteLabel2);
		nextWordLabel = (Button)findViewById(R.id.nextRouteBtn);
		prevWordLabel = (Button)findViewById(R.id.prevRouteBtn);
		progressBar = (ProgressBar) findViewById(R.id.progressRouteBar2);
		timeBar = (ProgressBar) findViewById(R.id.timeRouteBar2);

		timeBar.setMax(gm.getTimeLimit());
		prevWordLabel.setEnabled(false);
		progressBar.setMax(gm.getWordCount());
		updateLabels();
		
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
		    	 Intent intent = new Intent(LinkPhaseActivity.this, CountDown.class);
		    	 intent.putExtra("gameModel",gm);
		    	 startActivity(intent);
		    	 timer.cancel();
		    	 finish();
		     }
		  }.start();
		  
		  DAO.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.link_phase, menu);
		return true;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    
		
		
		if ((keyCode == KeyEvent.KEYCODE_BACK)) { //Back key pressed
	       //Things to Do
	    	Intent i = new Intent(LinkPhaseActivity.this, PauseScreen.class);
	    	i.putExtra("timeRemaining", timeRemaining);
	    	i.putExtra("gameModel", gm);
	    	timer.cancel();
	    	startActivityForResult(i,0);
	        return true;
	    }else if((keyCode == KeyEvent.KEYCODE_MENU)){
	    	Intent i = new Intent(LinkPhaseActivity.this, PauseScreen.class);
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
				    	 Intent intent = new Intent(LinkPhaseActivity.this, CountDown.class);
				    	 intent.putExtra("gameModel",gm);
				    	 startActivity(intent);
				    	 timer.cancel();
				    	 finish();
				    	 
				     }
				  }.start();
			}else if( resultCode == 2){
				//restart
				//TODO: restart
				Intent intent = new Intent(LinkPhaseActivity.this, LinkPhaseActivity.class);
		    	startActivity(intent);
		    	timer.cancel();
		    	 finish();
			}else if(resultCode == 3){
				//settings
				//TODO: settings
			}else if( resultCode == 4){
				//EXIT
				timer.cancel();
				finish();
			}
		}
	}
	
	
	public void nextWord(View v){
		
		prevWordLabel.setEnabled(true);
		if(gm.getCurrentWordIndex()+2==gm.getWordCount()){
			Intent intent = new Intent(this,CountDown.class);
			intent.putExtra("gameModel",gm);
			startActivity(intent);
			timer.cancel();
			finish();
		}else{
			if(gm.getCurrentWordIndex()+3==gm.getWordCount()){
				nextWordLabel.setText("Start Quiz!");
			}
			gm.nextWord();
			buttonSound.start();
			updateLabels();
		}
	}
	
	public void prevWord(View v){
		if(gm.getCurrentWordIndex()==1)
			prevWordLabel.setEnabled(false);
		if(gm.getCurrentWordIndex()+2==gm.getWordCount()){
			nextWordLabel.setText("Next Word");
		}
		gm.prevWord();
		buttonSound.start();
		updateLabels();
	}
	
	public void updateLabels(){
		//Progress
		
		
		int progress = gm.getCurrentWordIndex()+1;
		progressBar.setProgress(progress);
		progressLabel.setText(progress+"/"+gm.getWordCount());
		
		//Words
		firstWordLabel.setText(gm.getWordOne());
		secondWordLabel.setText(gm.getWordTwo());
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//clockSound.start();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		clockSound.release();
	}
	
	
}
