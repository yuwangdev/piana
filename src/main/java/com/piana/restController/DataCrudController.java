package com.piana.restController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.piana.repository.CourseDataDAO;
import com.piana.repository.DataPersistenceService;
import com.piana.restModel.request.DiffTimeDataRequestModel;
import com.piana.restModel.response.RegisterCoursesResponseModel;
import com.piana.restModel.response.TimeStampListModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * Created by yuwang on 11/6/16.
 */

@RestController
public class DataCrudController {

    private static final Logger log = LoggerFactory.getLogger(DataCrudController.class);
    //private static final String COURES_REGISTAR_COLLECTION = "courses_registar";
    private static String currentSessionNid = "";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @RequestMapping(value = "/data/courses", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public RegisterCoursesResponseModel registerCoursesForDatabasePersistence() throws ExecutionException, InterruptedException {

        DataPersistenceService dataPersistenceService = new DataPersistenceService();
        Set<String> registeredCoursesList = DataPersistenceService.getRegisteredCourseFromConfigFile();
        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        RegisterCoursesResponseModel registerCoursesResponseModel = new RegisterCoursesResponseModel(timestamp, registeredCoursesList);

        return registerCoursesResponseModel;

    }


    @RequestMapping(value = "/data/courses/feed", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public TimeStampListModel allTimeStampListofFeed() throws ExecutionException, InterruptedException {
        CourseDataDAO courseDataDAO = new CourseDataDAO();
        return new TimeStampListModel(courseDataDAO.getAllTimeStampLists("daily_feed"));
    }

    @RequestMapping(value = "/data/courses/stats", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public TimeStampListModel allTimeStampListofStats() throws ExecutionException, InterruptedException {
        CourseDataDAO courseDataDAO = new CourseDataDAO();
        return new TimeStampListModel(courseDataDAO.getAllTimeStampLists("daily_stats"));
    }

    @RequestMapping(value = "/data/courses/fullpost", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public TimeStampListModel allTimeStampListofAllPosts() throws ExecutionException, InterruptedException {
        CourseDataDAO courseDataDAO = new CourseDataDAO();
        return new TimeStampListModel(courseDataDAO.getAllTimeStampLists("daily_post"));
    }


    @RequestMapping(value = "/data/courses/feed/{nid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public TimeStampListModel allTimeStampListofFeedPerNid(@PathVariable String nid) throws ExecutionException, InterruptedException {
        CourseDataDAO courseDataDAO = new CourseDataDAO();
        return new TimeStampListModel(courseDataDAO.getAllTimeStampListsPerNid("daily_feed", nid));
    }

    @RequestMapping(value = "/data/courses/stats/{nid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public TimeStampListModel allTimeStampListofStatsPerNid(@PathVariable String nid) throws ExecutionException, InterruptedException {
        CourseDataDAO courseDataDAO = new CourseDataDAO();
        return new TimeStampListModel(courseDataDAO.getAllTimeStampListsPerNid("daily_stats", nid));
    }

    @RequestMapping(value = "/data/courses/fullpost/{nid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public TimeStampListModel allTimeStampListofAllPostsPerNid(@PathVariable String nid) throws ExecutionException, InterruptedException {
        CourseDataDAO courseDataDAO = new CourseDataDAO();
        return new TimeStampListModel(courseDataDAO.getAllTimeStampListsPerNid("daily_post", nid));
    }

    @RequestMapping(value = "/data/courses/feed/time/{timestamp}/record", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String feedContentPerTimeStamp(@PathVariable String timestamp) throws ExecutionException, InterruptedException {
        CourseDataDAO courseDataDAO = new CourseDataDAO();
        String res = courseDataDAO.getDataPerTimeStamp("daily_feed", timestamp);
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(res);
        return gson.toJson(jsonElement);

    }

    @RequestMapping(value = "/data/courses/stats/time/{timestamp}/record", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String statsContentPerTimeStamp(@PathVariable String timestamp) throws ExecutionException, InterruptedException {
        CourseDataDAO courseDataDAO = new CourseDataDAO();
        String response = courseDataDAO.getDataPerTimeStamp("daily_stats", timestamp);
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(response);
        return gson.toJson(jsonElement);

    }

    @RequestMapping(value = "/data/courses/fullpost/time/{timestamp}/record", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String postContentPerTimeStamp(@PathVariable String timestamp) throws ExecutionException, InterruptedException {
        CourseDataDAO courseDataDAO = new CourseDataDAO();
        String response = courseDataDAO.getDataPerTimeStamp("daily_post", timestamp);
        log.debug("response of " + timestamp + " is " + response);
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(response);
        return gson.toJson(jsonElement);

    }

    @RequestMapping(value = "/diff", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getDifference(@RequestBody DiffTimeDataRequestModel diffTimeDataRequestModel) throws ExecutionException, InterruptedException, IOException {

        String type = "";

        switch (diffTimeDataRequestModel.getTarget()) {

            case "feed":
                type = "daily_feed";
                break;

            case "stats":
                type = "daily_stats";
                break;

            case "fullpost":
                type = "daily_post";
                break;

            default:
                type = "daily_feed";
                break;

        }

        CourseDataDAO courseDataDAO = new CourseDataDAO();
        String response = courseDataDAO.diffTwoTimeStampJsonData(diffTimeDataRequestModel.getTimeStamp1(),
                diffTimeDataRequestModel.getTimeStamp2(),
                type);

        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(response);
        return gson.toJson(jsonElement);


    }
}
