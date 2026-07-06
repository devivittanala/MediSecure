package com.medsecure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medsecure.model.OPDStatus;

public interface OPDStatusRepository
extends JpaRepository<OPDStatus,Long>{

    OPDStatus findByDoctorName(String doctorName);

}