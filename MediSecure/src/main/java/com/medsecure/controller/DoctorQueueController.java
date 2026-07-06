package com.medsecure.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.medsecure.model.Appointment;
import com.medsecure.repository.AppointmentRepository;

@Controller
public class DoctorQueueController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @GetMapping("/doctor/waiting-queue")
    public String waitingQueue(Model model) {

        List<Appointment> waitingPatients =
                appointmentRepository
                .findByAppointmentStatus("Waiting");

        model.addAttribute(
                "waitingPatients",
                waitingPatients);

        return "doctor-waiting-queue";
    }
}