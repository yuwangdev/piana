package com.piana.restModel.request;

/**
 * Created by yuwang on 11/7/16.
 */
public class DiffTimeDataRequestModel {

    private String timeStamp1;
    private String timeStamp2;
    private String target;

    public DiffTimeDataRequestModel() {

    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTimeStamp1() {
        return timeStamp1;
    }

    public void setTimeStamp1(String timeStamp1) {
        this.timeStamp1 = timeStamp1;
    }

    public String getTimeStamp2() {
        return timeStamp2;
    }

    public void setTimeStamp2(String timeStamp2) {
        this.timeStamp2 = timeStamp2;
    }
}
