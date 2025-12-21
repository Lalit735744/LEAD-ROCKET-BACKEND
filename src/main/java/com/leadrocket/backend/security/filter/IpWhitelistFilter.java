package com.leadrocket.backend.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Set;

/**
 * Simple IP whitelist filter that restricts access to allowed IPs.
 * For convenience, registration endpoints are bypassed so local clients can create users.
 */
public class IpWhitelistFilter extends OncePerRequestFilter {

	private static final Set<String> ALLOWED_IPS = Set.of(
			"127.0.0.1",
			"0:0:0:0:0:0:0:1" // IPv6 loopback for local testing
	);

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
				throws java.io.IOException, jakarta.servlet.ServletException {

		String path = req.getRequestURI();
		String method = req.getMethod();
		if ("OPTIONS".equalsIgnoreCase(method) || isPublicRegistrationPath(path, method)) {
			chain.doFilter(req, res);
			return;
		}

		if (!ALLOWED_IPS.contains(req.getRemoteAddr())) {
			res.setStatus(403);
			return;
		}
		chain.doFilter(req, res);
	}

	private boolean isPublicRegistrationPath(String path, String method) {
		if (method == null) return false;
		boolean isPostOrGet = "POST".equalsIgnoreCase(method) || "GET".equalsIgnoreCase(method);
		if (!isPostOrGet) return false;
		return path != null && (path.startsWith("/api/users") || path.startsWith("/api/v1/users")
				|| path.startsWith("/api/signup") || path.startsWith("/api/v1/signup"));
	}
}
