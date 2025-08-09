package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.Model.Course;
import com.example.demo.Model.Enrollment;
import com.example.demo.Model.LoginRequest;
import com.example.demo.Model.RegisterRequest;
import com.example.demo.Model.Student;
import com.example.demo.Repository.CourseRepository;
import com.example.demo.Service.ApplicationService;
import com.example.demo.Service.JwtService;

import lombok.extern.slf4j.Slf4j;


@SpringBootTest
@Slf4j
class DemoApplicationTests {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ApplicationService appService;
    
    private UserDetails userDetailsAdmin;
    private UserDetails userDetailsUser;
    private Course course;
    private Student student;
    private RegisterRequest requestUser;
    private RegisterRequest requestAdmin;
    private LoginRequest loginAdmin;
    private LoginRequest loginUser;
    private Enrollment enrollees;

    @BeforeEach
    void setUp() {    	
    	loginAdmin = LoginRequest.builder()
    			.email("lykaabad6@gmail.com")
    			.password("1234")
    			.build();
    	
    	loginUser = LoginRequest.builder()
    			.email("raphaelsanjuan6@gmail.com")
    			.password("1234")
    			.build();
    	
        requestUser = RegisterRequest.builder()
                .firstname("raphael")
                .lastname("san juan")
                .email("raphaelsanjuan6@gmail.com")
                .password("1234")
                .build();
        
        requestAdmin = RegisterRequest.builder()
                .firstname("Lyka")
                .lastname("Abad")
                .email("lykaabad6@gmail.com")
                .password("1234")
                .build();

        userDetailsAdmin = User.builder()
        		.username("lykaabad6@gmail.com")
        		.password("1234")
        		.authorities(createAuthorityList("ROLE_ADMIN"))
        		.build();
        
        userDetailsUser = User.builder()
                .username("raphaelsanjuan6@gmail.com")
                .password("1234")
                .authorities(createAuthorityList("ROLE_USER"))
                .build();

        course = Course.builder()
                .title("Information Technology")
                .description("Focus on networking and coding")
                .build();
        
        student = Student.builder()
        		.email("raphaelsanjuan6@gmail.com")
        		.gender("Male")
        		.password("1234")
        		.build();
        
    	enrollees = Enrollment.builder()
    			.student(student)
    			.course(course)
    			.build();
    }

	private GrantedAuthority createAuthorityList(String string) {
		return new SimpleGrantedAuthority(string);
	}

	@Test
    void testSignUp() {
        String test = appService.registerUser(requestUser);
        assertTrue(
        		"Email is already sign up!".equals(test) || "Sign up successfully".equals(test),
        		"Test must be either already sign up or sing up successfully");
    }

    @Test
    void testExtractUsername() {
        String token = jwtService.generateToken(userDetailsUser);
        String username = jwtService.extractUsername(token);
        assertEquals("raphaelsanjuan6@gmail.com", username);
    }

    @Test
    void testAddCourse() {
        appService.addCourse(course);
        Optional<Course> courses = courseRepository.findCourseByTitle(course.getTitle());
        assertTrue(courses.isPresent());
        assertEquals(course.getTitle(), courses.get().getTitle());
    }
    
    @Test
    void testLogin() {
    	String token = appService.login(loginUser);
    	assertTrue(!token.isEmpty());
    }
    
    @Test
    void testApplyStudent() {
    	boolean apply = appService.applyStudent(student);
    	assertTrue(apply);
    }
    
    @Test
    void testEnrollStudent() {
    	boolean enroll = appService.enrollStudents(1L, 1L);
    	assertTrue(enroll);
    }
}
