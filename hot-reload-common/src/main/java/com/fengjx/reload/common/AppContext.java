package com.fengjx.reload.common;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author fengjianxin
 */
public class AppContext {

    private static final Map<String, Object> ATTR = new ConcurrentHashMap<>();

    public static void put(String key, Object value) {
        ATTR.put(key, value);
    }




}
