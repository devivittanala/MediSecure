package com.medsecure.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.medsecure.model.Appointment;
import com.medsecure.model.Doctor;
import com.medsecure.repository.DoctorRepository;
import com.medsecure.service.AppointmentService;
import jakarta.servlet.http.HttpSession;

@Controller
public class DoctorDashboardController {

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private AppointmentService appointmentService;
    @GetMapping("/doctor/dashboard")
    public String doctorDashboard(
            HttpSession session,
            Model model) {

        String username =
                (String) session.getAttribute("doctorUsername");

        System.out.println(
                "Doctor Session Username = "
                        + username);
        if (username == null) {
            return "redirect:/doctor/login";
        }

        Doctor doctor =
                doctorRepository.findByUsername(username);
        

        System.out.println(
                "Doctor = "
                        + doctor);
        if (doctor == null) {
            return "redirect:/doctor/login";
        }

        model.addAttribute("doctor", doctor);
        model.addAttribute(
                "todayAppointmentCount",
                appointmentService.getTodayAppointmentCount(
                        doctor.getDoctorName()));

        List<Appointment> appointments =
                appointmentService.getDoctorAppointments(
                        doctor.getDoctorName());
        model.addAttribute("appointments", appointments);

        List<Appointment> waitingPatients =
                appointmentService.getDoctorWaitingPatients(
                        doctor.getDoctorName());

        model.addAttribute(
                "waitingPatients",
                waitingPatients);
        long completedPatients =
                appointments.stream()
                .filter(a -> "Completed"
                        .equalsIgnoreCase(
                                a.getAppointmentStatus()))
                .count();

        model.addAttribute(
                "completedPatients",
                completedPatients);
        Appointment currentPatient =
                appointmentService.getCurrentToken();

        model.addAttribute(
                "currentPatient",
                currentPatient);
        model.addAttribute(
                "opdRunning",
                appointmentService.isOPDRunning(
                        doctor.getDoctorName()));
      

        return "doctor-dashboard";
    }
    @GetMapping("/doctor/logout")
    public String logout(HttpSession session) {

        session.invalidate();
        

        return "redirect:/";
    }
}