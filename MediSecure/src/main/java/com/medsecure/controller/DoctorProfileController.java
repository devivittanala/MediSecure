package com.medsecure.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.medsecure.model.Doctor;
import com.medsecure.repository.DoctorRepository;

@Controller
public class DoctorProfileController {

    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping("/doctor/profile")
    public String profile(
            HttpSession session,
            Model model) {

        String username =
                (String) session.getAttribute(
                        "doctorUsername");

        Doctor doctor =
                doctorRepository.findByUsername(
                        username);

        model.addAttribute(
                "doctor",
                doctor);

        return "doctor-profile";
    }
}