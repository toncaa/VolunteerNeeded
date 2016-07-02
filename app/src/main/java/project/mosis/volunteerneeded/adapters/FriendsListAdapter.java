package project.mosis.volunteerneeded.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import project.mosis.volunteerneeded.R;
import project.mosis.volunteerneeded.entities.Friend;
import project.mosis.volunteerneeded.entities.Person;
import project.mosis.volunteerneeded.entities.RankedVolunteer;

/**
 * Created by MilanToncic on 7/2/2016.
 */
public class FriendsListAdapter extends ArrayAdapter<Friend> {


    public FriendsListAdapter(Context context, int resource, List<Friend> objects) {
        super(context, resource, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // return super.getView(position, convertView, parent);
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.friend_list_item, parent, false);
        }

        Friend p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.volunteer_name);
            TextView tt2 = (TextView) v.findViewById(R.id.volunteer_points);
            TextView tt3 = (TextView) v.findViewById(R.id.volunteer_rank);

            if (tt1 != null) {
                tt1.setText(p.getName());
            }

            if (tt2 != null) {
                tt2.setText(String.valueOf(p.getVolunteeringPoints()) + " points");
            }

            if (tt3 != null) {
                tt3.setText("Rank: " + p.getRank());
            }

        }

        return v;


    }
}
