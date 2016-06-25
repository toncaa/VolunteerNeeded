package project.mosis.volunteerneeded;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {


    private GoogleMap googleMap;
    private HashMap<Marker, Integer> markerEventIdMap;

    public LocationManager locationManager;
    public LocationUpdateListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SupportMapFragment mapFragment = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id == R.id.add_event_item)
        {
            Intent intent = new Intent(this, VolunteerEventActivity.class);
            intent.putExtra("lon", googleMap.getMyLocation().getLongitude());
            intent.putExtra("lat", googleMap.getMyLocation().getLatitude());
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onMapReady(GoogleMap map) {

        googleMap = map;
        setUpMap();

        addVolunteerEventMarkers();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        listener = new LocationUpdateListener();
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);


        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener(){
            @Override
            public void onMapLongClick(LatLng latLng) {
                makeVolunteerCall(latLng);

            }
        });

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){
            @Override
            public boolean onMarkerClick(Marker marker) {

                marker.showInfoWindow();
                return true;
            }
        });
    }

    private void makeVolunteerCall(LatLng latLng)
    {
        String lon = Double.toString(latLng.longitude);
        String lat = Double.toString(latLng.latitude);
        Intent makeVolunteerEventIntent = new Intent(this, VolunteerEventActivity.class);
        makeVolunteerEventIntent.putExtra("lon", lon);
        makeVolunteerEventIntent.putExtra("lat", lat);
        startActivity(makeVolunteerEventIntent);
    }


    private void setUpMap(){
            // Enable MyLocation Layer of Google Map
            googleMap.setMyLocationEnabled(true);

            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }



    private void addVolunteerEventMarkers()
    {
        ArrayList<VolunteerEvent> places = VolunteerEventsData.getInstance().getVolunteerEvents();
        markerEventIdMap = new HashMap<Marker, Integer>((int)((double)places.size()*1.2));

        for (int i=0;i<places.size();i++)
        {
            VolunteerEvent event = places.get(i);
            String lat = event.getLatitude();
            String lon = event.getLongitude();
            LatLng loc = new LatLng(Double.parseDouble(lat),Double.parseDouble(lon));

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(loc);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.volunteer_event));
            markerOptions.title(event.getName());
            markerOptions.snippet(event.getDescription());
            Marker marker = googleMap.addMarker(markerOptions);
            markerEventIdMap.put(marker,i);
        }
    }



    class LocationUpdateListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            // TODO Auto-generated method stub
         //   Toast.makeText(MainActivity.this, "Location Changed!", Toast.LENGTH_SHORT).show();
            // update your marker here
        }

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub

        }

    }

}


