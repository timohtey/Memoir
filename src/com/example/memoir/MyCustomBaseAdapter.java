package com.example.memoir;

import java.util.ArrayList;

import Model.Route;
import android.content.Context;
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
	
	 public MyCustomBaseAdapter(Context context, ArrayList<Route> route) {
		  this.routeArrayList = route;
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
		  
		  holder.imgRoute.setImageResource(R.drawable.chainlink_icon);
		  
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