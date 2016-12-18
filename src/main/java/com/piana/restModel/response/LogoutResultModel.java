package com.piana.restModel.response;

/**
 * Created by yuwang on 10/30/16.
 */
public class LogoutResultModel {

    private String status;
    private String timeStamp;

    public LogoutResultModel() {
        super();
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
