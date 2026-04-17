package com.hospital.model;

import jakarta.persistence.*;

@Entity
@Table(name = "appointment")
public class Appointment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "patient_name")
    private String patientName;
    
    @Column(name = "doctor_name")
    private String doctorName;
    
    @Column(name = "appointment_date")
    private String appointmentDate;

    public Appointment() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }

    public String getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(String appointmentDate) { this.appointmentDate = appointmentDate; }
}
