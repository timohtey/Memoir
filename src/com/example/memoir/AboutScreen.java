package com.example.memoir;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.widget.TextView;

public class AboutScreen extends Activity{
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_screen);
        
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/Mojave/Mojave-Regular.ttf");
        TextView myTextView = (TextView)findViewById(R.id.anamnesiaTxt);
        TextView myTextView1 = (TextView)findViewById(R.id.kitsUsedTxt);
        myTextView.setTypeface(myTypeface);
        myTextView1.setTypeface(myTypeface);
     }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
