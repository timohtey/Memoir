package Model;

import java.io.Serializable;
import java.util.ArrayList;

import android.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.ImageView;

public class Route extends Activity implements Serializable{
	private ArrayList<ImageView> routeImages;
	private String routeDescription;
	private String routeTitle;
	private String routeMastery;
	private String lastPlayed;
	private String landmarksNmbr;
	
	public Route(){
		//Gets in the DAO
		routeImages = new ArrayList<ImageView>();
		routeDescription = "";
		routeTitle = "";
		routeMastery = "";
		
	}
	
	public ArrayList<ImageView> getRouteImages() {
		return routeImages;
	}
	
	public void setRouteImages(/*ArrayList<String> uri*/) {
		for(int i = 0; i< 5/*uri.size()*/; i++){
			routeImages.add(new ImageView(this));
			routeImages.get(i).setImageResource(R.drawable.bottom_bar);
			//routeImages.get(i).setImageBitmap(bitmap.createScaledBitmap(bitmap,50,50,false));
		}
	}
	public String getRouteDescription() {
		return routeDescription;
	}
	public void setRouteDescription(String routeDescription) {
		this.routeDescription = routeDescription;
	}
	public String getRouteTitle() {
		return routeTitle;
	}
	public void setRouteTitle(String routeTitle) {
		this.routeTitle = routeTitle;
	}
	public String getRouteMastery() {
		return routeMastery;
	}
	public void setRouteMastery(String routeMastery) {
		this.routeMastery = routeMastery;
	}

	public String getLastPlayed() {
		return lastPlayed;
	}

	public void setLastPlayed(String lastPlayed) {
		this.lastPlayed = lastPlayed;
	}

	public String getLandmarksNmbr() {
		return landmarksNmbr;
	}

	public void setLandmarksNmbr(String landmarksNmbr) {
		this.landmarksNmbr = landmarksNmbr;
	}
}
