package com.example.memoir;

import java.util.ArrayList;

import DAO.LinkCustomSet;
import DAO.MemoirDAO;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class LinkPhaseUtility extends Activity {
	EditText customWordsTxt;
	EditText titleCustom;
	EditText descCustom;
	Button imgBtn;
	private int EDIT_MODE = 1;
	private int ADD_MODE = 2;
	private int mode = 0;
	int position = 0;
	//MemoirDAO dao = new MemoirDAO(this);
	ArrayList<LinkCustomSet> linkCustom = new ArrayList<LinkCustomSet>();
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linkphase_utility);
        
        ActionBar actionBar = getActionBar();
	    actionBar.hide();
	    
	    Intent i = getIntent();

		   // dao.open();
		    //linkCustom = dao.getLinkCustomDisplay();
		    customWordsTxt = (EditText) findViewById(R.id.customWordsTxt);
		    titleCustom= (EditText) findViewById(R.id.titleCustomLink);
			descCustom= (EditText) findViewById(R.id.descCustomLink);
		    imgBtn = (Button) findViewById(R.id.goBtn);
		    
	    if(i.getExtras().containsKey("position")){
	    	position = i.getExtras().getInt("position", 0);
	    	linkCustom = (ArrayList<LinkCustomSet>) i.getSerializableExtra("custom");
	    	mode = EDIT_MODE;
	    	imgBtn.setText("Save Set");
	    } else {
	    	linkCustom = (ArrayList<LinkCustomSet>) i.getSerializableExtra("custom");
	    	mode = ADD_MODE;
	    	imgBtn.setText("Add Set");
	    }
	    
	    
	    if(mode == EDIT_MODE){
	    	//add word set
	    	titleCustom.setText(linkCustom.get(position).getSetName());
	    	descCustom.setText(linkCustom.get(position).getDescription());
	    	customWordsTxt.setText(linkCustom.get(position).getWords());
	    }
	   
	    
	    imgBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
            	
        	    Intent myIntent = new Intent(LinkPhaseUtility.this, LinkPhaseUtility_Start.class);
            	
            	if(mode == ADD_MODE){
            		linkCustom.add(new LinkCustomSet());
            		linkCustom.get(linkCustom.size()-1).setSetName(titleCustom.getText().toString());
            		linkCustom.get(linkCustom.size()-1).setDescription(descCustom.getText().toString());
            		linkCustom.get(linkCustom.size()-1).setWords(customWordsTxt.getText().toString());
            		//dao.insertWordList(titleCustom.getText().toString(), descCustom.getText().toString(), customWordsArray);
            	}else {
            		linkCustom.get(position).setSetName(titleCustom.getText().toString());
            		linkCustom.get(position).setDescription(descCustom.getText().toString());
            		linkCustom.get(position).setWords(customWordsTxt.getText().toString());
            		//dao.updateLinkCustom(titleCustom.getText().toString(), 0, 0);
            	}
            	//dao.close();
            	myIntent.putExtra("custom", linkCustom);
                startActivity(myIntent);				
			}
	    	
	    });
	    
     }
}
