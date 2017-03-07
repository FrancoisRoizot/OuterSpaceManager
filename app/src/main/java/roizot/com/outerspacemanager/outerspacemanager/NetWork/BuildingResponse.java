package roizot.com.outerspacemanager.outerspacemanager.NetWork;

import java.util.ArrayList;

import roizot.com.outerspacemanager.outerspacemanager.Models.Building;

/**
 * Created by mac4 on 07/03/2017.
 */

public class BuildingResponse {

    private int size;
    private ArrayList<Building> buildings;

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        String retour = "";

        for (Building item : buildings) {
            retour += item.getName() + ", ";
        }

        return retour;
    }
}
