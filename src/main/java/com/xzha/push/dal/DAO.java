package com.xzha.push.dal;

import com.xzha.push.storages.IStorage;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by zhabba on 13.07.15.
 */
//@SuppressWarnings("unchecked")
public class DAO {


    @Inject
    private IStorage<K, V> storage;

//    @Inject
//    public <K,V> DAO (IStorage<K,V> storage) {
//        this.storage = storage;
//    }

    public <K, V> void create(K key, V value) {
        storage.create(key, value);
    }

    public <K, V> V read(K key) {
        return (V) storage.read(key);
    }

    public <V> List<V> readAll() {
        return storage.readAll();
    }

    public <K, V> void update(K key, V value) {
        storage.update(key, value);
    }

    public <K, V> void delete (K key) {
        storage.delete(key);
    }
}