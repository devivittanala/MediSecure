package com.medsecure.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.medsecure.model.Appointment;
import com.medsecure.model.Doctor;
import com.medsecure.repository.AppointmentRepository;
import com.medsecure.repository.DoctorRepository;
import com.medsecure.service.AppointmentService;

import jakarta.servlet.http.HttpSession;

@Controller
public class DoctorAppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping("/doctor/appointments")
    public String doctorAppointments(
            HttpSession session,
            Model model) {

        String username =
                (String) session.getAttribute(
                        "doctorUsername");

        if(username == null) {
            return "redirect:/doctor/login";
        }

        Doctor doctor =
                doctorRepository.findByUsername(
                        username);

        if(doctor == null) {
            return "redirect:/doctor/login";
        }

        List<Appointment> appointments =
                appointmentRepository.findByDoctorName(
                        doctor.getDoctorName());

        model.addAttribute(
                "appointments",
                appointments);

        return "doctor-appointments";
    }
    @GetMapping("/doctor/confirm/{id}")
    public String confirmAppointment(
            @PathVariable Long id) {

        Appointment appointment =
                appointmentRepository.findById(id)
                .orElse(null);

        if (appointment != null) {

            appointment.setAppointmentStatus(
                    "Confirmed");

            appointmentRepository.save(
                    appointment);
        }

        return "redirect:/doctor/appointments";
    }
    @GetMapping("/doctor/todayAppointments")
    public String todayAppointments(Model model) {

        model.addAttribute(
                "appointments",
                appointmentService.getTodayAppointments());

        return "doctor-today-appointments";
    }
}