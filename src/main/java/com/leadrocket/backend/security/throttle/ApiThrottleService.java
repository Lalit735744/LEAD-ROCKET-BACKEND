package com.leadrocket.backend.security.throttle;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class ApiThrottleService {

	private final ConcurrentHashMap<String, Integer> requestCount = new ConcurrentHashMap<>();

	public boolean allowRequest(String key) {
		requestCount.put(key, requestCount.getOrDefault(key, 0) + 1);
		return requestCount.get(key) <= 1000;
	}
}
