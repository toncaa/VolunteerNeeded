package project.mosis.volunteerneeded.data;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.CalendarContract;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import project.mosis.volunteerneeded.DataListener;
import project.mosis.volunteerneeded.entities.Friend;
import project.mosis.volunteerneeded.entities.VolunteerEvent;
import project.mosis.volunteerneeded.data.VolunteerEventsData;
import project.mosis.volunteerneeded.VolunteerHTTPHelper;


/**
 * Created by Nikola on 02-Jul-16.
 *
 * This class will download friends pictures and cache them.
 */

public class DataLoader extends AsyncTask<Void, Void, Void> {
    private ArrayList<DataListener> dataListeners = new ArrayList<>();

    private static final String TAG = "Greska";
    private boolean isReady = false;
    private Context context;
    private String errorMessage;
    private String username;
    private ArrayList<Bitmap> friends;
    private static final String VOLUNTEER_IMAGES_LOCATION = "http://192.168.137.1:3000/users_images/";
    private static final String EVENTS_IMAGES_LOCATION = "http://192.168.137.1:3000/events_images/";
    private static final String IMAGES_EXTENSION = ".jpeg";

    public DataLoader(Context context, String username) {
        this.context = context;
        this.username = username;
    }

    @Override
    protected Void doInBackground(Void... params) {
        //load events data without images
        VolunteerEventsData eventsData = VolunteerEventsData.getInstance();
        eventsData.updateVolunteerEvents();
        //load friends data without images
        FriendsData friendsData = FriendsData.getInstance();
        friendsData.updateFriendsData(username);


        ArrayList<Friend> friends = friendsData.getPeople();
        ArrayList<VolunteerEvent> events = eventsData.getVolunteerEvents();


        for(int i=0; i<friends.size(); i++) {
            setFriendBitmap(friends.get(i));
        }

        for(int i=0; i<events.size(); i++){
            setEventBitmap(events.get(i));
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        isReady = true;
        norify();
    }


    public void addDataListener(DataListener dataListener){
        this.dataListeners.add(dataListener);
    }

    public void norify(){
        for(int i=0; i<dataListeners.size();i++)
            dataListeners.get(i).onDataReady();
    }


    private void setFriendBitmap(Friend p){
        String friendBitmapName = p.getUsername() + IMAGES_EXTENSION;

        Bitmap friendBmp;
        //check local storage or download image
        if(exist(friendBitmapName)) {
            friendBmp = loadBitmap(context, friendBitmapName);
        }else{
            friendBmp  = getBitmapFromURL(VOLUNTEER_IMAGES_LOCATION +  friendBitmapName);
        }
        //save to local storage
        saveToInternalStorage(context, friendBmp, friendBitmapName);
        //update object
        p.setImage(friendBmp);
    }

    private void setEventBitmap(VolunteerEvent e){
        String eventBitmapName = e.getImageUrl();

        Bitmap eventBmp;
        //check local storage or download image
        if(exist(eventBitmapName)) {
            eventBmp = loadBitmap(context, eventBitmapName);
        }else{
            eventBmp  = getBitmapFromURL(EVENTS_IMAGES_LOCATION + eventBitmapName);
        }
        //cahce it
        saveToInternalStorage(context, eventBmp, eventBitmapName);

        //update object
        e.setImage(eventBmp);
    }

    private Bitmap getBitmapFromURL(String src) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream((InputStream)new URL(src).getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private String saveToInternalStorage(Context context, Bitmap b, String picName){
        FileOutputStream fos;
        try {
            fos = context.openFileOutput(picName, Context.MODE_PRIVATE);
            b.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        }
        catch (FileNotFoundException e) {
            Log.d(TAG, "file not found");
            e.printStackTrace();
        }
        catch (IOException e) {
            Log.d(TAG, "io exception");
            e.printStackTrace();
        }

        if(exist(picName)){
            return "POSTOJI";
        }

        Bitmap loaded = loadBitmap(context,picName);
        return "OK";
    }

    public static Bitmap loadBitmap(Context context, String picName){
        Bitmap b = null;
        FileInputStream fis;
        try {
            fis = context.openFileInput(picName);
            b = BitmapFactory.decodeStream(fis);
            fis.close();

        }
        catch (FileNotFoundException e) {
            Log.d(TAG, "file not found");
            e.printStackTrace();
        }
        catch (IOException e) {
            Log.d(TAG, "io exception");
            e.printStackTrace();
        }
        return b;
    }

    private boolean exist(String filename){
        boolean exist = true;
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(filename);
            fis.close();
            exist = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            exist = false;
        } catch (IOException e) {
            e.printStackTrace();
            exist = true;
        }

//        if (exist)
//            return true;
//        else
//            return false;
        return false;
    }
}
