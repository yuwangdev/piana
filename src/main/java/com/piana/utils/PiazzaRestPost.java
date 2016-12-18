package com.piana.utils;

import com.piana.piazza.SessionCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by yuwang on 10/23/16.
 */
public class PiazzaRestPost {

    public static final String LOGIN_TEMPLATE = "{'method':'user.login','params':{'email':'email_temp','pass':'password_temp'}}";
    public static final String CONTENT_PER_CID_TEMPLATE = " {'method':'content.get','params':{'cid':'cid_temp','nid':'nid_temp'}}";
    public static final String ALL_USER_TEMPLATE = " {'method':'network.get_all_users','params': {'nid': 'nid_temp'}}";
    public static final String FEED_TEMPLATE = "{'method': 'network.get_my_feed','params': {'nid': 'nid_temp'}}";
    public static final String STATISTICS_TEMPLATE = "{'method': 'network.get_stats','params': {'nid': 'nid_temp'}}";
    private static final Logger logger = LoggerFactory.getLogger(PiazzaRestPost.class);
    private static final String USER_PROFILE_TEMPLATE = "{'method': 'user_profile.get_profile','params': {}}";
    private static final String SEARCH_TEMPLATE = "{'method':'network.search','params':{'nid':'nid_temp', 'query':'search_temp'}}";

    public String prepareLoginStatement(String username, String password) {

        String tempString = LOGIN_TEMPLATE;
        tempString = tempString.replace("email_temp", username);
        tempString = tempString.replace("password_temp", password);
        tempString = tempString.replace("'", "\"");
        logger.debug("the Piazza loggin api request: ", tempString);
        return tempString;
    }

    public String prepareGetContentPerCidStatement(String cid, String nid) {
        String tempString = CONTENT_PER_CID_TEMPLATE;
        tempString = tempString.replace("cid_temp", cid);
        tempString = tempString.replace("nid_temp", nid);
        tempString = tempString.replace("'", "\"");
        logger.debug("the Piazza get_conent_per_cid api request: ", tempString);
        return tempString;
    }

    public String prepareGetAllUserStatement(String nid) {
        String tempString = ALL_USER_TEMPLATE;
        tempString = tempString.replace("nid_temp", nid).replace("'", "\"");
        logger.debug("the Piazza get_all_user api request: ", tempString);
        return tempString;
    }

    public String prepareGetStatisticsStatement(String nid) {
        String tempString = STATISTICS_TEMPLATE;
        tempString = tempString.replace("nid_temp", nid);
        tempString = tempString.replace("'", "\"");
        logger.debug("the Piazza get_statistics api request: ", tempString);
        return tempString;
    }

    public String prepareGetFeedStatement(String nid) {
        String tempString = FEED_TEMPLATE;
        tempString = tempString.replace("nid_temp", nid).replace("'", "\"");
        logger.debug("the Piazza get_feed api request: ", tempString);
        return tempString;
    }

    public String prepareSearchStatement(String nid, String searchKeyWord) {
        String tempString = SEARCH_TEMPLATE;
        tempString = tempString.replace("nid_temp", nid).replace("search_temp", searchKeyWord).replace("'", "\"");
        logger.debug("the Piazza search api request: ", tempString);
        return tempString;
    }

    public String prepareGetUserProfile() {
        String tempString = USER_PROFILE_TEMPLATE;
        tempString = tempString.replace("'", "\"");
        logger.debug("the Piazza get_user_profile api request: ", tempString);
        return tempString;
    }


    public String doPost(String piazzaUrl, String postString, Boolean isLogin) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if (!isLogin) {
            logger.debug("not a Piazza login request, so put existed session cookie");
            headers.add("cookie", SessionCache.sessionCache.get("cookie"));
            logger.debug("Piazza session cookie:  " + SessionCache.sessionCache.get("cookie"));
        }

        HttpEntity<String> entity = new HttpEntity<String>(postString, headers);

        HttpEntity<String> response = restTemplate.exchange(piazzaUrl, HttpMethod.POST, entity, String.class);

        String responseString = response.getBody();

        logger.debug("response body string: " + responseString);
        logger.debug("response header string: " + response.getHeaders());

        if (isLogin) {
            logger.debug("is a Piazza login request");
            HttpHeaders head = response.getHeaders();
            if (responseString.contains("Email or password incorrect")) {
                throw new IllegalStateException("Password or email is wrong");
            }

            List<String> cookie = head.get("Set-Cookie");

            SessionCache.sessionCache.put("cookie", cookie.get(0));
            logger.debug("put Piazza session cookie into the SessionCache: ", cookie.get(0));
        }

        logger.debug("response string from doPost method: ", responseString);
        return responseString;
    }
}
