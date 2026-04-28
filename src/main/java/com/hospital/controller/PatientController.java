package com.hospital.controller;

import com.hospital.model.*;
import com.hospital.repository.*;
import com.hospital.service.*;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/patient")
public class PatientController {

    private final UserService userService;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final PdfReportService pdfReportService;

    @Autowired
    public PatientController(UserService userService, PatientRepository patientRepository,
                            DoctorRepository doctorRepository, AppointmentRepository appointmentRepository,
                            PrescriptionRepository prescriptionRepository, MedicalRecordRepository medicalRecordRepository,
                            PdfReportService pdfReportService) {
        this.userService = userService;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.pdfReportService = pdfReportService;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.PATIENT) {
            return "redirect:/auth/login";
        }
        
        Patient patient = patientRepository.findByUser(user).orElse(null);
        model.addAttribute("patient", patient);
        
        List<Appointment> appointments = appointmentRepository.findByPatientOrderByDateDesc(patient);
        model.addAttribute("appointments", appointments);
        
        return "patient/dashboard";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.PATIENT) {
            return "redirect:/auth/login";
        }
        
        Patient patient = patientRepository.findByUser(user).orElse(null);
        model.addAttribute("patient", patient);
        return "patient/profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(HttpSession session, @RequestParam String name,
                             @RequestParam Integer age, @RequestParam String gender,
                             @RequestParam String phone, @RequestParam String email,
                             @RequestParam String address) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.PATIENT) {
            return "redirect:/auth/login";
        }
        
        Patient patient = patientRepository.findByUser(user).orElse(null);
        if (patient != null) {
            patient.setName(name);
            patient.setAge(age);
            patient.setGender(gender);
            patient.setPhone(phone);
            patient.setEmail(email);
            patient.setAddress(address);
            patientRepository.save(patient);
        }
        
        return "redirect:/patient/profile?updated";
    }

    @GetMapping("/appointments")
    public String appointments(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.PATIENT) {
            return "redirect:/auth/login";
        }
        
        Patient patient = patientRepository.findByUser(user).orElse(null);
        List<Appointment> appointments = appointmentRepository.findByPatientOrderByDateDesc(patient);
        model.addAttribute("appointments", appointments);
        
        return "patient/appointments";
    }

    @GetMapping("/book")
    public String bookAppointment(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.PATIENT) {
            return "redirect:/auth/login";
        }
        
        List<Doctor> doctors = doctorRepository.findAll();
        model.addAttribute("doctors", doctors);
        
        return "patient/book";
    }

    @PostMapping("/book")
    public String bookAppointment(HttpSession session, @RequestParam Integer doctorId,
                              @RequestParam String date, @RequestParam String time,
                              @RequestParam String reason) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.PATIENT) {
            return "redirect:/auth/login";
        }
        
        Patient patient = patientRepository.findByUser(user).orElse(null);
        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);
        
        if (patient != null && doctor != null) {
            Appointment appointment = new Appointment();
            appointment.setPatient(patient);
            appointment.setDoctor(doctor);
            appointment.setDate(date);
            appointment.setTime(time);
            appointment.setReason(reason);
            appointment.setStatus("PENDING");
            appointmentRepository.save(appointment);
        }
        
        return "redirect:/patient/appointments?booked";
    }

    @PostMapping("/appointments/cancel/{id}")
    public String cancelAppointment(HttpSession session, @PathVariable Integer id) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.PATIENT) {
            return "redirect:/auth/login";
        }
        
        Appointment appointment = appointmentRepository.findById(id).orElse(null);
        if (appointment != null) {
            appointment.setStatus("CANCELLED");
            appointmentRepository.save(appointment);
        }
        
        return "redirect:/patient/appointments?cancelled";
    }

    @GetMapping("/prescriptions")
    public String prescriptions(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.PATIENT) {
            return "redirect:/auth/login";
        }
        
        Patient patient = patientRepository.findByUser(user).orElse(null);
        List<Prescription> prescriptions = prescriptionRepository.findByPatientOrderByDateDesc(patient);
        model.addAttribute("prescriptions", prescriptions);
        
        return "patient/prescriptions";
    }

    @GetMapping("/records")
    public String records(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.PATIENT) {
            return "redirect:/auth/login";
        }
        
        Patient patient = patientRepository.findByUser(user).orElse(null);
        List<MedicalRecord> records = medicalRecordRepository.findByPatientOrderByDateDesc(patient);
        model.addAttribute("records", records);
        
        return "patient/records";
    }

    @GetMapping("/prescriptions/download/{id}")
    public ResponseEntity<byte[]> downloadPrescription(HttpSession session, @PathVariable Integer id) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.PATIENT) {
            return ResponseEntity.status(401).build();
        }
        
        Patient patient = patientRepository.findByUser(user).orElse(null);
        Prescription prescription = prescriptionRepository.findById(id).orElse(null);
        
        if (prescription == null || !prescription.getPatient().equals(patient)) {
            return ResponseEntity.status(403).build();
        }
        
        byte[] pdf = pdfReportService.generatePrescriptionReport(prescription);
        
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=prescription_" + id + ".pdf")
            .contentType(MediaType.APPLICATION_PDF)
            .body(pdf);
    }

    @GetMapping("/records/download/{id}")
    public ResponseEntity<byte[]> downloadRecord(HttpSession session, @PathVariable Integer id) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.PATIENT) {
            return ResponseEntity.status(401).build();
        }
        
        Patient patient = patientRepository.findByUser(user).orElse(null);
        MedicalRecord record = medicalRecordRepository.findById(id).orElse(null);
        
        if (record == null || !record.getPatient().equals(patient)) {
            return ResponseEntity.status(403).build();
        }
        
        byte[] pdf = pdfReportService.generateMedicalRecordReport(record);
        
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=medical_record_" + id + ".pdf")
            .contentType(MediaType.APPLICATION_PDF)
            .body(pdf);
    }
}