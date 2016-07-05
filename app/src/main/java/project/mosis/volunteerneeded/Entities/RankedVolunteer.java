package project.mosis.volunteerneeded.entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.net.URL;

import project.mosis.volunteerneeded.data.DataLoader;

/**
 * Created by MilanToncic on 7/2/2016.
 */

public class RankedVolunteer
{
    private String name;
    private String imageUrl;
    private String rank;
    private int points;
    private Bitmap image;


    public RankedVolunteer(String name, String imageUrl, String rank, int points)
    {
        this.setName(name);
        this.setImageUrl(imageUrl);
        this.setRank(rank);
        this.setPoints(points);
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
        this.imageUrl = DataLoader.VOLUNTEER_IMAGES_LOCATION + imageUrl + ".jpeg";
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void downloadImage(){
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream((InputStream)new URL(imageUrl).getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.image = bitmap;
    }

}