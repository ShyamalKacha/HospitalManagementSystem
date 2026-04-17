package com.hospital.service;

import com.hospital.model.Appointment;
import java.util.List;

public interface AppointmentService {
    List<Appointment> getAllAppointments();
    Appointment saveAppointment(Appointment appointment);
    void deleteAppointmentByPatientName(String patientName);
    List<Appointment> searchAppointments(String keyword);
    boolean updateAppointment(String patientName, String newDate, String newDoctor);
}
