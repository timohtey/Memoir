package com.example.memoir;

import java.util.Timer;
import java.util.TimerTask;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;

public class CountDown extends Activity{
	
	MediaPlayer countdownSound;
	private ImageView imageView;
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.countdown_screen);
        
        countdownSound = MediaPlayer.create(CountDown.this, R.raw.countdown);
        
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        final Intent i = getIntent();
        imageView = (ImageView) findViewById(R.id.imageCount);

    	CountDownTimer timer = new CountDownTimer(4000, 500) {
		     public void onTick(long millisUntilFinished) {
		    	 if(millisUntilFinished < 1000){
		    		 imageView.setImageResource(R.drawable.count_go);
		    	 } else if(millisUntilFinished < 2000){
		            imageView.setImageResource(R.drawable.count_1);
		         }        
		    	 else if(millisUntilFinished < 3000){
	            	imageView.setImageResource(R.drawable.count_2);
	             }     
		    	 Log.d("time", ""+millisUntilFinished);
		     }

		     public void onFinish() {
	    		 imageView.setImageResource(R.drawable.count_go);
		    	 Intent intent = new Intent(CountDown.this, QuizPhaseActivity.class);
		    	 intent.putExtra("gameModel",i.getSerializableExtra("gameModel"));
		    	 startActivity(intent);
		    	 finish();
		     }
		  }.start();
		  countdownSound.start();
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) { //Back key pressed
	        return true;
	    }else if((keyCode == KeyEvent.KEYCODE_MENU)){
	    	return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
}
