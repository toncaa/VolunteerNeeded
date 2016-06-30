package project.mosis.volunteerneeded;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;



public class RegisterActivity extends AppCompatActivity {

    Context context;
    public static final int GET_FROM_GALLERY = 3;
    public static final int CAMERA_REQUEST = 4;

    private ImageView userImageView;
    private View cameraView;
    private EditText usernameEText;
    private EditText passwordEText;
    private EditText nameEText;
    private EditText phoneEText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        context = this;
        usernameEText = (EditText) findViewById(R.id.username);
        passwordEText = (EditText) findViewById(R.id.password);
        nameEText = (EditText) findViewById(R.id.fullname);
        phoneEText = (EditText) findViewById(R.id.phone);

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

        //when user click on Register button
        Button regBtn = (Button) findViewById(R.id.register);
        assert regBtn != null;
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = usernameEText.getText().toString();
                final String password = passwordEText.getText().toString();
                final String name = nameEText.getText().toString();
                final String phone = phoneEText.getText().toString();
                final Bitmap img = ((BitmapDrawable)userImageView.getDrawable()).getBitmap();

                //create AsyncTaks cause it can not call asynchounus method from ui thread
                new AsyncTask<Void, Void, String>() {
                    protected String doInBackground(Void... params) {
                        String errorMsg = VolunteerHTTPHelper.registerNewUser(username,password,name,phone,img);
                        //return value is parameter for onPostEecute method
                        return errorMsg;
                    }
                    protected void onPostExecute(String serverResponse) {
                            try {
                                String resMsg = (new JSONObject(serverResponse)).getString("msg");
                                if(!resMsg.equals("OK")) {
                                    Toast.makeText(context,resMsg,Toast.LENGTH_LONG).show();
                                }else{
                                    onRegistrationSucceed();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(context, "Received invalid message from server", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }

                    }
                }.execute();
            }
        });
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

        }
    }

    private void onRegistrationSucceed(){
        finish();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
