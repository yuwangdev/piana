package com.piana.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.piana.config.ParseEnvironmentConfig;
import com.piana.restModel.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * Created by yuwang on 11/6/16.
 */

@Component
public class DataPersistenceService {

    private static final Logger log = LoggerFactory.getLogger(DataPersistenceService.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yy:mm:dd:HH:mm:ss");
    private final static String CONFIG_FILE_NAME = "pianaEnv_dev.config";
    private static ParseEnvironmentConfig context = new ParseEnvironmentConfig(CONFIG_FILE_NAME);
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Autowired
    private ConfigurableApplicationContext applicationContext;

    public DataPersistenceService() {


    }

    public static Set<String> getRegisteredCourseFromConfigFile() {
        return context.getCourses();
    }

    @Scheduled(fixedRate = 43200000, initialDelay = 600000)
    @Async
    //@Scheduled(fixedRate = 60000, initialDelay = 600000)
    public void persistData() {

        Set<String> registeredCourseLists = getRegisteredCourseFromConfigFile();

        registeredCourseLists.forEach(x -> {
            log.debug("persist data for the courses of " + x);
            CourseDataDAO courseDataDAO = new CourseDataDAO();
            try {
                courseDataDAO.insertDailyFeedSnippet(x);
                courseDataDAO.insertDailyPostData(x);
                courseDataDAO.insertDailyStatistics(x);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("The time is now {}", dateFormat.format(new Date()));
            log.info("persist course of ", x);

            // String diff = diffToTheLastData("daily_post", x);
            sendMessageOnChange("daily_post", x);


        });

    }

//    public String diffToTheLastData(String collectionName, String nid) throws IOException {
//
//        String[] lastTwoRecords = DBUtils.getTheLastTwoDataRecordTimeStamp(collectionName, nid);
//
//        CourseDataDAO courseDataDAO = new CourseDataDAO();
//        String response = courseDataDAO.diffTwoTimeStampJsonData(lastTwoRecords[0], lastTwoRecords[1],
//                collectionName);
//
//        log.debug("last two timestamp: " + lastTwoRecords[0] + "   " + lastTwoRecords[1]);
//
//        JsonParser jsonParser = new JsonParser();
//        JsonElement jsonElement = jsonParser.parse(response);
//
//
//        String title = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()) + "_" + collectionName + "_" + nid + "_diff.json";
//        FileUtils.writeStringToFile(new File("daily_diff_data/" + title), gson.toJson(jsonElement), "UTF-8");
//
//        return gson.toJson(jsonElement);
//
//    }


    public void sendMessageOnChange(String collectionName, String nid) {

        JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
        jmsTemplate.convertAndSend("mailbox", new Email(collectionName, nid));


    }


}



