# Hospital Management System (HMS)

A comprehensive Spring Boot-based Hospital Management System with role-based authentication, appointment scheduling, medical records management, and PDF report generation.

## Features

### Role-Based Authentication
- **Admin**: Manage doctors, patients, and view all appointments
- **Doctor**: View patients, add medical records & prescriptions, manage appointments
- **Patient**: Book appointments, view prescriptions & medical records, manage profile

### Core Functionality
- **Appointments**: Book, view, cancel appointments with status tracking (PENDING, CONFIRMED, COMPLETED, CANCELLED)
- **Medical Records**: Doctors can add diagnoses and notes for patients
- **Prescriptions**: Create prescriptions with medicines, dosage, and instructions
- **PDF Reports**: Download prescriptions and medical records as PDF
- **Profile Management**: Edit personal details (patients and doctors)

### Technical Stack
- **Backend**: Spring Boot 3.2.3 with Spring Data JPA & Hibernate
- **Database**: PostgreSQL (localhost:5432/hms)
- **Frontend**: Thymeleaf templates with Tailwind CSS
- **PDF Generation**: iTextPDF
- **Build Tool**: Maven 3.9.6

## Project Structure

```
HospitalManagementSystem/
├── src/main/java/com/hospital/
│   ├── model/           # Entity classes
│   │   ├── Role.java    # ADMIN, DOCTOR, PATIENT enum
│   │   ├── User.java    # Authentication & role
│   │   ├── Patient.java # Patient details
│   │   ├── Doctor.java  # Doctor details & specialty
│   │   ├── Appointment.java
│   │   ├── MedicalRecord.java
│   │   └── Prescription.java
│   ├── repository/      # Data access layer
│   │   ├── UserRepository.java
│   │   ├── PatientRepository.java
│   │   ├── DoctorRepository.java
│   │   ├── AppointmentRepository.java
│   │   ├── MedicalRecordRepository.java
│   │   └── PrescriptionRepository.java
│   ├── service/         # Business logic layer
│   │   ├── UserService.java
│   │   ├── PatientService.java
│   │   ├── DoctorService.java
│   │   ├── MedicalRecordService.java
│   │   ├── PrescriptionService.java
│   │   └── impl/        # Implementations
│   ├── controller/     # Presentation layer
│   │   ├── HomeController.java
│   │   ├── AuthController.java
│   │   ├── AdminController.java
│   │   ├── DoctorController.java
│   │   └── PatientController.java
│   └── config/          # Configuration
│       └── DataSeeder.java  # Demo data
├── src/main/resources/
│   ├── application.properties
│   └── templates/       # Thymeleaf HTML
│       ├── login.html
│       ├── register.html
│       ├── admin/
│       ├── doctor/
│       └── patient/
└── target/             # Build output
```

## Getting Started

### Prerequisites
- Java 17+
- PostgreSQL (or use auto-created database)
- Maven 3.9.6 (included in project)

### Database Setup

**Option 1: Auto-created (Default)**
- PostgreSQL must be running on localhost:5432
- Database `hms` will be created automatically
- Default credentials: `postgres` / `admin`

**Option 2: Manual Setup**
```sql
CREATE DATABASE hms;
```

### Configuration

Edit `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/hms
spring.datasource.username=postgres
spring.datasource.password=admin
server.port=8081
```

### Build & Run

**Using Maven (included):**
```bash
# Build
./apache-maven-3.9.6/bin/mvn clean package -DskipTests

# Run
java -jar target/hospital-mvc-1.0.0-SNAPSHOT.jar
```

**Direct Maven:**
```bash
mvn clean package -DskipTests
mvn spring-boot:run
or
.\apache-maven-3.9.6\bin\mvn.cmd spring-boot:run
```

### Access the Application

Open browser: **http://localhost:8081/**

The app automatically redirects to `/auth/login`.

## Demo Accounts

| Role    | Username     | Password    |
|---------|--------------|-------------|
| Admin   | admin        | admin123    |
| Doctor  | dr_smith     | doctor123   |
| Patient| john         | patient123  |

