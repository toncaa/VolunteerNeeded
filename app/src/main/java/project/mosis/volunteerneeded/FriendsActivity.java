package project.mosis.volunteerneeded;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import java.util.ArrayList;

import project.mosis.volunteerneeded.adapters.FriendsListAdapter;
import project.mosis.volunteerneeded.data.FriendsData;
import project.mosis.volunteerneeded.entities.Friend;


public class FriendsActivity extends AppCompatActivity {


    private FriendsListAdapter friendsListAdapter;
    private ArrayList<Friend> friends;
    private AbsListView mListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);


        FriendsData friendsData = FriendsData.getInstance();
        friends = friendsData.getPeople();

        friendsListAdapter = new FriendsListAdapter(this, R.layout.activity_friends,friends);



        // Set the adapter
        mListView = (AbsListView) findViewById(R.id.list_of_friends);
        ((AdapterView<ListAdapter>) mListView).setAdapter(friendsListAdapter);
    }
}
