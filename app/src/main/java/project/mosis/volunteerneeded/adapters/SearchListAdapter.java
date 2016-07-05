package project.mosis.volunteerneeded.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import project.mosis.volunteerneeded.R;
import project.mosis.volunteerneeded.entities.VolunteerEvent;

/**
 * Created by MilanToncic on 7/4/2016.
 */
public class SearchListAdapter extends ArrayAdapter<VolunteerEvent> {



    public SearchListAdapter(Context context, int resource, List<VolunteerEvent> objects) {
        super(context, resource, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // return super.getView(position, convertView, parent);
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.search_list_item, parent, false);
        }

        VolunteerEvent p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.volunteer_name);
            TextView tt2 = (TextView) v.findViewById(R.id.vevent_title);
            TextView tt3 = (TextView) v.findViewById(R.id.volunteer_points);
            TextView tt4 = (TextView) v.findViewById(R.id.searchlist_vneeded);




            if (tt1 != null) {
                tt1.setText("Organizer: " + p.getOrganizerUsername());
            }

            if (tt2 != null) {
                tt2.setText(p.getTitle());
            }

            if (tt3 != null) {
                tt3.setText("PRIZE: " + String.valueOf(p.getPoints()) + " points");
            }

            if (tt4 != null) {
                tt4.setText("Volunteers more: " + p.getVolunteersNeeded());
            }

        }

        return v;


    }



}
