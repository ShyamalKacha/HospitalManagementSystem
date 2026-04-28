package com.hospital.service.impl;

import com.hospital.model.MedicalRecord;
import com.hospital.model.Doctor;
import com.hospital.model.Patient;
import com.hospital.repository.MedicalRecordRepository;
import com.hospital.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordRepository repository;

    @Autowired
    public MedicalRecordServiceImpl(MedicalRecordRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<MedicalRecord> getAllRecords() {
        return repository.findAll();
    }

    @Override
    public MedicalRecord saveRecord(MedicalRecord record) {
        return repository.save(record);
    }

    @Override
    public List<MedicalRecord> getPatientRecords(Patient patient) {
        return repository.findByPatientOrderByDateDesc(patient);
    }

    @Override
    public List<MedicalRecord> getDoctorRecords(Doctor doctor) {
        return repository.findByDoctor(doctor);
    }

    @Override
    public MedicalRecord getRecordById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void deleteRecord(Integer id) {
        repository.deleteById(id);
    }
}