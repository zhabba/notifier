package com.xzha.push.dal;

import com.xzha.push.dal.storages.IStorage;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import java.util.List;

/**
 * Class com.xzha.push.dal.storages.AbstractStorage
 * created at 13.08.15 - 11:53
 */
@Decorator
public abstract class AbstractDao<K, V> implements IStorage<K, V> {

	@Inject
	@Delegate
	@Any
	protected IStorage<K, V> delegate;

	@Override
	public void create(K key, V value) {
		delegate.create(key, value);
	}

	@Override
	public void delete(K key) {
		delegate.delete(key);
	}

	@Override
	public void update(K key, V value) {
		delegate.update(key, value);
	}

	@Override
	public V read(K key) {
		return delegate.read(key);
	}

	@Override
	public List<V> readAll() {
		return delegate.readAll();
	}
}
