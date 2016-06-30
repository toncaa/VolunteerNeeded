package project.mosis.volunteerneeded;

import com.google.android.gms.plus.People;

import java.util.ArrayList;

/**
 * Created by MilanToncic on 6/10/2016.
 */
public class PeopleData {

    public static PeopleData singleton;

    ArrayList<Person> people;


    private PeopleData(){
        people = new ArrayList<Person>();
        people.add(new Person());
    }

    public static PeopleData getInstance()
    {
        if(singleton == null)
            singleton = new PeopleData();
        return singleton;
    }

    public ArrayList<Person> getPeople()
    {
        return people;
    }

}
