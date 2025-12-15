package com.leadrocket.backend.security.ratelimit;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.concurrent.ConcurrentHashMap;

public class RateLimitFilter extends OncePerRequestFilter {

	private static final ConcurrentHashMap<String, Long> hits = new ConcurrentHashMap<>();

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws java.io.IOException, jakarta.servlet.ServletException {

		String ip = req.getRemoteAddr();
		hits.put(ip, hits.getOrDefault(ip, 0L) + 1);

		if (hits.get(ip) > 1000) {
			res.setStatus(429);
			return;
		}
		chain.doFilter(req, res);
	}
}
