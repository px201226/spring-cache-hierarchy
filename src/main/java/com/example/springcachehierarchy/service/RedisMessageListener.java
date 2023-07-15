package com.example.springcachehierarchy.service;

import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisMessageListener implements MessageListener {

	private final RedisTemplate<String, Object> redisTemplate;
	private final List<CacheManager> cacheManagerList;
	private final RedisMessageListenerContainer redisMessageListenerContainer;

	@PostConstruct
	private void init() {
		redisMessageListenerContainer.addMessageListener(this, new ChannelTopic("Cache"));
	}


	@Override public void onMessage(final Message message, final byte[] pattern) {
		final var evictKey = new String(message.getBody(), StandardCharsets.UTF_8);

		for (final CacheManager cacheManager : cacheManagerList) {
			cacheManager.getCache("Cache").evict(evictKey);
		}
	}
}

