package com.example.memoir;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class LinkPhaseUtility_Start extends Activity{
	private LinearLayout innerLayout;
	private ArrayList<TextView> textViews = new ArrayList<TextView>();
	private Button newBtn;
	private Button deleteBtn;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linkphase_utility_start);
        
        ActionBar actionBar = this.getActionBar();
        actionBar.hide();
        
        innerLayout =  (LinearLayout) findViewById(R.id.linearLayout);
        
        newBtn = (Button)findViewById(R.id.newWordBtn);
        deleteBtn = (Button)findViewById(R.id.deleteWordBtn);
        
        for(int i = 0; i <10; i++){
        	textViews.add(new TextView(this));
        	if(i == 0){
        		textViews.get(i).setText("hello, hi, here, tata");
        	}
        	else if (i == 1){
        		textViews.get(i).setText("haha, popo, chacha, hihi");
        	}
        	else textViews.get(i).setText("blah, blue, green");
        	textViews.get(i).setTextSize(40);
        	textViews.get(i).setClickable(true);
        	textViews.get(i).setOnClickListener(new View.OnClickListener(){
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
				}
        	});
        		  
        	innerLayout.addView(textViews.get(i));
        }
        
        newBtn.setOnClickListener(new OnClickListener(){    
            @Override
			public void onClick(View v) {
            	Intent myIntent = new Intent(LinkPhaseUtility_Start.this, LinkPhaseUtility.class);
                startActivity(myIntent);
			}
        }); 
        
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu; this adds items to the action bar if it is present.
	    //getMenuInflater().inflate(R.menu.main, menu);
	    return true;
	}
}
