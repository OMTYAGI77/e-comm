package com.one.security.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.one.constants.StringConstants;
import com.one.service.impl.UserDetailsImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtils {

	@Value("${dt.jwt.secret-key}")
	private String jwtSecretKey;

	@Value("${dt.jwt.expiration-ms}")
	private int jwtExpirationMs;

	@Value("${dt.jwt.ref.expiration-ms}")
	private int jwtRefExpirationMs;

	public String generateAccessToken(Authentication authentication) {
		SecretKey key = Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		Claims claims = Jwts.claims().subject(userPrincipal.getUsername()).build();
		// claims.put(StringConstants.USERNAME, userPrincipal.getUsername());
		return Jwts.builder().claims(claims).issuedAt(new Date())
				.expiration(new Date((new Date()).getTime() + jwtExpirationMs))
				// .signWith(SignatureAlgorithm.HS512, jwtSecretKey).compact();
				.encryptWith(key, Jwts.ENC.A128CBC_HS256).compact();
	}

	public String generateAccessToken(String username) {
		SecretKey key = Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
		Claims claims = Jwts.claims().subject(username).build();
		claims.put(StringConstants.USERNAME, username);
		return Jwts.builder().claims(claims).issuedAt(new Date())
				.expiration(new Date((new Date()).getTime() + jwtExpirationMs))
				// .signWith(SignatureAlgorithm.HS512, jwtSecretKey).compact();
				.encryptWith(key, Jwts.ENC.A128CBC_HS256).compact();
	}

	public String generateRefreshToken(Authentication authentication) {
		SecretKey key = Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		Claims claims = Jwts.claims().subject(userPrincipal.getUsername()).build();
		// claims.put(StringConstants.USERNAME, userPrincipal.getUsername());
		return Jwts.builder().claims(claims).issuedAt(new Date())
				.expiration(new Date((new Date()).getTime() + jwtRefExpirationMs))
				// .signWith(SignatureAlgorithm.HS512, jwtSecretKey).compact();
				.encryptWith(key, Jwts.ENC.A128CBC_HS256).compact();
	}

	public String generateRefreshToken(String username) {
		SecretKey key = Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
		Claims claims = Jwts.claims().subject(username).build();
		// claims.put(StringConstants.USERNAME, username);
		return Jwts.builder().claims(claims).issuedAt(new Date())
				.expiration(new Date((new Date()).getTime() + jwtRefExpirationMs))
				// .signWith(SignatureAlgorithm.HS512, jwtSecretKey).compact();
				.encryptWith(key, Jwts.ENC.A128CBC_HS256).compact();
	}

	public String getUsernameFromToken(String token) {
		SecretKey key = Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
		Claims claims = Jwts.parser().decryptWith(key).build().parseEncryptedClaims(token).getPayload();
		return claims.getSubject();
	}

	public Date getIssuedAtFromJwtToken(String token) throws Exception {
		SecretKey key = Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
		return Jwts.parser().decryptWith(key).build().parseEncryptedClaims(token).getPayload().getIssuedAt();
	}

	public Date getExpirationFromToken(String token) {
		SecretKey key = Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
		return Jwts.parser().decryptWith(key).build().parseEncryptedClaims(token).getPayload().getExpiration();
	}

	public boolean validateToken(String token) {

		if (log.isDebugEnabled()) {
			log.debug("Executing validateToken(Token) ->");
		}

		try {
			SecretKey key = Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
			Jwts.parser().decryptWith(key).build().parseEncryptedClaims(token);
			return true;
		} catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
			throw new BadCredentialsException("INVALID_CREDENTIALS", ex);
		} catch (ExpiredJwtException ex) {
			throw ex;
		}
	}

	public String resolveToken(HttpServletRequest rq) {
		String bearerToken = rq.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}

}
