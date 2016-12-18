package com.piana.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonDiff;
import com.piana.config.ParseEnvironmentConfig;
import com.piana.piazza.PiazzaDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * Created by yuwang on 11/1/16.
 */


public class CourseDataDAO {

    private static final Logger log = LoggerFactory.getLogger(CourseDataDAO.class);

    private final static String CONFIG_FILE_NAME = "pianaEnv_dev.config";
    private static final String COLLECTION_DAILY_STATISTICS = "daily_stats";
    private static final String COLLECTION_DAILY_FEED = "daily_feed";
    private static ParseEnvironmentConfig context = new ParseEnvironmentConfig(CONFIG_FILE_NAME);
    private static String COLLECTION_DAILY_FULL_POST = "daily_post";
    @Autowired
    PiazzaDataSource piazzaDataSource;

    public CourseDataDAO() {
        super();
        log.debug("begin construct the CourseDataDAO");
    }

    @Async
    public void insertDailyPostData(String nid) throws ExecutionException, InterruptedException {

        piazzaDataSource = new PiazzaDataSource();
        piazzaDataSource.login(context.getUsername(), context.getPassword()).get();
        piazzaDataSource.setNid(nid);
        String response = piazzaDataSource.getAllPostData().get().toString();
        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        DailyDataPoJo dailyPostDataPoJo = new DailyDataPoJo(timestamp, response);
        log.info("insert the daily post data into the mongodb");
        log.info("collection: " + COLLECTION_DAILY_FULL_POST);
        log.info("timestamp: " + timestamp);
        DBUtils.insert(dailyPostDataPoJo, COLLECTION_DAILY_FULL_POST);
    }

    @Async
    public void insertDailyStatistics(String nid) throws ExecutionException, InterruptedException {

        piazzaDataSource = new PiazzaDataSource();
        piazzaDataSource.login(context.getUsername(), context.getPassword()).get();
        piazzaDataSource.setNid(nid);
        String response = piazzaDataSource.getStatistics().get();
        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        DailyDataPoJo dailyStatisticsPoJo = new DailyDataPoJo(timestamp, response);
        log.info("insert the daily statistics data into the mongodb");
        log.info("collection: " + COLLECTION_DAILY_STATISTICS);
        log.info("timestamp: " + timestamp);
        DBUtils.insert(dailyStatisticsPoJo, COLLECTION_DAILY_STATISTICS);
    }

    @Async
    public void insertDailyFeedSnippet(String nid) throws ExecutionException, InterruptedException {

        piazzaDataSource = new PiazzaDataSource();
        piazzaDataSource.login(context.getUsername(), context.getPassword()).get();
        piazzaDataSource.setNid(nid);
        String response = piazzaDataSource.getAllFeedSnippet().get();
        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        DailyDataPoJo dailySnippetPoJo = new DailyDataPoJo(timestamp, response);
        log.info("insert the daily feed snippet data into the mongodb");
        log.info("collection: " + COLLECTION_DAILY_FEED);
        log.info("timestamp: " + timestamp);
        DBUtils.insert(dailySnippetPoJo, COLLECTION_DAILY_FEED);
    }

    @Async
    public Set<String> getAllCollectionNames() {
        return DBUtils.getAllCollectionNames();
    }

    @Async
    public Set<String> getAllTimeStampLists(String collectionName) {
        Set<String> results = DBUtils.getTimeStampLists(collectionName);
        return results;
    }

    @Async
    public Set<String> getAllTimeStampListsPerNid(String collectionName, String nid) {
        Set<String> results = DBUtils.getTimeStampListsPerNid(collectionName, nid);
        return results;
    }

    @Async
    public String getDataPerTimeStamp(String collectionName, String timeStamp) {
        return DBUtils.getDataPerTimeStampAndCollectionName(collectionName, timeStamp);
    }

    @Async
    @Cacheable(value = "diff", sync = true)
    public String diffTwoTimeStampJsonData(String timeStamp1, String timeStamp2, String collectionName) throws IOException {

        String data1 = getDataPerTimeStamp(collectionName, timeStamp1);
        String data2 = getDataPerTimeStamp(collectionName, timeStamp2);

        ObjectMapper jacksonObjectMapper = new ObjectMapper();

        JsonNode beforeNode = jacksonObjectMapper.readTree(data1);
        JsonNode afterNode = jacksonObjectMapper.readTree(data2);
        JsonNode patch = JsonDiff.asJson(beforeNode, afterNode);
        String diffs = patch.toString();

        return diffs;
    }


}
