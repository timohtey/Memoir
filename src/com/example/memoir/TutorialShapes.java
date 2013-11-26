package com.example.memoir;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TutorialShapes extends Activity {
	Button nextBtn;
	Button prevBtn;
	ImageView imageView;
	TextView textView;
	ArrayList<String> shapes = new ArrayList<String>();
	int count = 0;
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_shapes);
        
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        
        nextBtn = (Button) findViewById(R.id.nextBtnS);
        prevBtn = (Button) findViewById(R.id.prevBtnS);
        imageView = (ImageView)findViewById(R.id.shapesTutorial);
        textView = (TextView)findViewById(R.id.shapesTxtTutorial);
        addShapes();
        
        nextBtn.setOnClickListener(new OnClickListener(){    
            @Override
			public void onClick(View v) {
            	if(count == 4){
            		count = 0;
            	}
            	else count++;
                
            	if(count == 0){
        			imageView.setImageResource(R.drawable.flower);
        			textView.setText(shapes.get(count));
        		} else if (count == 1) {
        			imageView.setImageResource(R.drawable.dog);
        			textView.setText(shapes.get(count));
        		}else if (count == 2) {
        			imageView.setImageResource(R.drawable.spades);
        			textView.setText(shapes.get(count));
        		}else if (count == 3) {
        			imageView.setImageResource(R.drawable.heart);
        			textView.setText(shapes.get(count));
        		}else if (count == 4) {
        			imageView.setImageResource(R.drawable.rose);
        			textView.setText(shapes.get(count));
        		}
			}
        });
        
        prevBtn.setOnClickListener(new OnClickListener(){    
            @Override
			public void onClick(View v) {
            	if(count == 0){
            		count = 4;
            	} else count--;
            	
            	 if(count == 0){
         			imageView.setImageResource(R.drawable.flower);
         			textView.setText(shapes.get(count));
         		} else if (count == 1) {
         			imageView.setImageResource(R.drawable.dog);
         			textView.setText(shapes.get(count));
         		}else if (count == 2) {
         			imageView.setImageResource(R.drawable.spades);
         			textView.setText(shapes.get(count));
         		}else if (count == 3) {
         			imageView.setImageResource(R.drawable.heart);
         			textView.setText(shapes.get(count));
         		}else if (count == 4) {
         			imageView.setImageResource(R.drawable.rose);
         			textView.setText(shapes.get(count));
         		}
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
