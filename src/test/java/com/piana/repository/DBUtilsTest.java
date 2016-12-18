package com.piana.repository;

import org.junit.Test;

/**
 * Created by yuwang on 11/20/16.
 */
public class DBUtilsTest {

    @Test
    public void testGetTimeStampLists() throws Exception {

        DBUtils.getTimeStampLists("daily_feed").stream().forEach(System.out::println);

    }

    @Test
    public void testGetTimeStampListsPerNid() throws Exception {
        DBUtils.getTimeStampListsPerNid("daily_feed", "is4t7f44g4a8").stream().forEach(System.out::println);
        DBUtils.getTimeStampListsPerNid("daily_feed", "irl6njkfwh06de").stream().forEach(System.out::println);
    }

    @Test
    public void testGetDataPerNid() throws Exception {

        System.out.println(DBUtils.getDataPerTimeStampAndCollectionName("daily_feed", "2016.11.06.01.39.47"));
        // System.out.println(DBUtils.getDataPerNid("daily_stats", "2016.11.20.00.18.06"));
        //  System.out.println(DBUtils.getDataPerNid("daily_post", "2016.11.20.00.18.04"));
    }
}