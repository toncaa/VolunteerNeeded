package project.mosis.volunteerneeded;

import android.graphics.Bitmap;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by MilanToncic on 6/5/2016.
 */

enum VolunteerRank {rank1, rank2, rank3}

public class Person {

    private String name;
    private String password;
    private Bitmap image;
    private VolunteerRank rank;
    private int volunteeringPoints;
    private ArrayList<Person> friends;
    private LatLng loc;

    public Person(String name, String pass, Bitmap img)
    {
        this.name = name;
        this.password = pass;
        this.image = img;
        this.rank = VolunteerRank.rank1;
        this.volunteeringPoints = 0;
        this.friends = new ArrayList<>();
        loc = new LatLng(21.9126,43.3189);
    }

    public Person(){
        this.name= "Default Name";
        this.password = "Default Password";
        //this.image =
        this.rank = VolunteerRank.rank1;
        this.volunteeringPoints = 0;
        this.friends = new ArrayList<>();
        loc = new LatLng(21.9126,43.3189);
    }

    public LatLng getLatLng(){return this.loc;}
    public String getName(){ return this.name; }
    public Bitmap getImage(){ return this.image; }
    public VolunteerRank getRank(){ return this.rank; }
    public int getPoints(){ return this.volunteeringPoints; }

    public String getDescription()
    {
        String desc = new String("Rank: " + rank + "\n" + "Points: " + volunteeringPoints + "\n");
        return desc;
    }
}
