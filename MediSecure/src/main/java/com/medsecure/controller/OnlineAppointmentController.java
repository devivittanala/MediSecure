package com.medsecure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.medsecure.service.AppointmentService;

@Controller
public class OnlineAppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/book")
    public String bookingPage() {
        return "book-appointment";
    }
}