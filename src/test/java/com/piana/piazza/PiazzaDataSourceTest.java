package com.piana.piazza;

import com.google.common.base.Strings;
import com.piana.config.ParseEnvironmentConfig;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

/**
 * Created by yuwang on 10/26/16.
 */
public class PiazzaDataSourceTest {

    private static final Logger logger = LoggerFactory.getLogger(PiazzaDataSourceTest.class);
    private final static String CONFIG_FILE_NAME = "/Users/yuwang/Documents/pianaEnv_dev.config";
    private static final String CID_TEST = "491";
    private static ParseEnvironmentConfig context = new ParseEnvironmentConfig(CONFIG_FILE_NAME);
    public final String NID = "irl6njkfwh06de";

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetIsLogin() throws Exception {
        PiazzaDataSource piazzaService = new PiazzaDataSource();
        piazzaService.setNid(NID);
        Assert.assertFalse(piazzaService.getIsLogin());
        String response = piazzaService.login(context.getUsername(), context.getPassword()).get();
        Assert.assertTrue(piazzaService.getIsLogin());
        piazzaService.logout();
    }

    @Test(expected = IllegalStateException.class)
    public void testSetIsLogin() throws Exception {
        PiazzaDataSource piazzaService = new PiazzaDataSource();
        piazzaService.setNid(NID);
        piazzaService.setIsLogin(true);
        Assert.assertTrue(piazzaService.getIsLogin());
        piazzaService.logout();
    }

    @Test
    public void testGetNid() throws Exception {
        PiazzaDataSource piazzaService = new PiazzaDataSource();
        piazzaService.setNid(NID);
        Assert.assertFalse(piazzaService.getIsLogin());
        Assert.assertEquals(NID, piazzaService.getNid());
    }

    @Test
    public void testSetNid() throws Exception {
        PiazzaDataSource piazzaService = new PiazzaDataSource();
        piazzaService.setNid(NID);
        Assert.assertFalse(piazzaService.getIsLogin());
        Assert.assertEquals(NID, piazzaService.getNid());
        piazzaService.setNid(Strings.repeat(NID, 3));
        Assert.assertEquals(Strings.repeat(NID, 3), piazzaService.getNid());
    }

    @Test
    public void testRestTemplate() throws Exception {

    }

    @Test
    public void testGetContentByCourseId() throws Exception {
        PiazzaDataSource piazzaService = new PiazzaDataSource();
        piazzaService.setNid(NID);
        String response = piazzaService.login(context.getUsername(), context.getPassword()).get();
        logger.info("Login unittest: " + response);
        Assert.assertTrue(piazzaService.getIsLogin());
        Assert.assertEquals(NID, piazzaService.getNid());
        Assert.assertTrue(response.contains("OK"));
        response = piazzaService.getContentByCourseId(CID_TEST).get();
        logger.debug("current session cookie: " + SessionCache.sessionCache.get("cookie"));
        logger.debug("get cid content response: " + response);
        piazzaService.logout();
    }

    @Test
    public void testGetAllUsers() throws Exception {
        PiazzaDataSource piazzaService = new PiazzaDataSource();
        piazzaService.setNid(NID);
        String response = piazzaService.login(context.getUsername(), context.getPassword()).get();
        logger.info("Login unittest: " + response);
        Assert.assertTrue(piazzaService.getIsLogin());
        Assert.assertTrue(response.contains("OK"));
        response = piazzaService.getAllUsers().get();
        logger.debug("current session cookie: " + SessionCache.sessionCache.get("cookie"));
        logger.debug("get all users response: " + response);
        piazzaService.logout();
    }

    @Test
    public void testGetStatistics() throws Exception {
        PiazzaDataSource piazzaService = new PiazzaDataSource();
        piazzaService.setNid(NID);
        String response = piazzaService.login(context.getUsername(), context.getPassword()).get();
        logger.info("Login unittest: " + response);
        Assert.assertTrue(piazzaService.getIsLogin());
        Assert.assertEquals(NID, piazzaService.getNid());
        Assert.assertTrue(response.contains("OK"));
        response = piazzaService.getStatistics().get();
        logger.debug("current session cookie: " + SessionCache.sessionCache.get("cookie"));
        logger.debug("get all statistics response: " + response);
        piazzaService.logout();
    }

    @Test
    public void testGetFeedSnippet() throws Exception {
        PiazzaDataSource piazzaService = new PiazzaDataSource();
        piazzaService.setNid(NID);
        String response = piazzaService.login(context.getUsername(), context.getPassword()).get();
        logger.info("Login unittest: " + response);
        Assert.assertTrue(response.contains("OK"));
        response = piazzaService.getAllFeedSnippet().get();
        logger.debug("current session cookie: " + SessionCache.sessionCache.get("cookie"));
        logger.debug("get all feed snippet response: " + response);
        piazzaService.logout();
    }

    @Test
    public void testGetUserProfile() throws Exception {
        PiazzaDataSource piazzaService = new PiazzaDataSource();
        piazzaService.setNid(NID);
        String response = piazzaService.login(context.getUsername(), context.getPassword()).get();
        response = piazzaService.getUserProfile().get();
        logger.debug("current session cookie: " + SessionCache.sessionCache.get("cookie"));
        logger.debug("get all user profile response: " + response);
        piazzaService.logout();
    }

    @Test
    public void testGetSearchResult() throws Exception {
        PiazzaDataSource piazzaService = new PiazzaDataSource();
        piazzaService.setNid(NID);
        String re = piazzaService.login(context.getUsername(), context.getPassword()).get();
        re = piazzaService.getSearchResult("georgia").get();
        logger.debug("current session cookie: " + SessionCache.sessionCache.get("cookie"));
        logger.debug("get search response: " + re);
        piazzaService.logout();
    }


    @Test
    public void testGetAllCidsFromFeedSnippet() throws Exception {
        PiazzaDataSource piazzaService = new PiazzaDataSource();
        piazzaService.setNid(NID);
        String re = piazzaService.login(context.getUsername(), context.getPassword()).get();
        re = piazzaService.getAllFeedSnippet().get();
        logger.debug("feed snippet: " + re);
        Set<String> lists = piazzaService.getAllCidsFromFeedSnippet(re);
        lists.forEach(x -> System.out.println(x)
        );
    }

    @Test
    public void testLogin() throws Exception {
        PiazzaDataSource piazzaService = new PiazzaDataSource();
        piazzaService.setNid(NID);
        String response = piazzaService.login(context.getUsername(), context.getPassword()).get();
        logger.info("Login unittest: " + response);
        Assert.assertTrue(piazzaService.getIsLogin());
        Assert.assertEquals(NID, piazzaService.getNid());
        Assert.assertTrue(response.contains("OK"));
        logger.info("Session unittest: " + SessionCache.sessionCache.get("cookie"));
        piazzaService.logout();
        Assert.assertFalse(piazzaService.getIsLogin());
        Assert.assertEquals("", SessionCache.sessionCache.get("cookie"));
    }

    @Test
    public void testGetAllPostData() throws Exception {
        PiazzaDataSource piazzaService = new PiazzaDataSource();
        piazzaService.setNid(NID);
        String re = piazzaService.login(context.getUsername(), context.getPassword()).get();
        List<String> lists = piazzaService.getAllPostData().get();
        logger.debug("current session cookie: " + SessionCache.sessionCache.get("cookie"));

        lists.forEach(x -> logger.debug("get each post data " + x));


        piazzaService.logout();

    }
}