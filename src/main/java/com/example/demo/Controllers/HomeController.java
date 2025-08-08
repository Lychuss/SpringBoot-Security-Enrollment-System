package com.example.demo.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.LoginRequest;
import com.example.demo.Model.RegisterRequest;
import com.example.demo.Service.ApplicationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path= "/api/auth/home")
public class HomeController {

	private final ApplicationService service;
	
	@GetMapping
	public ResponseEntity<String> welcome(){
		return ResponseEntity.ok("Welcome you need to login");
	}
	
	@PostMapping("/user/signup")
	public ResponseEntity<String> signUpUser(@RequestBody  RegisterRequest request) {
		return ResponseEntity.ok(service.registerUser(request));
	}
	
	@PostMapping("/admin/signup")
	public ResponseEntity<String> signUpAdmin(@RequestBody  RegisterRequest request) {
		return ResponseEntity.ok(service.registerAdmin(request));
	}
	
	@PostMapping
	public ResponseEntity<String> login(@RequestBody LoginRequest request){
		return ResponseEntity.ok(service.login(request));
	}
	
}
