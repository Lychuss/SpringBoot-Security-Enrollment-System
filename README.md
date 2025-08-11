# Student Enrollment System - Spring Boot Backend

## 📌 Overview
This is a **Spring Boot backend application** for managing students, courses, and enrollments with **JWT-based authentication** and **role-based access control**.  
It supports **CRUD operations**, secure login/register, and student enrollment workflows.

Built in **5 days** as a portfolio project to showcase backend development skills.

---

## 🚀 Features
- **User Authentication & Authorization** using Spring Security & JWT
- **Role-Based Access Control** (`ROLE_USER`, `ROLE_ADMIN`)
- **Student & Course Management** with CRUD operations
- **Enrollment Management**
- **Validation** with `@Valid` and Hibernate Validator
- **Global Exception Handling** via `@ControllerAdvice`
- **Integration Tests** with JUnit & Spring Boot Test
- **Lombok** for reduced boilerplate
- **MySQL Database** with JPA/Hibernate

---

## 🛠️ Tech Stack
- **Java 21**
- **Spring Boot 3.5.4**
- **Spring Data JPA**
- **Spring Security**
- **JWT (io.jsonwebtoken)**
- **MySQL**
- **Lombok**
- **JUnit 5**
- **Maven**

---

## 📂 Project Structure
src/main/java/com/example/demo
│
├── controller # REST Controllers
├── service # Business logic layer
├── repository # JPA Repositories
├── model # Entities and DTOs
├── security # JWT and Security Configurations
└── exception # Global exception handler

## ⚙️ Installation & Setup

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/yourusername/student-enrollment-system.git
cd student-enrollment-system
```
applicationproperties.yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/enrollmentdb
    username: root
    password: yourpassword
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

Run Project:
mvn clean install
mvn spring-boot:run

---

🔑 API Endpoints
Auth
Method	Endpoint	Description
POST	/api/auth/register	Register a new user
POST	/api/auth/login	Login and get JWT

Courses
Method	Endpoint	Description
POST	/api/courses	Add a new course
GET	/api/courses	List all courses
DELETE	/api/courses/{id}	Delete a course

Enrollment
Method	Endpoint	Description
POST	/api/enroll/{studentId}/{courseId}	Enroll student in course
GET	/api/enrollments	List all enrollments

---

🧪 Running Tests
mvn test
JUnit tests cover:

User registration

JWT token generation & extraction

Course creation

Student application & enrollment

---

📜 License
This project is for portfolio purposes and can be used for learning or reference.

💼 Author
Raphael Mharcus G. San Juan
📧 raphaelsanjuan6@gmail.com
💻 GitHub | LinkedIn