## User Guide

### Admin Dashboard
- View statistics (total doctors, patients, appointments)
- Manage doctors (add, edit, delete)
- Manage patients (add, edit, delete)
- View all appointments

### Doctor Dashboard
- View and confirm/complete appointments
- View patient list
- Add medical records
- Add prescriptions
- Download PDF reports
- View past records and prescriptions

### Patient Dashboard
- View and edit profile
- Book new appointments
- View appointment history
- Cancel pending appointments
- View prescriptions and download PDF
- View medical records and download PDF

## UI/UX Features

- **Figtree Font**: Clean, accessible healthcare-appropriate typography
- **Color Scheme**: Cyan + Green healthcare palette
- **Responsive Design**: Works on mobile, tablet, and desktop
- **SVG Icons**: Professional Heroicons-style icons
- **Smooth Transitions**: 150-300ms hover effects
- **Status Badges**: Color-coded appointment status

## API Endpoints

### Authentication
- `GET /` - Redirect to login
- `GET /auth/login` - Login page
- `POST /auth/login` - Login processing
- `GET /auth/register` - Registration page
- `POST /auth/register` - Registration processing
- `GET /auth/logout` - Logout

### Patient
- `GET /patient/dashboard` - Patient dashboard
- `GET /patient/profile` - View/edit profile
- `POST /patient/profile/update` - Update profile
- `GET /patient/book` - Book appointment
- `POST /patient/book` - Submit appointment
- `GET /patient/appointments` - View appointments
- `POST /patient/appointments/cancel/{id}` - Cancel appointment
- `GET /patient/prescriptions` - View prescriptions
- `GET /patient/prescriptions/download/{id}` - Download PDF
- `GET /patient/records` - View medical records
- `GET /patient/records/download/{id}` - Download PDF

### Doctor
- `GET /doctor/dashboard` - Doctor dashboard
- `GET /doctor/appointments` - View appointments
- `POST /doctor/appointments/confirm/{id}` - Confirm
- `POST /doctor/appointments/complete/{id}` - Complete
- `GET /doctor/patients` - View patients
- `GET /doctor/records/add` - Add medical record
- `POST /doctor/records/add` - Submit record
- `GET /doctor/prescriptions/add` - Add prescription
- `POST /doctor/prescriptions/add` - Submit prescription
- `GET /doctor/records` - View all records
- `GET /doctor/prescriptions` - View all prescriptions

### Admin
- `GET /admin/dashboard` - Admin dashboard
- `GET /admin/doctors` - Manage doctors
- `GET /admin/doctors/add` - Add doctor form
- `POST /admin/doctors/add` - Add doctor
- `GET /admin/doctors/edit/{id}` - Edit doctor
- `POST /admin/doctors/edit/{id}` - Update doctor
- `POST /admin/doctors/delete/{id}` - Delete doctor
- `GET /admin/patients` - Manage patients
- `GET /admin/patients/add` - Add patient form
- `POST /admin/patients/add` - Add patient
- `GET /admin/patients/edit/{id}` - Edit patient
- `POST /admin/patients/edit/{id}` - Update patient
- `POST /admin/patients/delete/{id}` - Delete patient
- `GET /admin/appointments` - View all appointments

## Development Notes

### Database Auto-Creation
The application automatically creates the database on startup if it doesn't exist. This is handled in `HospitalApplication.java` via JDBC before Spring context loads.

### Thymeleaf URL Syntax
Important: When generating URLs with path variables, use string concatenation:
```html
<!-- Correct -->
<a th:href="@{'/path/' + ${id}}">Link</a>

<!-- Incorrect (won't work) -->
<a th:href="@{/path/{id}(id=${id})}">Link</a>
```

### Session-Based Auth
Uses simple HttpSession (no Spring Security) for educational purposes.

## License

This project is for educational purposes.

## Acknowledgments

- Spring Boot Documentation
- Thymeleaf Documentation
- Tailwind CSS
- iTextPDF