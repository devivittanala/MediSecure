 

package com.medsecure.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medsecure.model.Appointment;
import com.medsecure.model.OPDStatus;
import com.medsecure.repository.AppointmentRepository;
import com.medsecure.repository.OPDStatusRepository;

    @Service
    public class AppointmentService {

        @Autowired
        private AppointmentRepository appointmentRepository;
        @Autowired
        private NotificationService notificationService;
        @Autowired
        private OPDStatusRepository opdRepository;

        public void saveAppointment(Appointment appointment) {

            // New Appointment
            if (appointment.getId() == null) {
            	 

                long count =
                        appointmentRepository.count() + 1;

                appointment.setAppointmentId(
                        "APT" +
                        String.format("%04d", count));

                appointment.setTokenNumber(
                        "TKN" +
                        String.format("%03d", generateNextToken()));
                String otp =
            	        String.valueOf(
            	                (int)(100000 + Math.random() * 900000));

            	appointment.setOtp(otp);

            	appointment.setAppointmentStatus("Pending");

            	appointment.setPatientCondition("Normal");

            	appointment.setQueueStatus("Not Checked In");
            }

            appointmentRepository.save(appointment);
            notificationService.addNotification(
                    "APPOINTMENT",
                    "Appointment booked for "
                    + appointment.getPatientName());
            
        }
        public List<Appointment> getAllAppointments() {
            return appointmentRepository.findAll();
        }

        public Appointment getAppointmentById(Long id) {
            return appointmentRepository.findById(id).orElse(null);
        }
        

        
        public long getTodayAppointmentsCount() {
            return appointmentRepository.countByAppointmentDate(LocalDate.now());
        }
        public void deleteAppointment(Long id) {
            appointmentRepository.deleteById(id);
        }
        public long getPendingAppointments() {

            return appointmentRepository
                    .findAll()
                    .stream()
                    .filter(a ->
                            "Pending".equalsIgnoreCase(
                                    a.getAppointmentStatus()))
                    .count();
        }

       
        public long getPendingCount() {

            return appointmentRepository.findAll()
                    .stream()
                    .filter(a -> "Pending".equalsIgnoreCase(
                            a.getAppointmentStatus()))
                    .count();
        }

        public long getCompletedCount() {

            return appointmentRepository.findAll()
                    .stream()
                    .filter(a -> "Completed".equalsIgnoreCase(
                            a.getAppointmentStatus()))
                    .count();
        }

        public long getCriticalCount() {

            return appointmentRepository.findAll()
                    .stream()
                    .filter(a -> "Critical".equalsIgnoreCase(
                            a.getPatientCondition()))
                    .count();
        }
        public List<Appointment> getPatientHistory(String patientName) {
            return appointmentRepository.findByPatientName(patientName);
        }
        public List<Appointment> getWaitingPatients() {

            appointmentRepository.findAll().forEach(a -> {

                System.out.println(
                        a.getId() + " | " +
                        a.getAppointmentDate() + " | " +
                        a.getAppointmentStatus() + " | " +
                        a.getQueueStatus());

            });

            return appointmentRepository.findAll()
                    .stream()
                    .filter(a ->
                            LocalDate.now().equals(
                                    a.getAppointmentDate()))
                    .filter(a ->
                            "Waiting".equalsIgnoreCase(
                                    a.getQueueStatus()))
                    .filter(a ->
                    "Checked In".equalsIgnoreCase(
                            a.getAppointmentStatus()))
                    .sorted(
                            Comparator.comparing(
                                    Appointment::getAppointmentTime))
                    .toList();
        }
        public void callNextPatient() {

            Appointment currentPatient = getCurrentToken();

            if (currentPatient != null) {

                currentPatient.setQueueStatus("Served");

                appointmentRepository.save(currentPatient);
            }

            Appointment nextPatient =
                    getWaitingPatients()
                    .stream()
                    .findFirst()
                    .orElse(null);

            if (nextPatient != null) {

                nextPatient.setQueueStatus("In Consultation");

                appointmentRepository.save(nextPatient);
            }
        }
        
       
    
    public Appointment getCurrentToken() {

        return appointmentRepository.findAll()
                .stream()
                .filter(a ->
                        "In Consultation".equalsIgnoreCase(
                                a.getQueueStatus()))
                .filter(a ->
                        LocalDate.now().equals(
                                a.getAppointmentDate()))
                .findFirst()
                .orElse(null);
    }
    public void completePatient(Long id) {

        Appointment appointment =
                appointmentRepository.findById(id)
                .orElse(null);

        if (appointment != null) {

            appointment.setQueueStatus("Completed");

            appointment.setAppointmentStatus("Completed");

            appointmentRepository.save(appointment);
        }
    }
    public void confirmAppointment(Long id) {

        Appointment appointment =
                appointmentRepository.findById(id)
                .orElse(null);

        if (appointment != null) {

            appointment.setAppointmentStatus("Confirmed");
            appointment.setQueueStatus("Not Checked In");

            appointmentRepository.save(appointment);
        }
    }
    public List<Appointment> search(String keyword) {

        return appointmentRepository
                .findByPatientNameContainingIgnoreCaseOrDoctorNameContainingIgnoreCase(
                        keyword,
                        keyword);
    }
    public long getAppointmentCount() {
        return appointmentRepository.count();
    }

    public long getConfirmedAppointmentCount() {

        return appointmentRepository.findAll()
                .stream()
                .filter(a -> "Confirmed"
                        .equalsIgnoreCase(
                                a.getAppointmentStatus()))
                .count();
    }

    public long getPendingAppointmentCount() {

        return appointmentRepository.findAll()
                .stream()
                .filter(a -> "Pending"
                        .equalsIgnoreCase(
                                a.getAppointmentStatus()))
                .count();
    }
    public List<Appointment> getDoctorAppointments(String doctorName) {

        return appointmentRepository
                .findByDoctorName(doctorName);
    }

    public List<Appointment> getDoctorWaitingPatients(String doctorName) {

        return appointmentRepository.findAll()
                .stream()
                .filter(a ->
                        doctorName.equalsIgnoreCase(a.getDoctorName()))
                .filter(a ->
                        "Waiting".equalsIgnoreCase(a.getQueueStatus()))
                .filter(a ->
                        LocalDate.now().equals(a.getAppointmentDate()))
                .sorted(Comparator.comparing(Appointment::getAppointmentTime))
                .toList();
    }
    public void checkInPatient(Long id) {

        System.out.println("Check In Clicked for ID = " + id);

        Appointment appointment =
                appointmentRepository
                .findById(id)
                .orElse(null);

        System.out.println("Appointment = " + appointment);

        if (appointment != null) {
        	 appointment.setAppointmentStatus("Checked In");

            appointment.setQueueStatus("Waiting");
            appointment.setCheckedInTime(LocalDateTime.now());

            appointmentRepository.save(appointment);

            System.out.println("Queue Status Updated To Waiting");
        }
    }
    public Integer generateNextToken() {

    	return (int) appointmentRepository
                .countByAppointmentDate(LocalDate.now()) + 1;

    }
    public void acceptPatient(Long id) {

        Appointment appointment =
                appointmentRepository.findById(id).orElse(null);

        if (appointment != null) {

            appointment.setAppointmentStatus("Doctor Accepted");

            appointmentRepository.save(appointment);

            notificationService.addNotification(
                    "DOCTOR",
                    appointment.getPatientName()
                    + " accepted for consultation");
        }
    }
    public void startConsultation(Long id) {

        Appointment appointment =
                appointmentRepository.findById(id).orElse(null);

        if (appointment != null) {

            // Only one patient can be in consultation
            appointmentRepository.findAll()
                    .stream()
                    .filter(a -> "In Consultation"
                            .equalsIgnoreCase(a.getQueueStatus()))
                    .forEach(a -> {
                        a.setQueueStatus("Waiting");
                        appointmentRepository.save(a);
                    });

            appointment.setQueueStatus("In Consultation");
            appointment.setConsultationStartTime(LocalDateTime.now());

            appointmentRepository.save(appointment);

            notificationService.addNotification(
                    "CONSULTATION",
                    appointment.getPatientName()
                    + " consultation started.");
        }
    }
   
    public List<Appointment> getCompletedAppointments() {

        return appointmentRepository.findAll()
                .stream()
                .filter(a -> "Completed"
                        .equalsIgnoreCase(
                                a.getAppointmentStatus()))
                .toList();
    }
    public long getWaitingCount() {

        return appointmentRepository.findAll()
                .stream()
                .filter(a -> "Waiting"
                        .equalsIgnoreCase(a.getQueueStatus()))
                .count();

    }
    public long getConsultationCount() {

        return appointmentRepository.findAll()
                .stream()
                .filter(a -> "In Consultation"
                        .equalsIgnoreCase(a.getQueueStatus()))
                .count();

    }
    public long getCompletedTodayCount() {

        return appointmentRepository.findAll()
                .stream()
                .filter(a -> "Completed"
                        .equalsIgnoreCase(a.getAppointmentStatus()))
                .filter(a -> LocalDate.now()
                        .equals(a.getAppointmentDate()))
                .count();

    }
    public List<Appointment> getNextPatients() {

        return appointmentRepository.findAll()
                .stream()
                .filter(a -> LocalDate.now().equals(a.getAppointmentDate()))
                .filter(a -> "Waiting".equalsIgnoreCase(a.getQueueStatus()))
                .sorted((a1, a2) -> {

                    int p1 = getPriority(a1.getPatientCondition());
                    int p2 = getPriority(a2.getPatientCondition());

                    if (p1 != p2) {
                        return Integer.compare(p1, p2);
                    }

                    return a1.getAppointmentTime()
                            .compareTo(a2.getAppointmentTime());

                })
                .limit(5)
                .toList();
    }
    private int getPriority(String condition) {

        if (condition == null) {
            return 4;
        }

        switch (condition.toLowerCase()) {

            case "critical":
                return 1;

            case "serious":
                return 2;

            case "normal":
                return 3;

            default:
                return 4;
        }
    }
    public void startOPD(String doctorName){

        OPDStatus opd =
                opdRepository.findByDoctorName(doctorName);

        if(opd==null){

            opd=new OPDStatus();

            opd.setDoctorName(doctorName);

        }

        opd.setOpdRunning(true);

        opdRepository.save(opd);

    }
    public void stopOPD(String doctorName){

        OPDStatus opd =
                opdRepository.findByDoctorName(doctorName);

        if(opd!=null){

            opd.setOpdRunning(false);

            opdRepository.save(opd);

        }

    }
    public boolean isOPDRunning(String doctorName){

        OPDStatus opd =
                opdRepository.findByDoctorName(doctorName);

        return opd!=null && opd.isOpdRunning();

    }
    public void sendToDoctor(Long id){

        Appointment appointment =
                appointmentRepository.findById(id).orElse(null);

        if(appointment!=null){

            appointment.setQueueStatus("In Consultation");

            appointment.setConsultationStartTime(LocalDateTime.now());

            appointmentRepository.save(appointment);

        }

    }
    public boolean isAnyOPDRunning(){

        return opdRepository.findAll()
                .stream()
                .anyMatch(OPDStatus::isOpdRunning);

    }
    public Long getNextWaitingPatientId(){

        Appointment appointment =
                appointmentRepository.findAll()
                .stream()

                .filter(a->
                        "Doctor Accepted"
                        .equalsIgnoreCase(
                                a.getAppointmentStatus()))

                .filter(a->
                        "Waiting"
                        .equalsIgnoreCase(
                                a.getQueueStatus()))

                .sorted((a,b)->{

                    int p1=getPriority(a);

                    int p2=getPriority(b);

                    if(p1!=p2)
                        return Integer.compare(p1,p2);

                    return a.getAppointmentTime()
                            .compareTo(
                                    b.getAppointmentTime());

                })

                .findFirst()

                .orElse(null);

        return appointment==null?
                null:
                appointment.getId();

    }
    private int getPriority(Appointment a){

        switch(a.getPatientCondition()){

            case "Critical":
                return 1;

            case "Serious":
                return 2;

            default:
                return 3;

        }

    }
   
    public boolean deleteAppointmentByReception(Long id) {

        Appointment appointment = appointmentRepository
                .findById(id)
                .orElse(null);

        if (appointment == null) {
            return false;
        }

        if ("In Consultation".equalsIgnoreCase(appointment.getQueueStatus())
                || "Completed".equalsIgnoreCase(appointment.getQueueStatus())) {

            return false;
        }

        appointmentRepository.delete(appointment);
        return true;
    }
    public void completeConsultation(Long id){

        Appointment appointment =
                appointmentRepository.findById(id).orElse(null);

        if(appointment==null){
            return;
        }

        appointment.setQueueStatus("Completed");
        appointment.setAppointmentStatus("Completed");
        appointment.setConsultationEndTime(LocalDateTime.now());

        appointmentRepository.save(appointment);
    }
    public Appointment getNextPatient(){

        return appointmentRepository
                .findByAppointmentDate(LocalDate.now())
                .stream()

                .filter(a->
                        "Waiting".equals(a.getQueueStatus()))

                .filter(a->
                        "Doctor Accepted".equals(a.getAppointmentStatus()))

                .findFirst()

                .orElse(null);
    }
    public int getTodayAppointmentCount(String doctorName){

        return appointmentRepository
                .findByDoctorNameAndAppointmentDate(
                        doctorName,
                        LocalDate.now())
                .size();
    }
    public List<Appointment> getTodayAppointments(String doctorName) {

        return appointmentRepository
                .findByDoctorNameAndAppointmentDate(
                        doctorName,
                        LocalDate.now());
    }
    public List<Appointment> getTodayAppointments() {

        return appointmentRepository.findByAppointmentDate(LocalDate.now());

    }
}
    
    
