package com.example.memoir;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class LinkPhaseUtility extends Activity {
	EditText customWordsTxt;
	ImageView imgBtn;
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linkphase_utility);
        
        ActionBar actionBar = getActionBar();
	    actionBar.hide();
	    ArrayList<String> customWords;
	    Bundle extras = getIntent().getExtras();
	    //final Intent myIntent = new Intent(LinkPhaseUtility.this, LinkPhaseUtility_Start.class);
	    final Intent myIntent = new Intent(LinkPhaseUtility.this, LinkPhaseActivity.class);
	    customWordsTxt = (EditText) findViewById(R.id.customWordsTxt);
	    
	    if(extras != null){
	    	customWords = extras.getStringArrayList("customWords");
	    	String txt = "";
	    	for(int i = 0; i < customWords.size();i++){
	 	    	txt+=customWords.get(i);
	 	    	if(i!=customWords.size()-1){
	 	    		txt+=", ";
	 	    	}
	 	    }
	    	
	    	customWordsTxt.setText(txt);
	    	myIntent.putExtra("edited", "true");
	    }
	   
	    imgBtn = (ImageView)findViewById(R.id.goBtn);
	    
	    imgBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
                ArrayList<String> customWordsArray = new ArrayList<String>();
            	StringBuilder temp = new StringBuilder(customWordsTxt.getText().toString());
            	String customWords = "";    
            	for(int i = 0; i < temp.length(); i++){
            		if(temp.charAt(i) == ' ' || temp.charAt(i) == ','){
            			if(customWords.equals("") == false){
	            		  customWordsArray.add(customWords);
            			}
            			customWords = "";
            		}else {
            			customWords += temp.charAt(i);
            		}
   				}
            	customWordsArray.add(customWords);

               // myIntent.putExtra("customWords", customWordsTxt.getText().toString());
                myIntent.putExtra("customWords", customWordsArray);
                startActivity(myIntent);				
			}
	    	
	    });
	    
     }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
