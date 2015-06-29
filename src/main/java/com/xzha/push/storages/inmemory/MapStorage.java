package com.xzha.push.storages.inmemory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhabba on 28.06.15.
 */
public class MapStorage {
    private  static volatile Map<String, String> appStorage = new ConcurrentHashMap<>();

    public synchronized void save(String key, String value) {
        appStorage.put(key, value);
    }

    public synchronized String get(String key) {
        return appStorage.get(key);
    }

    public synchronized void delete(String key) {
        appStorage.remove(key);
    }

    public synchronized void update(String key, String value) {
        delete(key);
        save(key, value);
    }

    public synchronized long count () {
        return appStorage.size();
    }
}
