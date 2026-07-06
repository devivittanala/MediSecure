package com.medsecure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.medsecure.service.AppointmentService;

@Controller
public class TokenDisplayController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/display-token")
    public String displayToken(Model model) {

        model.addAttribute(
                "currentToken",
                appointmentService.getCurrentToken());

        return "token-display";
    }
}