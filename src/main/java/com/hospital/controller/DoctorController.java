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
@RequestMapping("/doctor")
public class DoctorController {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final PdfReportService pdfReportService;

    @Autowired
    public DoctorController(DoctorRepository doctorRepository,
                            PatientRepository patientRepository,
                            AppointmentRepository appointmentRepository,
                            MedicalRecordRepository medicalRecordRepository,
                            PrescriptionRepository prescriptionRepository,
                            PdfReportService pdfReportService) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.pdfReportService = pdfReportService;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.DOCTOR) {
            return "redirect:/auth/login";
        }
        
        Doctor doctor = doctorRepository.findByUser(user).orElse(null);
        model.addAttribute("doctor", doctor);
        
        if (doctor != null) {
            List<Appointment> appointments = appointmentRepository.findByDoctorOrderByDateDesc(doctor);
            model.addAttribute("appointments", appointments);
        }
        
        return "doctor/dashboard";
    }

    @GetMapping("/patients")
    public String patients(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.DOCTOR) {
            return "redirect:/auth/login";
        }
        
        Doctor doctor = doctorRepository.findByUser(user).orElse(null);
        List<Appointment> appointments = appointmentRepository.findByDoctor(doctor);
        
        model.addAttribute("doctor", doctor);
        model.addAttribute("appointments", appointments);
        
        return "doctor/patients";
    }

    @GetMapping("/appointments")
    public String appointments(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.DOCTOR) {
            return "redirect:/auth/login";
        }
        
        Doctor doctor = doctorRepository.findByUser(user).orElse(null);
        List<Appointment> appointments = appointmentRepository.findByDoctorOrderByDateDesc(doctor);
        model.addAttribute("appointments", appointments);
        
        return "doctor/appointments";
    }

    @PostMapping("/appointments/confirm/{id}")
    public String confirmAppointment(HttpSession session, @PathVariable Integer id) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.DOCTOR) {
            return "redirect:/auth/login";
        }
        
        Appointment appointment = appointmentRepository.findById(id).orElse(null);
        if (appointment != null) {
            appointment.setStatus("CONFIRMED");
            appointmentRepository.save(appointment);
        }
        
        return "redirect:/doctor/appointments?confirmed";
    }

    @PostMapping("/appointments/complete/{id}")
    public String completeAppointment(HttpSession session, @PathVariable Integer id) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.DOCTOR) {
            return "redirect:/auth/login";
        }
        
        Appointment appointment = appointmentRepository.findById(id).orElse(null);
        if (appointment != null) {
            appointment.setStatus("COMPLETED");
            appointmentRepository.save(appointment);
        }
        
        return "redirect:/doctor/appointments?completed";
    }

    @GetMapping("/records/add")
    public String addRecordForm(HttpSession session, @RequestParam Integer patientId, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.DOCTOR) {
            return "redirect:/auth/login";
        }
        
        Patient patient = patientRepository.findById(patientId).orElse(null);
        model.addAttribute("patient", patient);
        
        return "doctor/record-add";
    }

    @PostMapping("/records/add")
    public String addRecord(HttpSession session, @RequestParam Integer patientId,
                       @RequestParam String diagnosis, @RequestParam String notes) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.DOCTOR) {
            return "redirect:/auth/login";
        }
        
        Doctor doctor = doctorRepository.findByUser(user).orElse(null);
        Patient patient = patientRepository.findById(patientId).orElse(null);
        
        if (doctor != null && patient != null) {
            MedicalRecord record = new MedicalRecord();
            record.setPatient(patient);
            record.setDoctor(doctor);
            record.setDiagnosis(diagnosis);
            record.setNotes(notes);
            record.setDate(java.time.LocalDate.now().toString());
            medicalRecordRepository.save(record);
        }
        
        return "redirect:/doctor/patients?recordAdded";
    }

    @GetMapping("/prescriptions/add")
    public String addPrescriptionForm(HttpSession session, @RequestParam Integer patientId, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.DOCTOR) {
            return "redirect:/auth/login";
        }
        
        Patient patient = patientRepository.findById(patientId).orElse(null);
        model.addAttribute("patient", patient);
        
        return "doctor/prescription-add";
    }

    @PostMapping("/prescriptions/add")
    public String addPrescription(HttpSession session, @RequestParam Integer patientId,
                              @RequestParam String medicines, @RequestParam String dosage,
                              @RequestParam String instructions) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.DOCTOR) {
            return "redirect:/auth/login";
        }
        
        Doctor doctor = doctorRepository.findByUser(user).orElse(null);
        Patient patient = patientRepository.findById(patientId).orElse(null);
        
        if (doctor != null && patient != null) {
            Prescription prescription = new Prescription();
            prescription.setPatient(patient);
            prescription.setDoctor(doctor);
            prescription.setMedicines(medicines);
            prescription.setDosage(dosage);
            prescription.setInstructions(instructions);
            prescription.setDate(java.time.LocalDate.now().toString());
            prescriptionRepository.save(prescription);
        }
        
        return "redirect:/doctor/patients?prescriptionAdded";
    }

    @GetMapping("/prescriptions/download/{id}")
    public ResponseEntity<byte[]> downloadPrescription(HttpSession session, @PathVariable Integer id) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.DOCTOR) {
            return ResponseEntity.status(401).build();
        }
        
        Prescription prescription = prescriptionRepository.findById(id).orElse(null);
        
        if (prescription == null) {
            return ResponseEntity.status(404).build();
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
        if (user == null || user.getRole() != Role.DOCTOR) {
            return ResponseEntity.status(401).build();
        }
        
        MedicalRecord record = medicalRecordRepository.findById(id).orElse(null);
        
        if (record == null) {
            return ResponseEntity.status(404).build();
        }
        
        byte[] pdf = pdfReportService.generateMedicalRecordReport(record);
        
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=medical_record_" + id + ".pdf")
            .contentType(MediaType.APPLICATION_PDF)
            .body(pdf);
    }
}