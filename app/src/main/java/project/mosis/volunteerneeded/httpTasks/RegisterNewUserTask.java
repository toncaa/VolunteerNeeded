package project.mosis.volunteerneeded.httpTasks;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

import project.mosis.volunteerneeded.VolunteerHTTPHelper;

/**
 * Created by Nikola on 19-Jun-16.
 */
public class RegisterNewUserTask implements Callable<String> {
    private String username;
    private String password;
    private String name;
    private String phone;
    private Bitmap img;

    String retStr;

    public RegisterNewUserTask(String username, String password, String name, String phone, Bitmap img){
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.img = img;
    }


    @Override
    public String call() {
        try {
            URL url = new URL(VolunteerHTTPHelper.SERVER_URL + "/registerNewUser");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            JSONObject data = new JSONObject();
            data.put("username",username);
            data.put("password",password);
            data.put("name",name);
            data.put("phone",phone);
            data.put("img",VolunteerHTTPHelper.bitmapToString(img));

            //send data
            conn.setConnectTimeout(1000);
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(data.toString());
            writer.flush();
            writer.close();
            os.close();

            //receive response
            int responseCode = conn.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                retStr = VolunteerHTTPHelper.inputStreamToString(conn.getInputStream());
            }
            else {
                retStr = String.valueOf("Error:" + responseCode);
            }
        }catch (Exception error){
            error.printStackTrace();
            retStr = "Error: Exception occured!";
        }

        return retStr;
    }
}
