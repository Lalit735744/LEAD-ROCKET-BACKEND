package com.leadrocket.backend.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * JWT servlet filter that validates the Authorization: Bearer <token> header.
 * If valid, it sets request attribute "authenticatedUserId" with the token subject
 * and "authenticatedCompanyId" if the token contains company id claim.
 * Registration endpoints are bypassed to allow public user creation.
 */
public class JwtFilter extends OncePerRequestFilter {

	private final JwtProvider jwtProvider;

	public JwtFilter(JwtProvider jwtProvider) {
		this.jwtProvider = jwtProvider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
				throws java.io.IOException, jakarta.servlet.ServletException {

		String path = req.getRequestURI();
		String method = req.getMethod();

		// Allow preflight and public registration/signup endpoints without JWT
		if ("OPTIONS".equalsIgnoreCase(method)
				|| isPublicRegistrationPath(path, method)) {
			chain.doFilter(req, res);
			return;
		}

		String authHeader = req.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			res.setStatus(401);
			return;
		}
		String token = authHeader.substring(7);
		try {
			String userId = jwtProvider.getSubject(token);
			req.setAttribute("authenticatedUserId", userId);
			String cid = jwtProvider.getCompanyId(token);
			if (cid != null) {
				req.setAttribute("authenticatedCompanyId", cid);
			}
		} catch (Exception ex) {
			res.setStatus(401);
			return;
		}
		chain.doFilter(req, res);
	}

	private boolean isPublicRegistrationPath(String path, String method) {
		// Allow paths used for public user creation or signup. Use prefix matching
		// so paths like /api/users, /api/users/ and /api/users/signup are allowed.
		if (method == null) return false;
		boolean isPostOrGet = "POST".equalsIgnoreCase(method) || "GET".equalsIgnoreCase(method);
		if (!isPostOrGet) return false;

		return path != null && (
			path.startsWith("/api/users") ||
			path.startsWith("/api/v1/users") ||
			path.startsWith("/api/signup") ||
			path.startsWith("/api/v1/signup")
		);
	}
}
