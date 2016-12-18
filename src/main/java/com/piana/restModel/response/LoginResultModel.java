package com.piana.restModel.response;

/**
 * Created by yuwang on 10/29/16.
 */
public class LoginResultModel {

    private String status;
    private String username;
    private String timeStamp;

    public LoginResultModel() {
        super();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
