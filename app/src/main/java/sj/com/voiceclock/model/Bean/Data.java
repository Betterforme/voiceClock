package sj.com.voiceclock.model.Bean;

/**
 * Created by SJ on 2017/7/23.
 */

public class Data {

    /**
     * resultCode : 102
     * resultObject : 抱歉!请先注册
     * total :
     */

    private String resultCode;
    private Object resultObject;
    private String total;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public Object getResultObject() {
        return resultObject;
    }

    public void setResultObject(Object resultObject) {
        this.resultObject = resultObject;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
