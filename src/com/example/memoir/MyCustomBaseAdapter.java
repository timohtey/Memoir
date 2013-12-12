package com.example.memoir;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import Model.Route;
import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyCustomBaseAdapter extends BaseAdapter {
	 private ArrayList<Route> routeArrayList = new ArrayList<Route>();
	 
	 private LayoutInflater mInflater;
	 private Context context;
	
	 public MyCustomBaseAdapter(Context context, ArrayList<Route> route) {
		  this.routeArrayList = route;
		  this.context = context;
		  mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 }
	
	 public int getCount() {
		 return routeArrayList.size();
	 }
	
	 public Object getItem(int position) {
		 return routeArrayList.get(position);
	 }
	
	 public long getItemId(int position) {
		 return position;
	 }
	
	 public View getView(int position, View convertView, ViewGroup parent) {
		  ViewHolder holder;
		  
		  if (convertView == null) {
			   convertView = mInflater.inflate(R.layout.route_list_items, parent, false);
			   convertView.setMinimumHeight(180);
			   //setting up viewholder
			   holder = new ViewHolder();
			   holder.txtTitle = (TextView) convertView.findViewById(R.id.titleTxtView);
			   holder.txtDesc = (TextView) convertView.findViewById(R.id.descriptionTxtView);
			   holder.txtLastPracticed = (TextView) convertView.findViewById(R.id.lastPracticedTxt);
			   holder.txtLandMarks = (TextView) convertView.findViewById(R.id.landMarksTxt);
			   holder.txtMastery = (TextView) convertView.findViewById(R.id.masteryTxt);
			   holder.imgRoute= (ImageView) convertView.findViewById(R.id.routeImage);
			   
			   convertView.setTag(holder);
		  } else {
			  holder = (ViewHolder) convertView.getTag();
		  }
		  holder.txtTitle.setText(routeArrayList.get(position).getRouteTitle());
		  holder.txtDesc.setText(routeArrayList.get(position).getRouteDescription());
		  Bitmap bitmap = null;
		  if(routeArrayList.get(position).getRouteImages().size() != 0){
			  try {
				bitmap = MediaStore.Images.Media.getBitmap(this.context.getContentResolver(), routeArrayList.get(position).getRouteImages().get(0));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  	holder.imgRoute.setImageBitmap(bitmap.createScaledBitmap(bitmap,50,50,false));
		  }
		  
		  return convertView;
	 }
	
	 private static class ViewHolder {
		  TextView txtTitle = null;
		  TextView txtDesc = null;
		  TextView txtLastPracticed= null;
		  TextView txtLandMarks = null;
		  TextView txtMastery = null;
		  ImageView imgRoute = null;
	 }

}