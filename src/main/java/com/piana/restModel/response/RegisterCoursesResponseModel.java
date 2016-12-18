package com.piana.restModel.response;

import com.google.common.collect.Sets;
import org.springframework.data.annotation.Id;

import java.util.Set;

/**
 * Created by yuwang on 11/7/16.
 */
public class RegisterCoursesResponseModel {


    @Id
    private String timeStamp;

    private Set<String> courseList = Sets.newConcurrentHashSet();

    public RegisterCoursesResponseModel() {
    }

    public RegisterCoursesResponseModel(String timeStamp, Set<String> courseList) {
        this.timeStamp = timeStamp;
        this.courseList = courseList;
    }

    public Set<String> getCourseList() {
        return courseList;
    }

    public void setCourseList(Set<String> courseList) {
        this.courseList = courseList;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "RegisterCoursesResponseModel{" +
                "courseList=" + courseList +
                ", timeStamp='" + timeStamp + '\'' +
                '}';
    }
}

