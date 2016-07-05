package project.mosis.volunteerneeded.service;


import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import project.mosis.volunteerneeded.HighscoreActivity;
import project.mosis.volunteerneeded.MainActivity;
import project.mosis.volunteerneeded.R;
import project.mosis.volunteerneeded.VolunteerHTTPHelper;
import project.mosis.volunteerneeded.entities.VolunteerEvent;
import project.mosis.volunteerneeded.search_events.VolunteerEventDetailActivity;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class VolunteerService extends IntentService {

    private static final String TAG = VolunteerService.class.getSimpleName();
    public static final int NOTIFICATION_ID = 234;

    private Boolean serviceRunning = false;
    private Handler mHandler;

    private Object lock = new Object();

    public VolunteerService() {
        super("VolunteerService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        //onStartCommand se izvršava u Main Threadu
        //handler koristimo da ono što je odrađeno u pozadini gurnemo na main thread GUI
        mHandler = new Handler();
        serviceRunning = true;

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {

        Toast.makeText(getApplicationContext(), "onDestroy called..." , Toast.LENGTH_SHORT).show();
        mHandler.removeCallbacksAndMessages(null);
        serviceRunning = false;
        //super.onDestroy();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //ovo se poziva kada servis dobije start request


        while(serviceRunning)
        {
                synchronized(lock) {
                    try {
                        lock.wait(15000);
                        sendUserLocationToServer(intent);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


        }

    }

    public void sendUserLocationToServer(Intent intent)
    {
        final Double lon = intent.getDoubleExtra("lon",0);
        final Double lat = intent.getDoubleExtra("lat",0);
        final String username = intent.getStringExtra("username");

        VolunteerEvent volunteerEvent = VolunteerHTTPHelper.updateMyLocation(username,lat, lon);
        if(volunteerEvent != null)
            createNotification(volunteerEvent);
        else
        {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Nothing still..." , Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public void createNotification(VolunteerEvent volunteerEvent){

        //drugi parametar je gde će korisnik biti odveden kada klikne na notifikaciju
        Intent intent = new Intent(this, VolunteerEventDetailActivity.class);
        intent.putExtra("title", volunteerEvent.getTitle());

        //taskstackbuilder radi sa stakovima, tako se ređaju aktivitiji
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addParentStack(MainActivity.class);
        taskStackBuilder.addNextIntent(intent);

        //pending intent je onaj koji će se kasnije izvršiti
        PendingIntent pendingIntent = taskStackBuilder.
                getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        //creating notification object
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Event added near you!")
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);

    }
}