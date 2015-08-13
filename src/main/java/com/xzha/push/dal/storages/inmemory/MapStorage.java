package com.xzha.push.dal.storages.inmemory;

import com.xzha.push.dal.storages.IStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhabba on 28.06.15.
 * Temporary stub for debugging purposes
 */
public class MapStorage implements IStorage<String, String> {
    private  static Map<String, String> appStorage = new ConcurrentHashMap<>();

    public synchronized void create(String key, String value) {
        System.out.println("++++");
        appStorage.put(key, value);
    }

    public synchronized String read(String key) {
        return appStorage.get(key);
    }

    public synchronized void delete(String key) {
        appStorage.remove(key);
    }

    public synchronized void update(String key, String value) {
        delete(key);
        create(key, value);
    }

    public List<String> readAll() {
        List<String> all = new ArrayList<>();
        all.addAll(appStorage.values());
        return all;
    }
}
