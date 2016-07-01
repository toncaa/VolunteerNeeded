package project.mosis.volunteerneeded;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by MilanToncic on 6/5/2016.
 */
public class VolunteerHTTPHelper {

    public static final String SERVER_URL ="http://192.168.0.108:3000";
    private static final int CONNECTION_TIMEOUT = 5000;

    public static String registerNewUser(String username, String password, String name, String phone, Bitmap img){
        String retStr;
        try {
            URL url = new URL(SERVER_URL + "/registerNewUser");
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
            data.put("img",bitmapToString(img));

            //send data
            conn.setConnectTimeout(CONNECTION_TIMEOUT);
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
                retStr = inputStreamToString(conn.getInputStream());
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

    public static String loginUser(String username, String password){
        String retStr;
        try {
            URL url = new URL(SERVER_URL + "/loginUser");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            JSONObject data = new JSONObject();
            data.put("username",username);
            data.put("password",password);

            //send data
            conn.setConnectTimeout(CONNECTION_TIMEOUT);
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
                retStr = inputStreamToString(conn.getInputStream());
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



    public static String addEvent(VolunteerEvent event){
        String retStr;
        try {
            URL url = new URL(SERVER_URL + "/addEvent");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setDoInput(true);
            conn.setDoOutput(true);

         //   JSONObject data = new JSONObject();
        //   data.put("vEvent", event.toJSON());

            JSONObject data = new JSONObject();
            data.put("organizer", event.getOrganizerUsername());
            data.put("title",event.getTitle());
            data.put("desc",event.getDescription());
            data.put("lat",event.getLatitude());
            data.put("lon",event.getLongitude());
            data.put("category",event.getCategory());
            data.put("volNeeded",event.getVolunteersNeeded());
            data.put("time",event.getTime());
            data.put("image",bitmapToString(event.getImage()));


            //send data
            conn.setConnectTimeout(CONNECTION_TIMEOUT);
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
                retStr = inputStreamToString(conn.getInputStream());
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



    public static ArrayList<VolunteerEvent> getVolunteerEventsData(){
        try {
            String response;
            HttpURLConnection conn = getConnection(SERVER_URL + "/getAllEvents");
            JSONObject data = new JSONObject();

            //send data
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(data.toString());
            writer.flush();
            writer.close();
            os.close();

            //receive response
            ArrayList<VolunteerEvent> allEvents = new ArrayList<VolunteerEvent>();
            int responseCode = conn.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                response = inputStreamToString(conn.getInputStream());
                //parse response
                JSONObject resObj = new JSONObject(response);
                JSONArray eventsArray = resObj.getJSONArray("data");
                for(int i=0; i<eventsArray.length(); i++){
                    JSONObject eventObj = eventsArray.getJSONObject(i);
                    VolunteerEvent e = new VolunteerEvent(
                            eventObj.getString("organizer"),
                            eventObj.getString("title"),
                            eventObj.getString("lon"),
                            eventObj.getString("lat"),
                            eventObj.getString("desc"),
                            eventObj.getString("time"),
                            eventObj.getString("category"),
                            eventObj.getInt("volunteerNeeded"),
                            eventObj.getString("image"));
                    allEvents.add(e);
                }


                return allEvents;
            }
        }catch (Exception error){
            error.printStackTrace();
            return null;
        }

        return null;
    }


    private static HttpURLConnection getConnection(String urlAddress) throws IOException {
        URL url = new URL(urlAddress);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setConnectTimeout(CONNECTION_TIMEOUT);
        return conn;
    }


    public static String inputStreamToString(InputStream is){
        String line = "";
        StringBuilder total = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        try{
            while((line = rd.readLine()) != null){
                total.append(line);
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        return total.toString();
    }

    public static String bitmapToString(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


}
