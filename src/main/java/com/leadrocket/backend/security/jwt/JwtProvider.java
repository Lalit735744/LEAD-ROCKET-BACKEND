package com.leadrocket.backend.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {

	@Value("${security.jwt.secret:CHANGE_THIS_SECRET}")
	private String secret;

	@Value("${security.jwt.accessTokenTtlMs:900000}")
	private long accessTokenTtlMs; // default 15 minutes

	@Value("${security.jwt.refreshTokenTtlMs:2592000000}")
	private long refreshTokenTtlMs; // default 30 days

	private SecretKey getKey() {
		return Keys.hmacShaKeyFor(secret.getBytes());
	}

	public String generateAccessToken(String userId) {
		return generateAccessToken(userId, null);
	}

	/**
	 * Generate an access token embedding user id as subject and optional company id as claim 'cid'.
	 */
	public String generateAccessToken(String userId, String companyId) {
		Date now = new Date();
		io.jsonwebtoken.JwtBuilder b = Jwts.builder()
			.setSubject(userId)
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + accessTokenTtlMs));
		if (companyId != null) {
			b.claim("cid", companyId);
		}
		return b.signWith(getKey(), SignatureAlgorithm.HS256).compact();
	}

	public String generateRefreshToken(String userId) {
		Date now = new Date();
		return Jwts.builder()
			.setSubject(userId)
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + refreshTokenTtlMs))
			.claim("typ", "refresh")
			.signWith(getKey(), SignatureAlgorithm.HS256)
			.compact();
	}

	public String getSubject(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(getKey())
			.build()
			.parseClaimsJws(token)
			.getBody()
			.getSubject();
	}

	public String getCompanyId(String token) {
		Object val = Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody().get("cid");
		return val == null ? null : val.toString();
	}
}
