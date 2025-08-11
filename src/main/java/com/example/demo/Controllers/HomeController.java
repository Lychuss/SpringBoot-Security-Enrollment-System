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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path= "/api/auth/home")
@Tag(name= "Home API", description= "This is the face of the website, everyone has access")
public class HomeController {

	private final ApplicationService service;
	
	@GetMapping
	public ResponseEntity<String> welcome(){
		return ResponseEntity.ok("Welcome you need to login");
	}
	
	@PostMapping("/user/signup")
	@Operation(summary= "If you want to sign up as user to be converted into student")
	public ResponseEntity<String> signUpUser(@RequestBody  RegisterRequest request) {
		return ResponseEntity.ok(service.registerUser(request));
	}
	
	@PostMapping("/admin/signup")
	@Operation(summary= "This is if you are a teacher of the school")
	public ResponseEntity<String> signUpAdmin(@RequestBody  RegisterRequest request) {
		return ResponseEntity.ok(service.registerAdmin(request));
	}
	
	@PostMapping("/login")
	@Operation(summary= "Whether you are user or admin you must login here")
	public ResponseEntity<String> login(@RequestBody LoginRequest request){
		return ResponseEntity.ok(service.login(request));
	}
	
}
