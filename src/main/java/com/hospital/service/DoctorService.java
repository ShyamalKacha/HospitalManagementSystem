package com.hospital.service;

import com.hospital.model.Doctor;
import java.util.List;

public interface DoctorService {
    List<Doctor> getAllDoctors();
    Doctor saveDoctor(Doctor doctor);
    void deleteDoctorByName(String name);
    List<Doctor> searchDoctors(String keyword);
    boolean updateDoctorSpecialty(String name, String newSpecialty);
}
