package project.mosis.volunteerneeded;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by Nikola on 02-Jul-16.
 */

class EventInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View myContentsView;
    private Context context;

    EventInfoWindowAdapter(Context context){
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        myContentsView =(View) inflater.inflate(R.layout.event_marker, null);
    }

    @Override
    public View getInfoContents(Marker marker) {

        TextView tvTitle = ((TextView)myContentsView.findViewById(R.id.title));
        tvTitle.setText(marker.getTitle());
        TextView tvSnippet = ((TextView)myContentsView.findViewById(R.id.snippet));
        tvSnippet.setText(marker.getSnippet());

        return myContentsView;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        // TODO Auto-generated method stub
        return null;
    }

}