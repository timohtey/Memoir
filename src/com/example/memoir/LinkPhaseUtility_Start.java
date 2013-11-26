package com.example.memoir;

import java.util.ArrayList;

import Model.GameModel;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class LinkPhaseUtility_Start	extends Activity {
		
		MediaPlayer buttonSound, deleteSound;
		
		ArrayAdapter<String> mAdapter;
	    private ArrayList<String> arrayList = new ArrayList<String>();
		private Button newBtn;
		private ListView lv;
		private TextView title;
		private static int position;
		
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.linkphase_utility_start);
	        
	        buttonSound = MediaPlayer.create(LinkPhaseUtility_Start.this, R.raw.button);
	        deleteSound = MediaPlayer.create(LinkPhaseUtility_Start.this, R.raw.delete);
	        
	        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/Canter/Canter Bold.otf");
	        lv = (ListView) findViewById(R.id.list);
	        //lv = getListView();
	        newBtn = (Button)findViewById(R.id.newWordBtn);
	        title = (TextView)findViewById(R.id.title);
	        title.setTypeface(myTypeface);
	        
	        ActionBar actionBar = this.getActionBar();
	        actionBar.hide();
	        
	        // Set up ListView example
	        for(int i = 0; i <10; i++){
	        	if(i %2== 0){
	        		arrayList.add("hello, hi, here, tata");
	        	}
	        	else {
	        		arrayList.add("blah, blue, green");
	        	}
	        }

	        Bundle extras = getIntent().getExtras();
		    if(extras != null){
		    	if(extras.getString("edited").equals("true")){
		    		String newWord = extras.getString(("customWords"));
		    		arrayList.set(position, newWord);
		    	}
		    	else{
		    		String newWord = extras.getString(("customWords"));
		    		arrayList.add(newWord);
		    	}
		    }
		    
	        mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arrayList);
	        lv.setAdapter(mAdapter);  
	       
	        // Create a ListView-specific touch listener. ListViews are given special treatment because
	        // by default they handle touches for their list items... i.e. they're in charge of drawing
	        // the pressed state (the list selector), handling list item clicks, etc.
	        SwipeDismissListViewTouchListener touchListener =
	                new SwipeDismissListViewTouchListener(
	                        lv,
	                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
	                            @Override
	                            public boolean canDismiss(int position) {
	                                return true;
	                            }

	                            @Override
	                            public void onDismiss(ListView lv, int[] reverseSortedPositions) {
	                                for (int position : reverseSortedPositions) {
	                                	deleteSound.start();
	                                    mAdapter.remove(mAdapter.getItem(position));
	                                }
	                                mAdapter.notifyDataSetChanged();
	                            }
	                        });
	        lv.setOnTouchListener(touchListener);
	        // Setting this scroll listener is required to ensure that during ListView scrolling,
	        // we don't look for swipes.
	        lv.setOnScrollListener(touchListener.makeScrollListener());
	        
	        lv.setOnItemClickListener(new OnItemClickListener()
	        {
	            @Override
	            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3){
	            	
	            	buttonSound.start();
	            	
	            	String positionStr = adapter.getItemAtPosition(position).toString();
	            	ArrayList<String> customWordsArray = new ArrayList<String>();
	            	StringBuilder temp = new StringBuilder(positionStr);
	            	String customWords = "";    
	            	LinkPhaseUtility_Start.this.position = position;
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
	                Intent myIntent = new Intent(LinkPhaseUtility_Start.this, LinkPhaseUtility.class);
	                myIntent.putStringArrayListExtra("customWords", customWordsArray);
		            startActivity(myIntent);
	            }
	         });
	        
	        newBtn.setOnClickListener(new OnClickListener(){    
	            @Override
				public void onClick(View v) {
	            	buttonSound.start();
	            	Intent myIntent = new Intent(LinkPhaseUtility_Start.this, LinkPhaseUtility.class);
	                startActivity(myIntent);
				}
	        }); 
	    }
}
