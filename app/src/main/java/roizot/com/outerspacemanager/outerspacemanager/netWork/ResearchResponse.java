package roizot.com.outerspacemanager.outerspacemanager.netWork;

import java.util.ArrayList;

import roizot.com.outerspacemanager.outerspacemanager.models.Building;
import roizot.com.outerspacemanager.outerspacemanager.models.Research;

/**
 * Created by mac4 on 14/03/2017.
 */

public class ResearchResponse {

    private int size;
    private ArrayList<Research> searches;

    public ArrayList<Research> getResearches() {
        return searches;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        String retour = "";

        for (Research item : searches) {
            retour += item.getName() + ", ";
        }

        return retour;
    }
}
