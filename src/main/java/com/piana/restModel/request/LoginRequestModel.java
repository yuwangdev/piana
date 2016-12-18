package com.piana.restModel.request;

/**
 * Created by yuwang on 10/30/16.
 */
public class LoginRequestModel {

    private String username;
    private String password;

    public LoginRequestModel() {
        super();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
