package project.mosis.volunteerneeded.data;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import project.mosis.volunteerneeded.entities.VolunteerEvent;
import project.mosis.volunteerneeded.VolunteerHTTPHelper;

/**
 * Created by MilanToncic on 6/5/2016.
 */
public class VolunteerEventsData {

    public static VolunteerEventsData singleton;

    private ArrayList<VolunteerEvent> volunteerEvents;


    private VolunteerEventsData(){
        volunteerEvents = new ArrayList<VolunteerEvent>();
        volunteerEvents.add(new VolunteerEvent());
    }

    public static VolunteerEventsData getInstance() {
        if(singleton == null)
            singleton = new VolunteerEventsData();

        return singleton;
    }

    public ArrayList<VolunteerEvent> getVolunteerEvents()
    {
        return volunteerEvents;
    }

    public void setVolunteerEventsData(ArrayList<VolunteerEvent> events){
        volunteerEvents = events;
    }

    public void loadVolunteerEvents()
    {
        //TODO: add logic to load from local starage
        new LoadEvents().execute();
    }

    public VolunteerEvent getEventByName(String name){
      /*  for(int i=0; i< volunteerEvents.size(); i++)
            if(volunteerEvents.get(i).getDescription().equals(name))
                return volunteerEvents.get(i);*/
        return volunteerEvents.get(0);
      //  return null;
    }

    public void  updateVolunteerEvents()
    {
        ArrayList<VolunteerEvent> data = VolunteerHTTPHelper.getVolunteerEventsData();
        if(data != null)
            volunteerEvents = data;
    }


    public ArrayList<VolunteerEvent> findEvents(final int maxDistance, final String title, int numOfVolunteer){

        ArrayList<VolunteerEvent> result = new ArrayList<>();
        for(int i=0; i < volunteerEvents.size(); i++){
            if(volunteerEvents.get(i).meetCriteria(maxDistance,title,numOfVolunteer))
                result.add(volunteerEvents.get(i));
        }


        Collections.sort(result, new Comparator<VolunteerEvent>() {
            @Override
            public int compare(VolunteerEvent lhs, VolunteerEvent rhs) {
                if(maxDistance != 0) {
                    if (lhs.getDistance() > rhs.getDistance())
                        return 1;
                    else
                        return -1;
                }else if(title != null){
                    return lhs.getTitle().compareTo(rhs.getTitle());
                }else{
                    if (lhs.getVolunteersNeeded() > rhs.getVolunteersNeeded())
                        return 1;
                    else
                        return -1;
                }
            }
        });
        return  result;
    }

    public VolunteerEvent findEvent(String name)
    {
        for(int i=0; i<volunteerEvents.size();i++)
        {
            if(volunteerEvents.get(i).getTitle().equals(name))
                return volunteerEvents.get(i);
        }
        return null;
    }


    private static class LoadEvents extends AsyncTask<Void, Void, Boolean> {
        private String errorMessage;

        LoadEvents() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            ArrayList<VolunteerEvent> data = VolunteerHTTPHelper.getVolunteerEventsData();
            if(data != null)
                VolunteerEventsData.getInstance().setVolunteerEventsData(data);

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
        }
    }

}
