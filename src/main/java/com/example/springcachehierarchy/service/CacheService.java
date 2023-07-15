package com.example.springcachehierarchy.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CacheService {

	@Caching(cacheable =
			{
					@Cacheable(cacheManager = "l1CacheManager", cacheNames = "Cache"),
					@Cacheable(cacheManager = "l2RedisCacheManager", cacheNames = "Cache")
			}

	)
	public String findById(String id) {
		log.info("findById = {}", id);
		return id;
	}

}