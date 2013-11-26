package com.example.memoir;

import java.util.ArrayList;
import java.util.Random;

import DAO.MemoirDAO;
import android.R.color;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LoadingScreen extends Activity{
	private ArrayList<String> colors = new ArrayList<String>();
	private RelativeLayout rl;
	private TextView txt;
	private ArrayList<String> tipList = new ArrayList<String>();
	MemoirDAO DAO = new MemoirDAO(this);
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.loading_screen);
	        DAO.open();
	        ActionBar actionBar = getActionBar();
	        actionBar.hide();
	        
	        rl = (RelativeLayout) findViewById(R.id.relativeLayout);
	        txt = (TextView)findViewById(R.id.tipTxt);
	        addColors();
	        Random random = new Random();
	        Log.d("test", "hi");
	        int index = random.nextInt(colors.size()-1);
	        rl.setBackgroundColor(Color.parseColor(colors.get(index)));
	        
	        tipList = DAO.getTipList();
	        index = random.nextInt(tipList.size()-1);
	        txt.setText(tipList.get(index));
	        CountDownTimer timer = new CountDownTimer(3000, 500) {
			     public void onTick(long millisUntilFinished) {
			     }

			     public void onFinish() {
			    	 Intent intent = new Intent(LoadingScreen.this, LinkPhaseActivity.class);
			    	 startActivity(intent);
			     }
			  }.start();

	        DAO.close();
	        
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
