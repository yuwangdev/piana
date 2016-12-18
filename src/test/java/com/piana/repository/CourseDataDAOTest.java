package com.piana.repository;

import org.junit.Test;

import java.util.Set;

/**
 * Created by yuwang on 11/1/16.
 */
public class CourseDataDAOTest {

    @Test
    public void testInsertDailyPostData() throws Exception {
        CourseDataDAO courseDataRepository = new CourseDataDAO();
        courseDataRepository.insertDailyPostData("irl6njkfwh06de");
    }

    @Test
    public void testInsertDailyStats() throws Exception {
        CourseDataDAO courseDataRepository = new CourseDataDAO();
        courseDataRepository.insertDailyStatistics("irl6njkfwh06de");
    }

    @Test
    public void testInsertDailySnippet() throws Exception {
        CourseDataDAO courseDataRepository = new CourseDataDAO();
        courseDataRepository.insertDailyFeedSnippet("irl6njkfwh06de");
    }

    @Test
    public void testGetAllCollectionNames() throws Exception {
        CourseDataDAO courseDataRepository = new CourseDataDAO();
        Set<String> res = courseDataRepository.getAllCollectionNames();
        res.stream().forEach(x -> System.out.println(x));

    }

    @Test
    public void testDiffTwoTimeStampJsonData() throws Exception {
        CourseDataDAO courseDataRepository = new CourseDataDAO();
        String diff = courseDataRepository.diffTwoTimeStampJsonData("2016.11.06.09.47.40", "2016.11.20.00.17.35", "daily_feed");
        //System.out.println(diff);

        diff = courseDataRepository.diffTwoTimeStampJsonData("2016.11.06.09.33.56", "2016.12.03.23.02.27", "daily_stats");
        System.out.println(diff);

        diff = courseDataRepository.diffTwoTimeStampJsonData("2016.11.06.09.35.48", "2016.12.03.23.49.17", "daily_post");
        // System.out.println(diff);
    }
}

/* use for controller api test: post content
{
        "timeStamp1": "2016.11.06.09.47.40",
        "timeStamp2": "2016.11.20.00.17.35",
        "target": "feed"
}


{
  "timeStamp1": "2016.11.06.09.33.56",
  "timeStamp2": "2016.12.03.23.02.27",
  "target": "stats"
}







        */
