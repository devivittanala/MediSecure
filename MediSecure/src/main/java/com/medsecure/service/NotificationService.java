package com.medsecure.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medsecure.model.Notification;
import com.medsecure.repository.NotificationRepository;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public void addNotification(
            String type,
            String message) {

        Notification notification =
                new Notification();

        notification.setType(type);
        notification.setMessage(message);
        notification.setCreatedAt(
                LocalDateTime.now());

        notificationRepository.save(
                notification);
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }
    public List<Notification> getLatestNotifications() {

        return notificationRepository
                .findTop5ByOrderByCreatedAtDesc();
    }
}