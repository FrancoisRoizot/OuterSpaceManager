package roizot.com.outerspacemanager.outerspacemanager.helpers;

 import android.Manifest;
 import android.app.Activity;
 import android.content.Context;
 import android.content.SharedPreferences;
 import android.content.pm.PackageManager;
 import android.support.v4.app.ActivityCompat;
 import android.support.v4.content.ContextCompat;

 import java.util.concurrent.TimeUnit;

/**
 * Created by mac4 on 13/03/2017.
 */

public class Config {

    private static final String PREFS_NAME = "OGamePrefs";

    private static boolean DBRights = false;

    public static boolean isDBRights() {
        return DBRights;
    }

    public static void setDBRights(boolean DBRights) {
        Config.DBRights = DBRights;
    }

    public static String getToken(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Config.PREFS_NAME, 0);
        String token = settings.getString("token", "");

        return token;
    }

    public static void setToken(Context context, String sToken) {
        SharedPreferences settings = context.getSharedPreferences(Config.PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("token", sToken);
        editor.commit();
    }

    public static void deleteToken(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Config.PREFS_NAME, 0);
        settings.edit().remove("token").commit();
    }

    public static String formatTime(long millisecs) {
        return String.format("%02dh %02dm %02ds", TimeUnit.MILLISECONDS.toHours(millisecs),
                TimeUnit.MILLISECONDS.toMinutes(millisecs) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisecs)),
                TimeUnit.MILLISECONDS.toSeconds(millisecs) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisecs)));
    }
}
