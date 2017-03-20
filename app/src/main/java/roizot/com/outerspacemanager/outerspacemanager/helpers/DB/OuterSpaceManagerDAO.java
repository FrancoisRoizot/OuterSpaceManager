package roizot.com.outerspacemanager.outerspacemanager.helpers.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by mac4 on 20/03/2017.
 */

public class OuterSpaceManagerDAO {

    // Database fields
    private SQLiteDatabase database;
    private OuterSpaceManagerDB dbHelper;
    private String[] allColumnsBuildings = { OuterSpaceManagerDB.KEY_BUILDING_ID,OuterSpaceManagerDB.KEY_BUILDING_TIME};
    private String[] allColumnsResearch = { OuterSpaceManagerDB.KEY_RESEARCH_ID,OuterSpaceManagerDB.KEY_RESEARCH_TIME};

    public OuterSpaceManagerDAO(Context context) {
        dbHelper = new OuterSpaceManagerDB(context);
    }
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();
    }

    public BuildingDB addBuildingRunning(long buildingTime, int id) {
        ContentValues values = new ContentValues();
        values.put(OuterSpaceManagerDB.KEY_BUILDING_ID, id);
        values.put(OuterSpaceManagerDB.KEY_BUILDING_TIME, buildingTime);
        database.insert(OuterSpaceManagerDB.BUILDING_TABLE_NAME, null, values);
        Cursor cursor = database.query(OuterSpaceManagerDB.BUILDING_TABLE_NAME, allColumnsBuildings, OuterSpaceManagerDB.KEY_BUILDING_ID + " = " + id , null, null, null, null);
        cursor.moveToFirst();
        BuildingDB newBuilding = cursorToBuilding(cursor);
        cursor.close();
        return newBuilding;
    }

    public ArrayList<BuildingDB> getBuildingRunning(int id) {
        ArrayList<BuildingDB> buildings = new ArrayList<>();
        Cursor cursor = database.query(OuterSpaceManagerDB.BUILDING_TABLE_NAME, allColumnsBuildings, OuterSpaceManagerDB.KEY_BUILDING_ID + " = " + id, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            BuildingDB building = cursorToBuilding(cursor);
            buildings.add(building);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return buildings;
    }
    private BuildingDB cursorToBuilding(Cursor cursor) {
        BuildingDB building = new BuildingDB();
        building.setBuildingId(cursor.getInt(0));
        building.setFinishDate(cursor.getLong(1));
        return building;
    }

    public void deleteBuildings() {
        database.delete(OuterSpaceManagerDB.BUILDING_TABLE_NAME, OuterSpaceManagerDB.KEY_BUILDING_TIME + " < " + System.currentTimeMillis(), null);
    }

    public ResearchDB addResearchRunning(long researchTime, int id) {
        ContentValues values = new ContentValues();
        values.put(OuterSpaceManagerDB.KEY_RESEARCH_ID, id);
        values.put(OuterSpaceManagerDB.KEY_RESEARCH_TIME, researchTime);
        database.insert(OuterSpaceManagerDB.RESEARCH_TABLE_NAME, null, values);
        Cursor cursor = database.query(OuterSpaceManagerDB.RESEARCH_TABLE_NAME, allColumnsResearch, OuterSpaceManagerDB.KEY_RESEARCH_ID + " = " + id , null, null, null, null);
        cursor.moveToFirst();
        ResearchDB newResearch = cursorToResearch(cursor);
        cursor.close();
        return newResearch;
    }

    public ArrayList<ResearchDB> getResearchRunning(int id) {
        ArrayList<ResearchDB> researchs = new ArrayList<>();
        Cursor cursor = database.query(OuterSpaceManagerDB.RESEARCH_TABLE_NAME, allColumnsResearch, OuterSpaceManagerDB.KEY_RESEARCH_ID + " = " + id, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ResearchDB research = cursorToResearch(cursor);
            researchs.add(research);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return researchs;
    }

    private ResearchDB cursorToResearch(Cursor cursor) {
        ResearchDB research = new ResearchDB();
        research.setResearchId(cursor.getInt(0));
        research.setFinishDate(cursor.getLong(1));
        return research;
    }

    public void deleteResearchs() {
        database.delete(OuterSpaceManagerDB.RESEARCH_TABLE_NAME, OuterSpaceManagerDB.KEY_RESEARCH_TIME + " < " + System.currentTimeMillis(), null);
    }
}
