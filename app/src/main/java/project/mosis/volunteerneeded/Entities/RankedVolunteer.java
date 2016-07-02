package project.mosis.volunteerneeded.entities;

/**
 * Created by MilanToncic on 7/2/2016.
 */

public class RankedVolunteer
{
    private String name;
    private String imageUrl;
    private String rank;
    private int points;


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
        this.imageUrl = imageUrl;
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
}
