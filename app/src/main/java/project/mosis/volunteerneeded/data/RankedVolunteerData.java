package project.mosis.volunteerneeded.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import project.mosis.volunteerneeded.entities.RankedVolunteer;

/**
 * Created by MilanToncic on 7/2/2016.
 */
public class RankedVolunteerData {

    public static RankedVolunteerData singleton;

    ArrayList<RankedVolunteer> volunteers;


    private RankedVolunteerData(){
        volunteers = new ArrayList<RankedVolunteer>();
        volunteers.add(new RankedVolunteer("Nikola Tesla", "files/user_images/tesla.png","Helpful scientist",3600));
        volunteers.add(new RankedVolunteer("Jova Jović", "files/user_images/volunteer.jpg","Divine Helper",3556));
        volunteers.add(new RankedVolunteer("Nikola Nikolić", "files/user_images/volunteer.jpg","Divine Helper",3200));
        volunteers.add(new RankedVolunteer("Branko Ćopić", "files/user_images/volunteer.jpg","Divine Helper",2456));
        volunteers.add(new RankedVolunteer("Bog Otac", "files/user_images/tesla.png","Helpful scientist",5131));
        volunteers.add(new RankedVolunteer("Matija Bećković", "files/user_images/volunteer.jpg","Divine Helper",5353));
        volunteers.add(new RankedVolunteer("Laza Kostić", "files/user_images/volunteer.jpg","Divine Helper",2124));
        volunteers.add(new RankedVolunteer("Velimir Abramović", "files/user_images/volunteer.jpg","Divine Helper",4211));
    }

    public static RankedVolunteerData getInstance()
    {
        if(singleton == null)
            singleton = new RankedVolunteerData();
        return singleton;
    }

    public ArrayList<RankedVolunteer> getVolunteers()
    {
        return volunteers;
    }

    public ArrayList<RankedVolunteer> sortVolunteers() {

        Collections.sort(volunteers, new Comparator<RankedVolunteer>() {
            @Override public int compare(RankedVolunteer p1, RankedVolunteer p2) {
                return p2.getPoints() - p1.getPoints(); // Ascending
            }

        });

        return volunteers;
    }


}
