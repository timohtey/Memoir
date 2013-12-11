package com.example.memoir;

import Model.GameModel;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ViewWords extends Activity{
	ListView listView;
	GameModel gm;
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_words);
        Intent i = getIntent();
        gm = (GameModel) i.getSerializableExtra("words");
        
        listView = (ListView) findViewById(R.id.wordListView);
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, gm.getWordList());
        listView.setAdapter(mAdapter);  
        
        
	}
}
