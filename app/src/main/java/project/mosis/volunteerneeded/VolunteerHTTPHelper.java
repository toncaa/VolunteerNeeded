package project.mosis.volunteerneeded;

import android.net.Uri;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by MilanToncic on 6/5/2016.
 */
public class VolunteerHTTPHelper {

    private static final String POST_NEW_PLACE="1";
    private static final String GET_ALL_EVENTS="2";


    public static String addNewVolunteerEvent(VolunteerEvent vEvent)
    {
        String retStr;
        try{
            URL url =  new URL("http://10.0.2.2:8080");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setReadTimeout(10000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            // Ono sto saljemo
            JSONObject holder = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("name", vEvent.getName());
            data.put("desc", vEvent.getDescription());
            data.put("lon", vEvent.getLongitude());
            data.put("lat", vEvent.getLatitude());
            holder.put("vEvent", data);
            ///////////////////////////////

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("req", POST_NEW_PLACE)
                    .appendQueryParameter("payload", holder.toString());//ono sto saljemo
            String query = builder.build().getEncodedQuery();

            conn.setConnectTimeout(1000);
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                retStr = inputStreamToString(conn.getInputStream());
            }
            else {
                retStr = String.valueOf("Error:" + responseCode);
            }
        }
        catch (Exception error){
            error.printStackTrace();
            retStr = "Error durring upload!";
        }

        return retStr;
    }

    private static String inputStreamToString(InputStream is){
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


}
