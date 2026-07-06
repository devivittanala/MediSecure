
package com.medsecure.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.medsecure.model.Doctor;
import com.medsecure.service.AppointmentService;
import com.medsecure.service.DoctorService;
import com.medsecure.repository.DoctorRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class DoctorController {

    @Autowired
    private DoctorService doctorService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping("/addDoctor")
    public String addDoctorForm(Model model) {

        model.addAttribute("doctor", new Doctor());

        return "add-doctor";
    }

    @PostMapping("/saveDoctor")
    public String saveDoctor(@ModelAttribute Doctor doctor) {

        doctorService.saveDoctor(doctor);

        return "redirect:/doctors";
    }

    @GetMapping("/doctors")
    public String doctors(Model model) {

        model.addAttribute("doctors",
                doctorService.getAllDoctors());

        model.addAttribute("doctorCount",
                doctorService.getDoctorCount());

        model.addAttribute("specializationCount",
                doctorService.getSpecializationCount());

        model.addAttribute("availableDoctors",
                doctorService.getAvailableDoctorCount());

        return "doctors";
    }

    @GetMapping("/searchDoctors")
    public String searchDoctors(
            @RequestParam String keyword,
            Model model) {

        List<Doctor> doctors =
                doctorService.search(keyword);

        model.addAttribute("doctors", doctors);

        model.addAttribute("doctorCount",
                doctors.size());

        model.addAttribute("specializationCount",
                doctors.stream()
                        .map(Doctor::getSpecialization)
                        .distinct()
                        .count());

        model.addAttribute("availableDoctors",
                doctors.stream()
                        .filter(d -> "Available"
                        .equalsIgnoreCase(
                                d.getAvailabilityStatus()))
                        .count());

        return "doctors";
    }

    @GetMapping("/doctor/{id}")
    public String doctorProfile(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "doctor",
                doctorService.getDoctorById(id));

        return "doctor-profile";
    }
    @GetMapping("/deleteDoctor/{id}")
    public String deleteDoctor(@PathVariable Long id) {

        doctorService.deleteDoctor(id);

        return "redirect:/doctors";
    }
    @GetMapping("/editDoctor/{id}")
    public String editDoctor(
            @PathVariable Long id,
            Model model) {

        Doctor doctor = doctorService.getDoctorById(id);

        model.addAttribute("doctor", doctor);

        return "edit-doctor";
    }

    @PostMapping("/updateDoctor")
    public String updateDoctor(
            @ModelAttribute Doctor doctor) {

        doctorService.saveDoctor(doctor);

        return "redirect:/doctors";
    }
    @GetMapping("/doctor/start-opd")
    public String startOPD(HttpSession session){

        String username =
                (String)session.getAttribute("doctorUsername");

        Doctor doctor =
                doctorRepository.findByUsername(username);

        appointmentService.startOPD(
                doctor.getDoctorName());

        return "redirect:/doctor/dashboard";
    }
    @GetMapping("/doctor/stop-opd")
    public String stopOPD(HttpSession session){

        String username =
                (String)session.getAttribute("doctorUsername");

        Doctor doctor =
                doctorRepository.findByUsername(username);

        appointmentService.stopOPD(
                doctor.getDoctorName());

        return "redirect:/doctor/dashboard";
    }
   
   
   
   
}