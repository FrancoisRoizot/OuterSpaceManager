package roizot.com.outerspacemanager.outerspacemanager.models;

/**
 * Created by roizotf on 21/03/2017.
 */
public class Ship {

    private int gasCost;
    private int life;
    private int maxAttack;
    private int minAttack;
    private int mineralCost;
    private String name;
    private int shipId;
    private int shield;
    private int spatioportLevelNeeded;
    private int speed;
    private int timeToBuild;

    public int getGasCost() { return gasCost; }

    public int getLife() { return life; }

    public int getMaxAttack() { return maxAttack; }

    public int getMinAttack() { return minAttack; }

    public int getMineralCost() { return mineralCost; }

    public String getName() { return name; }

    public int getShipId() { return shipId; }

    public int getShield() { return shield; }

    public int getSpatioportLevelNeeded() { return spatioportLevelNeeded; }

    public int getSpeed() { return speed; }

    public int getTimeToBuild() { return timeToBuild; }
}
