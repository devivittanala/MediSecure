package com.medsecure.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.medsecure.export.PatientExcelExporter;
import com.medsecure.export.PatientPdfExporter;
import com.medsecure.model.Patient;
import com.medsecure.service.PatientService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/search")
    public String search(@RequestParam String keyword, Model model) {

        model.addAttribute(
                "patients",
                patientService.search(keyword));

        return "patients";
    }

   

        

    
    @GetMapping("/editPatient/{id}")
    public String editPatient(@PathVariable Long id, Model model) {

        Patient patient = patientService.getPatientById(id);

        model.addAttribute("patient", patient);

        return "edit-patient";
    }
    @PostMapping("/updatePatient")
    public String updatePatient(@ModelAttribute Patient patient) {

        patientService.savePatient(patient);

        return "redirect:/patients";
    }
    @PostMapping("/savePatient")
    public String savePatient(@ModelAttribute Patient patient) {
        patientService.savePatient(patient);
        return "redirect:/patients";
    }

    @GetMapping("/patients")
    public String viewPatients(Model model) {

        List<Patient> patients = patientService.getAllPatients();

        long totalPatients = patients.size();

        long activeCases = patients.stream()
                .filter(p ->
                        "Admitted".equalsIgnoreCase(p.getStatus()) ||
                        "Under Treatment".equalsIgnoreCase(p.getStatus()))
                .count();

        long recoveredCases = patients.stream()
                .filter(p ->
                        "Discharged".equalsIgnoreCase(p.getStatus()))
                .count();

        model.addAttribute("patients", patients);
        model.addAttribute("totalPatients", totalPatients);
        model.addAttribute("activeCases", activeCases);
        model.addAttribute("recoveredCases", recoveredCases);

        return "patients";
    }
     
    @GetMapping("/searchPatients")
    public String searchPatients(
            @RequestParam String keyword,
            Model model) {

        model.addAttribute(
                "patients",
                patientService.search(keyword));

        return "patients";
    }
    @GetMapping("/deletePatient/{id}")
    public String deletePatient(
            @PathVariable Long id) {

        patientService.deletePatient(id);

        return "redirect:/patients";
    }
    @GetMapping("/patients/pdf")
    public void exportPdf(
            HttpServletResponse response)
            throws IOException {

        response.setContentType(
                "application/pdf");

        response.setHeader(
                "Content-Disposition",
                "attachment; filename=patients.pdf");

        PatientPdfExporter exporter =
                new PatientPdfExporter(
                        patientService.getAllPatients());

        exporter.export(response);
    }
    @GetMapping("/patients/excel")
    public void exportExcel(
            HttpServletResponse response)
            throws IOException {

        response.setContentType(
                "application/octet-stream");

        response.setHeader(
                "Content-Disposition",
                "attachment; filename=patients.xlsx");

        PatientExcelExporter exporter =
                new PatientExcelExporter(
                        patientService.getAllPatients());

        exporter.export(response);
    }
    @GetMapping("/patient/{id}")
    public String patientProfile(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "patient",
                patientService.getPatientById(id));

        model.addAttribute(
                "backUrl",
                "/patients");

        return "patient-profile";
    }
   
    
   
    @GetMapping("/addPatient")
    public String addPatientForm(Model model) {

        model.addAttribute("patient", new Patient());
        model.addAttribute("dashboardUrl", "/dashboard");

        return "add-patient";
    }
    @GetMapping("/reception/addPatient")
    public String receptionAddPatient(Model model) {

        model.addAttribute("patient", new Patient());

        model.addAttribute(
                "patientsUrl",
                "/reception/patients");

        return "add-patient";
    }
    @GetMapping("/reception/patients")
    public String receptionPatients(Model model) {

        List<Patient> patients =
                patientService.getAllPatients();

        model.addAttribute("patients", patients);

        model.addAttribute(
                "totalPatients",
                patients.size());

        return "reception-patients";
    }
    @PostMapping("/reception/savePatient")
    public String receptionSavePatient(
            @ModelAttribute Patient patient) {

        patientService.savePatient(patient);

        return "redirect:/reception/patients";
    }
    
    @PostMapping("/reception/updatePatient")
    public String receptionUpdatePatient(
            @ModelAttribute Patient patient) {

        patientService.savePatient(patient);

        return "redirect:/reception/patients";
    }
    @GetMapping("/reception/deletePatient/{id}")
    public String receptionDeletePatient(
            @PathVariable Long id) {

        patientService.deletePatient(id);

        return "redirect:/reception/patients";
    }
    @GetMapping("/reception/searchPatients")
    public String receptionSearchPatients(
            @RequestParam String keyword,
            Model model) {

        model.addAttribute(
                "patients",
                patientService.search(keyword));

        return "reception-patients";
    }
    @GetMapping("/reception/editPatient/{id}")
    public String receptionEditPatient(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "patient",
                patientService.getPatientById(id));

        return "edit-patient";
    }
    @GetMapping("/reception/patient/{id}")
    public String receptionPatientProfile(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "patient",
                patientService.getPatientById(id));

        model.addAttribute(
                "backUrl",
                "/reception/patients");

        return "patient-profile";
    }
    
    
    
}