package com.xzha.push.dal;

import com.xzha.push.storages.IStorage;

import java.util.List;

/**
 * Created by zhabba on 13.07.15.
 */
public class Storage<K, V> {

    private IStorage<K, V> storage;

    public Storage (IStorage<K, V> storage) {
        this.storage = storage;
    }

    public void create(K key, V value) {
        storage.create(key, value);
    }

    public V read(K key) {
        return (V) storage.read(key);
    }

    public List<V> readAll() {
        return storage.readAll();
    }

    public void update(K key, V value) {
        storage.update(key, value);
    }

    public void delete (K key) {
        storage.delete(key);
    }
}