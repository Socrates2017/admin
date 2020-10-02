package com.zrzhen.admin.module.os.constant;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {

    private static Map cache = new ConcurrentHashMap();

    public static void put(String key, Object value) {
        cache.put(key, value);
    }

    public static Object get(String key) {
        return cache.get(key);
    }

    public static Object del(String key) {
        return cache.remove(key);
    }
}
