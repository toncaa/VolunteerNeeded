package project.mosis.volunteerneeded;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import project.mosis.volunteerneeded.data.VolunteerEventsData;
import project.mosis.volunteerneeded.entities.VolunteerEvent;

/**
 * Created by Nikola on 02-Jul-16.
 */

class EventInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View myContentsView;
    private Context context;
    private MarkersContainer markersContainer;

    EventInfoWindowAdapter(Context context, MarkersContainer markersContainer){
        this.context = context;
        this.markersContainer = markersContainer;

        LayoutInflater inflater = LayoutInflater.from(context);
        myContentsView =(View) inflater.inflate(R.layout.event_marker, null);
    }

    @Override
    public View getInfoContents(Marker marker) {

        ImageView eventImg = (ImageView) myContentsView.findViewById(R.id.event_image);
        String title = marker.getSnippet();


        VolunteerEvent event =  markersContainer.getEventByMarker(marker);
        if(event == null){
            //if marker doesn't represent VolunteerEvent object
            //then return null
            return null;
        }

        Bitmap eventBmp = event.getImage();
        eventImg.setImageBitmap(eventBmp);


        TextView tvTitle = ((TextView)myContentsView.findViewById(R.id.title));
        String markerTitle = marker.getTitle();
        tvTitle.setText(markerTitle);

        TextView tvSnippet = ((TextView)myContentsView.findViewById(R.id.snippet));
        String markerSnippet = marker.getSnippet();
        tvSnippet.setText(markerSnippet);

        return myContentsView;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

}