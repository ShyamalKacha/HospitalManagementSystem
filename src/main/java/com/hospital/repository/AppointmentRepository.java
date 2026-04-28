package com.hospital.repository;

import com.hospital.model.Appointment;
import com.hospital.model.Doctor;
import com.hospital.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByPatient(Patient patient);
    List<Appointment> findByDoctor(Doctor doctor);
    List<Appointment> findByStatus(String status);
    List<Appointment> findByDoctorAndStatus(Doctor doctor, String status);
    List<Appointment> findByPatientOrderByDateDesc(Patient patient);
    List<Appointment> findByDoctorOrderByDateDesc(Doctor doctor);
}