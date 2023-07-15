package com.example.springcachehierarchy.cache;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.core.serializer.support.SerializationDelegate;

@Slf4j
public class L1Cache extends ConcurrentMapCache {


	public L1Cache(final String name) {
		super(name);
	}

	public L1Cache(final String name, final boolean allowNullValues) {
		super(name, allowNullValues);
	}

	public L1Cache(final String name, final ConcurrentMap<Object, Object> store, final boolean allowNullValues) {
		super(name, store, allowNullValues);
	}

	protected L1Cache(final String name, final ConcurrentMap<Object, Object> store, final boolean allowNullValues,
			final SerializationDelegate serialization) {
		super(name, store, allowNullValues, serialization);
	}

	@Override protected Object lookup(final Object key) {
		log.info("L1 cache lookup key = {}", key);
		return super.lookup(key);
	}

	@Override public <T> T get(final Object key, final Callable<T> valueLoader) {
		log.info("L1 cache get key = {}", key);
		return super.get(key, valueLoader);
	}

	@Override public void put(final Object key, final Object value) {
		super.clear();
		log.info("L1 cache put = key:{}, value:{}", key, value);
		super.put(key, value);
	}

	@Override public ValueWrapper putIfAbsent(final Object key, final Object value) {
		return super.putIfAbsent(key, value);
	}

	@Override public void evict(final Object key) {
		super.evict(key);
	}

	@Override public boolean evictIfPresent(final Object key) {
		return super.evictIfPresent(key);
	}
}
