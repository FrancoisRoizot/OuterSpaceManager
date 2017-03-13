package roizot.com.outerspacemanager.outerspacemanager.helpers;

 import android.content.Context;
 import android.content.SharedPreferences;

/**
 * Created by mac4 on 13/03/2017.
 */

public class Config {

    public static final String PREFS_NAME = "OGamePrefs";

    public static String getToken(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Config.PREFS_NAME, 0);
        String token = settings.getString("token", "");

        return token;
    }

    public static final void setToken(Context context, String sToken) {
        SharedPreferences settings = context.getSharedPreferences(Config.PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("token", sToken);
        editor.commit();
    }

    public static final void deleteToken(Context context) {
        SharedPreferences settings = context.getSharedPreferences(Config.PREFS_NAME, 0);
        settings.edit().remove("token").commit();
    }
}
