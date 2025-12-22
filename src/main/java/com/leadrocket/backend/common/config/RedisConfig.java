package com.leadrocket.backend.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;

import java.time.Duration;

@Configuration
public class RedisConfig {

	@Bean
	public RedisTemplate<String, Object> redisTemplate(
			LettuceConnectionFactory connectionFactory) {

		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		return template;
	}

	@Bean
	public CacheManager cacheManager(
			LettuceConnectionFactory connectionFactory) {

		RedisCacheConfiguration cacheConfig =
				RedisCacheConfiguration.defaultCacheConfig()
						.entryTtl(Duration.ofMinutes(10))
						.disableCachingNullValues();

		return RedisCacheManager.builder(connectionFactory)
				.cacheDefaults(cacheConfig)
				.build();
	}
}