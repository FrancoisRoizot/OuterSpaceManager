package roizot.com.outerspacemanager.outerspacemanager.helpers.db;

/**
 * Created by mac4 on 20/03/2017.
 */

public class BuildingDB {

    private int buildingId;
    private long finishDate;

    public int getBuildingId() {
        return buildingId;
    }

    public long getFinishDate() {
        return finishDate;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public void setFinishDate(long finishDate) {
        this.finishDate = finishDate;
    }
}
