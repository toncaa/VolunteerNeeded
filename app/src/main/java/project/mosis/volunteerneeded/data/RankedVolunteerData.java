package project.mosis.volunteerneeded.data;

import java.util.ArrayList;

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
        volunteers.add(new RankedVolunteer("Nikola Tesla", "files/user_images/tesla.png","Helpful scientist",3600));
        volunteers.add(new RankedVolunteer("Jova Jović", "files/user_images/volunteer.jpg","Divine Helper",3556));
        volunteers.add(new RankedVolunteer("Nikola Nikolić", "files/user_images/volunteer.jpg","Divine Helper",3200));
        volunteers.add(new RankedVolunteer("Branko Ćopić", "files/user_images/volunteer.jpg","Divine Helper",2456));
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



}
