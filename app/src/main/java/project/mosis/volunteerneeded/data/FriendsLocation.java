package project.mosis.volunteerneeded.data;

import android.os.AsyncTask;

/**
 * Created by Nikola on 03-Jul-16.
 */
public class FriendsLocation extends AsyncTask<Void, Void, Void> {
    private String username;

    FriendsLocation(String username){
        this.username = username;
    }

    @Override
    protected Void doInBackground(Void... params) {
        return null;
    }
}
