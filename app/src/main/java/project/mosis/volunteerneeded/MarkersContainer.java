package project.mosis.volunteerneeded;

import com.google.android.gms.maps.model.Marker;

import project.mosis.volunteerneeded.entities.Friend;
import project.mosis.volunteerneeded.entities.VolunteerEvent;

/**
 * Created by Nikola on 04-Jul-16.
 */
public interface MarkersContainer {

    VolunteerEvent getEventByMarker(Marker m);
    Friend getFriendByMerker(Marker m);
}
