package com.example.memoir;

import java.util.ArrayList;
import java.util.Random;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;

public class LoadingScreen extends Activity{
	private ArrayList<String> colors = new ArrayList<String>();
	private RelativeLayout rl;
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.loading_screen);
	        
	        ActionBar actionBar = getActionBar();
	        actionBar.hide();
	        
	        rl = (RelativeLayout) findViewById(R.id.relativeLayout);
	        addColors();
	        Random random = new Random();
	        int index = random.nextInt(colors.size()-1);
	        rl.setBackgroundColor(Color.parseColor(colors.get(index)));
	 }
	 
	 private void addColors(){
		 colors.add("#1abc9c");
		 colors.add("#16a085");
		 colors.add("#27ae60");
		 colors.add("#2980b9");
		 colors.add("#9b59b6");
		 colors.add("#f1c40f");
		 colors.add("#f39c12");
		 colors.add("#e67e22");
		 colors.add("#d35400");
		 colors.add("#e74c3c");		 
		 colors.add("#8e44ad");	
		 colors.add("#34495e");	
		 colors.add("#2c3e50");	
	 }
}
