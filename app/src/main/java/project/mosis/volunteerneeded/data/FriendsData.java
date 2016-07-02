package project.mosis.volunteerneeded.data;

import java.util.ArrayList;

import project.mosis.volunteerneeded.entities.Friend;
import project.mosis.volunteerneeded.entities.Person;

/**
 * Created by MilanToncic on 6/10/2016.
 */
public class FriendsData {

    public static FriendsData singleton;

    ArrayList<Friend> friends;


    private FriendsData(){
        friends = new ArrayList<Friend>();
        friends.add(new Friend("Nikola Tesla", "files/user_images/tesla.png","Helpful scientist",3600));
        friends.add(new Friend("Jova Jović", "files/user_images/volunteer.jpg","Divine Helper",3556));
        friends.add(new Friend("Nikola Nikolić", "files/user_images/volunteer.jpg","Divine Helper",3200));
        friends.add(new Friend("Branko Ćopić", "files/user_images/volunteer.jpg","Divine Helper",2456));
        friends.add(new Friend("Bog Otac", "files/user_images/tesla.png","Helpful scientist",5131));
        friends.add(new Friend("Matija Bećković", "files/user_images/volunteer.jpg","Divine Helper",5353));
        friends.add(new Friend("Laza Kostić", "files/user_images/volunteer.jpg","Divine Helper",2124));
        friends.add(new Friend("Velimir Abramović", "files/user_images/volunteer.jpg","Divine Helper",4211));
    }

    public static FriendsData getInstance()
    {
        if(singleton == null)
            singleton = new FriendsData();
        return singleton;
    }

    public ArrayList<Friend> getPeople()
    {
        return friends;
    }

}
