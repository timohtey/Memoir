package com.example.memoir;

import java.util.ArrayList;

import Model.Route;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;


public class RouteMain extends Activity{
	private ListView routeListView;
	private ArrayList<Route> routes = new ArrayList<Route>();
	private Menu menuItem;
	private int EDIT_MODE = 1;
	private int ADD_MODE = 2;
	private int mode = 0;
	private int position = -1;
	private int clicked = 0;
	private View prevView = null;
	private MenuItem imageButton;
	private MenuItem deleteButton;
	private Button practiceBtn;
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_main);
        
        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1abc9c")));
        routeListView = (ListView) findViewById(R.id.routeListView);
        practiceBtn = (Button) findViewById(R.id.practiceRouteBtn);
        Bundle extras = getIntent().getExtras();
        if(extras == null){
	        routes.add(new Route());
	        routes.get(0).setRouteTitle("School");
	        routes.get(0).setRouteDescription("something school");
	        routes.add(new Route());
	        routes.get(1).setRouteTitle("House");
	        routes.get(1).setRouteDescription("something house");
	        routes.add(new Route());
	        routes.get(2).setRouteTitle("School");
	        routes.get(2).setRouteDescription("something school");
	        routes.add(new Route());
	        routes.get(3).setRouteTitle("House");
	        routes.get(3).setRouteDescription("something house");
	        routes.add(new Route());
	        routes.get(4).setRouteTitle("School");
	        routes.get(4).setRouteDescription("something school");
	        routes.add(new Route());
	        routes.get(5).setRouteTitle("House");
	        routes.get(5).setRouteDescription("something house");
        } else{
        	Intent i = getIntent();
        	routes =  (ArrayList<Route>) i.getSerializableExtra("routes");
	        
        }
        
        updateData();
        
        practiceBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				Intent intent = new Intent(RouteMain.this, RouteStart.class);
				intent.putExtra("routes", routes);
				startActivity(intent);
			}
        	
        });
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
	        	Intent intent = new Intent(RouteMain.this, AddRouteScreen.class);
	        	if(mode == EDIT_MODE){
		        	intent.putExtra("routes", routes);
		        	intent.putExtra("position", position);
	        	} else {
	        		intent.putExtra("routes", routes);
	        	}
				startActivity(intent);
	            return true;
	        case R.id.deleteRouteBtn_:
	        	routes.remove(position);
	        	updateData();
	        	mode = ADD_MODE;
	        	imageButton.setIcon(R.drawable.add_route);
				imageButton.setTitle("Add Route");
				deleteButton.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
	        	position = -1;
	            return true;
        }
		return false;
	}
	
	private void setButtons(){
		imageButton=menuItem.findItem(R.id.addRouteBtn_);
		deleteButton = menuItem.findItem(R.id.deleteRouteBtn_);
	}
	private void updateData(){
		routeListView.invalidateViews();
		routeListView.setAdapter(new MyCustomBaseAdapter(RouteMain.this, routes));
	    routeListView.setBackgroundColor(Color.WHITE);
	    routeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				
		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int position,
			long arg3) {
			setButtons();
		
			if(getPosition() != position){
				mode = EDIT_MODE;
				imageButton.setIcon(R.drawable.edit_route);
				imageButton.setTitle("Edit Route");
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
					imageButton.setTitle("Edit Route");
					deleteButton.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
					view.setBackgroundColor(Color.LTGRAY);
				} else {
					mode = ADD_MODE;
					imageButton.setIcon(R.drawable.add_route);
					imageButton.setTitle("Add Route");
					deleteButton.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
					view.setBackgroundColor(Color.WHITE);
				}
			} 
			
			changePosition(position);	
			setPrevView(view);
			}
	    });
	}
}
