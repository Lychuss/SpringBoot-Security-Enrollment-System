package com.example.demo.Security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.Service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	private final JwtService service;
	private final UserDetailsService userDetailsSerivce;
	
	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request,
			@NonNull HttpServletResponse response, 
			@NonNull FilterChain filterChain)
			throws ServletException, IOException {
		
	    String path = request.getServletPath();
	    if (path.startsWith("/swagger-ui") ||
	        path.startsWith("/v3/api-docs") ||
	        path.startsWith("/swagger-resources") ||
	        path.startsWith("/webjars")) {
	        filterChain.doFilter(request, response);
	        return;
	    }
	    
		String authHeader = request.getHeader("Authorization");
		String jwt;
		String userEmail;
		
		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		jwt = authHeader.substring(7);
		userEmail = service.extractUsername(jwt);
		Claims claim = Jwts.parserBuilder()
				.setSigningKey(service.getSignInKey())
				.build()
				.parseClaimsJws(jwt)
				.getBody();
		
		if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsSerivce.loadUserByUsername(userEmail);
			if(service.isTokenValid(jwt, userDetails)) {
				String role = claim.get("role", String.class);
				List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
				auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
		filterChain.doFilter(request, response);
	}
}
