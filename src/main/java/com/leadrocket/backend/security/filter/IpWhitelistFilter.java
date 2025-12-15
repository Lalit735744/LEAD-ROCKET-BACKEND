package com.leadrocket.backend.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Set;

public class IpWhitelistFilter extends OncePerRequestFilter {

	private static final Set<String> ALLOWED_IPS = Set.of(
			"127.0.0.1"
	);

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws java.io.IOException, jakarta.servlet.ServletException {

		if (!ALLOWED_IPS.contains(req.getRemoteAddr())) {
			res.setStatus(403);
			return;
		}
		chain.doFilter(req, res);
	}
}
