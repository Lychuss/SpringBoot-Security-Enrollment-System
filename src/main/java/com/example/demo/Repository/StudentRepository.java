package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{

	@Query("SELECT s FROM Student s WHERE s.id =?1")
	Optional<Student> findStudentById (Long id);
}
