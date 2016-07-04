package project.mosis.volunteerneeded.search_events;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import project.mosis.volunteerneeded.R;

public class SearchActivity extends AppCompatActivity {


    private NumberPicker numPicker;
    private Button searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        numPicker = (NumberPicker) findViewById(R.id.number_picker_vn);
        numPicker.setMinValue(0);
        numPicker.setMaxValue(30);


        searchBtn = (Button) findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, SearchListActivity.class);
                startActivity(intent);
            }
        });

    }
}
