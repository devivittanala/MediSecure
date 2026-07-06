package com.medsecure.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AppointmentReminderScheduler {

    @Scheduled(cron = "0 0 9 * * *")
    public void sendReminders() {

        System.out.println(
            "Checking tomorrow appointments..."
        );

        // Email sending code will go here

    }
}