package project.mosis.volunteerneeded.search_events;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import java.util.ArrayList;

import project.mosis.volunteerneeded.R;
import project.mosis.volunteerneeded.adapters.SearchListAdapter;
import project.mosis.volunteerneeded.data.VolunteerEventsData;
import project.mosis.volunteerneeded.entities.VolunteerEvent;

public class SearchListActivity extends AppCompatActivity {

    private SearchListAdapter searchListAdapter;
    private ArrayList<VolunteerEvent> events;
    private AbsListView mListView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        //ovde trebamo da pribavimo samo one koji zadovoljavaju kriterijum
        VolunteerEventsData volunteersEventsData = VolunteerEventsData.getInstance();
        events = volunteersEventsData.getVolunteerEvents();

        searchListAdapter = new SearchListAdapter(this, R.layout.activity_highscore,events);



        // Set the adapter
        mListView = (AbsListView) findViewById(R.id.search_results_list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(searchListAdapter);

    }
}
