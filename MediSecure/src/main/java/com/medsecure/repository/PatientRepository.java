package com.medsecure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medsecure.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findByFirstNameContainingIgnoreCase(String keyword);

    List<Patient> findByDiseaseContainingIgnoreCase(String disease);
    
}