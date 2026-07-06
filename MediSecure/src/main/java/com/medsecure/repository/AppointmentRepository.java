package com.medsecure.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medsecure.model.Appointment;

public interface AppointmentRepository
        extends JpaRepository<Appointment, Long> {
    Appointment findByAppointmentId(String appointmentId);
    long countByAppointmentDate(LocalDate appointmentDate);
   

	
	List<Appointment>
	findByDoctorNameAndAppointmentDate(
	        String doctorName,
	        LocalDate date);
	List<Appointment> findByPatientName(String patientName);
	Appointment findByAppointmentIdAndOtp(
	        String appointmentId,
	        String otp);
	List<Appointment>
	findByPatientNameContainingIgnoreCaseOrDoctorNameContainingIgnoreCase(
	        String patient,
	        String doctor);
	List<Appointment> findByDoctorName(String doctorName);

	List<Appointment> findByDoctorNameAndQueueStatus(
	        String doctorName,
	        String queueStatus);
	List<Appointment> findByAppointmentStatus(String appointmentStatus);
	
	List<Appointment> findByQueueStatus(String queueStatus);
	
	
	List<Appointment> findByAppointmentDate(LocalDate appointmentDate);
}
