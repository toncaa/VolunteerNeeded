package project.mosis.volunteerneeded;

import java.util.ArrayList;

/**
 * Created by MilanToncic on 6/5/2016.
 */
public class VolunteerEventsData {

    public static VolunteerEventsData singleton;

    ArrayList<VolunteerEvent> volunteerEvents;


    private VolunteerEventsData(){
        volunteerEvents = new ArrayList<VolunteerEvent>();
        volunteerEvents.add(new VolunteerEvent());
    }

    public static VolunteerEventsData getInstance()
    {
        if(singleton == null)
            singleton = new VolunteerEventsData();
        return singleton;
    }

    public ArrayList<VolunteerEvent> getVolunteerEvents()
    {
        return volunteerEvents;
    }


}
