package project.mosis.volunteerneeded;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import java.util.ArrayList;

import project.mosis.volunteerneeded.entities.RankedVolunteer;
import project.mosis.volunteerneeded.adapters.HighscoreListAdapter;
import project.mosis.volunteerneeded.data.RankedVolunteerData;

public class HighscoreActivity extends AppCompatActivity {

    private HighscoreListAdapter highscoreListAdapter;
    private ArrayList<RankedVolunteer> volunteers;
    private AbsListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        RankedVolunteerData rankedVolunteerData = RankedVolunteerData.getInstance();
        volunteers = rankedVolunteerData.getVolunteers();

        highscoreListAdapter = new HighscoreListAdapter(this, R.layout.activity_highscore,volunteers);



        // Set the adapter
        mListView = (AbsListView) findViewById(R.id.highest_volunteers);
        ((AdapterView<ListAdapter>) mListView).setAdapter(highscoreListAdapter);

    }






}
