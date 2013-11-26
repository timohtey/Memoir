package com.example.memoir;

import java.util.ArrayList;
import java.util.Random;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ShapesScreen extends Activity{
	ArrayList<String> shapes = new ArrayList<String>();
	ImageView imageView;
	TextView textView;
	TextView score;
	TextView time;
	int numFalse = 0;
	int scoreValue = 0;
	
	Button trueBtn;
	Button falseBtn;
	int randomShapeIndex = 0;
	int randomShapeTxt = 0;
    Random randomShapesImg = new Random();
	Random randomShapesTxt = new Random();
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shapes_screen);
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		imageView=(ImageView) findViewById(R.id.images);
		textView = (TextView) findViewById(R.id.shapeTxt);
		trueBtn = (Button) findViewById(R.id.trueBtn);
		falseBtn= (Button) findViewById(R.id.falseBtn);
		score= (TextView) findViewById(R.id.scoreTxt);
		time = (TextView) findViewById(R.id.timeRemaining);
		
		addShapes();
		addListeners();

		randomShapeIndex = randomShapesImg.nextInt(shapes.size()-1);
		randomShapeTxt = randomShapesTxt.nextInt(shapes.size()-1);
		CountDownTimer timer = new CountDownTimer(30000, 1000) {
			int i = 30;
		    public void onTick(long millisUntilFinished) {
		    	i--;
		    	 if(i<10){
			    	 time.setText((millisUntilFinished/1000)/60 + ":0"+ i);
		    	 }
		    	 else time.setText((millisUntilFinished/1000)/60 + ":"+ i);
		    	 if(i == 0){
		    		 i = 60;
		    	 }
		    	 if (i ==1 && (millisUntilFinished/1000)/60 == 0 ){
		    		 i = 0;
		    	 }
					if(randomShapeIndex == 0){
						imageView.setImageResource(R.drawable.flower);
					} else if (randomShapeIndex == 1) {
						imageView.setImageResource(R.drawable.dog);
					}else if (randomShapeIndex == 2) {
						imageView.setImageResource(R.drawable.spades);
					}else if (randomShapeIndex == 3) {
						imageView.setImageResource(R.drawable.heart);
					}else if (randomShapeIndex == 4) {
						imageView.setImageResource(R.drawable.rose);
					}
					
					textView.setText(shapes.get(randomShapeTxt));
		     }

		     public void onFinish() {
//		    	 Intent intent = new Intent(LoadingScreen.this, LinkPhaseActivity.class);
//		    	 startActivity(intent);
		     }
		  }.start();		
	}
	
	private void addListeners() {
		trueBtn.setOnClickListener(new OnClickListener(){    
            @Override
			public void onClick(View v) {
            	if(randomShapeIndex == randomShapeTxt){
            		scoreValue += 10;
            	} else {
            		scoreValue -= 10;
            	}
				randomShapeIndex = randomShapesImg.nextInt(shapes.size()-1);
				randomShapeTxt = randomShapesTxt.nextInt(shapes.size()-1);

        		score.setText(""+ scoreValue);
			}
        });
		
		falseBtn.setOnClickListener(new OnClickListener(){    
            @Override
			public void onClick(View v) {
            	if(randomShapeIndex != randomShapeTxt){
            		scoreValue += 10;
            		numFalse++;
            	} else {
            		scoreValue -= 10;
            	}
            	
            	if(numFalse >3){
            		if(randomShapesImg.nextInt(2) == 1){
	            		randomShapeIndex = randomShapesImg.nextInt(shapes.size()-1);
	            		randomShapeTxt = randomShapeIndex;
            		}else{
            			randomShapeIndex = randomShapesImg.nextInt(shapes.size()-1);
            			randomShapeTxt = randomShapesTxt.nextInt(shapes.size()-1);
            		}
            	}else{
					randomShapeIndex = randomShapesImg.nextInt(shapes.size()-1);
					randomShapeTxt = randomShapesTxt.nextInt(shapes.size()-1);
            	}
				
        		score.setText(""+scoreValue);
			}
        });
	
	}

	private void addShapes() {
		shapes.add("Flower");
		shapes.add("Dog");
		shapes.add("Spades");
		shapes.add("Heart");
		shapes.add("Rose");
	}
}
