package com.piana.core;

/**
 * Created by yuwang on 10/22/16.
 */
public class Account {

    private String username;
    private int age;

    public Account(String username, int age) {
        this.username = username;
        this.age = age;
    }

    /**
     * @return
     */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}


