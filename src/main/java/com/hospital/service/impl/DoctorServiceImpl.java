package com.hospital.service.impl;

import com.hospital.model.Doctor;
import com.hospital.repository.DoctorRepository;
import com.hospital.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository repository;

    @Autowired
    public DoctorServiceImpl(DoctorRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return repository.findAll();
    }

    @Override
    public Doctor saveDoctor(Doctor doctor) {
        return repository.save(doctor);
    }

    @Override
    @Transactional
    public void deleteDoctorByName(String name) {
        Doctor entity = repository.findByNameIgnoreCase(name);
        if (entity != null) {
            repository.delete(entity);
        }
    }

    @Override
    public List<Doctor> searchDoctors(String keyword) {
        return repository.findByNameContainingIgnoreCase(keyword);
    }

    @Override
    @Transactional
    public boolean updateDoctorSpecialty(String name, String newSpecialty) {
        Doctor d = repository.findByNameIgnoreCase(name);
        if (d != null) {
            d.setSpecialty(newSpecialty);
            repository.save(d);
            return true;
        }
        return false;
    }
}