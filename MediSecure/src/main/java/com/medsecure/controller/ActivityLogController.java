package com.medsecure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.medsecure.service.NotificationService;

@Controller
public class ActivityLogController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/activity-logs")
    public String activityLogs(Model model) {

        model.addAttribute(
                "logs",
                notificationService.getAllNotifications());

        return "activity-logs";
    }
}