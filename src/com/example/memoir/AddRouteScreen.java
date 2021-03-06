package com.example.memoir;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import Model.GameModel;
import Model.Route;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridLayout.LayoutParams;
import android.widget.ImageView;

import com.example.memoir.R;

public class AddRouteScreen extends Activity {

    // this is the action code we use in our intent, 
    // this way we know we're looking at the response from our own action
    private static final int SELECT_PICTURE = 1;
    
    private String selectedImagePath;
    EditText routeName;
    EditText routeDesc;
    Button addLandmarkBtn;
    GridLayout gridLayout;
    ArrayList<Route> routes;
    Button saveRoute;
    String mode = "";
    int position;
    Uri uri;
    ArrayList<ImageView> addImageView = new ArrayList<ImageView>();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_route);

        addLandmarkBtn = (Button) findViewById(R.id.addLandmarkBtn);
        saveRoute = (Button) findViewById(R.id.saveRouteBtn);
        gridLayout = (GridLayout) findViewById(R.id.gridLayoutImage);
        routeName = (EditText) findViewById(R.id.routeNameTxt);
        routeDesc = (EditText) findViewById(R.id.routeDescTxt);
        
        Intent i = getIntent();
        if(i.getExtras().containsKey("position") == true){
	        routes =  (ArrayList<Route>) i.getSerializableExtra("routes");
	        position = i.getIntExtra("position", 0);
	        
	        routeName.setText(routes.get(position).getRouteTitle());
	        routeDesc.setText(routes.get(position).getRouteDescription());
	        mode = "EDIT_MODE";
        }
        else {
        	mode = "ADD_MODE";
        	routes =  (ArrayList<Route>) i.getSerializableExtra("routes");
        	saveRoute.setText("Add Route");
        }
        addLandmarkBtn.setOnClickListener(new OnClickListener() {
        	public void onClick(View arg0) {			
				// in onCreate or any event where your want the user to
				// select a file
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(intent,
				        "Select Picture"), SELECT_PICTURE);
        	}
         });
        
        saveRoute.setOnClickListener(new OnClickListener() {
        	public void onClick(View arg0) {
				Intent intent = new Intent(AddRouteScreen.this, RouteMain.class);
				if(mode == "ADD_MODE"){
					routes.add(new Route());
					routes.get(routes.size()-1).setRouteTitle(routeName.getText().toString());
					routes.get(routes.size()-1).setRouteDescription(routeDesc.getText().toString());
					intent.putExtra("routes", routes);
				}
				else if(mode == "EDIT_MODE"){
					routes.get(position).setRouteTitle(routeName.getText().toString());
					routes.get(position).setRouteDescription(routeDesc.getText().toString());
					intent.putExtra("routes", routes);
				} 
				startActivity(intent);
        	}
         });
        
        
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	Bitmap bitmap = null;
    	addImageView.add(new ImageView(this));
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                this.uri = data.getData();
                selectedImagePath = getPath(this.uri);
                try {
					bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), this.uri);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

                LayoutParams params=new GridLayout.LayoutParams();
                addImageView.get(getLast()).setLayoutParams(params);
                addImageView.get(getLast()).setImageBitmap(bitmap.createScaledBitmap(bitmap,180,180,false));
                //routeImg.setImageBitmap(bitmap.createScaledBitmap(bitmap, 50,50,false));
                gridLayout.addView(addImageView.get(getLast()), params);
                
                addImageView.get(getLast()).setClickable(true);
                addImageView.get(getLast()).setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						addLandmarkBtn.setText("Edit Image: "+selectedImagePath);
						routes.get(position).setRouteImages(getUri());
					}
                	
                });
            }
        }
    }
    private Uri getUri(){
    	return this.uri;
    }
    private int getLast() {
    	return addImageView.size()-1;
    }
    /**
     * helper to retrieve the path of an image URI
     */
    public String getPath(Uri uri) {
            // just some safety built in 
            if( uri == null ) {
                // TODO perform some logging or show user feedback
                return null;
            }
            // try to retrieve the image from the media store first
            // this will only work for images selected from gallery
            String[] projection = { MediaStore.Images.Media.DATA };
            Cursor cursor = managedQuery(uri, projection, null, null, null);
            if( cursor != null ){
                int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                return cursor.getString(column_index);
            }
            // this is our fallback here
            return uri.getPath();
    }
}