package com.hospital.service.impl;

import com.hospital.model.Appointment;
import com.hospital.repository.AppointmentRepository;
import com.hospital.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository repository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return repository.findAll();
    }

    @Override
    public Appointment saveAppointment(Appointment appointment) {
        return repository.save(appointment);
    }

    @Override
    @Transactional
    public void deleteAppointmentByPatientName(String patientName) {
        Appointment entity = repository.findByPatientNameIgnoreCase(patientName);
        if (entity != null) {
            repository.delete(entity);
        }
    }

    @Override
    public List<Appointment> searchAppointments(String keyword) {
        return repository.findByPatientNameContainingIgnoreCase(keyword);
    }

    @Override
    @Transactional
    public boolean updateAppointment(String patientName, String newDate, String newDoctor) {
        Appointment a = repository.findByPatientNameIgnoreCase(patientName);
        if (a != null) {
            a.setAppointmentDate(newDate);
            a.setDoctorName(newDoctor);
            repository.save(a);
            return true;
        }
        return false;
    }
}
