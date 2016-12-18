package com.piana.core;

/**
 * Created by yuwang on 12/4/16.
 */


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.piana.repository.CourseDataDAO;
import com.piana.repository.DBUtils;
import com.piana.restModel.Email;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Receiver {

    private static final Logger log = LoggerFactory.getLogger(Receiver.class);
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();


    @JmsListener(destination = "mailbox", containerFactory = "myFactory")
    public void receiveMessage(Email email) {
        try {
            diffToTheLastData(email.getAddress(), email.getTitle());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void diffToTheLastData(String collectionName, String nid) throws IOException {

        String[] lastTwoRecords = DBUtils.getTheLastTwoDataRecordTimeStamp(collectionName, nid);

        CourseDataDAO courseDataDAO = new CourseDataDAO();
        String response = courseDataDAO.diffTwoTimeStampJsonData(lastTwoRecords[0], lastTwoRecords[1],
                collectionName);

        log.debug("last two timestamp: " + lastTwoRecords[0] + "   " + lastTwoRecords[1]);

        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(response);


        String title = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()) + "_" + collectionName + "_" + nid + "_diff.json";
        FileUtils.writeStringToFile(new File("daily_diff_data/" + title), gson.toJson(jsonElement), "UTF-8");


    }

}