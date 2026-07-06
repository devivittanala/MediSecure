package com.medsecure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.medsecure.repository.PatientRepository;
import com.medsecure.repository.DoctorRepository;
import com.medsecure.repository.AppointmentRepository;
import com.medsecure.repository.BillRepository;

@Controller
public class AnalyticsController {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private BillRepository billRepository;

    @GetMapping("/analytics")
    public String analytics(Model model) {

        model.addAttribute("patientCount",
                patientRepository.count());

        model.addAttribute("doctorCount",
                doctorRepository.count());

        model.addAttribute("appointmentCount",
                appointmentRepository.count());

        model.addAttribute("billCount",
                billRepository.count());

        double totalRevenue =
                billRepository.findAll()
                        .stream()
                        .mapToDouble(b -> b.getAmount() == null ? 0 : b.getAmount())
                        .sum();

        model.addAttribute("totalRevenue", totalRevenue);

        return "analytics";
    }
}