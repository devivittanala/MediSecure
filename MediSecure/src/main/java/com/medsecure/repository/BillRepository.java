package com.medsecure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medsecure.model.Bill;

public interface BillRepository extends JpaRepository<Bill, Long> {

    List<Bill> findByPatientNameContainingIgnoreCase(String keyword);

    List<Bill> findByPaymentStatus(String paymentStatus);

}