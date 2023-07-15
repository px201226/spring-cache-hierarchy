package com.example.springcachehierarchy.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
@SpringBootTest
class CacheServiceTest {

	@Autowired List<CacheManager> cacheManagerList;
	@Autowired RedisTemplate<String, Object> redisTemplate;
	@Autowired CacheService cacheService;


	@Test
	void CacheService_캐시_동작_테스트() {

		final var value1 = cacheService.findById("a");
		log.info("value1 return");

		final var value2 = cacheService.findById("a");
		log.info("value2 return");

		final var value3 = cacheService.findById("b");
		log.info("value3 return");

		final var value4 = cacheService.findById("b");
		log.info("value4 return");

		final var value5 = cacheService.findById("a");
		log.info("value5 return");
	}


	@Test
	@DisplayName("Reids Topic에 subscribe이 발생하면 해당 키가 l1, l2 캐시에서 evict 된다")
	void redis_pub_sub_cache_evict_테스트() throws InterruptedException {

		// given
		var topic = "Cache";
		var key = "key1";
		var value = "value1";

		for (final CacheManager cacheManager : cacheManagerList) {
			cacheManager.getCache(topic).put(key, value);
			Assertions.assertEquals(value, cacheManager.getCache(topic).get(key).get());
		}

		// when
		redisTemplate.convertAndSend(topic, key);
		Thread.sleep(1000L); // 캐시 무효화 메시지가 subscribe 될때까지 기다린다

		// then
		for (final CacheManager cacheManager : cacheManagerList) {
			Assertions.assertNull(cacheManager.getCache(topic).get(key));
		}

	}

}