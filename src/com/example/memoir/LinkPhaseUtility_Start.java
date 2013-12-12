package com.example.memoir;

import java.util.ArrayList;

import DAO.LinkCustomSet;
import DAO.MemoirDAO;
import Model.GameModel;
import Model.Route;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
		Menu menuItem;
		ArrayAdapter<String> mAdapter;
		private Button newBtn;
		private ListView lv;
		private MenuItem imageButton;
		private MenuItem deleteButton;
		private int EDIT_MODE = 1;
		private int ADD_MODE = 2;
		private int mode = 0;
		private int position = -1;
		private int clicked = 0;
		private View prevView = null;
		MemoirDAO dao = new MemoirDAO(this);
		ArrayList<LinkCustomSet> linkCustom = new ArrayList<LinkCustomSet>();
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.linkphase_utility_start);
	        
	        ActionBar actionBar = getActionBar();
	        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1abc9c")));
	        
	        buttonSound = MediaPlayer.create(LinkPhaseUtility_Start.this, R.raw.button);
	        deleteSound = MediaPlayer.create(LinkPhaseUtility_Start.this, R.raw.delete);
	        
	        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/Canter/Canter Bold.otf");
	        lv = (ListView) findViewById(R.id.list);
	        //lv = getListView();
	        newBtn = (Button)findViewById(R.id.newWordBtn);
	        dao.open();
	        //linkCustom = dao.getLinkCustomDisplay();
		    
	        Bundle extras = getIntent().getExtras();
	        if(extras == null){
		        for(int i = 0; i<5;i++){
		        	linkCustom.add(new LinkCustomSet());
		        	if(i == 0){
		        		linkCustom.get(i).setSetName("KASPIL2");
		        		linkCustom.get(i).setDescription("For exam #1");
		        		linkCustom.get(i).setWords("bolo, barong, nobela, gun");
		        	} else if (i %2== 0) {
		        		linkCustom.get(i).setSetName("EXERCISE");
		        		linkCustom.get(i).setDescription("Exercise routine");
		        		linkCustom.get(i).setWords("situps, pushups, crunches, eat");
		        	} else if (i == 3) {
		        		linkCustom.get(i).setSetName("GROCERY");
		        		linkCustom.get(i).setDescription("To buy in the grocery store");
		        		linkCustom.get(i).setWords("butter, egg, bread, potato, apple, orange");
		        	} else{
		        		linkCustom.get(i).setSetName("SHOPPING LIST");
		        		linkCustom.get(i).setDescription("Pamasko sa mga bata");
		        		linkCustom.get(i).setWords("clothes, toy gun, iphone, laptop, ps4, xboxone");
		        	}
		        }
	        } else{
	        	Intent i = getIntent();
	        	linkCustom =  (ArrayList<LinkCustomSet>) i.getSerializableExtra("custom");
	        }
	        
	        updateData();
	        
	       
	        
	        newBtn.setOnClickListener(new OnClickListener(){    
	            @Override
				public void onClick(View v) {
	            	buttonSound.start();
	            	Intent myIntent = new Intent(LinkPhaseUtility_Start.this, LinkPhaseActivity.class);
	            	
	            	ArrayList<String> customWordsArray = new ArrayList<String>();;
	            	String customWords = "";    
	            	for(int i = 0; i < linkCustom.get(position).getWords().length(); i++){
	            		if(linkCustom.get(position).getWords().charAt(i) == ' ' || 
	            				linkCustom.get(position).getWords().charAt(i) == ','){
	            			if(customWords.equals("") == false){
		            		  customWordsArray.add(customWords);
	            			}
	            			customWords = "";
	            		}else {
	            			customWords += linkCustom.get(position).getWords().charAt(i);
	            		}
	   				}
	            	customWordsArray.add(customWords);
	            	myIntent.putStringArrayListExtra("customWords", customWordsArray);
	                startActivity(myIntent);
				}
	        }); 
	    }
	    
		
		private void setButtons(){
			imageButton=menuItem.findItem(R.id.addRouteBtn_);
			deleteButton = menuItem.findItem(R.id.deleteRouteBtn_);
		}

		private void setPrevView(View view){
			prevView = view;
		}
		
		private View getPrevView(){
			return prevView;
		}
		private void addClick(){
			this.clicked++;
		}
		
		private int getClick(){
			return clicked;
		}
		private void changePosition(int position){
			this.position = position;
		}
		
		private int getPosition(){
			return this.position;
		}
		
		private void updateData(){
			//linkCustom = dao.getLinkCustomDisplay();
			lv.invalidateViews();
			lv.setAdapter(new LinkCustomBaseAdapter(LinkPhaseUtility_Start.this, linkCustom));
			lv.setBackgroundColor(Color.WHITE);
			lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
				long arg3) {
				setButtons();
			
				if(getPosition() != position){
					mode = EDIT_MODE;
					imageButton.setIcon(R.drawable.edit_route);
					imageButton.setTitle("Edit Set");
					deleteButton.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
					view.setBackgroundColor(Color.LTGRAY);

					if(getPrevView() != null){
						getPrevView().setBackgroundColor(Color.WHITE);
					}
				}
				else if(getPosition() == position){
					addClick();
					if(getClick()%2 == 0){
						mode = EDIT_MODE;
						imageButton.setIcon(R.drawable.edit_route);
						imageButton.setTitle("Edit Set");
						deleteButton.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
						view.setBackgroundColor(Color.LTGRAY);
					} else {
						mode = ADD_MODE;
						imageButton.setIcon(R.drawable.add_route);
						imageButton.setTitle("Add Set");
						deleteButton.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
						view.setBackgroundColor(Color.WHITE);
					}
				} 
				
				changePosition(position);	
				setPrevView(view);
				}
		    });
		}
	    
	    public boolean onCreateOptionsMenu(Menu menu) {
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.route_menu, menu);
	        menuItem = menu;
	        return super.onCreateOptionsMenu(menu);
	    }
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Take appropriate action for each action item click
	        switch (item.getItemId()) {
		        case R.id.addRouteBtn_:
		        	Intent intent = new Intent(LinkPhaseUtility_Start.this, LinkPhaseUtility.class);
		        	
		        	if(mode == EDIT_MODE){
		        		intent.putExtra("custom", linkCustom);
		        		intent.putExtra("position", position);
		        	} else intent.putExtra("custom", linkCustom);
					startActivity(intent);
		            return true;
		        case R.id.deleteRouteBtn_:
		        	mode = ADD_MODE;
		        	imageButton.setIcon(R.drawable.add_route);
					imageButton.setTitle("Add Set");
					deleteButton.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
					linkCustom.remove(position);
		        	position = -1;
		        	//dao.deleteCustomWordList(linkCustom.get(position).getSetName());
		        	updateData();
		            return true;
	        }
			return false;
		}
}
