package com.example.demo.ErrorHandler;

public class UserNotFoundException extends RuntimeException{

	public UserNotFoundException (String message) {
		super(message);
	}
}
