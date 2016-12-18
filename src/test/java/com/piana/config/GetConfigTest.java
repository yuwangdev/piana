package com.piana.config;

import org.junit.Test;

/**
 * Created by yuwang on 10/23/16.
 */
public class GetConfigTest {


    @Test
    public void testdevEnv() {
        final String fileName = "/Users/yuwang/Documents/pianaEnv_dev.config";
        ParseEnvironmentConfig getConfig = new ParseEnvironmentConfig(fileName);
        System.out.println(getConfig.getUsername());
        System.out.println(getConfig.getPassword());
        System.out.println(getConfig.getCourses());
        System.out.println(getConfig.getPiazzaLogicUrl());
        System.out.println(getConfig.getPiazzaMainUrl());
        System.out.println(getConfig.getEnv());

    }


}