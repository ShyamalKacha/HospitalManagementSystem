package com.hospital.service;

import com.hospital.model.MedicalRecord;
import com.hospital.model.Doctor;
import com.hospital.model.Patient;
import java.util.List;

public interface MedicalRecordService {
    List<MedicalRecord> getAllRecords();
    MedicalRecord saveRecord(MedicalRecord record);
    List<MedicalRecord> getPatientRecords(Patient patient);
    List<MedicalRecord> getDoctorRecords(Doctor doctor);
    MedicalRecord getRecordById(Integer id);
    void deleteRecord(Integer id);
}