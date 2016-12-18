package com.piana.repository;

import org.springframework.data.annotation.Id;

/**
 * Created by yuwang on 11/5/16.
 */
public class DailyDataPoJo {

    private String postContent;

    @Id
    private String timeStamp;

    public DailyDataPoJo(String timeStamp, String postContent) {
        this.timeStamp = timeStamp;
        this.postContent = postContent;
    }

}
