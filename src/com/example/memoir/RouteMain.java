package com.example.memoir;

import java.util.ArrayList;

import Model.Route;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


public class RouteMain extends Activity{
	private ArrayAdapter<Route> mAdapter;
	private ListView routeListView;
	private Button addRouteBtn;
	private ArrayList<Route> routes = new ArrayList<Route>();
	private ArrayList<String> title = new ArrayList<String>();
	private ArrayList<String> description = new ArrayList<String>();
	private ArrayList<Uri> uri = new ArrayList<Uri>();
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_main);
        
        routeListView = (ListView) findViewById(R.id.routeListView);
        addRouteBtn = (Button) findViewById(R.id.addRouteBtn);
        
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
        
        routeListView.setAdapter(new MyCustomBaseAdapter(RouteMain.this, routes));
        
        addRouteBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(RouteMain.this, AddRouteScreen.class);
				startActivity(intent);
			}
        	
        });
	}
}
