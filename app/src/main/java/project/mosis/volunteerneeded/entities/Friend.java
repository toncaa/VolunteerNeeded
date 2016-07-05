
package project.mosis.volunteerneeded.entities;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by MilanToncic on 6/5/2016.
 */

public class Friend {
    private String username;
    private String name;
    private String password;
    private String phone;
    private String lat;
    private String lon;
    private String rang;
    private Bitmap image;
    private String imageUrl;
    private int volunteeringPoints;
    private ArrayList<String> friends;
    private LatLng loc;

    public Friend(String name, String pass, Bitmap img)
    {
        this.name = name;
        this.password = pass;
        this.image = img;
        this.volunteeringPoints = 0;
        this.friends = new ArrayList<>();
        loc = new LatLng(21.9126,43.3189);
    }

    public Friend(String name, String username, String phone, int points, String rang, String lat, String lon, ArrayList<String> friends)
    {
        this.name = name;
        this.phone = phone;
        this.lat = lat;
        this.lon = lon;
        this.volunteeringPoints = points;
        this.friends = new ArrayList<>();
        this.loc = new LatLng(Double.parseDouble(lat),Double.parseDouble(lon));
        this.friends = friends;
        this.rang = rang;
        this.username = username;
    }

    public Friend(){
        this.name= "Default Name";
        this.password = "Default Password";
        this.volunteeringPoints = 0;
        this.friends = new ArrayList<>();
        loc = new LatLng(21.9126,43.3189);
    }

    public LatLng getLatLng(){return this.loc;}
    public String getName(){ return this.name; }
    public Bitmap getImage(){ return this.image; }
    public int getPoints(){ return this.volunteeringPoints; }

    public String getUsername() {
        return username;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getRang(){return this.rang;}

    public String getDescription()
    {
        String desc = new String("Rank: " + rang + "\n" + "Points: " + volunteeringPoints + "\n");
        return desc;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }
}



