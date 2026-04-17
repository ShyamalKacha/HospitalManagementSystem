package com.hospital.service;

import com.hospital.model.Patient;
import java.util.List;

public interface PatientService {
    List<Patient> getAllPatients();
    Patient savePatient(Patient patient);
    void deletePatientByName(String name);
    List<Patient> searchPatients(String keyword);
    boolean updatePatientAge(String name, int newAge);
}
