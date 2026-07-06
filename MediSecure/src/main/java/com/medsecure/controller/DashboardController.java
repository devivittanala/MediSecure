package com.medsecure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.medsecure.model.Patient;
import com.medsecure.model.Doctor;
import com.medsecure.model.Appointment;
import com.medsecure.repository.PatientRepository;
import com.medsecure.service.NotificationService;
import com.medsecure.repository.DoctorRepository;
import com.medsecure.repository.AppointmentRepository;
import com.medsecure.repository.BillRepository;

@Controller
public class DashboardController {
@Autowired
private PatientRepository patientRepository;

@Autowired
private DoctorRepository doctorRepository;

@Autowired
private AppointmentRepository appointmentRepository;

@Autowired
private BillRepository billRepository;
@Autowired
private NotificationService notificationService;

@GetMapping("/dashboard")
public String dashboard(Model model) {

    model.addAttribute("patientCount",
            patientRepository.count());

    model.addAttribute("doctorCount",
            doctorRepository.count());

    model.addAttribute("appointmentCount",
            appointmentRepository.count());

    model.addAttribute("billCount",
            billRepository.count());
    model.addAttribute(
            "recentActivities",
            notificationService.getLatestNotifications());
    

    double totalRevenue =
            billRepository.findAll()
                    .stream()
                    .mapToDouble(b -> b.getAmount() == null ? 0 : b.getAmount())
                    .sum();

    long paidBills =
            billRepository
                    .findByPaymentStatus("PAID")
                    .size();

    long pendingBills =
            billRepository
                    .findByPaymentStatus("PENDING")
                    .size();

    model.addAttribute("totalRevenue", totalRevenue);
    model.addAttribute("paidBills", paidBills);
    model.addAttribute("pendingBills", pendingBills);

    model.addAttribute("patients",
            patientRepository.findAll());

    model.addAttribute("doctors",
            doctorRepository.findAll());

    model.addAttribute("appointments",
            appointmentRepository.findAll());

    return "dashboard";
}


}
