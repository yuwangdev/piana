package com.piana.piazza;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yuwang on 10/25/16.
 */
public class SessionCache {
    public static ConcurrentHashMap<String, String> sessionCache = new ConcurrentHashMap<>();
}
