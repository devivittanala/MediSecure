package com.medsecure.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medsecure.model.Doctor;
import com.medsecure.repository.DoctorRepository;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private NotificationService notificationService;

    public void saveDoctor(Doctor doctor) {
        doctorRepository.save(doctor);
        notificationService.addNotification(
                "DOCTOR",
                "New Doctor Added : "
                + doctor.getDoctorName());
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id).orElse(null);
    }
    
    public void deleteDoctor(Long id) {

        doctorRepository.deleteById(id);

    }

    public List<Doctor> search(String keyword) {
        return doctorRepository.findByDoctorNameContainingIgnoreCase(keyword);
    }
    
    public long getDoctorCount() {
        return doctorRepository.count();
    }
    public long getAvailableDoctorCount() {

        return doctorRepository.findAll()
                .stream()
                .filter(d -> "Available"
                        .equalsIgnoreCase(d.getAvailabilityStatus()))
                .count();
    }
    public long getSpecializationCount() {

        return doctorRepository.findAll()
                .stream()
                .map(Doctor::getSpecialization)
                .distinct()
                .count();
    }
}