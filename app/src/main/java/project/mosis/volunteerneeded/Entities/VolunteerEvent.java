
package project.mosis.volunteerneeded.entities;
import android.graphics.Bitmap;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import project.mosis.volunteerneeded.MainActivity;
import project.mosis.volunteerneeded.VolunteerHTTPHelper;

/**
 * Created by MilanToncic on 6/5/2016.
 */
public class VolunteerEvent {

    private String organizerUsername;
    private String title;
    private String desc;
    private int points;
    private int volunteersNeeded;
    private String category;
    private String time;
    private Bitmap image;
    private String imageUrl;

    private LatLng loc;

    private String lat;
    private String lon;

    private float distance;

    public VolunteerEvent(String organizerUsername, String title, String lat, String lon, String desc,
                          String time, String category, int volunteersNeeded, Bitmap image)
    {
        this.organizerUsername = organizerUsername;
        this.title = title;
        this.desc = desc;
        this.lon = lon;
        this.lat = lat;
        this.volunteersNeeded = volunteersNeeded;
        this.time = time;
        this.category = category;
        this.image = image;

        this.points = this.volunteersNeeded * 2;

        this.loc = new LatLng(Double.parseDouble(lat),Double.parseDouble(lon));
    }

    public VolunteerEvent(String organizerUsername, String title, String lat, String lon, String desc,
                          String time, String category, int volunteersNeeded, String imageUrl)
    {
        this.organizerUsername = organizerUsername;
        this.title = title;
        this.desc = desc;
        this.lon = lon;
        this.lat = lat;
        this.volunteersNeeded = volunteersNeeded;
        this.time = time;
        this.category = category;
        this.imageUrl = imageUrl;
        this.points = this.volunteersNeeded * 2;

        this.loc = new LatLng(Double.parseDouble(lat),Double.parseDouble(lon));
    }

    public VolunteerEvent()
    {
        this.title = "default Title";
        this.desc = "default Description";
        this.lat = "43.32238345";
        this.lon = "21.89639568";
        this.volunteersNeeded = 2;

        this.points = this.volunteersNeeded * 2;
    }



    public boolean meetCriteria(int maxDistance, String title, int numOfVolunteer){
        boolean distancePass = false, titlePass = false, numOfVolunteerPass=false;
        float res[] = new float[3];
        float finalDistance = 0;

        if(maxDistance != 0) {
            Location userLocation = MainActivity.getMyCurrentLocation();
            Location.distanceBetween(userLocation.getLatitude(), userLocation.getLongitude(),
                    loc.latitude, loc.longitude, res);
            finalDistance = res[0];
            if(finalDistance < maxDistance)
                distancePass = true;

            distance = finalDistance;
        }else{
            distancePass = true;
        }

        if(title.length() == 0) title = null;

        if(title != null) {
            if (this.title.toLowerCase().contains(title.toLowerCase()))
                titlePass = true;
        }else {
            titlePass = true;
        }


        if(numOfVolunteer != 0) {
            if (volunteersNeeded > numOfVolunteer)
                numOfVolunteerPass = true;
        }else {
            numOfVolunteerPass = true;
        }


        if(distancePass && numOfVolunteerPass && titlePass)
            return true;
        else
            return false;
    }

    public String getDescription()
    {
        return this.desc;
    }

    public int getPoints(){return this.points;}

    public int getVolunteersNeeded(){return this.volunteersNeeded;}

    public Bitmap getImage(){return this.image;}

    public String getTitle(){return this.title; }

    public String getLatitude() { return this.lat; }

    public String getLongitude()
    {
        return this.lon;
    }

    public String getOrganizerUsername(){ return this.organizerUsername; }
    public String toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("organizer", organizerUsername);
        json.put("title",title);
        json.put("desc",desc);
        json.put("lat",lat);
        json.put("lon",lon);
        json.put("category",category);
        json.put("volNeeded",volunteersNeeded);
        json.put("time",time);
        json.put("image", VolunteerHTTPHelper.bitmapToString(image));
        return json.toString();
    }


    public String getCategory() {return category;  }

    public String getTime() { return time;   }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public float getDistance(){return this.distance;}

}
