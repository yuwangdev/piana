package com.piana.restModel.response;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Created by yuwang on 11/20/16.
 */
public class TimeStampListModel {

    private int totalCount;

    private Set<String> timeStampList = Sets.newConcurrentHashSet();

    public TimeStampListModel(Set<String> timeStampList) {
        this.timeStampList = timeStampList;
        this.totalCount = this.timeStampList.size();
    }

    public TimeStampListModel() {
    }

    public Set<String> getTimeStampList() {
        return timeStampList;
    }

    public void setTimeStampList(Set<String> timeStampList) {
        this.timeStampList = timeStampList;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "TimeStampListModel{" +
                "totalCount=" + totalCount +
                ", timeStampList=" + timeStampList +
                '}';
    }
}

