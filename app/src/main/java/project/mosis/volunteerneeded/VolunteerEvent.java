package project.mosis.volunteerneeded;

import android.graphics.Bitmap;

/**
 * Created by MilanToncic on 6/5/2016.
 */
public class VolunteerEvent {


    private String title;
    private String desc;
    private int points;
    private int volunteersNeeded;
    private Bitmap image;


    private String lat;
    private String lon;

    public VolunteerEvent(String title,String lon, String lat, String desc, int volunteersNeeded, Bitmap image)
    {
        this.title = title;
        this.desc = desc;
        this.lon = lon;
        this.lat = lat;
        this.volunteersNeeded = volunteersNeeded;
        this.image = image;

        this.points = this.volunteersNeeded * 2;
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



    public String getName()
    {
        return this.title;
    }

    public String getDescription()
    {
        return this.desc;
    }

    public int getPoints(){return this.points;}

    public int getVolunteersNeeded(){return this.volunteersNeeded;}

    public Bitmap getImage(){return this.image;}

    public String getLatitude()
    {
        return this.lat;
    }

    public String getLongitude()
    {
        return this.lon;
    }


}
