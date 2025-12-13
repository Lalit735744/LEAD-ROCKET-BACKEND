package com.leadrocket.backend.security.filter;

import com.leadrocket.backend.security.authkey.AuthKeyValidator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthKeyFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request,
	                                HttpServletResponse response,
	                                FilterChain filterChain)
			throws ServletException, IOException {

		String authKey = request.getHeader("AuthKey");
		AuthKeyValidator.validate(authKey);
		filterChain.doFilter(request, response);
	}
}
