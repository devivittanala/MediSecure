package com.medsecure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medsecure.model.Receptionist;

@Repository
public interface ReceptionistRepository
        extends JpaRepository<Receptionist, Long> {
}
