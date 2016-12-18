package com.piana.config;

import com.google.common.collect.Sets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Set;

/**
 * Created by yuwang on 10/23/16.
 */
public class ParseEnvironmentConfig {

    private String envConfigFilePath = "";

    private String env = "";
    private String username = "";
    private String password = "";
    private String piazzaLogicUrl = "";
    private String piazzaMainUrl = "";


    private Set<String> courses = Sets.newHashSet();

    public ParseEnvironmentConfig(String envConfigFilePath) {
        this.envConfigFilePath = envConfigFilePath;
        if (this.envConfigFilePath.contains("_dev")) {
            this.env = "dev";
        } else if (this.envConfigFilePath.contains("_qa")) {
            this.env = "qa";
        } else if (this.envConfigFilePath.contains("_prod")) {
            this.env = "prod";
        }
        loadEnvConfigFile();
    }

    private void loadEnvConfigFile() {

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(this.envConfigFilePath));
            for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {

                String[] temp = line.split("\\=");
                if (temp[0].equalsIgnoreCase("username")) {
                    this.username = temp[1].trim();
                } else if (temp[0].equalsIgnoreCase("password")) {
                    this.password = temp[1].trim();
                } else if (temp[0].equalsIgnoreCase("courses")) {
                    String stringTemp = temp[1].trim();
                    String[] stringTempArray = stringTemp.split(",");
                    for (int i = 0; i < stringTempArray.length; i++) {
                        this.courses.add(stringTempArray[i]);
                    }

                } else if (temp[0].equalsIgnoreCase("logicUrl")) {
                    this.piazzaLogicUrl = temp[1].trim();
                } else if (temp[0].equalsIgnoreCase("mainLogic")) {
                    this.piazzaMainUrl = temp[1].trim();
                }
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }


    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    public String getPiazzaLogicUrl() {
        return piazzaLogicUrl;
    }

    public String getPiazzaMainUrl() {
        return piazzaMainUrl;
    }

    public String getEnv() {
        return env;
    }

    public Set<String> getCourses() {
        return courses;
    }
}
