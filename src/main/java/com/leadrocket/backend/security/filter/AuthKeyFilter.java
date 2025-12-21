package com.leadrocket.backend.security.filter;

import com.leadrocket.backend.common.exception.UnauthorizedException;
import com.leadrocket.backend.security.authkey.AuthKeyService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Servlet filter that validates presence and correctness of a custom AuthKey header.
 * Important: registration endpoints (/api/users and /api/v1/users) are bypassed so
 * users can be created without providing the AuthKey.
 */
public class AuthKeyFilter extends OncePerRequestFilter {

	private final AuthKeyService authKeyService;

	public AuthKeyFilter(AuthKeyService authKeyService) {
		this.authKeyService = authKeyService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
	                                HttpServletResponse response,
	                                FilterChain filterChain)
				throws ServletException, IOException {

		// allow public registration endpoints
		String path = request.getRequestURI();
		String method = request.getMethod();
		if (("/api/users".equals(path) || "/api/v1/users".equals(path)) && ("POST".equalsIgnoreCase(method) || "GET".equalsIgnoreCase(method))) {
			filterChain.doFilter(request, response);
			return;
		}

		String authKey = request.getHeader("AuthKey");
		if (!authKeyService.isValid(authKey)) {
			throw new UnauthorizedException("Invalid Auth Key");
		}
		filterChain.doFilter(request, response);
	}
}
