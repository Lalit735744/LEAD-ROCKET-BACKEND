package com.leadrocket.backend.security.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {

	private final String SECRET = "CHANGE_THIS_SECRET";

	public String validateAndGetUser(String token) {
		return Jwts.parser()
				.setSigningKey(SECRET)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
}
