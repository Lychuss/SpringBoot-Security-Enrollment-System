package com.example.demo.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.ErrorHandler.UserNotFoundException;
import com.example.demo.Model.AccountDTO;
import com.example.demo.Model.LoginRequest;
import com.example.demo.Model.RegisterRequest;
import com.example.demo.Model.Role;
import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationService {
	
	private final UserRepository repository;
	private final JwtService service;
	private final PasswordEncoder encoder;
	private final AuthenticationManager authenticationManager;

	public String registerUser(RegisterRequest request) {
		var user = User.builder()
				.firstname(request.getFirstname())
				.lastname(request.getLastname())
				.email(request.getEmail())
				.password(encoder.encode(request.getPassword()))
				.role(Role.USER)
				.build();
		var checkUser = repository.findUserByEmail(request.getEmail());
		if(checkUser.isEmpty()) {
			repository.save(user);
			return "Sign up successfully";
		} else {
			return "Email is already sign up!";
		}
	}
	
	public String registerAdmin(RegisterRequest request) {
		var user = User.builder()
				.firstname(request.getFirstname())
				.lastname(request.getLastname())
				.email(request.getEmail())
				.password(encoder.encode(request.getPassword()))
				.role(Role.ADMIN)
				.build();
		var checkUser = repository.findUserByEmail(request.getEmail());
		if(checkUser.isEmpty()) {
			repository.save(user);
			return "Sign up successfully";
		} else {
			return "Email is already sign up!";
		}
	}
	
	
	public String login(LoginRequest request) {
	  try {
		authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(
				request.getEmail(), request.getPassword()));
		var user = repository.findUserByEmail(request.getEmail()).orElseThrow(
			() -> new UserNotFoundException("Incorrect email"));
			var token = service.generateToken(user);
			return token;
	  } catch (BadCredentialsException e) {
		  return "Incorrect password";
	  }
	}
	
	public List<AccountDTO> showAllUser() {
	  List<User> users = repository.findAll();
	  List<AccountDTO> dto = new ArrayList<>();
	  for(User i : users) {
		  AccountDTO user = new AccountDTO(i.getFirstname(), i.getLastname(), i.getEmail());
		  dto.add(user);
	  }
	  return dto;
	}
}
