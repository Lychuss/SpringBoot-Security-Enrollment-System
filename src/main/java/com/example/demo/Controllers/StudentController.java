package com.example.demo.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Student;
import com.example.demo.Service.ApplicationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path= "/api/student")
@Tag(name= "Student API", description="All user who whats to be a student can only access")
public class StudentController {
	
	private final ApplicationService service;
	
	@PostMapping("/applystudent")
	@Operation(summary= "A user can apply for a student")
	public ResponseEntity<String> applyStudent(@RequestBody Student student){
		boolean x = service.applyStudent(student);
		if(x) {
			return ResponseEntity.ok("You are now a student!");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email is not an active user or you are an Admin!");
	}
}
