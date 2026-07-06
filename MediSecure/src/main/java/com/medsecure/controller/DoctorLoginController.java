package com.medsecure.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.medsecure.model.Doctor;
import com.medsecure.repository.DoctorRepository;

@Controller
public class DoctorLoginController {

    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping("/doctor/login")
    public String doctorLoginPage() {
        return "doctor-login";
    }

    @PostMapping("/doctor/login")
    public String doctorLogin(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session) {

        Doctor doctor =
                doctorRepository.findByUsername(username);

        if (doctor != null &&
                doctor.getPassword().equals(password)) {

            session.setAttribute(
                    "doctorUsername",
                    doctor.getUsername());

            return "redirect:/doctor/dashboard";
        }

        return "redirect:/doctor/login?error=true";
}
}