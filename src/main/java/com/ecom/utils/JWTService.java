package com.ecom.utils;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTService {

//	public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

	private String secretKey = "";

	public JWTService() {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
			SecretKey sk = keyGen.generateKey();
			secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

	}

	public String generateToken(String username) {

		Map<String, Object> claims = new HashMap<>();

		return Jwts.builder().claims().add(claims).subject(username).issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)).and().signWith(getKey()).compact();
	}

	private SecretKey getKey() {

		byte[] keyBytes = Decoders.BASE64.decode(secretKey);

		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String extractUserName(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		final Claims claims = extractAllClaims(token);

		return claimResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
	}

	public boolean validateToken(String token, UserDetails userDetails) {

		final String userName = extractUserName(token);

		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		return extractexpiration(token).before(new Date());
	}

	private Date extractexpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

}
