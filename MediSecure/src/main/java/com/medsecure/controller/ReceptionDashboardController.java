package com.medsecure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.medsecure.model.Appointment;
import com.medsecure.service.AppointmentService;
import com.medsecure.service.BillService;
import com.medsecure.service.PatientService;
@Controller
public class ReceptionDashboardController {

    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private PatientService patientService;

    @Autowired
    private BillService billService;
    

    @GetMapping("/reception/dashboard")
    public String receptionDashboard(Model model) {

        model.addAttribute(
                "appointments",
                appointmentService.getAllAppointments());

        model.addAttribute(
                "appointmentCount",
                appointmentService.getAllAppointments().size());
        model.addAttribute(
                "pendingCount",
                appointmentService.getPendingCount());

        model.addAttribute(
                "completedCount",
                appointmentService.getCompletedCount());
        Appointment currentToken =
                appointmentService.getCurrentToken();

        model.addAttribute(
                "currentToken",
                currentToken);

        model.addAttribute(
                "criticalCount",
                appointmentService.getCriticalCount());
        model.addAttribute(
                "todayCount",
                appointmentService.getTodayAppointmentsCount());
        model.addAttribute(
                "waitingPatients",
                appointmentService.getWaitingPatients());
         model.addAttribute(
                "currentToken",
                appointmentService.getCurrentToken());
         model.addAttribute(
        	        "opdRunning",
        	        appointmentService.isAnyOPDRunning());

        	model.addAttribute(
        	        "nextPatientId",
        	        appointmentService.getNextWaitingPatientId());
        	model.addAttribute(
        	        "waitingCount",
        	        appointmentService.getWaitingCount());

        	model.addAttribute(
        	        "consultationCount",
        	        appointmentService.getConsultationCount());

        	model.addAttribute(
        	        "completedTodayCount",
        	        appointmentService.getCompletedTodayCount());

        	model.addAttribute(
        	        "completedAppointments",
        	        appointmentService.getCompletedAppointments()
        	                .stream()
        	                .limit(5)
        	                .toList());

        	model.addAttribute(
        	        "nextPatients",
        	        appointmentService.getNextPatients());
        	
         return "reception-dashboard";

    }
//    
    @GetMapping("/completePatient/{id}")
    public String completePatient(@PathVariable Long id) {

        appointmentService.completePatient(id);

        return "redirect:/reception/dashboard";
    }
    @GetMapping("/callNextPatient")
    public String callNextPatient() {

        appointmentService.callNextPatient();

        return "redirect:/reception/dashboard";
    }
   
    
   
    @GetMapping("/reception/reports")
    public String receptionReports(Model model) {

        model.addAttribute(
                "appointmentCount",
                appointmentService.getAllAppointments().size());

        model.addAttribute(
                "completedCount",
                appointmentService.getCompletedCount());

        model.addAttribute(
                "pendingCount",
                appointmentService.getPendingCount());

        model.addAttribute(
                "patientCount",
                patientService.getAllPatients().size());

        model.addAttribute(
                "billCount",
                billService.getBillCount());

        model.addAttribute(
                "appointments",
                appointmentService.getAllAppointments());
        model.addAttribute(
                "completedAppointments",
                appointmentService.getCompletedAppointments());
        model.addAttribute(
                "waitingCount",
                appointmentService.getWaitingCount());

        model.addAttribute(
                "consultationCount",
                appointmentService.getConsultationCount());

        model.addAttribute(
                "completedTodayCount",
                appointmentService.getCompletedTodayCount());
        Appointment nextPatient = null;

        if (!appointmentService.getNextPatients().isEmpty()) {

            nextPatient =
                    appointmentService.getNextPatients().get(0);

        }

        model.addAttribute(
                "nextPatient",
                nextPatient);
        return "reception-reports";
    }
    @GetMapping("/sendToDoctor/{id}")
    public String sendToDoctor(
            @PathVariable Long id){

        appointmentService.sendToDoctor(id);

        return "redirect:/reception/dashboard";
    }
    @GetMapping("/reception/deleteAppointment/{id}")
    public String deleteAppointment(@PathVariable Long id,
                                    RedirectAttributes redirectAttributes) {

        boolean deleted =
                appointmentService.deleteAppointmentByReception(id);

        if (deleted) {
            redirectAttributes.addFlashAttribute(
                    "success",
                    "Appointment deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "Cannot delete an appointment that is in consultation or completed.");
        }

        return "redirect:/reception/dashboard";
    }
    
    
    
  }