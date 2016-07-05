package project.mosis.volunteerneeded.search_events;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import project.mosis.volunteerneeded.R;

public class SearchActivity extends AppCompatActivity {

    private static final int EVENT_LOCATION = 555;

    private EditText titleView;
    private EditText distanceView;

    private String title;
    private int distance,volunteersNeeded;


    private NumberPicker numPicker;
    private Button searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        titleView = (EditText) findViewById(R.id.search_title);


        distanceView = (EditText) findViewById(R.id.search_distance);


        numPicker = (NumberPicker) findViewById(R.id.search_number_picker_vn);
        numPicker.setMinValue(0);
        numPicker.setMaxValue(30);


        searchBtn = (Button) findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, SearchListActivity.class);

                title = titleView.getText().toString();

                String distanceString = distanceView.getText().toString();
                if(distanceString.length() > 0)
                    distance = Integer.getInteger(distanceString);

                volunteersNeeded = numPicker.getValue();

                intent.putExtra("title", title);
                intent.putExtra("distance", distance);
                intent.putExtra("volunteersNeeded", volunteersNeeded);

                startActivityForResult(intent, EVENT_LOCATION);
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
