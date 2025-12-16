package com.leadrocket.backend.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtFilter extends OncePerRequestFilter {

	private final JwtProvider jwtProvider;

	public JwtFilter(JwtProvider jwtProvider) {
		this.jwtProvider = jwtProvider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws java.io.IOException, jakarta.servlet.ServletException {

		String authHeader = req.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			res.setStatus(401);
			return;
		}
		String token = authHeader.substring(7);
		try {
			String userId = jwtProvider.getSubject(token);
			req.setAttribute("authenticatedUserId", userId);
		} catch (Exception ex) {
			res.setStatus(401);
			return;
		}
		chain.doFilter(req, res);
	}
}
