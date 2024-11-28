# Student Management System - Microservices

A comprehensive student management system built using Spring Boot microservices architecture.

## Services

1. **Service Registry (Eureka Server)** - Port: 8761
   - Service discovery and registration

2. **Config Server** - Port: 8888
   - Centralized configuration management
   - Configuration stored in GitHub repository

3. **API Gateway** - Port: 8080
   - Single entry point for all services
   - Request routing and load balancing

4. **Student Service** - Port: 8081
   - Manages student personal information
   - PostgreSQL database (student_db)

5. **College Service** - Port: 8082
   - Manages academic records (marks and attendance)
   - PostgreSQL database (college_db)

6. **Email Service** - Port: 8083
   - Sends notifications to students and parents
   - Uses Gmail SMTP for sending emails

## Prerequisites

- Java 17
- PostgreSQL
- Maven
- Git

## Setup Instructions

1. Clone the repository:
   ```bash
   git clone [repository-url]
   cd student-management
   ```

2. Create PostgreSQL databases:
   - student_db
   - college_db

3. Update email configuration in email-service/application.yml:
   - Add your Gmail credentials
   - Use app-specific password for security

4. Start services in order:
   1. Service Registry
   2. Config Server
   3. API Gateway
   4. Other services (student, college, email)

## Testing

Import the provided Postman collection (`Student-Management-Postman-Collection.json`) to test all endpoints.

## Architecture

- Built with Spring Boot and Spring Cloud
- Microservices architecture
- Service discovery using Netflix Eureka
- Centralized configuration with Spring Cloud Config
- API Gateway using Spring Cloud Gateway
- Email notifications using Spring Mail and Thymeleaf templates

## Technologies Used

- Spring Boot 3.1.5
- Spring Cloud 2022.0.4
- PostgreSQL
- Maven
- Spring Mail
- Thymeleaf
- Lombok
