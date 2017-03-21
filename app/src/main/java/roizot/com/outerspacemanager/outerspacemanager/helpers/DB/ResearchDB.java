package roizot.com.outerspacemanager.outerspacemanager.helpers.db;

/**
 * Created by mac4 on 20/03/2017.
 */

public class ResearchDB {

    private int researchId;
    private long finishDate;

    public int getResearchId() {
        return researchId;
    }

    public long getFinishDate() {
        return finishDate;
    }

    public void setResearchId(int researchId) {
        this.researchId = researchId;
    }

    public void setFinishDate(long finishDate) {
        this.finishDate = finishDate;
    }
}
