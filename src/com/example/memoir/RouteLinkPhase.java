package com.example.memoir;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.view.Menu;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

public class RouteLinkPhase extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route_link_phase);
		
		ActionBar actionBar = getActionBar();
	    actionBar.hide();
		
		
		Button yourBtn = (Button) findViewById(R.id.rl_backBtn);
		int btnSize=yourBtn.getLayoutParams().width;
		yourBtn.setLayoutParams(new LayoutParams(btnSize, btnSize));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.route_link_phase, menu);
		return true;
	}

}
