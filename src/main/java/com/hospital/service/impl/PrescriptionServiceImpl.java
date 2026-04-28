package com.hospital.service.impl;

import com.hospital.model.Prescription;
import com.hospital.model.Doctor;
import com.hospital.model.Patient;
import com.hospital.repository.PrescriptionRepository;
import com.hospital.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository repository;

    @Autowired
    public PrescriptionServiceImpl(PrescriptionRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Prescription> getAllPrescriptions() {
        return repository.findAll();
    }

    @Override
    public Prescription savePrescription(Prescription prescription) {
        return repository.save(prescription);
    }

    @Override
    public List<Prescription> getPatientPrescriptions(Patient patient) {
        return repository.findByPatientOrderByDateDesc(patient);
    }

    @Override
    public List<Prescription> getDoctorPrescriptions(Doctor doctor) {
        return repository.findByDoctor(doctor);
    }

    @Override
    public Prescription getPrescriptionById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void deletePrescription(Integer id) {
        repository.deleteById(id);
    }
}