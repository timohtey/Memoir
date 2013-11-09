package com.example.memoir;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class GameListScreen extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamelist_screen);
        
        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3498DB")));
        actionBar.setTitle(Html.fromHtml("<large>CHOOSE A GAME</large>"));
        
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/Lovelo/LoveloBlack.otf");
        TextView myTextView = (TextView)findViewById(R.id.linkTxt1);
        myTextView.setTypeface(myTypeface);
        
        Button helpBtn =(Button) findViewById(R.id.helpBtn);
        Typeface type=Typeface.createFromAsset(getAssets(), "fonts/Lovelo/LoveloBlack.otf");
        helpBtn.setTypeface(type);
        
        Button startLinkingBtn =(Button) findViewById(R.id.startLinkingBtn);
        Typeface type1=Typeface.createFromAsset(getAssets(), "fonts/Lovelo/LoveloBlack.otf");
        startLinkingBtn.setTypeface(type1);
        
        startLinkingBtn.setOnClickListener(new OnClickListener(){    
            @Override
			public void onClick(View v) {
            	Intent myIntent = new Intent(GameListScreen.this, LinkPhaseActivity.class);
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
    
    public void startLinkMethodGame(){
    	Intent intent = new Intent(this,LinkPhaseActivity.class);
    	startActivity(intent);
    }

}
