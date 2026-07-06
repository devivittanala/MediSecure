package com.medsecure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medsecure.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Doctor findByUsername(String username);
    List<Doctor> findByDoctorNameContainingIgnoreCase(String keyword);

}