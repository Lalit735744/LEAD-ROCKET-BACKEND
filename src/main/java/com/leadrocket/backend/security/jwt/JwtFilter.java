package com.leadrocket.backend.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws java.io.IOException, jakarta.servlet.ServletException {

		String token = req.getHeader("Authorization");
		if (token == null || !token.startsWith("Bearer ")) {
			res.setStatus(401);
			return;
		}
		chain.doFilter(req, res);
	}
}
