package com.piana.restController;

import org.junit.Test;

/**
 * Created by yuwang on 11/20/16.
 */
public class DataCrudControllerTest {

    @Test
    public void testRegisterCoursesForDatabasePersistence() throws Exception {
        DataCrudController dataCrudController = new DataCrudController();
        System.out.println(dataCrudController.registerCoursesForDatabasePersistence());
    }


    @Test
    public void testAllTimeStampListofFeed() throws Exception {
        DataCrudController dataCrudController = new DataCrudController();
        System.out.println(dataCrudController.allTimeStampListofFeed());
        System.out.println(dataCrudController.allTimeStampListofFeedPerNid("irl6njkfwh06de"));

    }

    @Test
    public void testAllTimeStampListofStats() throws Exception {
        DataCrudController dataCrudController = new DataCrudController();
        System.out.println(dataCrudController.allTimeStampListofStats());
        System.out.println(dataCrudController.allTimeStampListofStatsPerNid("irl6njkfwh06de"));

    }

    @Test
    public void testAllTimeStampListofAllPosts() throws Exception {
        DataCrudController dataCrudController = new DataCrudController();
        System.out.println(dataCrudController.allTimeStampListofAllPosts());
        System.out.println(dataCrudController.allTimeStampListofAllPostsPerNid("irl6njkfwh06de"));

    }

    @Test
    public void testFeedContentPerTimeStamp() throws Exception {

        DataCrudController dataCrudController = new DataCrudController();
        System.out.println(dataCrudController.feedContentPerTimeStamp("2016.11.06.01.39.47"));
    }

    @Test
    public void testStatsContentPerTimeStamp() throws Exception {
        DataCrudController dataCrudController = new DataCrudController();
        System.out.println(dataCrudController.statsContentPerTimeStamp("2016.11.20.00.18.06"));

    }

    @Test
    public void testPostContentPerTimeStamp() throws Exception {
        DataCrudController dataCrudController = new DataCrudController();
        System.out.println(dataCrudController.postContentPerTimeStamp("2016.11.20.00.18.04"));

    }
}