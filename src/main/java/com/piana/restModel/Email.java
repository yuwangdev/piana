package com.piana.restModel;

/**
 * Created by yuwang on 12/4/16.
 */
public class Email {

    private String address;
    private String title;

    public Email() {
    }

    public Email(String address, String title) {
        this.address = address;
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Email{" +
                "title='" + address + '\'' +
                ", change='" + title + '\'' +
                '}';
    }
}
