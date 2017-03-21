package roizot.com.outerspacemanager.outerspacemanager.helpers.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

/**
 * Created by mac4 on 20/03/2017.
 */

public class OuterSpaceManagerDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "OuterSpaceManager.db";

    public static final String BUILDING_TABLE_NAME = "Building";
    public static final String KEY_BUILDING_ID = "buildingId";
    public static final String KEY_BUILDING_TIME = "timeToBuild";
    private static final String BUILDING_TABLE_CREATE = "CREATE TABLE " + BUILDING_TABLE_NAME + " (" + KEY_BUILDING_ID + " INTEGER, " + KEY_BUILDING_TIME + " INTEGER);";

    public static final String RESEARCH_TABLE_NAME = "Research";
    public static final String KEY_RESEARCH_ID = "researchId";
    public static final String KEY_RESEARCH_TIME = "timeToSearch";
    private static final String RESEARCH_TABLE_CREATE = "CREATE TABLE " + RESEARCH_TABLE_NAME + " (" + KEY_RESEARCH_ID + " INTEGER, " + KEY_RESEARCH_TIME + " INTEGER);";

    public OuterSpaceManagerDB(Context context) {
        super(context, Environment.getExternalStorageDirectory()+"/"+DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BUILDING_TABLE_CREATE);
        db.execSQL(RESEARCH_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BUILDING_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + RESEARCH_TABLE_NAME);
        onCreate(db);
    }
}
