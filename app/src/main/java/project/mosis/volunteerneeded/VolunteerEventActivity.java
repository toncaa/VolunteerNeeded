package project.mosis.volunteerneeded;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VolunteerEventActivity extends AppCompatActivity {


    public static final int GET_FROM_GALLERY = 3;
    public static final int CAMERA_REQUEST = 4;


    private ImageView userImageView;
    private View cameraView;
    private EditText titleView, descView;
    private NumberPicker numPicker;
    private Button postNewEvent;
    private String lon, lat;
    private Bitmap eventPhoto;

    //////////////
    Handler guiThread;
    Context context;
    ProgressDialog progressDialog;

    ////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_event);

        userImageView = (ImageView)findViewById(R.id.image_btn);
        userImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }
        });

        cameraView = findViewById(R.id.camera_option);
        cameraView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });


        numPicker = (NumberPicker) findViewById(R.id.number_picker);
        numPicker.setMinValue(0);
        numPicker.setMaxValue(30);

        postNewEvent = (Button) findViewById(R.id.post_new_event);
        postNewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExecutorService transThread = Executors.newSingleThreadExecutor();
                transThread.submit(new Runnable() {
                    @Override
                    public void run() {


                        guiStartProgressDialog("Sending new event", "Sending "+titleView.getText());

                        try{
                            final String message =  addNewVolunteerEvent();
                            guiNotifyUser(message);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        guiDismissProgressDialog();
                    }
                });
            }
        });

        titleView = (EditText) findViewById(R.id.title);
        descView = (EditText) findViewById(R.id.desc);

        Intent intent = getIntent();
        lon = intent.getStringExtra("lon");
        lat = intent.getStringExtra("lat");

        guiThread = new Handler();
        context = this;
        progressDialog = new ProgressDialog(this);
    }

    //dodaje event
    private String addNewVolunteerEvent()
    {
         String title = titleView.getText().toString();
         String desc = descView.getText().toString();
         int numOfVolunteers = numPicker.getValue();

         String organizer = LocalMemoryManager.getUsername(context);
         VolunteerEvent vEvent = new VolunteerEvent(organizer,title,lon,lat,desc,numOfVolunteers,eventPhoto);


         // Toast.makeText(this,"New Volunteer Event added!",Toast.LENGTH_LONG).show();
         return VolunteerHTTPHelper.addEvent(vEvent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                eventPhoto = bitmap;
                userImageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else if(requestCode==CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            userImageView.setImageBitmap(photo);
            eventPhoto = photo;
        }
    }



    private void guiDismissProgressDialog() {
        guiThread.post(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        });
    }

    private void guiNotifyUser(final String message) {
        guiThread.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context,message,Toast.LENGTH_LONG).show();
            }
        });
    }

    private void guiStartProgressDialog(final String title, final String message) {

        guiThread.post(new Runnable() {
            @Override
            public void run() {
                progressDialog.setTitle(title);
                progressDialog.setMessage(message);
                progressDialog.show();
            }
        });
    }


    /**
     * Represents an asynchronous addEvent task used to authenticate
     * the user.
     */
    private class AddEventTask extends AsyncTask<Void, Void, Boolean> {

        private VolunteerEvent event;

        AddEventTask(VolunteerEvent event) {
            this.event = event;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(context,getString(R.string.adding_event),"Pleas wait", true);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            String serverResponse = VolunteerHTTPHelper.addEvent(event);

            try {
                String resMsg = (new JSONObject(serverResponse)).getString("msg");
                if(!resMsg.equals("OK")) {
                    return false;
                }else{
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }

            //return value is parameter for onPostEecute method
            // TODO: register the new account here.
            //  return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            progressDialog.dismiss();
            if (success) {
                //TODO:reload markers
                finish();
                startMainAcivity();
            } else {

            }
        }

        @Override
        protected void onCancelled() {
            progressDialog.dismiss();
        }


        private void startMainAcivity(){
            Intent i = new Intent(context, MainActivity.class);
            startActivity(i);
        }
    }
}
