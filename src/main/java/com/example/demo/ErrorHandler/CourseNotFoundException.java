package com.example.demo.ErrorHandler;

public class CourseNotFoundException extends RuntimeException{

	public CourseNotFoundException(String message) {
		super(message);
	}
}
