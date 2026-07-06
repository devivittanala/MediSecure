package com.medsecure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.medsecure.service.NotificationService;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notifications")
    public String notifications(Model model) {

        model.addAttribute(
                "notifications",
                notificationService.getAllNotifications());

        return "notifications";
    }
}