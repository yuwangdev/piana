package com.piana.restController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.piana.piazza.PiazzaDataSource;
import com.piana.restModel.request.LoginRequestModel;
import com.piana.restModel.response.LoginResultModel;
import com.piana.restModel.response.LogoutResultModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * Created by yuwang on 10/28/16.
 */

@RestController
public class PiazzaRawController {


    private static final Logger log = LoggerFactory.getLogger(PiazzaRawController.class);
    private static String currentSessionNid = "";
    @Autowired
    PiazzaDataSource piazzaDataSource;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();


    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public LoginResultModel login(@RequestBody LoginRequestModel loginRequestModel) throws ExecutionException, InterruptedException {


        String response = piazzaDataSource.login(loginRequestModel.getUsername(), loginRequestModel.getPassword()).get();
        LoginResultModel loginResultModel = new LoginResultModel();
        if (piazzaDataSource.getIsLogin()) {
            loginResultModel.setStatus("ok");
            loginResultModel.setUsername(loginRequestModel.getUsername());
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            loginResultModel.setTimeStamp(dateFormat.format(date));
        } else {
            loginResultModel.setStatus("error");
            loginResultModel.setUsername(loginRequestModel.getUsername());
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            loginResultModel.setTimeStamp(dateFormat.format(date));
        }
        return loginResultModel;
    }

    @RequestMapping(value = "/raw/{nid}/{cid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getContentPerCid(@PathVariable String nid, @PathVariable String cid) throws ExecutionException, InterruptedException {
        currentSessionNid = nid;
        piazzaDataSource.setNid(currentSessionNid);
        String response = piazzaDataSource.getContentByCourseId(cid).get();
        currentSessionNid = "";
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(response);
        return gson.toJson(jsonElement);
    }

    @RequestMapping(value = "/raw/{nid}/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllUsers(@PathVariable String nid) throws ExecutionException, InterruptedException {
        currentSessionNid = nid;
        piazzaDataSource.setNid(currentSessionNid);
        String response = piazzaDataSource.getAllUsers().get();
        currentSessionNid = "";
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(response);
        return gson.toJson(jsonElement);
    }

    @RequestMapping(value = "/raw/{nid}/stats", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getStatistics(@PathVariable String nid) throws ExecutionException, InterruptedException {
        currentSessionNid = nid;
        piazzaDataSource.setNid(currentSessionNid);
        String response = piazzaDataSource.getStatistics().get();
        currentSessionNid = "";
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(response);
        return gson.toJson(jsonElement);
    }

    @RequestMapping(value = "/raw/{nid}/userprofile", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getUserProfiles(@PathVariable String nid) throws ExecutionException, InterruptedException {
        currentSessionNid = nid;
        piazzaDataSource.setNid(currentSessionNid);
        String response = piazzaDataSource.getUserProfile().get();
        currentSessionNid = "";
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(response);
        return gson.toJson(jsonElement);
    }

    @RequestMapping(value = "/raw/{nid}/simplefeed", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllFeedSnippet(@PathVariable String nid) throws ExecutionException, InterruptedException {
        currentSessionNid = nid;
        piazzaDataSource.setNid(currentSessionNid);
        String response = piazzaDataSource.getAllFeedSnippet().get();
        currentSessionNid = "";
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(response);
        return gson.toJson(jsonElement);
    }

    @RequestMapping(value = "/raw/{nid}/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getSearch(@PathVariable String nid,
                            @RequestParam(value = "keyword") String keyword) throws ExecutionException, InterruptedException {
        currentSessionNid = nid;
        piazzaDataSource.setNid(currentSessionNid);
        String response = piazzaDataSource.getSearchResult(keyword).get();
        currentSessionNid = "";
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(response);
        return gson.toJson(jsonElement);
    }

    @RequestMapping(value = "/raw/{nid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllPosts(@PathVariable String nid) throws ExecutionException, InterruptedException {
        currentSessionNid = nid;
        piazzaDataSource.setNid(currentSessionNid);
        List<String> response = piazzaDataSource.getAllPostData().get();
        currentSessionNid = "";
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(response.toString());
        return gson.toJson(jsonElement);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public LogoutResultModel logout() throws ExecutionException, InterruptedException {
        Boolean response = piazzaDataSource.logout().get();
        LogoutResultModel logoutResultModel = new LogoutResultModel();
        if (response) {
            logoutResultModel.setStatus("logout ok");
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            logoutResultModel.setTimeStamp(dateFormat.format(date));
        } else {
            logoutResultModel.setStatus("logout error");
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            logoutResultModel.setTimeStamp(dateFormat.format(date));
        }
        return logoutResultModel;
    }


}


