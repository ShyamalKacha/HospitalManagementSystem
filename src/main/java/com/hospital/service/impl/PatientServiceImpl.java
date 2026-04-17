package com.hospital.service.impl;

import com.hospital.model.Patient;
import com.hospital.repository.PatientRepository;
import com.hospital.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository repository;

    @Autowired
    public PatientServiceImpl(PatientRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Patient> getAllPatients() {
        return repository.findAll();
    }

    @Override
    public Patient savePatient(Patient patient) {
        return repository.save(patient);
    }

    @Override
    @Transactional
    public void deletePatientByName(String name) {
        Patient entity = repository.findByNameIgnoreCase(name);
        if (entity != null) {
            repository.delete(entity);
        }
    }

    @Override
    public List<Patient> searchPatients(String keyword) {
        return repository.findByNameContainingIgnoreCase(keyword);
    }

    @Override
    @Transactional
    public boolean updatePatientAge(String name, int newAge) {
        Patient p = repository.findByNameIgnoreCase(name);
        if (p != null) {
            p.setAge(newAge);
            repository.save(p);
            return true;
        }
        return false;
    }
}
