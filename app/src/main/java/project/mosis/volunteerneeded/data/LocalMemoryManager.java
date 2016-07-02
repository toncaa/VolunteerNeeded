package project.mosis.volunteerneeded.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import project.mosis.volunteerneeded.R;

/**
 * Created by Nikola on 25-Jun-16.
 */
public class LocalMemoryManager {

    public static void saveUsername(Context context, String username){
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(context.getString(R.string.sp_username), username);
        editor.commit();
    }

    public static String getUsername(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        return sharedPref.getString(context.getString(R.string.sp_username), "defUsername");
    }

    public static Bitmap loadBitmap(Context context, String imageUri){
        return null;
    }


}
