package com.hospital.controller;

import com.hospital.model.Appointment;
import com.hospital.model.Doctor;
import com.hospital.model.Patient;
import com.hospital.service.AppointmentService;
import com.hospital.service.DoctorService;
import com.hospital.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HospitalController {

    private final PatientService patientService;
    private final DoctorService doctorService;
    private final AppointmentService appointmentService;

    @Autowired
    public HospitalController(PatientService patientService, DoctorService doctorService, AppointmentService appointmentService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/patient")
    public String patient(Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
        return "patient";
    }

    @PostMapping("/patient/add")
    public String addPatient(@RequestParam String name, @RequestParam int age) {
        Patient p = new Patient();
        p.setName(name);
        p.setAge(age);
        patientService.savePatient(p);
        return "redirect:/patient";
    }

    @PostMapping("/patient/delete")
    public String deletePatient(@RequestParam String name) {
        patientService.deletePatientByName(name);
        return "redirect:/patient";
    }

    @PostMapping("/patient/search")
    public String searchPatient(@RequestParam String name, Model model) {
        model.addAttribute("patients", patientService.searchPatients(name));
        return "patient";
    }

    @PostMapping("/patient/update")
    public String updatePatient(@RequestParam String name, @RequestParam int age) {
        patientService.updatePatientAge(name, age);
        return "redirect:/patient";
    }

    @GetMapping("/doctor")
    public String doctor(Model model) {
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "doctor";
    }

    @PostMapping("/doctor/add")
    public String addDoctor(@RequestParam String name, @RequestParam String specialty) {
        Doctor d = new Doctor();
        d.setName(name);
        d.setSpecialty(specialty);
        doctorService.saveDoctor(d);
        return "redirect:/doctor";
    }

    @PostMapping("/doctor/delete")
    public String deleteDoctor(@RequestParam String name) {
        doctorService.deleteDoctorByName(name);
        return "redirect:/doctor";
    }

    @PostMapping("/doctor/search")
    public String searchDoctor(@RequestParam String name, Model model) {
        model.addAttribute("doctors", doctorService.searchDoctors(name));
        return "doctor";
    }

    @PostMapping("/doctor/update")
    public String updateDoctor(@RequestParam String name, @RequestParam String specialty) {
        doctorService.updateDoctorSpecialty(name, specialty);
        return "redirect:/doctor";
    }

    @GetMapping("/appointment")
    public String appointment(Model model) {
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        return "appointment";
    }

    @PostMapping("/appointment/add")
    public String addAppointment(@RequestParam String patientName, @RequestParam String doctorName, @RequestParam String date) {
        Appointment a = new Appointment();
        a.setPatientName(patientName);
        a.setDoctorName(doctorName);
        a.setAppointmentDate(date);
        appointmentService.saveAppointment(a);
        return "redirect:/appointment";
    }

    @PostMapping("/appointment/delete")
    public String deleteAppointment(@RequestParam String patientName) {
        appointmentService.deleteAppointmentByPatientName(patientName);
        return "redirect:/appointment";
    }

    @PostMapping("/appointment/search")
    public String searchAppointment(@RequestParam String patientName, Model model) {
        model.addAttribute("appointments", appointmentService.searchAppointments(patientName));
        return "appointment";
    }

    @PostMapping("/appointment/update")
    public String updateAppointment(@RequestParam String patientName, @RequestParam String newDoctor, @RequestParam String newDate) {
        appointmentService.updateAppointment(patientName, newDate, newDoctor);
        return "redirect:/appointment";
    }
}
