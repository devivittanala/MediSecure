package com.medsecure.controller;
import com.medsecure.service.AppointmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.medsecure.model.Appointment;
import com.medsecure.repository.AppointmentRepository;

@Controller
public class DoctorConsultationController {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/doctor/consultation/{id}")
    public String consultationPage(
            @PathVariable Long id,
            Model model) {

        Appointment appointment =
                appointmentRepository
                .findById(id)
                .orElse(null);

        model.addAttribute(
                "appointment",
                appointment);

        return "doctor-consultation";
    }
    @GetMapping("/doctor/start/{id}")
    public String startConsultation(
            @PathVariable Long id) {

        Appointment appointment =
                appointmentRepository
                        .findById(id)
                        .orElse(null);

        if (appointment != null) {

            appointment.setQueueStatus(
                    "In Consultation");

            appointment.setConsultationStartTime(
                    java.time.LocalDateTime.now());

            appointmentRepository.save(appointment);
        }

        return "redirect:/doctor/dashboard";
    }
    @GetMapping("/doctor/accept/{id}")
    public String acceptPatient(
            @PathVariable Long id) {

        Appointment appointment =
                appointmentRepository
                        .findById(id)
                        .orElse(null);

        if (appointment != null) {

            appointment.setAppointmentStatus(
                    "Doctor Accepted");

            appointmentRepository.save(appointment);
        }

        return "redirect:/doctor/dashboard";
    }
    @GetMapping("/doctor/complete/{id}")
    public String completeConsultation(
            @PathVariable Long id) {

        appointmentService.completeConsultation(id);

        return "redirect:/doctor/dashboard";
    }
}