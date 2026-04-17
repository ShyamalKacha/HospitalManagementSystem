# Practical 9: Spring Boot Multi-Tier MVC Architecture

`PR9` completes the transition of the application from legacy Servlets into the industry-standard **Spring Boot Ecosystem**, fully enforcing Strict Interface Segregation across a Multi-Tier architecture.

## Architecture

**1. Data Access Layer:** `com.hospital.repository`
Contains Spring Data `JpaRepository` interfaces capable of automatically generating complex SQL queries instantly.

**2. Business Logic Layer:** `com.hospital.service`
Splits the codebase using defining Interfaces (`PatientService`) and implementing their concrete behavior securely inside `PatientServiceImpl`.

**3. Presentation MVC Layer:** `com.hospital.controller`
Uses exactly one `@Controller` that processes all web traffic. Crucially, the controller knows *nothing* about the database; it utilizes `@Autowired` to talk strictly to the Business Interfaces.

**4. View Layer:** `src/main/resources/templates`
Replaces JSF entirely with **Thymeleaf HTML** templates.

## Running the Application
Because this is a standard Maven build, compilation and Tomcat deployment happen **automatically** with a single command!
*(Note: Because this is a fresh Spring project, Maven will briefly download the Spring framework dependencies upon first execution)*

1. Open a terminal to the `PR9` folder.
2. Run the application:
.\apache-maven-3.9.6\bin\mvn.cmd spring-boot:run
```
3. Open your browser and navigate to `http://localhost:8080/`. The embedded Apache Tomcat server will serve the new multi-tier Dashboard!
