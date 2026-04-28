package com.hospital.repository;

import com.hospital.model.Doctor;
import com.hospital.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    List<Doctor> findByNameContainingIgnoreCase(String name);
    Doctor findByNameIgnoreCase(String name);
    Optional<Doctor> findByUser(User user);
    List<Doctor> findBySpecialty(String specialty);
}