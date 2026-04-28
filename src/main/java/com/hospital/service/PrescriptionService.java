package com.hospital.service;

import com.hospital.model.Prescription;
import com.hospital.model.Doctor;
import com.hospital.model.Patient;
import java.util.List;

public interface PrescriptionService {
    List<Prescription> getAllPrescriptions();
    Prescription savePrescription(Prescription prescription);
    List<Prescription> getPatientPrescriptions(Patient patient);
    List<Prescription> getDoctorPrescriptions(Doctor doctor);
    Prescription getPrescriptionById(Integer id);
    void deletePrescription(Integer id);
}