package roizot.com.outerspacemanager.outerspacemanager.models;

/**
 * Created by mac4 on 07/03/2017.
 */

public class Building {

    private int amountOfEffectByLevel;
    private int amountOfEffectLevel0;
    private boolean building;
    private String effect;
    private String effectAdded;
    private int level;
    private int gasCostByLevel;
    private int gasCostLevel0;
    private int mineralCostByLevel;
    private int mineralCostLevel0;
    private String name;
    private int timeToBuildByLevel;
    private int timeToBuildLevel0;

    public int getAmountOfEffectByLevel() {
        return amountOfEffectByLevel;
    }

    public int getAmountOfEffectLevel0() {
        return amountOfEffectLevel0;
    }

    public boolean isBuilding() {
        return building;
    }

    public String getEffect() {
        return effect;
    }

    public int getLevel() {
        return level;
    }

    public int getGasCostByLevel() {
        return gasCostByLevel;
    }

    public int getGasCostLevel0() {
        return gasCostLevel0;
    }

    public int getMineralCostByLevel() {
        return mineralCostByLevel;
    }

    public int getMineralCostLevel0() {
        return mineralCostLevel0;
    }

    public String getName() {
        return name;
    }

    public int getTimeToBuildByLevel() {
        return timeToBuildByLevel;
    }

    public int getTimeToBuildLevel0() {
        return timeToBuildLevel0;
    }
}
