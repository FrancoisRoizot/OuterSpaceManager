package roizot.com.outerspacemanager.outerspacemanager.netWork;

import java.util.ArrayList;

import roizot.com.outerspacemanager.outerspacemanager.models.Ship;
import roizot.com.outerspacemanager.outerspacemanager.models.UserInfos;

/**
 * Created by roizotf on 21/03/2017.
 */
public class ShipsResponse {

    private double currentUserMinerals;
    private double currentUserGas;
    private int size;
    private ArrayList<Ship> ships;

    public double getCurrentUserMinerals() { return currentUserMinerals; }

    public double getCurrentUserGas() { return currentUserGas; }

    public int getSize() { return size; }

    public ArrayList<Ship> getShips() { return ships; }

    @Override
    public String toString() {
        String str = "";
        for (Ship ship : ships) {
            str += ship.getName() + ", ";
        }
        return str;
    }
}
