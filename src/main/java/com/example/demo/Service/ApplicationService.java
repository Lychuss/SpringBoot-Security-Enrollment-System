package com.example.demo.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.ErrorHandler.CourseNotFoundException;
import com.example.demo.ErrorHandler.UserNotFoundException;
import com.example.demo.Model.AccountDTO;
import com.example.demo.Model.Course;
import com.example.demo.Model.Enrollment;
import com.example.demo.Model.LoginRequest;
import com.example.demo.Model.RegisterRequest;
import com.example.demo.Model.Role;
import com.example.demo.Model.Student;
import com.example.demo.Model.StudentDTO;
import com.example.demo.Model.User;
import com.example.demo.Repository.CourseRepository;
import com.example.demo.Repository.EnrollmentRepository;
import com.example.demo.Repository.StudentRepository;
import com.example.demo.Repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApplicationService {
	
	
	private final UserRepository userRepository;
	private final CourseRepository courseRepository;
	private final EnrollmentRepository enrollmentRepository;
	private final JwtService service;
	private final PasswordEncoder encoder;
	private final AuthenticationManager authenticationManager;
	private final StudentRepository studentRepository;

	public String registerUser(RegisterRequest request) {
		var user = User.builder()
				.firstname(request.getFirstname())
				.lastname(request.getLastname())
				.email(request.getEmail())
				.password(encoder.encode(request.getPassword()))
				.role(Role.USER)
				.build();
		var checkUser = userRepository.findUserByEmail(request.getEmail());
		if(checkUser.isEmpty()) {
			userRepository.save(user);
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
		var checkUser = userRepository.findUserByEmail(request.getEmail());
		if(checkUser.isEmpty()) {
			userRepository.save(user);
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
		var user = userRepository.findUserByEmail(request.getEmail()).orElseThrow(
			() -> new UserNotFoundException("Incorrect email"));
			var token = service.generateToken(user);
			return token;
	  } catch (BadCredentialsException e) {
		  return "Incorrect password";
	  }
	}
	
	public List<AccountDTO> showAllUser() {
	  List<User> users = userRepository.findAll();
	  List<AccountDTO> dto = new ArrayList<>();
	  for(User i : users) {
		  AccountDTO user = new AccountDTO(i.getFirstname(), i.getLastname(), i.getEmail());
		  dto.add(user);
	  }
	  return dto;
	}
	
	public boolean addCourse(Course course) {
		Optional<Course> courses = courseRepository.findCourseByTitle(course.getTitle());
		if(!courses.isPresent()) {
			courseRepository.save(course);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean applyStudent(Student student) {
		User user = userRepository.findUserByEmail(student.getEmail()).orElseThrow(
			() -> new UserNotFoundException("Email is not an active user: " + student.getEmail()));
	try {	
		authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken (student.getEmail(), student.getPassword()));
		if(!user.getRole().equals(Role.ADMIN)) {
			return true;
		}
	} catch (BadCredentialsException e) {
		log.error("Incorrect Password");
	}
		return false;
	}
	
	public boolean enrollStudents(Long student_id, Long course_id) {
		Student student =	studentRepository.findStudentById(student_id).orElseThrow(
			() -> new UserNotFoundException("User not found id: " + student_id));
		Course course = courseRepository.findCourseById(course_id).orElseThrow(
			() -> new CourseNotFoundException("Course not found id: " + course_id));
		List<Enrollment> enrollment = enrollmentRepository.findAll();
		Enrollment enrollees = new Enrollment(student, course);
		for(Enrollment i : enrollment) {
			if(i == enrollees) {
				return false;
			}
		}
		enrollmentRepository.save(enrollees);
		return true;
	}
	
	public List<StudentDTO> getStudents(Long course_id) {
		List<Student> students = enrollmentRepository.findUserById(course_id);
		List<StudentDTO> studentsDTO = new ArrayList<>();
		for(Student i : students) {
			StudentDTO student = new StudentDTO(i.getEmail(), i.getGender());
			studentsDTO.add(student);
		}
		return studentsDTO;
	}
	
	public List<Course> getCourses(Long student_id) {
		List<Course> courses = enrollmentRepository.findCourseById(student_id);
		return courses;
	}
	
	public boolean deleteCourse(Long course_id) {
		Optional<Course> course = courseRepository.findCourseById(course_id);
		if(course.isPresent()) {
			enrollmentRepository.deleteCourseById(course_id.intValue());
			courseRepository.deleteById(course_id);
			return true;
		}
		return false;
	}
}
