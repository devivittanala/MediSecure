package com.medsecure.model;

import jakarta.persistence.*;

@Entity
public class OPDStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String doctorName;

    private boolean opdRunning;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id=id;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName=doctorName;
    }

    public boolean isOpdRunning() {
        return opdRunning;
    }

    public void setOpdRunning(boolean opdRunning) {
        this.opdRunning=opdRunning;
    }
}