package com.hospital.controller;

import com.hospital.model.*;
import com.hospital.repository.*;
import com.hospital.service.*;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final DoctorService doctorService;
    private final PatientService patientService;

    @Autowired
    public AdminController(UserService userService, DoctorRepository doctorRepository,
                          PatientRepository patientRepository, AppointmentRepository appointmentRepository,
                          DoctorService doctorService, PatientService patientService) {
        this.userService = userService;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.ADMIN) {
            return "redirect:/auth/login";
        }
        
        long doctorCount = doctorRepository.count();
        long patientCount = patientRepository.count();
        long appointmentCount = appointmentRepository.count();
        
        model.addAttribute("doctorCount", doctorCount);
        model.addAttribute("patientCount", patientCount);
        model.addAttribute("appointmentCount", appointmentCount);
        
        return "admin/dashboard";
    }

    @GetMapping("/doctors")
    public String doctors(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.ADMIN) {
            return "redirect:/auth/login";
        }
        
        List<Doctor> doctors = doctorRepository.findAll();
        model.addAttribute("doctors", doctors);
        
        return "admin/doctors";
    }

    @GetMapping("/doctors/add")
    public String addDoctorForm(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.ADMIN) {
            return "redirect:/auth/login";
        }
        
        return "admin/doctor-add";
    }

    @PostMapping("/doctors/add")
    public String addDoctor(HttpSession session, @RequestParam String username, @RequestParam String password,
                      @RequestParam String name, @RequestParam String specialty,
                      @RequestParam String phone, @RequestParam String email) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.ADMIN) {
            return "redirect:/auth/login";
        }
        
        if (userService.existsByUsername(username)) {
            return "redirect:/admin/doctors?error";
        }
        
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setRole(Role.DOCTOR);
        newUser.setEnabled(true);
        userService.saveUser(newUser);
        
        Doctor doctor = new Doctor();
        doctor.setName(name);
        doctor.setSpecialty(specialty);
        doctor.setPhone(phone);
        doctor.setEmail(email);
        doctor.setUser(newUser);
        doctorRepository.save(doctor);
        
        return "redirect:/admin/doctors?added";
    }

    @GetMapping("/doctors/edit/{id}")
    public String editDoctorForm(HttpSession session, @PathVariable Integer id, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.ADMIN) {
            return "redirect:/auth/login";
        }
        
        Doctor doctor = doctorRepository.findById(id).orElse(null);
        model.addAttribute("doctor", doctor);
        
        return "admin/doctor-edit";
    }

    @PostMapping("/doctors/edit/{id}")
    public String editDoctor(HttpSession session, @PathVariable Integer id,
                       @RequestParam String name, @RequestParam String specialty,
                       @RequestParam String phone, @RequestParam String email) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.ADMIN) {
            return "redirect:/auth/login";
        }
        
        Doctor doctor = doctorRepository.findById(id).orElse(null);
        if (doctor != null) {
            doctor.setName(name);
            doctor.setSpecialty(specialty);
            doctor.setPhone(phone);
            doctor.setEmail(email);
            doctorRepository.save(doctor);
        }
        
        return "redirect:/admin/doctors?updated";
    }

    @PostMapping("/doctors/delete/{id}")
    public String deleteDoctor(HttpSession session, @PathVariable Integer id) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.ADMIN) {
            return "redirect:/auth/login";
        }
        
        Doctor doctor = doctorRepository.findById(id).orElse(null);
        if (doctor != null && doctor.getUser() != null) {
            userService.findByUsername(doctor.getUser().getUsername()).ifPresent(u -> {
                u.setEnabled(false);
                userService.saveUser(u);
            });
            doctorRepository.delete(doctor);
        }
        
        return "redirect:/admin/doctors?deleted";
    }

    @GetMapping("/patients")
    public String patients(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.ADMIN) {
            return "redirect:/auth/login";
        }
        
        List<Patient> patients = patientRepository.findAll();
        model.addAttribute("patients", patients);
        
        return "admin/patients";
    }

    @GetMapping("/patients/add")
    public String addPatientForm(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.ADMIN) {
            return "redirect:/auth/login";
        }
        
        return "admin/patient-add";
    }

    @PostMapping("/patients/add")
    public String addPatient(HttpSession session, @RequestParam String username, @RequestParam String password,
                         @RequestParam String name, @RequestParam Integer age,
                         @RequestParam String gender, @RequestParam String phone,
                         @RequestParam String email, @RequestParam String address) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.ADMIN) {
            return "redirect:/auth/login";
        }
        
        if (userService.existsByUsername(username)) {
            return "redirect:/admin/patients?error";
        }
        
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setRole(Role.PATIENT);
        newUser.setEnabled(true);
        userService.saveUser(newUser);
        
        Patient patient = new Patient();
        patient.setName(name);
        patient.setAge(age);
        patient.setGender(gender);
        patient.setPhone(phone);
        patient.setEmail(email);
        patient.setAddress(address);
        patient.setUser(newUser);
        patientRepository.save(patient);
        
        return "redirect:/admin/patients?added";
    }

    @GetMapping("/patients/edit/{id}")
    public String editPatientForm(HttpSession session, @PathVariable Integer id, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.ADMIN) {
            return "redirect:/auth/login";
        }
        
        Patient patient = patientRepository.findById(id).orElse(null);
        model.addAttribute("patient", patient);
        
        return "admin/patient-edit";
    }

    @PostMapping("/patients/edit/{id}")
    public String editPatient(HttpSession session, @PathVariable Integer id,
                            @RequestParam String name, @RequestParam Integer age,
                            @RequestParam String gender, @RequestParam String phone,
                            @RequestParam String email, @RequestParam String address) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.ADMIN) {
            return "redirect:/auth/login";
        }
        
        Patient patient = patientRepository.findById(id).orElse(null);
        if (patient != null) {
            patient.setName(name);
            patient.setAge(age);
            patient.setGender(gender);
            patient.setPhone(phone);
            patient.setEmail(email);
            patient.setAddress(address);
            patientRepository.save(patient);
        }
        
        return "redirect:/admin/patients?updated";
    }

    @PostMapping("/patients/delete/{id}")
    public String deletePatient(HttpSession session, @PathVariable Integer id) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.ADMIN) {
            return "redirect:/auth/login";
        }
        
        Patient patient = patientRepository.findById(id).orElse(null);
        if (patient != null && patient.getUser() != null) {
            userService.findByUsername(patient.getUser().getUsername()).ifPresent(u -> {
                u.setEnabled(false);
                userService.saveUser(u);
            });
            patientRepository.delete(patient);
        }
        
        return "redirect:/admin/patients?deleted";
    }

    @GetMapping("/appointments")
    public String appointments(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != Role.ADMIN) {
            return "redirect:/auth/login";
        }
        
        List<Appointment> appointments = appointmentRepository.findAll();
        model.addAttribute("appointments", appointments);
        
        return "admin/appointments";
    }
}