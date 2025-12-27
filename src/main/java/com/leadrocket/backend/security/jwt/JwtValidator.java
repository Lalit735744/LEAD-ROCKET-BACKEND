package com.leadrocket.backend.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {

	@Value("${security.jwt.secret}")
	private String secret;

	public String resolveToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if (header == null || !header.startsWith("Bearer ")) {
			return null;
		}
		return header.substring(7);
	}

	public boolean validate(String token) {
		try {
			Jwts.parserBuilder()
					.setSigningKey(secret.getBytes())
					.build()
					.parseClaimsJws(token);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	private Claims claims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(secret.getBytes())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	public String getUserId(String token) {
		return claims(token).getSubject();
	}

	public String getCompanyId(String token) {
		return claims(token).get("companyId", String.class);
	}
}
