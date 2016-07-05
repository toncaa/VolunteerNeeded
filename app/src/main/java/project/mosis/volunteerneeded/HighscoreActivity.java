package project.mosis.volunteerneeded;

import android.os.AsyncTask;
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
    private AbsListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);


        LoadRankedVolunteers loadRankedVolunteers = new LoadRankedVolunteers();
        loadRankedVolunteers.execute();
    }

    private class LoadRankedVolunteers extends AsyncTask<Void,Void, Void> {

        ArrayList<RankedVolunteer> volunteers;
        boolean finished = false;

        public LoadRankedVolunteers(){

        }

        @Override
        protected Void doInBackground(Void... params) {
            volunteers = VolunteerHTTPHelper.getHighestRankedVolunteers();

            RankedVolunteerData rankedVolunteerData = RankedVolunteerData.getInstance();
            rankedVolunteerData.setVolunteers(volunteers);
            rankedVolunteerData.downloadImages();
            volunteers = rankedVolunteerData.getVolunteers();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            highscoreListAdapter = new HighscoreListAdapter(HighscoreActivity.this, R.layout.activity_highscore,volunteers);
            // Set the adapter
            mListView = (AbsListView) findViewById(R.id.highest_volunteers);
            ((AdapterView<ListAdapter>) mListView).setAdapter(highscoreListAdapter);
        }

        public boolean isFinished(){
            return finished;
        }
    }






}
