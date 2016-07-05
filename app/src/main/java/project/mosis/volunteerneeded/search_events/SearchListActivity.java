package project.mosis.volunteerneeded.search_events;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import project.mosis.volunteerneeded.R;
import project.mosis.volunteerneeded.adapters.SearchListAdapter;
import project.mosis.volunteerneeded.data.VolunteerEventsData;
import project.mosis.volunteerneeded.entities.VolunteerEvent;

public class SearchListActivity extends AppCompatActivity {

    private static final int EVENT_LOCATION = 555;

    private SearchListAdapter searchListAdapter;
    private ArrayList<VolunteerEvent> events;
    private AbsListView mListView;

    private LatLng searchEventLoc;

    private String title;
    private int distance,volunteersNeeded;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        distance = intent.getIntExtra("distance", 0);
        volunteersNeeded = intent.getIntExtra("volunteersNeeded", 0);



        //ovde trebamo da pribavimo samo one koji zadovoljavaju kriterijum
        VolunteerEventsData volunteersEventsData = VolunteerEventsData.getInstance();

        events =  volunteersEventsData.findEvents(distance, title, volunteersNeeded);

        searchListAdapter = new SearchListAdapter(this, R.layout.activity_highscore,events);



        // Set the adapter
        mListView = (AbsListView) findViewById(R.id.search_results_list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(searchListAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                Intent eventInfo = new Intent(SearchListActivity.this, VolunteerEventDetailActivity.class);
                VolunteerEvent volunteerEvent = (VolunteerEvent) mListView.getItemAtPosition(position);
                Toast.makeText(SearchListActivity.this,"Position: "+ position, Toast.LENGTH_LONG).show();
//                eventInfo.putExtra("desc", volunteerEvent.getDescription());
//                eventInfo.putExtra("vneeded", volunteerEvent.getVolunteersNeeded());
//                eventInfo.putExtra("points", volunteerEvent.getPoints());
//                eventInfo.putExtra("organizer", volunteerEvent.getOrganizerUsername());
                eventInfo.putExtra("title", volunteerEvent.getTitle());


                startActivityForResult(eventInfo, EVENT_LOCATION);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == EVENT_LOCATION && resultCode ==  RESULT_OK && data!=null)
        {
            //searchEventLoc = new LatLng();
            Intent intent = new Intent();
            intent.putExtra("lon", data.getStringExtra("lon"));
            intent.putExtra("lat", data.getStringExtra("lat"));

            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
