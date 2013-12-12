package Model;

import java.io.Serializable;
import java.util.ArrayList;

import android.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

public class Route extends Activity implements Serializable{
	private String routeDescription;
	private String routeTitle;
	private String routeMastery;
	private String lastPlayed;
	private String landmarksNmbr;
	private ArrayList<Uri> routeImages = new ArrayList<Uri>();
	
	public Route(){
		//Gets in the DAO
		routeDescription = "";
		routeTitle = "";
		routeMastery = "";
		
	}
	
	public ArrayList<Uri> getRouteImages() {
		return routeImages;
	}
	
	public void setRouteImages(Uri uri) {
		routeImages.add(uri);
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
