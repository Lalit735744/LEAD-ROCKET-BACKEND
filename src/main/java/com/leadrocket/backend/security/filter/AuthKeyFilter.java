package com.leadrocket.backend.security.filter;

import com.leadrocket.backend.common.exception.UnauthorizedException;
import com.leadrocket.backend.security.authkey.AuthKeyService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

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

		String authKey = request.getHeader("AuthKey");
		if (!authKeyService.isValid(authKey)) {
			throw new UnauthorizedException("Invalid Auth Key");
		}
		filterChain.doFilter(request, response);
	}
}
