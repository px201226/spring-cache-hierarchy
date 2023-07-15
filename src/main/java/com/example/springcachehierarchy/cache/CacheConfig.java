package com.example.springcachehierarchy.cache;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class CacheConfig {

	private final RedisConnectionFactory connectionFactory;

	@Primary
	@Bean("l1CacheManager")
	public CacheManager l1CacheManager() {
		final var simpleCacheManager = new SimpleCacheManager();
		simpleCacheManager.setCaches(List.of(new L1Cache("Cache")));
		return simpleCacheManager;
	}


	@Bean("l2CacheManager")
	public CacheManager l2CacheManager() {
		final var simpleCacheManager = new SimpleCacheManager();
		simpleCacheManager.setCaches(List.of(new L2Cache("Cache")));
		return simpleCacheManager;
	}


	@Bean("l2RedisCacheManager")
	public CacheManager redisCacheManager() {
		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()));

		return RedisCacheManager.RedisCacheManagerBuilder
				.fromConnectionFactory(connectionFactory)
				.cacheDefaults(redisCacheConfiguration)
				.build();
	}
}
