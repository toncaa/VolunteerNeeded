package project.mosis.volunteerneeded;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
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
import java.util.Iterator;

import project.mosis.volunteerneeded.entities.Friend;
import project.mosis.volunteerneeded.entities.VolunteerEvent;
import project.mosis.volunteerneeded.bluetoothscanner.ListActivity;
import project.mosis.volunteerneeded.data.FriendsData;
import project.mosis.volunteerneeded.data.VolunteerEventsData;
import project.mosis.volunteerneeded.data.DataLoader;
import project.mosis.volunteerneeded.data.LocalMemoryManager;
import project.mosis.volunteerneeded.search_events.SearchActivity;
import project.mosis.volunteerneeded.service.VolunteerService;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, DataListener, MarkersContainer {


    private GoogleMap googleMap;
    private HashMap<Marker, VolunteerEvent> markerEventMap;
    private HashMap<Marker, Friend> markerFriendMap;
    private ArrayList<Friend> friends;
    private ArrayList<VolunteerEvent> events;
    private static Location mCurrentLocation;

    public LocationManager locationManager;
    public LocationUpdateListener listener;

    private String username;

    private LatLng searchEventLoc;

    private Menu menu;
    private Boolean serviceStarted = false;

    private static final int EVENT_LOCATION = 555;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SupportMapFragment mapFragment = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);

        //observer
        username = LocalMemoryManager.getUsername(this);
        DataLoader dataLoader = new DataLoader(this, username);
        dataLoader.addDataListener(this);
        dataLoader.execute();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        this.menu = menu;

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
        else if(id == R.id.add_friend_item)
        {
            new AlertDialog.Builder(this)
                    .setTitle("Add Friend")
                    .setMessage("To add friend you can listen for requests that other send and accept it or you" +
                            " can ask your friend to listen and by typing his device add him. So do you want to be a listener?!")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                            Intent intent = new Intent(MainActivity.this, ListActivity.class);
                            intent.putExtra("Listener", true);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                            Intent intent = new Intent(MainActivity.this, ListActivity.class);
                            intent.putExtra("Listener", false);
                            startActivity(intent);
                        }
                    })
                    .show();


        }
        else if(id == R.id.search_item)
        {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivityForResult(intent,EVENT_LOCATION );
        }
        else if(id == R.id.start_service)
        {
            MenuItem serviceItem = menu.findItem(R.id.start_service);
            if(serviceItem.getTitle().equals("Stop Service"))
                stopServices();
            else
                startServices();


            if(serviceStarted)
                serviceItem.setTitle("Stop Service");
            else
                serviceItem.setTitle("Start Service");

        }
        else if(id == R.id.friends_item)
        {
            Intent intent = new Intent(MainActivity.this, FriendsActivity.class);
            startActivity( intent);
        }

        return super.onOptionsItemSelected(item);
    }


    /*
        After updating friends in FriendsData
        updating their locations on map
     */
    public void updateFriendMarkers()
    {
        FriendsData fdata = FriendsData.getInstance();

        Iterator<Marker> iterator = markerFriendMap.keySet().iterator();

        while(iterator.hasNext())
        {
           Marker friendMarker =  iterator.next();
           Friend friend = fdata.findFriendByName(friendMarker.getTitle());
            if(friend != null)
                friendMarker.setPosition(friend.getLatLng());
        }

    }


    @Override
    public void onMapReady(GoogleMap map) {

        googleMap = map;
        setUpMap();

        //addVolunteerEventMarkers();
        //addFriendPositionsOnMap();

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

    //startuje aktiviti za dodavanje event-a
    private void makeVolunteerCall(LatLng latLng)
    {
        Double lon = latLng.longitude;
        Double lat = latLng.latitude;
        Intent makeVolunteerEventIntent = new Intent(this, VolunteerEventActivity.class);
        makeVolunteerEventIntent.putExtra("lon", lon);
        makeVolunteerEventIntent.putExtra("lat", lat);
        startActivity(makeVolunteerEventIntent);
    }


    private void setUpMap(){
            // Enable MyLocation Layer of Google Map
            googleMap.setMyLocationEnabled(true);

            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

            googleMap.setInfoWindowAdapter(new EventInfoWindowAdapter(this, this));
    }



    private void addVolunteerEventMarkers()
    {
        for (int i=0;i<events.size();i++)
        {
            VolunteerEvent e = events.get(i);
            String lat = e.getLatitude();
            String lon = e.getLongitude();
            LatLng loc = new LatLng(Double.parseDouble(lat),Double.parseDouble(lon));

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(loc);
            //TODO: Promeni ikonu
           // Bitmap eventBmp = VolunteerEventsData.getInstance().getEventByName(event.getDescription()).getImage();
          //  Bitmap resizedBmp = Bitmap.createScaledBitmap(eventBmp, 60, 60, false);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.event_icon));

            markerOptions.title(e.getTitle());
            markerOptions.snippet(e.getDescription());

            Marker marker = googleMap.addMarker(markerOptions);
            markerEventMap.put(marker,e);

        }
    }

    private void addFriendPositionsOnMap()
    {
        for (int i=0;i<friends.size();i++)
        {
            Friend f = friends.get(i);
            LatLng loc = f.getLatLng();

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(loc);

            //postavlja sliku prijatelja
            Bitmap friendBmp = f.getImage();
            Bitmap resizedBmp = Bitmap.createScaledBitmap(friendBmp, 60, 60, false);
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(resizedBmp));

            markerOptions.title(f.getName());
            markerOptions.snippet(f.getDescription());

            Marker marker = googleMap.addMarker(markerOptions);
            markerFriendMap.put(marker,f);


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == EVENT_LOCATION && resultCode ==  RESULT_OK && data!=null)
        {
            double latitude = Double.valueOf(data.getStringExtra("lat"));
            double longitude = Double.valueOf(data.getStringExtra("lon"));


            searchEventLoc = new LatLng(latitude,longitude);

            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(searchEventLoc, 17);
            googleMap.animateCamera(cameraUpdate);
        }

    }

    @Override
    public void onDataReady() {

        //set data
        friends = FriendsData.getInstance().getPeople();
        markerFriendMap = new HashMap<Marker, Friend>((int)((double)friends.size()*1.2));

        events = VolunteerEventsData.getInstance().getVolunteerEvents();
        markerEventMap = new HashMap<Marker, VolunteerEvent>((int)((double)events.size()*1.2));

        //add markers
        addVolunteerEventMarkers();
        addFriendPositionsOnMap();

        //VolunteerEventsData.getInstance().findEvents(5000,null,0);


    }

    public void startServices()
    {
        Intent intent = new Intent(this, VolunteerService.class);

        if(mCurrentLocation != null){
            intent.putExtra("lat", mCurrentLocation.getLatitude());
            intent.putExtra("lon", mCurrentLocation.getLongitude());
            intent.putExtra("username", username);


            startService(intent);
            serviceStarted = true;
        }

    }

    public void stopServices()
    {
        Intent intent = new Intent(this, VolunteerService.class);
        getApplicationContext().stopService(intent);
        serviceStarted= false;
    }



    public VolunteerEvent getEventByMarker(Marker m){
        return (VolunteerEvent) markerEventMap.get(m);
    }

    public Friend getFriendByMerker(Marker m){
        return (Friend) markerFriendMap.get(m);
    }


    public static Location getMyCurrentLocation(){
        return  mCurrentLocation;
    }

    class LocationUpdateListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            // TODO Auto-generated method stub
            //Toast.makeText(MainActivity.this, "Location Changed!", Toast.LENGTH_SHORT).show();
            mCurrentLocation = location;
            // update your marker here
           // Toast.makeText(MainActivity.this,"Location updated...", Toast.LENGTH_LONG).show();
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


