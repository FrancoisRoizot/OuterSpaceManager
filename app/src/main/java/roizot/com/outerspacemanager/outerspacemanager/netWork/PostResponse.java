package roizot.com.outerspacemanager.outerspacemanager.netWork;

/**
 * Created by mac4 on 13/03/2017.
 */

public class PostResponse {

    private String code;
    private String internalCode;
    private long attackTime;

    public String getInternalCode() {
        return internalCode;
    }

    public String getCode() {
        return code;
    }

    public long getAttackTime() { return attackTime; }
}
