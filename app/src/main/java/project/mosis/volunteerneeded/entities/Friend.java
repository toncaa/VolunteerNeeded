package project.mosis.volunteerneeded.entities;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by MilanToncic on 7/2/2016.
 */

public class Friend {

    private String name;
    private String imageUrl;
    private String rank;
    private int volunteeringPoints;

    private LatLng loc;

    public Friend(String name,String imgUrl, String rank, int points)
    {
        this.setName(name);
        this.setImageUrl(imgUrl);
        this.setRank(rank);
        this.setVolunteeringPoints(points);

        loc = new LatLng(21.9126,43.3189);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getVolunteeringPoints() {
        return volunteeringPoints;
    }

    public void setVolunteeringPoints(int volunteeringPoints) {
        this.volunteeringPoints = volunteeringPoints;
    }


    public LatLng getLatLng(){return this.loc;}


    public String getDescription()
    {
        String desc = new String("Rank: " + rank + "\n" + "Points: " + volunteeringPoints + "\n");
        return desc;
    }
}



