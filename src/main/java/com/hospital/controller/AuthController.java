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
import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    @Autowired
    public AuthController(UserService userService, PatientRepository patientRepository, DoctorRepository doctorRepository) {
        this.userService = userService;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                      HttpSession session, Model model) {
        Optional<User> userOpt = userService.findByUsername(username);
        
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            User user = userOpt.get();
            session.setAttribute("user", user);
            
            if (user.getRole() == Role.ADMIN) {
                return "redirect:/admin/dashboard";
            } else if (user.getRole() == Role.DOCTOR) {
                return "redirect:/doctor/dashboard";
            } else {
                return "redirect:/patient/dashboard";
            }
        }
        
        model.addAttribute("error", "Invalid username or password");
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("roles", Role.values());
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                      @RequestParam String password,
                      @RequestParam Role role,
                      @RequestParam String name,
                      @RequestParam(required = false) Integer age,
                      @RequestParam(required = false) String gender,
                      @RequestParam(required = false) String phone,
                      @RequestParam(required = false) String email,
                      @RequestParam(required = false) String address,
                      @RequestParam(required = false) String specialty,
                      Model model) {
        
        if (userService.existsByUsername(username)) {
            model.addAttribute("error", "Username already exists");
            model.addAttribute("roles", Role.values());
            return "register";
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        user.setEnabled(true);
        user = userService.saveUser(user);

        if (role == Role.PATIENT) {
            Patient patient = new Patient();
            patient.setName(name);
            patient.setAge(age != null ? age : 0);
            patient.setGender(gender);
            patient.setPhone(phone);
            patient.setEmail(email);
            patient.setAddress(address);
            patient.setUser(user);
            patientRepository.save(patient);
        } else if (role == Role.DOCTOR) {
            Doctor doctor = new Doctor();
            doctor.setName(name);
            doctor.setSpecialty(specialty);
            doctor.setPhone(phone);
            doctor.setEmail(email);
            doctor.setUser(user);
            doctorRepository.save(doctor);
        }

        return "redirect:/auth/login?registered";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login?loggedout";
    }
}