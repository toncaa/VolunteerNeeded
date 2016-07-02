package project.mosis.volunteerneeded;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuActivity extends FragmentActivity implements MenuFragment.OnFragmentInteractionListener {


    private Button viewMapButton, highscoreButton, searchButton, friendButton, exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        viewMapButton = (Button)findViewById(R.id.view_map_btn);
        viewMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        highscoreButton = (Button)findViewById(R.id.highscore_btn);
        highscoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, HighscoreActivity.class);
                startActivity(intent);
            }
        });

        friendButton = (Button)findViewById(R.id.friends_btn);
        friendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, FriendsActivity.class);
                startActivity(intent);
            }
        });

        exitButton = (Button)findViewById(R.id.exit_btn);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuActivity.this.finish();
                System.exit(0);
            }
        });




    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
