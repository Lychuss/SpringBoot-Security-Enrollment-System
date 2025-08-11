package com.example.demo.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Course;
import com.example.demo.Model.StudentDTO;
import com.example.demo.Service.ApplicationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path= "/api/admin")
@Tag(name= "Admin API", description= "Only admin can see this API and use all controllers")
public class AdminController {

	private final ApplicationService service;
	
	@GetMapping
	public String helloAdmin() {
		return "Welcome to admin";
	}
	
	@PostMapping("/enrollstudent")
	@Operation(summary= "Admin can ernoll student by param")
	public ResponseEntity<String> enrollStudent(@RequestParam Long student_id, @RequestParam Long course_id){
		boolean x = service.enrollStudents(student_id, course_id);
		if(x) {
			return ResponseEntity.ok("You enrolled a student!");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot enrolled same student in same course");
	}
	
	@PostMapping("/addcourse")
	@Operation(summary= "Admin can access on adding course")
	public ResponseEntity<String> addCourse(@RequestBody Course course){
		boolean x = service.addCourse(course);
		if(x) {
			return ResponseEntity.ok("Added successfully");
		} 
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The course is already added!");
	}
	
	@PostMapping("/enrollees/course={id}/students")
	@Operation(summary= "Admin can show all students enroll in that course")
	public ResponseEntity<List<StudentDTO>> getStudents(@PathVariable Long id) {
		List<StudentDTO> students = service.getStudents(id);
		if(students != null) {
			return ResponseEntity.ok(students);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	
	@PostMapping("/enrollees/student={id}/courses")
	@Operation(summary= "Admin can see all courses of what the student applied for")
	public ResponseEntity<List<Course>> getCourses(@PathVariable Long id) {
		List<Course> course = service.getCourses(id);
		if(course != null) {
			return ResponseEntity.ok(course);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	
	@DeleteMapping("/courses/delete={id}")
	@Operation(summary= "Admin can delete courses")
	public ResponseEntity<String> deleteCourse(@PathVariable Long id) {
		boolean x = service.deleteCourse(id);
		if(x) {
			return ResponseEntity.ok("Course has been deleted");
		}
		return ResponseEntity.notFound().build();
	}
}
