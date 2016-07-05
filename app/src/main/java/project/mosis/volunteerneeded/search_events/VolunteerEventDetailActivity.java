package project.mosis.volunteerneeded.search_events;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import project.mosis.volunteerneeded.R;
import project.mosis.volunteerneeded.data.VolunteerEventsData;
import project.mosis.volunteerneeded.entities.VolunteerEvent;

public class VolunteerEventDetailActivity extends AppCompatActivity {

    private TextView titleView, organizerView, pointsView, vneededView,detailView;
    private ImageView vEventImageView;

    private Button gotoLocBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_event_detail);


        Intent intent = getIntent();

        String event_name = intent.getStringExtra("title");
        VolunteerEventsData vdata = VolunteerEventsData.getInstance();

        final VolunteerEvent volunteerEvent = vdata.getEventByName(event_name);

        titleView = (TextView) findViewById(R.id.vevent_title);
        titleView.setText(volunteerEvent.getTitle());

        organizerView = (TextView) findViewById(R.id.organizer_name);
        organizerView.setText("Organizer: " + volunteerEvent.getOrganizerUsername());

        pointsView = (TextView) findViewById(R.id.volunteerEvent_prize);
        pointsView.setText("PRIZE: " + String.valueOf(volunteerEvent.getPoints()) + " points");

        vneededView = (TextView) findViewById(R.id.vneeded);
        vneededView.setText("Volunteers more: " + String.valueOf(volunteerEvent.getVolunteersNeeded()));

        detailView = (TextView) findViewById(R.id.vevent_detail);
        detailView.setText(volunteerEvent.getDescription());

        vEventImageView = (ImageView) findViewById(R.id.volunteerEvent_image);
        vEventImageView.setImageBitmap(volunteerEvent.getImage());

        gotoLocBtn = (Button) findViewById(R.id.gotoloc_btn);
        gotoLocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("lon", volunteerEvent.getLongitude());
                intent.putExtra("lat", volunteerEvent.getLatitude());

                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
}
