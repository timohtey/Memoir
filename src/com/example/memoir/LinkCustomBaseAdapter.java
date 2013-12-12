package com.example.memoir;

import java.util.ArrayList;

import DAO.LinkCustomSet;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LinkCustomBaseAdapter extends BaseAdapter {
	 private ArrayList<LinkCustomSet> linkCustomWords = new ArrayList<LinkCustomSet>();
	 
	 private LayoutInflater mInflater;
	
	 public LinkCustomBaseAdapter(Context context, ArrayList<LinkCustomSet> linkCustomWords) {
		  this.linkCustomWords = linkCustomWords;
		  mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 }
	
	 public int getCount() {
		 return linkCustomWords.size();
	 }
	
	 public Object getItem(int position) {
		 return linkCustomWords.get(position);
	 }
	
	 public long getItemId(int position) {
		 return position;
	 }
	
	 public View getView(int position, View convertView, ViewGroup parent) {
		  ViewHolder holder;
		  
		  if (convertView == null) {
			   convertView = mInflater.inflate(R.layout.link_custom_list, parent, false);
			   convertView.setMinimumHeight(180);
			   //setting up viewholder
			   holder = new ViewHolder();
			   holder.txtTitle = (TextView) convertView.findViewById(R.id.titleCustomTxt);
			   holder.txtDesc = (TextView) convertView.findViewById(R.id.descLinkCustom);
			    holder.txtLinkCustomWords = (TextView) convertView.findViewById(R.id.linkCustomWords);
			   
			   convertView.setTag(holder);
		  } else {
			  holder = (ViewHolder) convertView.getTag();
		  }
		  
		  holder.txtTitle.setText(linkCustomWords.get(position).getSetName());
		  holder.txtDesc.setText(linkCustomWords.get(position).getDescription());
		  
		  holder.txtLinkCustomWords.setText(linkCustomWords.get(position).getWords());
		  
		  return convertView;
	 }
	
	 private static class ViewHolder {
		  TextView txtTitle = null;
		  TextView txtDesc = null;
		  TextView txtLinkCustomWords= null;
	 }

}