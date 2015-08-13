package com.xzha.push.dal.storages;

import java.util.List;

/**
 * Created by zhabba on 13.07.15.
 */
public interface IStorage<K, V> {
    void create(K key, V value);
    void delete(K key);
    void update(K key,V value);
    V read(K key);
    List<V> readAll();
}

