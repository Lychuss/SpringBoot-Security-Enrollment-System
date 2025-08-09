package com.example.demo.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Course;
import com.example.demo.Service.ApplicationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path= "/api/admin")
public class AdminController {

	private final ApplicationService service;
	
	@GetMapping
	public String helloAdmin() {
		return "Welcome to admin";
	}
	
	@PostMapping("/enrollstudent")
	public ResponseEntity<String> enrollStudent(@RequestParam Long student_id, @RequestParam Long course_id){
		boolean x = service.enrollStudents(student_id, course_id);
		if(x) {
			return ResponseEntity.ok("You enrolled a student!");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot enrolled same student in same course");
	}
	
	@PostMapping("/addcourse")
	public ResponseEntity<String> addCourse(@RequestBody Course course){
		boolean x = service.addCourse(course);
		if(x) {
			return ResponseEntity.ok("Added successfully");
		} 
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The course is already added!");
	}
}
