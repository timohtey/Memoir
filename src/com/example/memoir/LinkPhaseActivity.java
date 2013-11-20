package com.example.memoir;

import DAO.MemoirDAO;
import Model.GameModel;
import Model.WordPool;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class LinkPhaseActivity extends Activity {

	
	GameModel gm;
	TextView progressLabel;
	TextView firstWordLabel;
	TextView secondWordLabel;
	TextView timeLabel;
	ImageButton nextWordLabel;
	ImageButton prevWordLabel;
	MemoirDAO DAO = new MemoirDAO(LinkPhaseActivity.this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_link_phase);
		
		
		DAO.open();
		ActionBar actionBar = getActionBar();
	    actionBar.hide();
		gm = new GameModel(GameModel.FIXED_TIME_MODE, DAO);
		
		
		progressLabel = (TextView)findViewById(R.id.progressLabel);
		firstWordLabel = (TextView)findViewById(R.id.firstWordLabel);
		secondWordLabel = (TextView)findViewById(R.id.secondWordLabel);
		timeLabel = (TextView)findViewById(R.id.timeLabel);
		nextWordLabel = (ImageButton)findViewById(R.id.nextWordButton);
		prevWordLabel = (ImageButton)findViewById(R.id.prevBtn);

		prevWordLabel.setEnabled(false);
		updateLabels();
		
		new CountDownTimer(gm.getTimeLimit(), 1000) {
			int i = 60;
		     public void onTick(long millisUntilFinished) {
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
		    	 Intent intent = new Intent(LinkPhaseActivity.this, QuizPhaseActivity.class);
		    	 intent.putExtra("gameModel",gm);
		    	 startActivity(intent);
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
	
	
	public void nextWord(View v){
		prevWordLabel.setEnabled(true);
		if(gm.getCurrentWordIndex()+2==gm.getWordCount()){
			Intent intent = new Intent(this,QuizPhaseActivity.class);
			intent.putExtra("gameModel",gm);
			startActivity(intent);
		}else{
			if(gm.getCurrentWordIndex()+3==gm.getWordCount()){
				//nextWordLabel.setText("Start Quiz!");
			}
			gm.nextWord();
			updateLabels();
		}
	}
	
	public void prevWord(View v){
		if(gm.getCurrentWordIndex()==0)
			prevWordLabel.setEnabled(false);
		if(gm.getCurrentWordIndex()+3==gm.getWordCount()){
			//nextWordLabel.setText("Next Word");
		}
		gm.prevWord();
		updateLabels();
	}
	
	public void updateLabels(){
		//Progress
		int progress = gm.getCurrentWordIndex()+1;
		progressLabel.setText(progress+"/"+gm.getWordCount());
		
		//Words
		firstWordLabel.setText(gm.getWordOne());
		secondWordLabel.setText(gm.getWordTwo());
	}
	
}
