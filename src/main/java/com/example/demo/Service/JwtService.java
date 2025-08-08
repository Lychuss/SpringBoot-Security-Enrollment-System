package com.example.demo.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private final String SECRET_KEY = "ffbab3fe5a8bc87efdeda1fb3917b0d2133806b6a2727a02c3c34ce01c05c17c";
	
//
	public Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}

//The token provided will be converted to
//username if the token is valid
	public Claims extractAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
//Provide you a value based on the token you give
//and return a value based on your input whether 
//string, boolean, integer
	public <T> T extractClaim (String token, Function<Claims, T> claimsResolver) {
		final Claims claim = extractAllClaims(token);
		return claimsResolver.apply(claim);
	}
	
//Use the extractClaim to extract the token
//in terms of expiration date
	private Date extractExpirationDate(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
//Use the extractExpirationDate to determine whether 
//the token is expired using extractClaim
	private boolean isTokenExpired(String token) {
		return extractExpirationDate(token).before(new Date());
	}
	
//Uses the token to extractClaim and return if it valid
//and a user uses it 
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

//After signing up, a user is generated a token
//this can be used to know if a current user
//is signup
	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return Jwts
				.builder()
				.setClaims(extraClaims)
				.setSubject(userDetails.getUsername())
				.claim("role", userDetails.getAuthorities().iterator().next().getAuthority())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
//This is where the sign up data converted into tokens	
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}

//Check if the token is valid like if its use to identify a real user
//and check if its not expired
	public boolean isTokenValid(String token, UserDetails userDetails) {
		String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}
}
