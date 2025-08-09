package com.example.demo.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
	    name = "enrollees",
	    uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "course_id"})
	)
public class Enrollment {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name= "student_id")
	private Student student;
	
	@ManyToOne
	@JoinColumn(name= "course_id")
	private Course course;
	
	public Enrollment(Student student, Course course) {
		this.student = student;
		this.course = course;
	}
}
