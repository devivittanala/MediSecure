package com.medsecure.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medsecure.model.Patient;
import com.medsecure.repository.PatientRepository;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private NotificationService notificationService;

    public void savePatient(Patient patient) {
    	boolean isNewPatient = (patient.getId() == null);

        patientRepository.save(patient);

        if (isNewPatient) {

            notificationService.addNotification(
                    "PATIENT",
                    "New Patient Added : "
                    + patient.getFirstName() + " "
                    + patient.getLastName());
        }
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }
    
    public List<Patient> search(String keyword) {
        return patientRepository
                .findByFirstNameContainingIgnoreCase(keyword);
    }

	public void deletePatient(Long id) {

    patientRepository.deleteById(id);

}

	public Patient getPatientById(Long id) {

	    return patientRepository
	            .findById(id)
	            .orElse(null);
	}
}