package com.example.demo.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.AccountDTO;
import com.example.demo.Service.ApplicationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path= "/api/user/inside")
public class UserController {

	private final ApplicationService service;
	
	@GetMapping
	public ResponseEntity<String> welcome(){
		return ResponseEntity.ok("Welcome you have login successfully!");
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<AccountDTO>> allUsers(){
		return ResponseEntity.ok(service.showAllUser());
	}
}
