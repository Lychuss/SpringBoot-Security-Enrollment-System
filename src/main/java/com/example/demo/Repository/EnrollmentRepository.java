package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Course;
import com.example.demo.Model.Enrollment;
import com.example.demo.Model.Student;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long>{
	
	@Query("SELECT s FROM Enrollment s WHERE s.student.id = ?1")
		List<Enrollment> findEnrollmentById (Long id);
	
	@Query("SELECT s.student FROM Enrollment s WHERE s.course.id = ?1")
		List<Student> findUserById (Long id); 
	
	@Query("SELECT s.course FROM Enrollment s WHERE s.student.id = ?1")
		List<Course> findCourseById (Long id);
	
	@Query("SELECT s.student FROM Enrollment s WHERE s.course.id = ?1")
		Student findStudentById(Long id);
	
	@Query("SELECT s.course FROM Enrollment s WHERE s.student.id = ?1")
		Course findCoursesById(Long id);
}
