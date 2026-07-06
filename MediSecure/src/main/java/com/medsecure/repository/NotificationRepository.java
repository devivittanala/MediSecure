package com.medsecure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medsecure.model.Notification;

public interface NotificationRepository
        extends JpaRepository<Notification, Long> {
	List<Notification> findTop5ByOrderByCreatedAtDesc();

}