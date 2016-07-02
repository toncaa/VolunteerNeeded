package project.mosis.volunteerneeded;

import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

public class SearchActivity extends AppCompatActivity {


    private NumberPicker numPicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        numPicker = (NumberPicker) findViewById(R.id.number_picker_vn);
        numPicker.setMinValue(0);
        numPicker.setMaxValue(30);
    }
}
