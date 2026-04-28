package com.hospital.config;

import com.hospital.model.*;
import com.hospital.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PatientRepository patientRepository;
    
    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword("admin123");
            adminUser.setRole(Role.ADMIN);
            adminUser.setEnabled(true);
            userRepository.save(adminUser);
            
            User doctorUser1 = new User();
            doctorUser1.setUsername("dr_smith");
            doctorUser1.setPassword("doctor123");
            doctorUser1.setRole(Role.DOCTOR);
            doctorUser1.setEnabled(true);
            userRepository.save(doctorUser1);
            
            Doctor doctor1 = new Doctor();
            doctor1.setName("Dr. Smith");
            doctor1.setSpecialty("Cardiology");
            doctor1.setPhone("555-0101");
            doctor1.setEmail("smith@hospital.com");
            doctor1.setUser(doctorUser1);
            doctorRepository.save(doctor1);
            
            User doctorUser2 = new User();
            doctorUser2.setUsername("dr_jones");
            doctorUser2.setPassword("doctor123");
            doctorUser2.setRole(Role.DOCTOR);
            doctorUser2.setEnabled(true);
            userRepository.save(doctorUser2);
            
            Doctor doctor2 = new Doctor();
            doctor2.setName("Dr. Jones");
            doctor2.setSpecialty("General");
            doctor2.setPhone("555-0102");
            doctor2.setEmail("jones@hospital.com");
            doctor2.setUser(doctorUser2);
            doctorRepository.save(doctor2);
            
            User patientUser = new User();
            patientUser.setUsername("john");
            patientUser.setPassword("patient123");
            patientUser.setRole(Role.PATIENT);
            patientUser.setEnabled(true);
            userRepository.save(patientUser);
            
            Patient patient = new Patient();
            patient.setName("John Doe");
            patient.setAge(30);
            patient.setGender("Male");
            patient.setPhone("555-1000");
            patient.setEmail("john@email.com");
            patient.setAddress("123 Main St");
            patient.setUser(patientUser);
            patientRepository.save(patient);
            
            System.out.println("=== Database seeded with demo accounts ===");
            System.out.println("Admin: admin / admin123");
            System.out.println("Doctor: dr_smith / doctor123");
            System.out.println("Doctor: dr_jones / doctor123");
            System.out.println("Patient: john / patient123");
        }
    }
}