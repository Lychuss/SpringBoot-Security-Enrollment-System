package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.Service.JwtService;

@SpringBootTest
class DemoApplicationTests {
	
	private JwtService jwtService;
	private UserDetails userDetails;

	@BeforeEach
	void setUp() {
		jwtService = new JwtService();
		userDetails = User.builder()
				.username("raphael")
				.password("12345")
				.build();
	}
	
	@Test
	void testExtractUsername() {
		String token = jwtService.generateToken(userDetails);
		String username = jwtService.extractUsername(token);
		assertEquals("raphael", username);
	}

}
