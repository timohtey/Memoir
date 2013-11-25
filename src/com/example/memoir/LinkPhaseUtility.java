package com.example.memoir;

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
	    
	    customWordsTxt = (EditText) findViewById(R.id.customWordsTxt);
	    imgBtn = (ImageView)findViewById(R.id.goBtn);
	    
	    imgBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent myIntent = new Intent(LinkPhaseUtility.this, LinkPhaseUtility_Start.class);
                myIntent.putExtra("customWords", customWordsTxt.getText().toString());
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
