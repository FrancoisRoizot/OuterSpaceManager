package roizot.com.outerspacemanager.outerspacemanager.models;

/**
 * Created by mac4 on 07/03/2017.
 */

public class UserInfos {

    private double gas;
    private double gasModifier;
    private double minerals;
    private double mineralsModifier;
    private int points;
    private String username;


    public String getUsername() {
        return username;
    }

    public double getGas() {
        return gas;
    }

    public double getGasModifier() {
        return gasModifier;
    }

    public double getMinerals() {
        return minerals;
    }

    public double getMineralsModifier() {
        return mineralsModifier;
    }

    public int getPoints() {
        return points;
    }
}
