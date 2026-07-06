package com.medsecure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.medsecure.model.Appointment;
import com.medsecure.service.AppointmentService;

@Controller
public class PublicAppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/bookAppointment")
    public String bookAppointment(Model model) {

        model.addAttribute(
                "appointment",
                new Appointment());

        return "book-appointment";
    }



}