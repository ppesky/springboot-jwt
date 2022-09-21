package io.ppesky.auth.config.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {

	private String ACCESS_SECRET_TXT  = "lmjn-U90c4uNip%fsaSf*skd12345678";
	private String REFRESH_SECRET_TXT = "ln3129fd89f(cffa@kf05g0kd8765432";
	
	private Key accessKey;
	private Key refreshKey;

	private final long ACCESS_TOKEN_VALID_TIME  = 10 * 60 * 1000L;   // 10분
	private final long REFRESH_TOKEN_VALID_TIME = 60 * 60 * 24 * 7 * 1000L;   // 1주

//	@Autowired UserDetailsService userDetailsService;

	@PostConstruct
	protected void init() {
		accessKey  = Keys.hmacShaKeyFor(ACCESS_SECRET_TXT.getBytes(StandardCharsets.UTF_8));
		refreshKey = Keys.hmacShaKeyFor(REFRESH_SECRET_TXT.getBytes(StandardCharsets.UTF_8));
	}
	
	public String createAccessToken(String userId) {
		Claims claims = Jwts.claims().setIssuer("ppesky");
		claims.put("user-uid", userId);
		
		Date now = new Date();
		return Jwts.builder()
				.setClaims(claims) // payloads
				.setIssuedAt(now) // 토큰 발행일자
				.setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME))
				.signWith(accessKey, SignatureAlgorithm.HS256)
				.compact();
	}
	public Claims getClaimsAccessToken(String token) {
		try {
	        return Jwts.parserBuilder()
	                .setSigningKey(accessKey)
	                .build()
	                .parseClaimsJws(token)
	                .getBody();
		} catch(ExpiredJwtException e) {
			log.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This token has expired.");
		} catch(UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
			log.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This token is not valid.");
		}
    }
	
	public String createRefreshToken(String userId) {
		Claims claims = Jwts.claims().setIssuer("ppesky");
		claims.put("user-uid", userId);
        
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_VALID_TIME))
                .signWith(refreshKey, SignatureAlgorithm.HS256)
                .compact();
    }
	public Claims getClaimsRefreshToken(String token) {
		try {
	        return Jwts.parserBuilder()
	                .setSigningKey(refreshKey)
	                .build()
	                .parseClaimsJws(token)
	                .getBody();
		} catch(ExpiredJwtException e) {
			log.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This token has expired.");
		} catch(UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
			log.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This token is not valid.");
		}
    }
}
