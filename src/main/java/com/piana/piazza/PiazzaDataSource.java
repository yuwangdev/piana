package com.piana.piazza;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.piana.config.ParseEnvironmentConfig;
import com.piana.utils.PiazzaRestPost;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

/**
 * Created by yuwang on 10/23/16.
 */

@Service
public class PiazzaDataSource {
    private final static String CONFIG_FILE_NAME = "pianaEnv_dev.config";
    private static final Logger logger = LoggerFactory.getLogger(PiazzaDataSource.class);
    private static Boolean isLogin = false;
    private static ParseEnvironmentConfig context = new ParseEnvironmentConfig(CONFIG_FILE_NAME);
    private static PiazzaRestPost piazzaRestPost = null;
    private String nid = "";

    public PiazzaDataSource() {
        piazzaRestPost = new PiazzaRestPost();
        if (SessionCache.sessionCache.size() == 0) {
            SessionCache.sessionCache.put("cookie", "");
        }
    }

    public Boolean getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(Boolean isLogin) {
        PiazzaDataSource.isLogin = isLogin;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    @Async
    @Cacheable(value = "cidContent", sync = true)
    public Future<String> getContentByCourseId(String cid) {
        if (SessionCache.sessionCache.get("cookie") == null ||
                SessionCache.sessionCache.get("cookie") == "") {
            throw new IllegalStateException("session cookie is empty");
        }
        String response = piazzaRestPost.doPost(context.getPiazzaMainUrl(),
                piazzaRestPost.prepareGetContentPerCidStatement(cid, this.nid), false);
        logger.debug("Piazza get content by cid response: " + response);
        return new AsyncResult<>(response);

    }

    @Async
    @Cacheable(value = "allUsers", sync = true)
    public Future<String> getAllUsers() {
        if (SessionCache.sessionCache.get("cookie") == null ||
                SessionCache.sessionCache.get("cookie") == "") {
            throw new IllegalStateException("session cookie is empty");
        }
        String response = piazzaRestPost.doPost(context.getPiazzaMainUrl(),
                piazzaRestPost.prepareGetAllUserStatement(this.nid), false);
        logger.debug("Piazza get all users response: " + response);
        return new AsyncResult<>(response);
    }

    @Async
    @Cacheable(value = "statistics", sync = true)
    public Future<String> getStatistics() {
        if (SessionCache.sessionCache.get("cookie") == null ||
                SessionCache.sessionCache.get("cookie") == "") {
            throw new IllegalStateException("session cookie is empty");
        }
        String response = piazzaRestPost.doPost(context.getPiazzaMainUrl(),
                piazzaRestPost.prepareGetStatisticsStatement(this.nid), false);
        logger.debug("Piazza get statistics response: " + response);
        return new AsyncResult<>(response);
    }

    @Async
    @Cacheable(value = "userProfile", sync = true)
    public Future<String> getUserProfile() {
        if (SessionCache.sessionCache.get("cookie") == null ||
                SessionCache.sessionCache.get("cookie") == "") {
            throw new IllegalStateException("session cookie is empty");
        }
        String response = piazzaRestPost.doPost(context.getPiazzaMainUrl(),
                piazzaRestPost.prepareGetUserProfile(), false);
        logger.debug("Piazza get user_profile response: " + response);
        return new AsyncResult<>(response);
    }

    @Async
    @Cacheable(value = "allFeedSnippet", sync = true)
    public Future<String> getAllFeedSnippet() {
        if (SessionCache.sessionCache.get("cookie") == null ||
                SessionCache.sessionCache.get("cookie") == "") {
            throw new IllegalStateException("session cookie is empty");
        }
        String response = piazzaRestPost.doPost(context.getPiazzaMainUrl(),
                piazzaRestPost.prepareGetFeedStatement(this.nid), false);
        logger.debug("Piazza get all feed snippet response: " + response);


        return new AsyncResult<>(response);
    }

    @Async
    @Cacheable(value = "searchResult", sync = true)
    public Future<String> getSearchResult(String keyword) {
        if (SessionCache.sessionCache.get("cookie") == null ||
                SessionCache.sessionCache.get("cookie") == "") {
            throw new IllegalStateException("session cookie is empty");
        }
        String response = piazzaRestPost.doPost(context.getPiazzaMainUrl(),
                piazzaRestPost.prepareSearchStatement(this.nid, keyword), false);
        logger.debug("Piazza get search response: " + response);
        return new AsyncResult<>(response);
    }

    @Async
    public Future<String> login(String username, String password) {
        if (SessionCache.sessionCache.get("cookie") == null) {
            throw new IllegalStateException("session cookie is not initialized");
        }
        String response = piazzaRestPost.doPost(context.getPiazzaLogicUrl(),
                piazzaRestPost.prepareLoginStatement(username, password), true);
        isLogin = response.contains("OK") ? true : false;
        logger.debug("isLogin: " + isLogin);
        logger.debug("Piazza login response: " + response);
        return new AsyncResult<>(response);

    }

    @Async
    public Future<Boolean> logout() {
        if (SessionCache.sessionCache.get("cookie") == null
                || SessionCache.sessionCache.get("cookie") == "") {
            throw new IllegalStateException("the Piazza data source has not yet logged in");
        } else {
            isLogin = false;
            SessionCache.sessionCache.put("cookie", "");
            return new AsyncResult<>(true);
        }
    }

    @Async
    @Cacheable(value = "allPostData", sync = true)
    public Future<List<String>> getAllPostData() throws ExecutionException, InterruptedException {
        List<String> result = Lists.newArrayList();
        Set<String> cidList = getAllCidsFromFeedSnippet(getAllFeedSnippet().get());


        ExecutorService executor = Executors.newFixedThreadPool(50);


        cidList.forEach(x -> {
            try {
                Future<String> tempResult = executor.submit(new WorkerThread(x, this.nid));
                result.add(tempResult.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        return new AsyncResult<>(result);
    }

    public Set<String> getAllCidsFromFeedSnippet(String allFeedSnippet) {
        Set<String> cidList = Sets.newConcurrentHashSet();
        JSONObject jsonObject = new JSONObject(allFeedSnippet);
        JSONObject jsonObject1 = jsonObject.getJSONObject("result");
        JSONArray jsonMainArr = jsonObject1.getJSONArray("feed");
        for (int i = 0; i < jsonMainArr.length(); i++) {
            JSONObject temp = jsonMainArr.getJSONObject(i);
            cidList.add(temp.get("nr").toString());
        }

        return cidList;
    }


    public class WorkerThread implements Callable<String> {

        private String cid;
        private String nid;

        public WorkerThread(String cid, String nid) {
            this.cid = cid;
            this.nid = nid;
        }

        @Override
        public String call() {
            System.out.println(Thread.currentThread().getName() + "Start. Command = " + this.cid);
            try {
                return processCommand();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " End.");
            return "";
        }

        private String processCommand() throws ExecutionException, InterruptedException {
//            PiazzaDataSource piazzaDataSource = new PiazzaDataSource();
//            return piazzaDataSource.getContentByCourseId(this.cid).get();

            return piazzaRestPost.doPost(context.getPiazzaMainUrl(),
                    piazzaRestPost.prepareGetContentPerCidStatement(this.cid, this.nid), false);

        }


    }
}
