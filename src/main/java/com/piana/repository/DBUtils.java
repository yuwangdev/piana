package com.piana.repository;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.*;
import com.mongodb.util.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

/**
 * Created by yuwang on 11/6/16.
 */
public class DBUtils {

    private static final Logger log = LoggerFactory.getLogger(DBUtils.class);
    private static Mongo mongo = new Mongo("localhost", 27017);
    private static DB db = mongo.getDB("piana");
    private static Gson gson = new Gson();

    public static void insert(Object object, String collectionName) {

        try {
            log.debug("create the mongodb connection");
            DBCollection collection = db.getCollection(collectionName);
            DBObject dbObject = (DBObject) JSON.parse(gson.toJson(object));
            collection.insert(dbObject);

        } catch (MongoException e) {
            e.printStackTrace();
        }
    }


    public static Set<String> getAllCollectionNames() {

        try {
            Set<String> collections = db.getCollectionNames();
            return collections;

        } catch (MongoException e) {
            e.printStackTrace();
        }
        return Sets.newHashSet();
    }


    public static Set<String> getTimeStampLists(String collectionName) {


        Set<String> results = Sets.newConcurrentHashSet();

        try {
            DBCollection collection = db.getCollection(collectionName);
            log.debug(collection.toString());

            BasicDBObject allQuery = new BasicDBObject();
            BasicDBObject fields = new BasicDBObject();
            fields.put("timeStamp", 1);
            fields.put("_id", 0);

            DBCursor cursor = collection.find(allQuery, fields);
            while (cursor.hasNext()) {
                results.add(cursor.next().toString());
            }

            return results;

        } catch (MongoException e) {
            e.printStackTrace();
        }
        return results;
    }

    public static Set<String> getTimeStampListsPerNid(String collectionName, String nid) {

        final String pat = "instr_" + nid.trim();

        Set<String> results = Sets.newConcurrentHashSet();
        Set<String> tempFull = Sets.newConcurrentHashSet();

        try {

            DBCollection collection = db.getCollection(collectionName);
            log.debug(collection.toString());

            BasicDBObject allQuery = new BasicDBObject();
            BasicDBObject fields = new BasicDBObject();
            fields.put("timeStamp", 1);
            fields.put("postContent", 1);
            fields.put("_id", 0);

            DBCursor cursor = collection.find(allQuery, fields);
            while (cursor.hasNext()) {
                tempFull.add(cursor.next().toString());
            }


        } catch (MongoException e) {
            e.printStackTrace();
        }

        if (tempFull.size() == 0) return results;

        tempFull.stream().forEach(x -> {

            if (x.contains(pat)) {
                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(x).getAsJsonObject();
                results.add(jsonObject.get("timeStamp").getAsString());
            }
        });

        return results;
    }

    public static String getDataPerTimeStampAndCollectionName(String collectionName, String timeStamp) {

        StringBuilder stringBuilder = new StringBuilder();


        try {

            DBCollection collection = db.getCollection(collectionName);
            log.debug(collection.toString());

            BasicDBObject whereQuery = new BasicDBObject();
            whereQuery.put("timeStamp", timeStamp);
            DBCursor cursor = collection.find(whereQuery);

            int index = 0;
            while (cursor.hasNext()) {
                stringBuilder.append(cursor.next().toString());
                index++;
            }

            log.debug("extract data count of " + index);

            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(stringBuilder.toString()).getAsJsonObject();
            return jsonObject.get("postContent").getAsString();


        } catch (MongoException e) {
            e.printStackTrace();
        }


        return "";
    }

    public static String[] getTheLastTwoDataRecordTimeStamp(String collectionName, String timeStamp) {

        String[] result = new String[2];

        List<String> allTimeStampList = Lists.newArrayList();

        getTimeStampListsPerNid(collectionName, timeStamp).stream().forEach(x -> {
            allTimeStampList.add(x);
        });

        //  result[0] = getDataPerTimeStampAndCollectionName(collectionName, allTimeStampList.get(allTimeStampList.size() - 1));
        //  result[1] = getDataPerTimeStampAndCollectionName(collectionName, allTimeStampList.get(allTimeStampList.size() - 2));

        result[0] = allTimeStampList.get(allTimeStampList.size() - 1);
        result[1] = allTimeStampList.get(allTimeStampList.size() - 2);


        return result;

    }


}
