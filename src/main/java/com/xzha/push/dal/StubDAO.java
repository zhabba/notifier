package com.xzha.push.dal;

import com.xzha.push.dal.storages.IStorage;
import com.xzha.push.dal.storages.inmemory.MapStorage;
import org.apache.log4j.Logger;

import javax.enterprise.inject.Default;
import java.util.List;

/**
 * Created by zhabba on 13.07.15.
 */
@Default
public class StubDAO implements IStorage<String, String>{
    private static final Logger LOG = Logger.getLogger(StubDAO.class);

    protected IStorage<String, String> storage = new MapStorage();

    @Override
    public void create(String key, String value) {
        storage.create(key, value);
    }

    @Override
    public void delete(String key) {
        storage.delete(key);
    }

    @Override
    public void update(String key, String value) {
        storage.update(key, value);
    }

    @Override
    public String read(String key) {
        return storage.read(key);
    }

    @Override
    public List<String> readAll() {
        return storage.readAll();
    }
}