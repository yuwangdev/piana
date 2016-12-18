package com.piana.repository;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by yuwang on 11/20/16.
 */
public class DataPersistenceServiceTest {

    @Test
    public void testPersistData() throws Exception {

        DataPersistenceService dataPersistenceService = new DataPersistenceService();
        System.out.println(DataPersistenceService.getRegisteredCourseFromConfigFile());


    }

    @Test
    @Ignore
    public void testGetRegisteredCourseFromConfigFile() throws Exception {
        DataPersistenceService dataPersistenceService = new DataPersistenceService();
        dataPersistenceService.persistData();

    }


}